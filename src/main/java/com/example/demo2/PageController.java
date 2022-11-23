package com.example.demo2;

import static MathGeo.TypeAMS.MAST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MathGeo.DataPreparer;
import MathGeo.ShootingOnTheBelts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import persistence.repository.AngleJpaRepository;


public class PageController {

    /**
     * Переменная для адреса файла
     */
    String address;

    /**
     * Лист для количества стоянок в каждом направлении
     */
    private LinkedHashMap<String, Integer> mapQuantityPointsForEachDirection = new LinkedHashMap<>();

    public String getAddress() {
        return address;
    }

    public int getQuantityOfPoints() {
        return quantityOfPoints;
    }

    public List<Double> getListRibs() {
        return listRibs;
    }

    /**
     * Переменная для общего количества стоянок
     */
    private int quantityOfPoints;

    /**
     * Лист для рёбер
     */
    private List<Double> listRibs = new ArrayList<>();

    @FXML
    private RadioButton rb1;

    @FXML
    private RadioButton rb2;

    @FXML
    private ListView<Double> listHeights;

    @FXML
    private ListView<Double> listVerticalityOnThe_X_Axis;

    @FXML
    private ListView<Double> listverticalityOnThe_Y_Axis;

    @FXML
    private ListView<Double> listCalculatedOffset;

    @FXML
    private ListView<Double> listVerticalityBorder;

    @FXML
    private ListView<Double> listAngleDisplacementVector;

    @FXML
    private ListView<Double> listHeightsTwo;

    @FXML
    private ListView<Double> listVerticalityOnThe_X_AxisTwo;

    @FXML
    private ListView<Double> listverticalityOnThe_Y_AxisTwo;

    @FXML
    private ListView<Double> listCalculatedOffsetTwo;

    @FXML
    private ListView<Double> listVerticalityBorderTwo;

    @FXML
    private ListView<Double> listHeightsThree;

    @FXML
    private ListView<Double> listBeltA;

    @FXML
    private ListView<Double> listBeltB;

    @FXML
    private ListView<Double> listBeltC;

    @FXML
    private ListView<Double> listBeltD;

    @FXML
    private ListView<Double> listStraightnessBorder;

    @FXML
    private LineChart<Number, Number> chartX;

    @FXML
    private LineChart<Number, Number> chartY;

    @FXML
    private LineChart<Number, Number> chartVerticalDeviation;

    @FXML
    private LineChart<Number, Number> chartDeviationInPlan;

    @FXML
    private LineChart<Number, Number> chartBeltA;

    @FXML
    private LineChart<Number, Number> chartBeltB;

    @FXML
    private LineChart<Number, Number> chartBeltC;

    @FXML
    private LineChart<Number, Number> chartBeltD;

    @FXML
    public TextArea textPanel;

    @FXML
    private Hyperlink ribs;

    @FXML
    private TextField fieldPath;

    @FXML
    private SplitMenuButton directionOne;

    @FXML
    private SplitMenuButton directionTwo;

    @FXML
    private SplitMenuButton directionFour;

    @FXML
    private SplitMenuButton directionThree;

    @FXML
    private MenuItem litOfPointAOne;

    @FXML
    private MenuItem litOfPointATwo;

    @FXML
    private MenuItem litOfPointAThree;

    @FXML
    private MenuItem litOfPointAFour;

    @FXML
    private MenuItem litOfPointBOne;

    @FXML
    private MenuItem litOfPointBTwo;

    @FXML
    private MenuItem litOfPointBThree;

    @FXML
    private MenuItem litOfPointBFour;

    @FXML
    private MenuItem litOfPointCOne;

    @FXML
    private MenuItem litOfPointCTwo;

    @FXML
    private MenuItem litOfPointCThree;

    @FXML
    private MenuItem litOfPointCFour;

    @FXML
    private MenuItem litOfPointDOne;

    @FXML
    private MenuItem litOfPointDTwo;

    @FXML
    private MenuItem litOfPointDThree;
    @FXML
    private MenuItem litOfPointDFour;
    @FXML
    private SplitMenuButton pointFour;

