import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Создаем класс "Калькулятор"
public class CalculatorFourBelt extends VerticalityStraightnessCountAble {

    // field
    String axis;
    int border;

    // Конструктор
    public CalculatorFourBelt(DataPreparer dataPreparer) throws IOException {
        super(dataPreparer);
    }

    @Override
    // Метод, считающий проекцию
    public List<Double> getProjection(String litPoint) throws IOException {
        // Переменные метода
        List<String> inputDataList = dataPreparer.getInputData(); // входные данные из файла
        double distance = dataPreparer.getDistance(inputDataList, litPoint); // расстояние
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        List<Double> ribs = dataPreparer.getListRibs(); // лист с ребрами
        List<Double> vertAngles = dataPreparer.getListAngle(inputDataList, litPoint, "v");// вертикальные углы

        List<Double> projections = new ArrayList<>();
        if (distance < 0) {
            projections.add(-1 * distance * Math.cos(vertAngles.get(0)));
            for (int i = 0; i < numberOfMeasurements - 1; i++) {
                projections.add(projections.get(0) - ((ribs.get(0) - ribs.get(i + 1)) / 2) /
                        Math.cos(Math.toRadians(45)));
            }

        } else {
            projections.add(distance * Math.cos(vertAngles.get(0)));
            for (int i = 0; i < numberOfMeasurements - 1; i++) {
                projections.add(projections.get(0) + ((ribs.get(0) - ribs.get(i + 1)) / 2) /
                        Math.cos(Math.toRadians(45)));
            }
        }
        return projections;
    }

    @Override
    // Метод, считающий высоты для каждой стоянки
    public List<Double> getHeights(String litPoint) throws IOException {
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        List<Double> vertAngles = dataPreparer.getListAngle(inputDataList, litPoint, "v"); // вертикальные углы
        List<Double> projections = getProjection(litPoint); // проекции
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений

        List<Double> heights = new ArrayList<>();
        heights.add(projections.get(0) * Math.tan(vertAngles.get(1)));
        for (int i = 0; i < numberOfMeasurements - 1; i++) {
            heights.add(projections.get(i + 1) * Math.tan(vertAngles.get(i + 2)) - heights.get(0));
        }
        return heights;
    }

    @Override
    // Метод, считающий среднюю высоту
    public List<Double> getAverageHeights() throws IOException {
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        int quantityPoint = dataPreparer.getQuantityPoint(inputDataList); // количество стоянок
        Double[][] heights = new Double[quantityPoint][numberOfMeasurements];
        // заполняем высотами двумерный массив
        for (int i = 0; i < quantityPoint; i++) {
            for (int j = 0; j < numberOfMeasurements; j++) {
                switch (i) {
                    case 0 -> {
                        heights[i][j] = getHeights("A").get(j);
                        break;
                    }
                    case 1 -> {
                        heights[i][j] = getHeights("B").get(j);
                        break;
                    }
                    case 2 -> {
                        heights[i][j] = getHeights("C").get(j);
                        break;
                    }
                    case 3 -> {
                        heights[i][j] = getHeights("D").get(j);
                        break;
                    }
                }
            }
        }
        List<Double> averageHeights = new ArrayList<>();
        averageHeights.add(0.00); // начинаем с нуля
        Double sum = 0.0;
        for (int i = 1; i < numberOfMeasurements; i++) {
            for (int j = 0; j < heights.length; j++) {
                sum += heights[j][i];
            }
            averageHeights.add(sum / heights.length);
            sum = 0.0;
        }
        return averageHeights;
    }

    @Override
    // Считаем смещение
    public List<Double> getOffset(String litPoint) throws IOException {
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        List<Double> horizAngles = dataPreparer.getListAngle(inputDataList, litPoint, "h"); // горизонтальные углы
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        List<Double> projections = getProjection(litPoint); // проекции

        List<Double> offset = new ArrayList<>();
        offset.add(0.0); // первое смещение - ноль
        for (int i = 1; i < numberOfMeasurements; i++) {
            offset.add(projections.get(i - 1) * (Math.tan(horizAngles.get(i + 1))));
        }
        return offset;
    }

    @Override
    // Метод, считающий углы смещения
    public List<Double> getOffsetAngles(String litPoint) throws IOException {
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        List<Double> averageHeights = getAverageHeights(); // средние высоты
        List<Double> offset = getOffset(litPoint); // смещение

        List<Double> offsetAngles = new ArrayList<>();
        List<Double> pointOne = new ArrayList<>();
        List<Double> pointTwo = new ArrayList<>();
        for (int i = 0; i < numberOfMeasurements - 2; i++) {
            pointOne.add(Math.tan((offset.get(i + 1) - offset.get(i)) / (averageHeights.get(i + 1) -
                    averageHeights.get(i))));
            pointTwo.add(Math.tan((offset.get(i + 2) - offset.get(i)) / (averageHeights.get(i + 2) -
                    averageHeights.get(i))));
            offsetAngles.add(pointOne.get(i) - pointTwo.get(i));
        }
        offsetAngles.add(0.00);
        return offsetAngles;
    }

