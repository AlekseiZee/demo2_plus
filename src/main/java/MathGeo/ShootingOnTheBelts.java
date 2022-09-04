package MathGeo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShootingOnTheBelts extends AMS {

    /**
     * Ось "X" или ось "Y"
     */
    private String axis;

    /**
     * Количество измерений
     */
    private int numberOfMeasurements = dataPreparer.getNumberOfTiers();

    /**
     * Количество стоянок
     */
    private int quantityPoint = dataPreparer.getQuantityOfPoints();

    /**
     * Мапа с направлениями и количеством стоянок в них
     */
    private HashMap<String, Integer> mapQuantityPointsForEachDirection =
            dataPreparer.getMapQuantityPointsForEachDirection();

    /**
     * Лист со значаниями мапы
     */
    List<Integer> valuesMapQuantityPointsForEachDirection =
            dataPreparer.getNumberOfValuesMapQuantityPointsForEachDirection(mapQuantityPointsForEachDirection);

    /**
     * Лист с литерами мапы
     */
    List<String> keysMapQuantityPointsForEachDirection = new ArrayList(mapQuantityPointsForEachDirection.keySet());

    /**
     * Средние высоты
     */
    private List<Double> averageHeights = new ArrayList<>();

    public ShootingOnTheBelts(DataPreparer dataPreparer, TypeAMS nameType) {

        super(dataPreparer, nameType);
    }

    /**
     * Получение типа АМС (Башня, мачта или столб)
     *
     * @return
     */
    public TypeAMS getType() {
        return type;
    }

    /**
     * Изменение типа АМС
     *
     * @param type
     */
    public void setType(TypeAMS type) {
        this.type = type;
    }

    /**
     * Получеиние средних высот.
     */
    @Override
    public List<Double> getAverageHeights() {
        return averageHeights;
    }

    /**
     * Получение количества измерений
     *
     * @return
     */
    public int getNumberOfMeasurements() {
        return numberOfMeasurements;
    }

    /**
     * Изменение количества измерений
     *
     * @param numberOfMeasurements
     */
    public void setNumberOfMeasurements(int numberOfMeasurements) {
        this.numberOfMeasurements = numberOfMeasurements;
    }

    /**
     * Получение количества стоянок
     *
     * @return
     */
    public int getQuantityPoint() {
        return quantityPoint;
    }

    /**
     * Изменение количества стоянок
     *
     * @param quantityPoint
     */
    public void setQuantityPoint(int quantityPoint) {
        this.quantityPoint = quantityPoint;
    }

    /**
     * Метод, считающий проекции
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @throws DataPreparer.DateIsNotReadyException
     */
    public List<Double> getProjection(String litPoint) throws DataPreparer.DateIsNotReadyException {
        // Переменные метода
        double distance = dataPreparer.getDistance(litPoint); // расстояние
        List<Double> ribs = dataPreparer.getListRibs(); // лист с ребрами
        List<Double> vertAngles = dataPreparer.getListAngle(litPoint, "v");// вертикальные углы

        List<Double> projections = new ArrayList<>();
        if (distance < 0) {
            projections.add(-1 * distance * Math.cos(vertAngles.get(0)));
            for (int i = 0; i < numberOfMeasurements - 1; i++) {
                if (getQuantityPoint() > 2) {
                    projections.add(projections.get(0) - ((ribs.get(0) - ribs.get(i + 1)) / 2) /
                            Math.cos(Math.toRadians(45)));
                } else {
                    projections.add(projections.get(0));
                }
            }

        } else {
            projections.add(distance * Math.cos(vertAngles.get(0)));
            for (int i = 0; i < numberOfMeasurements - 1; i++) {
                if (getQuantityPoint() > 2) {
                    projections.add(projections.get(0) + ((ribs.get(0) - ribs.get(i + 1)) / 2) /
                            Math.cos(Math.toRadians(45)));
                } else {
                    projections.add(projections.get(0));
                }
            }
        }
        return projections;
    }

    /**
     * Метод считающий проекции с двумя стоянками в поясе
     */
    private List<List<Double>> getProjectionForTwoPoints(String litPoint) throws DataPreparer.DateIsNotReadyException {
        Double distanceFirstPoint = dataPreparer.getDistanceForTwoPoints(litPoint).get(0); //Дистанция первая
        Double distanceSecondPoint = dataPreparer.getDistanceForTwoPoints(litPoint).get(1); //Дистанция вторая

        List<List<String>> ListAnglesForTwoPoint = dataPreparer.getListAnglesForTwoPoint(litPoint);// данные по поясу
        List<String> list1 = ListAnglesForTwoPoint.get(0);
        List<String> list2 = ListAnglesForTwoPoint.get(1);
        int sizeOneList = (list1.size() - 4) / 3 / 2; // Количество измерений в первом
        int sizeTwoList = (list2.size() - 4) / 3 / 2; // Количество измерений во втором
        // ребра для первой стоянки
        List<Double> ribsFirstPoints = new ArrayList<>();
        for (int i = 0; i < sizeOneList; i++) {
            ribsFirstPoints.add(dataPreparer.getListRibs().get(i));
        }
        // ребра для второй стоянки
        List<Double> ribsSecondPoints = new ArrayList<>();
        for (int i = 0; i < sizeTwoList; i++) {
            ribsSecondPoints.add(dataPreparer.getListRibs().get((sizeOneList - 1) + i));
        }
        List<Double> vertAnglesFirstPoint = dataPreparer.getAverageAnglesForTwoPoints(litPoint, "v").get(0);
        List<Double> vertAnglesSecondPoint = dataPreparer.getAverageAnglesForTwoPoints(litPoint, "v").get(1);
        // Создаем коллекции для нашик проекций
        List<Double> projectionsFirstPoint = new ArrayList<>();
        List<Double> projectionsSecondPoint = new ArrayList<>();
        // Проекции для первой стоянки
        if (distanceFirstPoint < 0) {
            projectionsFirstPoint.add(-1 * distanceFirstPoint * Math.cos(vertAnglesFirstPoint.get(0)));
            for (int i = 0; i < sizeOneList - 1; i++) {
                if (getQuantityPoint() > 2) {
                    projectionsFirstPoint.add(projectionsFirstPoint.get(0) - ((ribsFirstPoints.get(0) -
                            ribsFirstPoints.get(i + 1)) / 2) /
                            Math.cos(Math.toRadians(45)));
                } else {
                    projectionsFirstPoint.add(projectionsFirstPoint.get(0));
                }
            }

        } else {
            projectionsFirstPoint.add(distanceFirstPoint * Math.cos(vertAnglesFirstPoint.get(0)));
            for (int i = 0; i < sizeOneList - 1; i++) {
                if (getQuantityPoint() > 2) {
                    projectionsFirstPoint.add(projectionsFirstPoint.get(0) + ((ribsFirstPoints.get(0) -
                            ribsFirstPoints.get(i + 1)) / 2) /
                            Math.cos(Math.toRadians(45)));
                } else {
                    projectionsFirstPoint.add(projectionsFirstPoint.get(0));
                }
            }
        }
        // Проекции для втрой стоянки
        if (distanceSecondPoint < 0) {
            projectionsSecondPoint.add(-1 * distanceSecondPoint * Math.cos(vertAnglesSecondPoint.get(0)));
            for (int i = 0; i < sizeTwoList - 1; i++) {
                if (getQuantityPoint() > 2) {
                    projectionsSecondPoint.add(projectionsSecondPoint.get(0) - ((ribsSecondPoints.get(0) -
                            ribsSecondPoints.get(i + 1)) / 2) /
                            Math.cos(Math.toRadians(45)));
                } else {
                    projectionsFirstPoint.add(projectionsFirstPoint.get(0));
                }
            }
        } else {
            projectionsSecondPoint.add(distanceSecondPoint * Math.cos(vertAnglesSecondPoint.get(0)));
            for (int i = 0; i < sizeTwoList - 1; i++) {
                if (getQuantityPoint() > 2) {
                    projectionsSecondPoint.add(projectionsSecondPoint.get(0) + ((ribsSecondPoints.get(0) -
                            ribsSecondPoints.get(i + 1)) / 2) /
                            Math.cos(Math.toRadians(45)));
                } else {
                    projectionsFirstPoint.add(projectionsFirstPoint.get(0));
                }
            }
        }
        List<List<Double>> projectionForTwoPoints = new ArrayList<>();
        projectionForTwoPoints.add(projectionsFirstPoint);
        projectionForTwoPoints.add(projectionsSecondPoint);
        return projectionForTwoPoints;
    }

    /**
     * Метод, считающий высоты для каждого пояса, если в поясах нет доп. стоянок.
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @throws DataPreparer.DateIsNotReadyException
     */
    private List<Double> getHeights(String litPoint) throws DataPreparer.DateIsNotReadyException {

        List<Double> vertAngles = dataPreparer.getListAngle(litPoint, "v"); // вертикальные углы
        List<Double> projections = getProjection(litPoint); // проекции

        List<Double> heights = new ArrayList<>();
        heights.add(projections.get(0) * Math.tan(vertAngles.get(1)));
        for (int i = 0; i < numberOfMeasurements - 1; i++) {
            heights.add(projections.get(i + 1) * Math.tan(vertAngles.get(i + 2)) - heights.get(0));
        }
        return heights;
    }

    /**
     * Метод, считающий высоты для пояса, в котором было две стоянки.
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @throws DataPreparer.DateIsNotReadyException
     */
    public List<List<Double>> getHeightsForTwoPoints(String litPoint) throws DataPreparer.DateIsNotReadyException {
        List<Double> vertAnglesFirstPoint = dataPreparer.getAverageAnglesForTwoPoints(litPoint, "v").get(0);
        List<Double> vertAnglesSecondPoint = dataPreparer.getAverageAnglesForTwoPoints(litPoint, "v").get(1);

        List<Double> projectionsFirstPoint = getProjectionForTwoPoints(litPoint).get(0); // проекции
        List<Double> projectionsSecondPoint = getProjectionForTwoPoints(litPoint).get(1); // проекции
        List<List<String>> ListAnglesForTwoPoint = dataPreparer.getListAnglesForTwoPoint(litPoint);// данные по поясу
        List<String> list1 = ListAnglesForTwoPoint.get(0);
        List<String> list2 = ListAnglesForTwoPoint.get(1);

        int sizeOneList = (list1.size() - 4) / 3 / 2; // Количество измерений в первом
        int sizeTwoList = (list2.size() - 4) / 3 / 2; // Количество измерений во втором

        List<Double> heightsFirstPoint = new ArrayList<>();
        List<Double> heightsSecondPoint = new ArrayList<>();
        heightsFirstPoint.add(projectionsFirstPoint.get(0) * Math.tan(vertAnglesFirstPoint.get(1)));
        for (int i = 0; i < sizeOneList - 1; i++) {
            heightsFirstPoint.add(projectionsFirstPoint.get(i + 1) * Math.tan(vertAnglesFirstPoint.get(i + 2)) -
                    heightsFirstPoint.get(0));
        }
        heightsSecondPoint.add(projectionsSecondPoint.get(0) * Math.tan(vertAnglesSecondPoint.get(1)));
        for (int i = 0; i < sizeTwoList - 1; i++) {
            heightsSecondPoint.add(projectionsSecondPoint.get(i + 1) * Math.tan(vertAnglesSecondPoint.get(i + 2)));
        }
        List<List<Double>> heightsForTwoPoints = new ArrayList<>();
        heightsForTwoPoints.add(heightsFirstPoint);
        heightsForTwoPoints.add(heightsSecondPoint);
        return heightsForTwoPoints;
    }

    /**
     * Метод объединения высот в один лист при двух стоянка в направлении. При этом последнее
     * значение первой стоянки объединяется с первым значением второй стоянки. На выходе получаем стандартную коллекцию
     * с нужным количеством высот.
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @throws DataPreparer.DateIsNotReadyException
     */
    private List<Double> getHeightsForPointWithTheAddition(String litPoint) throws DataPreparer.DateIsNotReadyException {
        List<List<Double>> heightsForTwoPoints = getHeightsForTwoPoints(litPoint);
        List<Double> heights = new ArrayList<>();
        heights.addAll(heightsForTwoPoints.get(0));
        List<Double> heightTwo = heightsForTwoPoints.get(1);
        heightTwo.remove(0);
        heights.addAll(heightTwo);
        return heights;
    }

    /**
     * Метод, считающий среднюю высоту
     *
     * @throws DataPreparer.DateIsNotReadyException
     */
    public void buildAverageHeights() throws DataPreparer.DateIsNotReadyException {
        Double[][] heights = new Double[quantityPoint][numberOfMeasurements];
        // заполняем высотами двумерный массив
        for (int i = 0; i < mapQuantityPointsForEachDirection.size(); i++) {
            for (int j = 0; j < numberOfMeasurements; j++) {
                if (valuesMapQuantityPointsForEachDirection.get(i) > 1) {
                    List<Double> heightsForPoint =
                            getHeightsForPointWithTheAddition(keysMapQuantityPointsForEachDirection.get(i));
                    heights[i][j] = heightsForPoint.get(j);
                } else {
                    heights[i][j] = getHeights(keysMapQuantityPointsForEachDirection.get(i)).get(j);
                }
            }
        }
        List<Double> intermediateResultAverageHeights = new ArrayList<>();
        intermediateResultAverageHeights.add(0.00); // начинаем с нуля
        Double sum = 0.0;
        for (int i = 1; i < numberOfMeasurements; i++) {
            for (int j = 0; j < heights.length; j++) {
                sum += heights[j][i];
            }
            intermediateResultAverageHeights.add(sum / heights.length);
            sum = 0.0;
        }
        this.averageHeights = setValueOfDecimalPlaces(intermediateResultAverageHeights, 1);
    }

    /**
     * Метод, считающий смещения, если одна стоянка в поясе.
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @throws DataPreparer.DateIsNotReadyException
     */
    private List<Double> getOffset(String litPoint) throws DataPreparer.DateIsNotReadyException {
        List<Double> horizAngles = dataPreparer.getListAngle(litPoint, "h"); // горизонтальные углы
        List<Double> projections = getProjection(litPoint); // проекции
        List<Double> offset = new ArrayList<>();
        offset.add(0.0); // первое смещение - ноль
        for (int i = 0; i < numberOfMeasurements - 1; i++) {
            offset.add(projections.get(i) * (Math.tan(horizAngles.get(i + 1))));
        }
        return offset;
    }

    /**
     * Метод, считающий смещения, если две стоянки в поясе.
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @throws DataPreparer.DateIsNotReadyException
     */
    private List<Double> getOffsetForTwoPoints(String litPoint) throws DataPreparer.DateIsNotReadyException {
        List<List<String>> ListAnglesForTwoPoint = dataPreparer.getListAnglesForTwoPoint(litPoint);// данные по поясу
        List<String> list1 = ListAnglesForTwoPoint.get(0);
        List<String> list2 = ListAnglesForTwoPoint.get(1);

        int sizeOneList = (list1.size() - 4) / 3 / 2; // Количество измерений в первом
        int sizeTwoList = (list2.size() - 4) / 3 / 2; // Количество измерений во втором
        // горизонтальные углы
        List<Double> horizAnglesFirstPoint = dataPreparer.getAverageAnglesForTwoPoints(litPoint, "h").get(0);
        List<Double> horizAnglesSecondtPoint = dataPreparer.getAverageAnglesForTwoPoints(litPoint, "h").get(1);

        List<Double> projectionsFirstPoint = getProjectionForTwoPoints(litPoint).get(0); // проекции
        List<Double> projectionsSecondPoint = getProjectionForTwoPoints(litPoint).get(1); // проекции

        List<Double> offsetFirst = new ArrayList<>();
        List<Double> offsetSecond = new ArrayList<>();
        offsetFirst.add(0.0); // первое смещение - ноль
        for (int i = 1; i < sizeOneList; i++) {
            offsetFirst.add(projectionsFirstPoint.get(i - 1) * (Math.tan(horizAnglesFirstPoint.get(i))));
        }
        for (int i = 1; i < sizeTwoList; i++) {
            offsetSecond.add(projectionsSecondPoint.get(i - 1) * (Math.tan(horizAnglesSecondtPoint.get(i))));
        }

        ArrayList<Double> offsetForBeltThisTwoPoints = new ArrayList<>();
        offsetForBeltThisTwoPoints.addAll(offsetFirst);
        offsetForBeltThisTwoPoints.addAll(offsetSecond);

        return offsetForBeltThisTwoPoints;
    }

    /**
     * Метод, считающий углы смещения.
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @throws DataPreparer.DateIsNotReadyException
     */
    private List<Double> getOffsetAngles(String litPoint) throws DataPreparer.DateIsNotReadyException {
        List<Double> offset; // смещение
        if (dataPreparer.isThereAnAdditionalPoint(litPoint)) {
            offset = getOffsetForTwoPoints(litPoint);
        } else {
            offset = getOffset(litPoint);
        }
        List<Double> offsetAngles = new ArrayList<>();
        List<Double> pointOne = new ArrayList<>();
        List<Double> pointTwo = new ArrayList<>();
        for (int i = 0; i < numberOfMeasurements - 2; i++) {
            pointOne.add(Math.tan((offset.get(i + 1) - offset.get(i)) / (averageHeights.get(i + 1) -
                    averageHeights.get(i))));
            pointTwo.add(Math.tan((offset.get(i + 2) - offset.get(i)) / (averageHeights.get(i + 2) -
                    averageHeights.get(i))));
            if (pointOne.get(i) > (pointTwo.get(i))) {
                offsetAngles.add(Math.abs(
                        (Math.atan((averageHeights.get(i + 2) - averageHeights.get(i)) /
                                (offset.get(i + 2) - offset.get(i)))) -
                                (Math.atan((averageHeights.get(i + 1) - averageHeights.get(i)) /
                                        (offset.get(i + 1) - offset.get(i))))
                ));
            } else {
                offsetAngles.add(-1 * (Math.abs(
                        (Math.atan((averageHeights.get(i + 2) - averageHeights.get(i)) /
                                (offset.get(i + 2) - offset.get(i)))) -
                                (Math.atan((averageHeights.get(i + 1) - averageHeights.get(i)) /
                                        (offset.get(i + 1) - offset.get(i))))
                )));
            }
        }
        offsetAngles.add(0.00);
        return offsetAngles;
    }

    /**
     * Метод считающий прямолинейность пояса.
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @throws DataPreparer.DateIsNotReadyException
     */
    @Override
    public List<Double> getStraightness(String litPoint) throws DataPreparer.DateIsNotReadyException {
        List<Double> offset; // смещение
        if (dataPreparer.isThereAnAdditionalPoint(litPoint)) {
            offset = getOffsetForTwoPoints(litPoint);
        } else {
            offset = getOffset(litPoint);
        }
        List<Double> offsetAngles = getOffsetAngles(litPoint); // угол смещения
        List<Double> intermediateValueStraightness = new ArrayList<>();
        intermediateValueStraightness.add(0.00);
        for (int i = 0; i < numberOfMeasurements - 2; i++) {
            intermediateValueStraightness.add(1000 * (Math.sqrt((Math.pow((averageHeights.get(i + 1) - averageHeights.get(i)), 2)) +
                    (Math.pow((offset.get(i + 1) - offset.get(i)), 2))) * Math.sin(offsetAngles.get(i))));
        }
        List<Double> straightness = setValueOfDecimalPlaces(intermediateValueStraightness, 1);
        straightness.add(0.00);
        return straightness;
    }

    /**
     * Метод, считающий вертикальность по осям (Х, Y). Операется на данные находящиеся в мапе
     *
     * @param axis must be -> x, X или y, Y
     * @throws DataPreparer.DateIsNotReadyException
     */
    @Override
    public List<Double> getVerticality(String axis) throws DataPreparer.DateIsNotReadyException {
        int quantityPoint = getQuantityPoint(); // количество стоянок
        Pattern pattern2 = Pattern.compile("[xXyY]");
        Matcher matcher2 = pattern2.matcher(axis);
        if (matcher2.matches()) {
            this.axis = axis;
        } else {
            throw new IllegalArgumentException("axis must be -> x, X или y, Y");
        }
        Double[][] offsets = new Double[quantityPoint][numberOfMeasurements];
        // заполняем смещениями двумерный массив
        for (int i = 0; i < mapQuantityPointsForEachDirection.size(); i++) {
            for (int j = 0; j < numberOfMeasurements; j++) {
                if (valuesMapQuantityPointsForEachDirection.get(i) > 1) {
                    offsets[i][j] = getOffsetForTwoPoints(keysMapQuantityPointsForEachDirection.get(i)).get(j);
                } else {
                    offsets[i][j] = getOffset(keysMapQuantityPointsForEachDirection.get(i)).get(j);
                }
            }
        }
        List<Double> intermediateResultVerticalities = new ArrayList<>();
        intermediateResultVerticalities.add(0.00);
        switch (dataPreparer.getQuantityOfPoints()) {
            case 4 -> {
                if (axis.equals("x") || axis.equals("X")) {
                    for (int i = 1; i < numberOfMeasurements; i++) {
                        intermediateResultVerticalities.add(1000 * (offsets[1][i] - offsets[3][i]) / 2);
                    }
                } else {
                    for (int i = 1; i < numberOfMeasurements; i++) {
                        intermediateResultVerticalities.add(1000 * (offsets[0][i] - offsets[2][i]) / 2);
                    }
                }
                break;
            }
            case 3 -> {
                if (axis.equals("x") || axis.equals("X")) {
                    for (int i = 1; i < numberOfMeasurements; i++) {
                        intermediateResultVerticalities.add(1000 * (offsets[1][i] - offsets[2][i]) / Math.sqrt(3));
                    }
                } else {
                    for (int i = 1; i < numberOfMeasurements; i++) {
                        intermediateResultVerticalities.add(1000 * (2 * offsets[0][i] - offsets[1][i] - offsets[2][i]) / 3);
                    }
                }
                break;
            }
            case 2 -> {
                if (axis.equals("x") || axis.equals("X")) {
                    for (int i = 1; i < numberOfMeasurements; i++) {
                        intermediateResultVerticalities.add(1000 * (offsets[0][i]));
                    }
                } else {
                    for (int i = 1; i < numberOfMeasurements; i++) {
                        intermediateResultVerticalities.add(1000 * (offsets[1][i]));
                    }
                }
                break;
            }
            default -> throw new IllegalArgumentException("Ошибка в количестве стоянок");
        }
        List<Double> verticality = setValueOfDecimalPlaces(intermediateResultVerticalities, 1);
        return verticality;
    }

    /**
     * Метод, считающий смещение центра
     *
     * @throws DataPreparer.DateIsNotReadyException
     */
    @Override
    public List<Double> getCenterOffset() throws DataPreparer.DateIsNotReadyException {
        List<Double> verticalityAxisX = getVerticality("X");
        List<Double> verticalityAxisY = getVerticality("Y");
        List<Double> intermediateValueCenterOffset = new ArrayList<>();
        intermediateValueCenterOffset.add(0.00);
        for (int i = 1; i < numberOfMeasurements; i++) {
            intermediateValueCenterOffset.add(Math.sqrt((Math.pow(verticalityAxisX.get(i), 2)) +
                    (Math.pow(verticalityAxisY.get(i), 2))));
        }

        List<Double> centerOffset = setValueOfDecimalPlaces(intermediateValueCenterOffset, 1);
        return centerOffset;
    }

    /**
     * Метод, считающий допуск вертикальности. Допуск задается, например 1/1000
     * Допуск(1/1000 - Башня; 1/1500 - Мачта)
     *
     * @return
     */
    @Override
    public Double[][] getLineVerticalityBorder(int border) {
        Double[][] LineVerticalityborder = new Double[2][numberOfMeasurements];
        double scale = Math.pow(10, 1);
        LineVerticalityborder[0][0] = 0.00;
        LineVerticalityborder[1][0] = 0.00;
        for (int j = 1; j < numberOfMeasurements; j++) {
            Double verticalityBorder = ((double) 1000 / (double) border) * averageHeights.get(j);
            LineVerticalityborder[0][j] = Math.ceil(scale * verticalityBorder) / scale;
            LineVerticalityborder[1][j] = -1 * Math.ceil(scale * verticalityBorder) / scale;
        }
        return LineVerticalityborder;
    }

    /**
     * Метод, считающий допуск прямолинейности. Поумолчанию - 1/750.
     */
    @Override
    public Double[][] getLineStraightnessBorder() {
        Double[][] LineStraightnessborder = new Double[2][numberOfMeasurements];
        double scale = Math.pow(10, 1);
        for (int j = 0; j < numberOfMeasurements - 2; j++) {
            Double straightnessBorder = (1.333) * (averageHeights.get(j + 2) - averageHeights.get(j));
            LineStraightnessborder[0][j + 1] = Math.ceil(scale * straightnessBorder) / scale;
            LineStraightnessborder[1][j + 1] = -1 * Math.ceil(scale * straightnessBorder) / scale;
        }
        LineStraightnessborder[0][0] = LineStraightnessborder[0][1];
        LineStraightnessborder[1][0] = LineStraightnessborder[1][1];
        LineStraightnessborder[0][numberOfMeasurements - 1] = LineStraightnessborder[0][numberOfMeasurements - 2];
        LineStraightnessborder[1][numberOfMeasurements - 1] = LineStraightnessborder[1][numberOfMeasurements - 2];
        return LineStraightnessborder;
    }

    /**
     * Метод, считающий угол вектора смещения.
     *
     * @throws DataPreparer.DateIsNotReadyException
     */
    @Override
    public List<Double> getAngleDisplacementVector() throws DataPreparer.DateIsNotReadyException {
        List<Double> verticalityX = getVerticality("X");
        List<Double> verticalityY = getVerticality("Y");
        List<Double> intermediateResultAngleDisplacementVector = new ArrayList<>();
        intermediateResultAngleDisplacementVector.add(0.00);
        intermediateResultAngleDisplacementVector.add(((180 * (Math.atan(verticalityX.get(1) /
                verticalityY.get(1)))) / Math.PI) + 90);
        for (int i = 2; i < numberOfMeasurements; i++) {
            intermediateResultAngleDisplacementVector.add(90 - ((180 * (Math.atan(verticalityX.get(i) /
                    verticalityY.get(i)))) / Math.PI));
        }
        List<Double> angleDisplacementVector = setValueOfDecimalPlaces(
                intermediateResultAngleDisplacementVector, 2);
        return angleDisplacementVector;
    }

    /**
     * Метод, регулирует количество знаков после запятой
     *
     * @param intermediateValues Коллекция значений, которую нужно обработать
     * @param numberOfCharacters Количество знаков после запятой
     */
    private List<Double> setValueOfDecimalPlaces(List<Double> intermediateValues, int numberOfCharacters) {
        List<Double> values = new ArrayList<>();
        for (Double d : intermediateValues) {
            double scale = Math.pow(10, numberOfCharacters);
            double result = Math.ceil(d * scale) / scale;
            values.add(result);
        }
        return values;
    }
}