    @FXML
    private SplitMenuButton pointOne;

    @FXML
    private SplitMenuButton pointThree;

    @FXML
    private SplitMenuButton pointTwo;

    @FXML
    private SplitMenuButton totalPoints;

    @FXML
    private MenuItem aOne;
    @FXML
    private MenuItem aTwo;

    @FXML
    private MenuItem bOne;
    @FXML
    private MenuItem bTwo;

    @FXML
    private MenuItem cZero;
    @FXML
    private MenuItem cOne;
    @FXML
    private MenuItem cTwo;

    @FXML
    private MenuItem dZero;
    @FXML
    private MenuItem dOne;
    @FXML
    private MenuItem dTwo;

    @FXML
    private MenuItem totalOne;

    @FXML
    private MenuItem totalTwo;

    @FXML
    private MenuItem totalThree;

    @FXML
    private void setLetterPointOfFirstDirectionA() {
        directionOne.setText(litOfPointAOne.getText());
    }

    @FXML
    private void setLetterPointOfFirstDirectionB() {
        directionOne.setText(litOfPointBOne.getText());
    }

    @FXML
    private void setLetterPointOfFirstDirectionC() {
        directionOne.setText(litOfPointCOne.getText());
    }

    @FXML
    private void setLetterPointOfFirstDirectionD() {
        directionOne.setText(litOfPointDOne.getText());
    }

    @FXML
    private void setLetterPointOfSecondDirectionA() {
        directionTwo.setText(litOfPointATwo.getText());
    }

    @FXML
    private void setLetterPointOfSecondDirectionB() {
        directionTwo.setText(litOfPointBTwo.getText());
    }

    @FXML
    private void setLetterPointOfSecondDirectionC() {
        directionTwo.setText(litOfPointCTwo.getText());
    }

    @FXML
    private void setLetterPointOfSecondDirectionD() {
        directionTwo.setText(litOfPointDTwo.getText());
    }

    @FXML
    private void setLetterPointOfThirdDirectionA() {
        directionThree.setText(litOfPointAThree.getText());
    }

    @FXML
    private void setLetterPointOfThirdDirectionB() {
        directionThree.setText(litOfPointBThree.getText());
    }

    @FXML
    private void setLetterPointOfThirdDirectionC() {
        directionThree.setText(litOfPointCThree.getText());
    }

    @FXML
    private void setLetterPointOfThirdDirectionD() {
        directionThree.setText(litOfPointDThree.getText());
    }

    @FXML
    private void setLetterPointOfFourthDirectionA() {
        directionFour.setText(litOfPointAFour.getText());
    }

    @FXML
    private void setLetterPointOfFourthDirectionB() {
        directionFour.setText(litOfPointBFour.getText());
    }

    @FXML
    private void setLetterPointOfFourthDirectionC() {
        directionFour.setText(litOfPointCFour.getText());
    }

    @FXML
    private void setLetterPointOfFourthDirectionD() {
        directionFour.setText(litOfPointDFour.getText());
    }

    @FXML
    private void setTextPointOneO() {
        pointOne.setText(aOne.getText());
    }

    @FXML
    private void setTextPointOneT() {
        pointOne.setText(aTwo.getText());
    }

    @FXML
    private void setTextPointTwoO() {
        pointTwo.setText(bOne.getText());
    }

    @FXML
    private void setTextPointTwoT() {
        pointTwo.setText(bTwo.getText());
    }

    @FXML
    private void setTextPointThreeZ() {
        pointThree.setText(cZero.getText());
    }

    @FXML
    private void setTextPointThreeO() {
        pointThree.setText(cOne.getText());
    }

    @FXML
    private void setTextPointThreeT() {
        pointThree.setText(cTwo.getText());
    }

    @FXML
    private void setTextPointFourZ() {
        pointFour.setText(dZero.getText());
    }

    @FXML
    private void setTextPointFourO() {
        pointFour.setText(dOne.getText());
    }