    @Override
    //Метод получения прямолинейности пояса
    public List<Double> getStraightness(String litPoint) throws IOException {
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        List<Double> averageHeights = getAverageHeights(); // средние высоты
        List<Double> offset = getOffset(litPoint); // смещение
        List<Double> offsetAngles = getOffsetAngles(litPoint); // угол смещения

        List<Double> straightness = new ArrayList<>();
        straightness.add(0.00);
        for (int i = 0; i < numberOfMeasurements - 2; i++) {
            straightness.add(1000 * (Math.sqrt((Math.pow(averageHeights.get(i + 1) - averageHeights.get(i), 2)) +
                    (Math.pow(offset.get(i + 1) - offset.get(i), 2))) * Math.sin(offsetAngles.get(i))));
        }
        straightness.add(0.00);
        return straightness;
    }
    @Override
    // Мето расчета вертикальности по осям
    //axis must be -> x, X или y, Y
    public List<Double> getVerticality(String axis) throws IOException {
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        int quantityPoint = dataPreparer.getQuantityPoint(inputDataList); // количество стоянок
        Pattern pattern2 = Pattern.compile("[xXyY]");
        Matcher matcher2 = pattern2.matcher(axis);
        if (matcher2.matches()) {
            this.axis = axis;
        } else {
            throw new IllegalArgumentException("axis must be -> x, X или y, Y");
        }

        Double[][] offsets = new Double[quantityPoint][numberOfMeasurements];
        // заполняем смещениями двумерный массив
        for (int i = 0; i < quantityPoint; i++) {
            for (int j = 0; j < numberOfMeasurements; j++) {
                switch (i) {
                    case 0 -> {
                        offsets[i][j] = getOffset("A").get(j);
                        break;
                    }
                    case 1 -> {
                        offsets[i][j] = getOffset("B").get(j);
                        break;
                    }
                    case 2 -> {
                        offsets[i][j] = getOffset("C").get(j);
                        break;
                    }
                    case 3 -> {
                        offsets[i][j] = getOffset("D").get(j);
                        break;
                    }
                }
            }
        }
        List<Double> verticality = new ArrayList<>();
        verticality.add(0.00);
        if (axis.equals("x") || axis.equals("X")) {
            for (int i = 1; i < numberOfMeasurements; i++) {
                verticality.add(1000 * (offsets[1][i] - offsets[3][i]) / 2);
            }
        } else {
            for (int i = 1; i < numberOfMeasurements; i++) {
                verticality.add(1000 * (offsets[0][i] - offsets[2][i]) / 2);
            }
        }
        return verticality;
    }

    @Override
    // Метод, считающий смещение центра
    public List<Double> пуеCenterOffset() throws IOException{
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        List<Double> verticalityAxisX = getVerticality("X");
        List<Double> verticalityAxisY = getVerticality("Y");

        List<Double> centerOffset = new ArrayList<>();
        centerOffset.add(0.00);
        for (int i = 1; i < numberOfMeasurements; i++) {
            centerOffset.add(Math.sqrt((Math.pow(verticalityAxisX.get(i), 2)) +
                    (Math.pow(verticalityAxisY.get(i), 2))));
        }
        return centerOffset;
    }
    @Override
    // Метод, считающий допуск вертикальности 1/1000
    public Double[][] getLineVerticalityborder(int border) throws IOException {
        this.border = border;
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        List<Double> averageHeights = getAverageHeights(); // средние высоты

        Double[][] LineVerticalityborder = new Double[2][12];
        LineVerticalityborder[0][0] = 0.00;
        LineVerticalityborder[1][0] = 0.00;
        for (int j = 1; j < numberOfMeasurements; j++) {
            LineVerticalityborder[0][j] = (1000 / border) * averageHeights.get(j);
            LineVerticalityborder[1][j] = (-1) * (1000 / border) * averageHeights.get(j);
        }
        return LineVerticalityborder;
    }

    @Override
    // Метод, считающий допуск прямолинейности 1/750
    public Double[][] getLineStraightnessBorder() throws IOException{
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        List<Double> averageHeights = getAverageHeights(); // средние высоты

        Double[][] LineStraightnessborder = new Double[2][numberOfMeasurements];
        for (int j = 0; j < numberOfMeasurements - 2; j++) {
            LineStraightnessborder[0][j+1] = (1.333) * (averageHeights.get(j + 2) - averageHeights.get(j));
            LineStraightnessborder[1][j+1] = -1 * (1.333) * (averageHeights.get(j + 2) - averageHeights.get(j));
        }
        LineStraightnessborder[0][0] = LineStraightnessborder[0][1];
        LineStraightnessborder[1][0] = LineStraightnessborder[1][1];
        LineStraightnessborder[0][numberOfMeasurements - 1] = LineStraightnessborder[0][numberOfMeasurements - 2];
        LineStraightnessborder[1][numberOfMeasurements - 1] = LineStraightnessborder[1][numberOfMeasurements - 2];

        return LineStraightnessborder;
    }
    @Override
    // Метод, считающий угол вектора смещения
    public Double[] getAngleDisplacementVector() throws IOException{
        List<String> inputDataList = dataPreparer.getInputData(); // первоначальные данные
        int numberOfMeasurements = dataPreparer.getNumberOfMeasurements(inputDataList); // количество измерений
        List<Double> verticalityX = getVerticality("X");
        List<Double> verticalityY = getVerticality("Y");

        Double[] angleDisplacementVector = new Double[numberOfMeasurements];
        angleDisplacementVector[0] = 0.00;
        angleDisplacementVector[1] = ((180 * (Math.atan(verticalityX.get(1) / verticalityY.get(1)))) / Math.PI) + 90;
        for (int i = 2; i < numberOfMeasurements; i++) {
          angleDisplacementVector[i] = 90 - ((180 * (Math.atan(verticalityX.get(i) / verticalityY.get(i)))) / Math.PI);
        }
        return angleDisplacementVector;
    }
}