    @FXML
    private void setTextPointFourT() {
        pointFour.setText(dTwo.getText());
    }

    @FXML
    private void setTextTotalOne() {
        totalPoints.setText(totalOne.getText());
    }

    @FXML
    private void setTextTotalTwo() {
        totalPoints.setText(totalTwo.getText());
    }

    @FXML
    private void setTextTotalThree() {
        totalPoints.setText(totalThree.getText());
    }

    /**
     * Считываем путь к файлу
     */
    private void getPath() {
        Pattern pattern = Pattern.compile("([a-zA-Z]\\:)\\\\([\\w-]+\\\\)*\\w([\\w-.])+");
        Matcher matcher = pattern.matcher(fieldPath.getText());
        if (matcher.matches()) {
            this.address = fieldPath.getText();
            fieldPath.setText(this.address);
        } else {
            textPanel.setText("Некорректный путь к файлу" + "\n");
            textPanel.getText();
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке "Рёбра АМС..."
     * Открывает диалоговое окно для ввода/редактирования ребер АМС.
     */
    @FXML
    private void setListRibs() {
        boolean applyClicked = HelloApplication.showRibsEditDialog(listRibs);
        if (applyClicked) {
            this.listRibs = listRibs;
        }
    }

    /**
     * Заполняем мапу с направлениями и количеством стоянок
     */
    private void setQuantityPointsForEachDirection() {
        // Отчищаем мапу
        mapQuantityPointsForEachDirection.clear();
        // Заполняем значениямми
        mapQuantityPointsForEachDirection.put((directionOne.getText()), Integer.parseInt(pointOne.getText()));
        mapQuantityPointsForEachDirection.put((directionTwo.getText()), Integer.parseInt(pointTwo.getText()));
        if (Integer.parseInt(pointThree.getText()) != 0) {
            mapQuantityPointsForEachDirection.put((directionThree.getText()), Integer.parseInt(pointThree.getText()));
            if (Integer.parseInt(pointFour.getText()) != 0) {
                mapQuantityPointsForEachDirection.put((directionFour.getText()), Integer.parseInt(pointFour.getText()));
            }
        }
    }

    /**
     * Общее количество стоянок
     */
    private void setQuantityOfPoints() {
        this.quantityOfPoints = Integer.parseInt(totalPoints.getText());
    }

    /**
     * Считаем вертикальность и прямолинейность
     *
     * @throws IOException
     * @throws DataPreparer.DateIsNotReadyException
     */
    @FXML
    private void geoBuild() throws IOException, DataPreparer.DateIsNotReadyException {
        textPanel.clear();
        // Считываем путь к файлу
        getPath();
        // Заполняем мапу направления и количество стоянок в направлениях
        setQuantityPointsForEachDirection();
        // Инициализируем переменную общее количество стоянок
        setQuantityOfPoints();
        boolean isDataCorrect = false;
        while (isDataCorrect){
            //Проверяем путь к файлу
            if (fieldPath == null) {
                textPanel.setText("Не введен путь к файлу");
                textPanel.getText();
                continue;
            }
            else {
                // Проверяем на корректность количество стоянок
                if (quantityOfPoints != mapQuantityPointsForEachDirection.size()) {
                    textPanel.setText("Не корректны данные по стоянкам");
                    textPanel.getText();
                    continue;
                } else {
                    // Проверяем заполненость листа со значениями ребер
                    if (listRibs.isEmpty() && getQuantityOfPoints() > 2) {
                        textPanel.setText("Не заполнены рёбра АМС");
                        textPanel.getText();
                        continue;
                    } else {
                        isDataCorrect = true;
                    }
                }
            }
        }
        // Создаем класс формирователь входных данных
        DataPreparer dataPreparer;
        if (getQuantityOfPoints() > 2) {
            textPanel.setText("Пусть к файлу: " + address + "\n" + "Рёбра АМС: " + listRibs + "\n" +
                    "Количества стоянок в направлениях: " + mapQuantityPointsForEachDirection + "\n" +
                    "Общее количество стоянок: " + quantityOfPoints + "\n");

            dataPreparer = new DataPreparer(address, this.quantityOfPoints,
                    mapQuantityPointsForEachDirection, this.listRibs);
            try {
                dataPreparer.buildInputData();
            } catch (DataPreparer.DateIsNotReadyException e) {
                textPanel.setText("Не корректны входные данные");
                textPanel.getText();
                //System.out.println(e.getMessage());
            }
        } else {
            textPanel.setText("Пусть к файлу: " + address + "\n" +
                    "Количества стоянок в направлениях: " + mapQuantityPointsForEachDirection + "\n" +
                    "Общее количество стоянок: " + quantityOfPoints + "\n");

            dataPreparer = new DataPreparer(address, this.quantityOfPoints, mapQuantityPointsForEachDirection);
            try {
                dataPreparer.buildInputData();
            } catch (DataPreparer.DateIsNotReadyException e) {
                textPanel.setText("Не корректны входные данные");
                textPanel.getText();
                //System.out.println(e.getMessage());
            }
        }
        dataPreparer.getInputData();
        ShootingOnTheBelts shootingOnBelt = new ShootingOnTheBelts(dataPreparer, MAST);

        /**
         * Средние высоты
         */
        shootingOnBelt.buildAverageHeights();
        List<Double> heights = shootingOnBelt.getAverageHeights();

        /**
         * Вертикальность
         */
        List<Double> verticalityX = shootingOnBelt.getVerticality("X");
        List<Double> verticalityY = shootingOnBelt.getVerticality("Y");

        /**
         * смещение центра
         */
        List<Double> calculatedOffset = shootingOnBelt.getCenterOffset();

        /**
         * угол вектора смещения
         */
        List<Double> angleDisplacementVector = shootingOnBelt.getAngleDisplacementVector();

        /**
         * Допуск вертикальности
         */
        Double[][] lineVerticalityBorder = shootingOnBelt.getLineVerticalityBorder(1000);
        List<Double> verticalityBorder = new ArrayList<>();
        for (
                int i = 0; i < heights.size(); i++) {
            verticalityBorder.add(lineVerticalityBorder[0][i]);
        }

        /**
         * Допуск прямолинейности
         */
        Double[][] lineStraightnessBorder = shootingOnBelt.getLineStraightnessBorder();
        List<Double> straightnessBorder = new ArrayList<>();
        for (
                int i = 0; i < heights.size(); i++) {
            straightnessBorder.add(lineStraightnessBorder[0][i]);
        }

        /**
         * Данные прямолинейности по поясам
         */
        List<Double> straightnessA = shootingOnBelt.getStraightness("A");
        List<Double> straightnessB = shootingOnBelt.getStraightness("B");
        List<Double> straightnessC = shootingOnBelt.getStraightness("C");

        XYChart.Series<Number, Number> seriesVerticalityOnXAxis = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesVerticalityOnYAxis = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesVerticalityBorderRightOnXAxis = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesVerticalityBorderLeftOnXAxis = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesVerticalityBorderRightOnYAxis = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesVerticalityBorderLeftOnYAxis = new XYChart.Series<>();

        XYChart.Series<Number, Number> seriesСalculatedOffset = new XYChart.Series<>();
        XYChart.Series<Number, Number> serieslineVerticalityBorder = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesDeviationInPlan = new XYChart.Series<>();

        XYChart.Series<Number, Number> seriesBeltA = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesBeltB = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesBeltC = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesBeltD = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesStraightnessBorderRightA = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesStraightnessBorderLeftA = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesStraightnessBorderRightB = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesStraightnessBorderLeftB = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesStraightnessBorderRightC = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesStraightnessBorderLeftC = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesStraightnessBorderRightD = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesStraightnessBorderLeftD = new XYChart.Series<>();


        for (
                int i = 0; i < shootingOnBelt.getNumberOfMeasurements(); i++) {
            // График вертикальности по оси Х
            seriesVerticalityOnXAxis.getData().add(new XYChart.Data<>(verticalityX.get(i), heights.get(i)));
            // График веткальности по оси Y
            seriesVerticalityOnYAxis.getData().add(new XYChart.Data<>(verticalityY.get(i), heights.get(i)));
            // Правая линия допуска на "Ось Х"
            seriesVerticalityBorderRightOnXAxis.getData().add(new XYChart.Data<>(lineVerticalityBorder[0][i], heights.get(i)));
            // Левая линия допуска на "Ось Х"
            seriesVerticalityBorderLeftOnXAxis.getData().add(new XYChart.Data<>(lineVerticalityBorder[1][i], heights.get(i)));
            // Правая линия допуска на "Ось Y"
            seriesVerticalityBorderRightOnYAxis.getData().add(new XYChart.Data<>(lineVerticalityBorder[0][i], heights.get(i)));
            // Левая линия допуска на "Ось Y"
            seriesVerticalityBorderLeftOnYAxis.getData().add(new XYChart.Data<>(lineVerticalityBorder[1][i], heights.get(i)));
            // Отклонение вертикали
            seriesСalculatedOffset.getData().add(new XYChart.Data<>(calculatedOffset.get(i), heights.get(i)));
            // Линия допуукска
            serieslineVerticalityBorder.getData().add(new XYChart.Data<>(lineVerticalityBorder[0][i], heights.get(i)));
            // Отклонение в плане
            seriesDeviationInPlan.getData().add(new XYChart.Data<>(verticalityX.get(i), verticalityY.get(i)));
            // Прямолинейности поясов
            seriesBeltA.getData().add(new XYChart.Data<>(straightnessA.get(i), heights.get(i)));
            seriesBeltB.getData().add(new XYChart.Data<>(straightnessB.get(i), heights.get(i)));
            seriesBeltC.getData().add(new XYChart.Data<>(straightnessC.get(i), heights.get(i)));

            seriesStraightnessBorderRightA.getData().add(new XYChart.Data<>(lineStraightnessBorder[0][i],
                    heights.get(i))); // Допуск прямолинейности правая линия
            seriesStraightnessBorderLeftA.getData().add(new XYChart.Data<>(lineStraightnessBorder[1][i],
                    heights.get(i))); // Допуск прямолинейности левая линия

            seriesStraightnessBorderRightB.getData().add(new XYChart.Data<>(lineStraightnessBorder[0][i],
                    heights.get(i))); // Допуск прямолинейности правая линия
            seriesStraightnessBorderLeftB.getData().add(new XYChart.Data<>(lineStraightnessBorder[1][i],
                    heights.get(i))); // Допуск прямолинейности левая линия

            seriesStraightnessBorderRightC.getData().add(new XYChart.Data<>(lineStraightnessBorder[0][i],
                    heights.get(i))); // Допуск прямолинейности правая линия
            seriesStraightnessBorderLeftC.getData().add(new XYChart.Data<>(lineStraightnessBorder[1][i],
                    heights.get(i))); // Допуск прямолинейности левая линия

        }
        //Отчищаем старые значения с графиков
        chartX.getData().setAll();
        chartY.getData().setAll();
        chartVerticalDeviation.getData().setAll();
        chartDeviationInPlan.getData().setAll();
        chartBeltA.getData().setAll();
        chartBeltB.getData().setAll();
        chartBeltC.getData().setAll();
        chartBeltD.getData().setAll();

        // Передаем (новые) данные на наши графики
        chartX.getData().add(seriesVerticalityOnXAxis);
        chartX.getData().add(seriesVerticalityBorderRightOnXAxis);
        chartX.getData().add(seriesVerticalityBorderLeftOnXAxis);
        chartY.getData().add(seriesVerticalityOnYAxis);
        chartY.getData().add(seriesVerticalityBorderRightOnYAxis);
        chartY.getData().add(seriesVerticalityBorderLeftOnYAxis);

        chartVerticalDeviation.getData().add(seriesСalculatedOffset);
        chartVerticalDeviation.getData().add(serieslineVerticalityBorder);

        chartDeviationInPlan.getData().add(seriesDeviationInPlan);

        chartBeltA.getData().add(seriesBeltA);
        chartBeltA.getData().add(seriesStraightnessBorderRightA);
        chartBeltA.getData().add(seriesStraightnessBorderLeftA);
        chartBeltB.getData().add(seriesBeltB);
        chartBeltB.getData().add(seriesStraightnessBorderRightB);
        chartBeltB.getData().add(seriesStraightnessBorderLeftB);
        chartBeltC.getData().add(seriesBeltC);
        chartBeltC.getData().add(seriesStraightnessBorderRightC);
        chartBeltC.getData().add(seriesStraightnessBorderLeftC);

        // Задаем цвета линиям
        seriesVerticalityOnXAxis.getNode().setStyle("-fx-stroke: #03fc0b; ");
        seriesVerticalityOnYAxis.getNode().setStyle("-fx-stroke: #0730fa; ");
        seriesVerticalityBorderRightOnXAxis.getNode().setStyle("-fx-stroke: #fc0320; ");
        seriesVerticalityBorderLeftOnXAxis.getNode().setStyle("-fx-stroke: #fc0320; ");
        seriesVerticalityBorderRightOnYAxis.getNode().setStyle("-fx-stroke: #fc0320; ");
        seriesVerticalityBorderLeftOnYAxis.getNode().setStyle("-fx-stroke: #fc0320; ");

        seriesСalculatedOffset.getNode().setStyle("-fx-stroke: #03fc0b; ");
        serieslineVerticalityBorder.getNode().setStyle("-fx-stroke: #fc0320; ");

        seriesBeltA.getNode().setStyle("-fx-stroke: #03fc0b; ");
        seriesBeltB.getNode().setStyle("-fx-stroke: #03fc0b; ");
        seriesBeltC.getNode().setStyle("-fx-stroke: #03fc0b; ");
        seriesStraightnessBorderRightA.getNode().setStyle("-fx-stroke: #fc0320; ");
        seriesStraightnessBorderLeftA.getNode().setStyle("-fx-stroke: #fc0320; ");
        seriesStraightnessBorderRightB.getNode().setStyle("-fx-stroke: #fc0320; ");
        seriesStraightnessBorderLeftB.getNode().setStyle("-fx-stroke: #fc0320; ");
        seriesStraightnessBorderRightC.getNode().setStyle("-fx-stroke: #fc0320; ");
        seriesStraightnessBorderLeftC.getNode().setStyle("-fx-stroke: #fc0320; ");

        /**
         * Заполняем таблицу во вкладке "СХЕМА ВЕРТИКАЛЬНОСТИ"
         */
        ObservableList<Double> nameHeights = FXCollections.observableArrayList(heights);
        listHeights.setItems(nameHeights);
        listHeights.getItems();

        ObservableList<Double> nameVerticalityOnThe_X_Axis = FXCollections.observableArrayList(verticalityX);
        listVerticalityOnThe_X_Axis.setItems(nameVerticalityOnThe_X_Axis);
        listVerticalityOnThe_X_Axis.getItems();

        ObservableList<Double> nameVerticalityOnThe_Y_Axis = FXCollections.observableArrayList(verticalityY);
        listverticalityOnThe_Y_Axis.setItems(nameVerticalityOnThe_Y_Axis);
        listverticalityOnThe_Y_Axis.getItems();
        // Вычисленное
        ObservableList<Double> nameCalculatedOffset = FXCollections.observableArrayList(calculatedOffset);
        listCalculatedOffset.setItems(nameCalculatedOffset);
        listCalculatedOffset.getItems();
        // Допуск
        ObservableList<Double> nameVerticalityBorder = FXCollections.observableArrayList(verticalityBorder);
        listVerticalityBorder.setItems(nameVerticalityBorder);
        listVerticalityBorder.getItems();
        // Угол разворота
        ObservableList<Double> nameAngleDisplacementVector = FXCollections.observableArrayList(angleDisplacementVector);
        listAngleDisplacementVector.setItems(nameAngleDisplacementVector);
        listAngleDisplacementVector.getItems();

        /**
         * Заполняем таблицу во вкладке "СХЕМА ВЕРТ В ПЛАНЕ"
         */
        ObservableList<Double> nameHeightsTwo = FXCollections.observableArrayList(heights);
        listHeightsTwo.setItems(nameHeightsTwo);
        listHeightsTwo.getItems();

        ObservableList<Double> nameVerticalityOnThe_X_AxisTwo = FXCollections.observableArrayList(verticalityX);
        listVerticalityOnThe_X_AxisTwo.setItems(nameVerticalityOnThe_X_AxisTwo);
        listVerticalityOnThe_X_AxisTwo.getItems();

        ObservableList<Double> nameVerticalityOnThe_Y_AxisTwo = FXCollections.observableArrayList(verticalityY);
        listverticalityOnThe_Y_AxisTwo.setItems(nameVerticalityOnThe_Y_AxisTwo);
        listverticalityOnThe_Y_AxisTwo.getItems();

        ObservableList<Double> nameCalculatedOffsetTwo = FXCollections.observableArrayList(calculatedOffset);
        listCalculatedOffsetTwo.setItems(nameCalculatedOffsetTwo);
        listCalculatedOffsetTwo.getItems();

        ObservableList<Double> nameVerticalityBorderTwo = FXCollections.observableArrayList(verticalityBorder);
        listVerticalityBorderTwo.setItems(nameVerticalityBorderTwo);
        listVerticalityBorderTwo.getItems();

        /**
         * Заполняем таблицу на вкладке "Протокол прямолинейности"
         */
        ObservableList<Double> nameHieghtthree = FXCollections.observableArrayList(heights);
        listHeightsThree.setItems(nameHieghtthree);
        listHeightsThree.getItems();

        ObservableList<Double> nameBeltA = FXCollections.observableArrayList(straightnessA);
        listBeltA.setItems(nameBeltA);
        listBeltA.getItems();

        ObservableList<Double> nameBeltB = FXCollections.observableArrayList(straightnessB);
        listBeltB.setItems(nameBeltB);
        listBeltB.getItems();

        ObservableList<Double> nameBeltC = FXCollections.observableArrayList(straightnessC);
        listBeltC.setItems(nameBeltC);
        listBeltC.getItems();

        ObservableList<Double> nameStraightnessBorder = FXCollections.observableArrayList(straightnessBorder);
        listStraightnessBorder.setItems(nameStraightnessBorder);
        listStraightnessBorder.getItems();

        if (dataPreparer.getQuantityOfPoints() == 4) {
            List<Double> straightnessD = shootingOnBelt.getStraightness("D");
            for (int i = 0; i < shootingOnBelt.getNumberOfMeasurements(); i++) {
                seriesBeltD.getData().add(new XYChart.Data<>(straightnessD.get(i), heights.get(i)));
                seriesStraightnessBorderRightD.getData().add(new XYChart.Data<>(lineStraightnessBorder[0][i],
                        heights.get(i))); // Допуск прямолинейности правая линия
                seriesStraightnessBorderLeftD.getData().add(new XYChart.Data<>(lineStraightnessBorder[1][i],
                        heights.get(i))); // Допуск прямолинейности левая линия
            }
            chartBeltD.getData().add(seriesBeltD);
            chartBeltD.getData().add(seriesStraightnessBorderRightD);
            chartBeltD.getData().add(seriesStraightnessBorderLeftD);

            seriesBeltD.getNode().setStyle("-fx-stroke: #03fc0b; ");
            seriesStraightnessBorderRightD.getNode().setStyle("-fx-stroke: #fc0320; ");
            seriesStraightnessBorderLeftD.getNode().setStyle("-fx-stroke: #fc0320; ");

            ObservableList<Double> nameBeltD = FXCollections.observableArrayList(straightnessD);
            listBeltD.setItems(nameBeltD);
            listBeltD.getItems();
        }
    }
}
