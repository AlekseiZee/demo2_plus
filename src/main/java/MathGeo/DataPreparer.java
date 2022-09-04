package MathGeo;

import com.example.demo2.PageController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

/**
 * Клас, формирующий данные для расчета
 */
public class DataPreparer {

    /**
     * Адрес, нашего файла с прибора
     */
    private String filePath;

    /**
     * Количество стоянок (2, 3 или 4)
     */
    private int quantityOfPoints;

    /**
     * Номер методики съемки (1, 2)
     */
    private int methodNumber = (quantityOfPoints == 2) ? 1 : 2;

    /**
     * Литера угла. v или h (v - вертикальный, h - горизонтальный)
     */
    private String ang;

    /**
     * Литера стоянки (A, B, C, D)
     */
    private String litPoint;

    /**
     * Мапа со значениями, показывающими количество стоянок в каждом направле
     */
    private LinkedHashMap<String, Integer> mapQuantityPointsForEachDirection;

    /**
     * Коллекция со значениями, показывающими количество стоянок в каждом направле
     */
    /*private List<Integer> listQuantityPointsForEachDirection = new ArrayList<>();*/

    /**
     * Коллекция длин ребёр АМС от пояса до пояса на каждом ярусе, мм.
     */
    private List<Double> listRibs = new ArrayList<>();

    /**
     * Коллекция, куда записываются наши данные из файла
     */
    private List<String> inputData = new ArrayList<>();

    /**
     * Конструктор
     *
     * @param filePath                          Наш путь к файлу
     * @param quantityOfPoints                  Количество стоянок (2, 3 или 4)
     * @param mapQuantityPointsForEachDirection Мапа количества стоянок в каждом направле
     * @param listRibs                          Лист с длинами ребер
     */
    public DataPreparer(String filePath, int quantityOfPoints,
                        LinkedHashMap<String, Integer> mapQuantityPointsForEachDirection, List<Double> listRibs) {
        // Задали регулярное выражение и сравниваем с ним введенный нами путь.
        Pattern pattern = Pattern.compile("([a-zA-Z]\\:)\\\\([\\w-]+\\\\)*\\w([\\w-.])+");
        Matcher matcher = pattern.matcher(filePath);
        if (matcher.matches() & matcher != null) {
            this.filePath = filePath;
        } else {
            System.out.println("Вы ввели не верный путь к файлу");
        }
        this.quantityOfPoints = quantityOfPoints;
        this.mapQuantityPointsForEachDirection = mapQuantityPointsForEachDirection;


        if (!listRibs.isEmpty() & quantityOfPoints != 2) {
            this.listRibs = listRibs;
        } else { // лист пустой  и количество стоянок не рабовно 2
            System.out.println("Не заданы ребра АМС");
        }
    }
    public DataPreparer(String filePath, int quantityOfPoints,
                        LinkedHashMap<String, Integer> mapQuantityPointsForEachDirection) {
        // Задали регулярное выражение и сравниваем с ним введенный нами путь.
        Pattern pattern = Pattern.compile("([a-zA-Z]\\:)\\\\([\\w-]+\\\\)*\\w([\\w-.])+");
        Matcher matcher = pattern.matcher(filePath);
        if (matcher.matches() && matcher != null) {
            this.filePath = filePath;
        } else {
            System.out.println("Вы ввели не верный путь к файлу");
        }
        this.quantityOfPoints = quantityOfPoints;
        this.mapQuantityPointsForEachDirection = mapQuantityPointsForEachDirection;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        Pattern pattern = Pattern.compile("([a-zA-Z]\\:)\\\\([\\w-]+\\\\)*\\w([\\w-.])+");
        Matcher matcher = pattern.matcher(filePath);
        if (matcher.matches()) {
            this.filePath = filePath;
        } else {
            System.out.println("Вы ввели не верный путь к файлу");
        }
    }

    // Получение количества стоянок (2, 3 или 4)
    public int getQuantityOfPoints() {
        return quantityOfPoints;
    }

    public void setQuantityOfPoints(int quantityOfPoints) {
        this.quantityOfPoints = quantityOfPoints;
    }

    public int getMethodNumber() {
        return methodNumber;
    }

    public LinkedHashMap<String, Integer> getMapQuantityPointsForEachDirection() {
        return mapQuantityPointsForEachDirection;
    }

    /**
     * Получам лист со значениями мапы
     * @return лист значений количества стоянок по направлениям
     */
    public List<Integer> getNumberOfValuesMapQuantityPointsForEachDirection(HashMap<String, Integer>
                                                                                    mapQuantityPointsForEachDirection) {
        List<Integer> numberOfValuesMapQuantityPointsForEachDirection = new ArrayList<>();
        for (String key : mapQuantityPointsForEachDirection.keySet()){
            numberOfValuesMapQuantityPointsForEachDirection.add(mapQuantityPointsForEachDirection.get(key));
        }
        return numberOfValuesMapQuantityPointsForEachDirection;
    }

    /**
     * Получение листа с ребрами
     * @return
     */
    public List<Double> getListRibs() {
        return listRibs;
    }

    public void setListRibs(Double... ribs) {
        for (Double r : ribs) {
            listRibs.add(r);
        }
    }

    /**
     * Наш основной лист с данными, которые считались из файла
     */
    public List<String> getInputData() {
        return inputData;
    }

    public void setInputData(List<String> inputData) {
        this.inputData = inputData;
    }

    /**
     * Самодельный класс, выбрасывающий исключение
     */
    public class DateIsNotReadyException extends Exception {
        public DateIsNotReadyException(String message) {
            super(message);
        }
    }

    /**
     * Метод, который читает файл и формирует коллекцию данных с прибора.
     */
    public void buildInputData() throws IOException, DateIsNotReadyException {
        BufferedReader buffer = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath)));
        for (int i = 0; i < 5; i++) {
            buffer.readLine();
        }
        while (buffer.ready()) {
            StringBuilder line = new StringBuilder(buffer.readLine()).delete(0, 31);
            String line2 = valueOf(line); // получили строку с нужными значениями
            Scanner scanner = new Scanner(line2); // читаем строку сканером посимвольно
            while (scanner.hasNext()) { // пока есть символы в строке, продолжаем читать
                String symbol = scanner.next();
               /* Character symbolChar = symbol.charAt(0); // возвращает символ, расположенный по указанному индексу
                // Выкидываем символы из данных
                if (Character.isDigit(symbolChar)) {
                    this.inputData.add(symbol);
                }*/
                this.inputData.add(symbol);
            }
            scanner.close();
        }
        buffer.close();

        /**
         * Проверка. Если при расчете получается,что данных либо больше, либо меньше в какой-то из стоянок или данные
         * пусты, метод выбрасывает исключение.
         */
        int count = 0; // счетчик доп. стоянок в направлениях
        for (String key : mapQuantityPointsForEachDirection.keySet()){
            Integer value = mapQuantityPointsForEachDirection.get(key);
            count = count + (value - 1);
        }
        int numberOfAdd = count * 10; // количество лишних значений в листе при доп. стоянках
        boolean isDataRibsCorrect = false;
        if ((quantityOfPoints > 2) && (getNumberOfTiers() != listRibs.size())) {
            isDataRibsCorrect = false;
        } else {
            isDataRibsCorrect = true;
        }
        if ((this.inputData == null) || !isDataRibsCorrect || (((this.inputData.size() - numberOfAdd) % quantityOfPoints) != 0)) {
            this.inputData.clear();
            throw new DateIsNotReadyException("Ошибка в данных. Проверте файл с данными из тахеометра, " +
                    "заданные данные по напрвлениям или количество значений ребер");
        } else {// Все хорошо. Выдаем считаные данные
        }
    }

    /**
     * Метод получения данных при двух стоянках в направлении.
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     */
    public List<List<String>> getListAnglesForTwoPoint(String litPoint) throws DateIsNotReadyException {
        //Обозначение первой стоянки (А1, В1, С1, D1)
        String itemInTheCollectionFirstPoint = litPoint + "1";
        //Обозначение дополнительной стоянки (А2, В2, С2, D2)
        String itemInTheCollectionSecondPoint = litPoint + "2";

        List<List<String>> listAnglesForTwoPoint = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        // Выдергиваем данные о направлении с дополнительной стоянкой.
        // Раскидываем по стоянкам в одном направлении по двум коллекциям
        for (int i = 0; i < inputData.indexOf(itemInTheCollectionSecondPoint); i++) {
            list1.add(inputData.get(inputData.indexOf(itemInTheCollectionFirstPoint) + i));
        }
        for (int j = 0; j < (getTotalAmountOfDataInTheDirection(litPoint) -
                inputData.indexOf(itemInTheCollectionSecondPoint)); j++) {
            list2.add(inputData.get(inputData.indexOf(itemInTheCollectionSecondPoint) + j));
        }
        listAnglesForTwoPoint.add(list1);
        listAnglesForTwoPoint.add(list2);
        return listAnglesForTwoPoint;
    }

    /**
     * Метод, проверяющий, есть ли доп. стоянки в направлении
     *
     * @param litPoint Литера направления
     * @return Возвращает "TRUE", если есть доп. стоянки.
     */
    public boolean isThereAnAdditionalPoint(String litPoint) {
        boolean morePointDirection = false;
        // Смотрим мапу по ключу(litPoint), если значене больше единицы, значит есть доп стоянки
        if (mapQuantityPointsForEachDirection.get(litPoint) > 1) {
            morePointDirection = true;
        }
        else {// Иначе остается false, что значит - нет доп. стоянок в направлении
            }
        return morePointDirection;
    }

    /**
     * Метод получения количества ярусов (количество измерений) из входных данных
     *
     * @return Получаем количество ярусов АМС (количество фланцев)
     */
    public int getNumberOfTiers() {
        if (inputData.isEmpty()) {
            throw new IllegalArgumentException("Входные данные отсутсвуют");
        } else {
            int count = 0; // счетчик
            // Проходим по листу значений мапы и определяем количество добавочных стоянок
            for (Integer value  : getNumberOfValuesMapQuantityPointsForEachDirection(mapQuantityPointsForEachDirection)){
                count = count + (value - 1);
            }
            int numberOfAdd = count * 10; // количество добавочных символов
            //Получаем количество ярусов АМС (количество фланцев)
            int numberOfTiers = ((((inputData.size() - numberOfAdd) - 4 * getQuantityOfPoints()) /
                    getQuantityOfPoints()) / 3) / 2;
            return numberOfTiers;
        }
    }

    /**
     * Метод получения общего количества данных в направлении
     * 2 - лево, право. 3 - три столбца, 4 - количество значений в шапке (литера, расстояние и два угла)
     * getNumberOfMeasurementsInOneDirection - добавочные данные
     */
    public int getTotalAmountOfDataInTheDirection(String litPoint) {
        int totalAmountOfDataInTheDirection;
        if (mapQuantityPointsForEachDirection.get(litPoint) > 1) {
            totalAmountOfDataInTheDirection = getNumberOfTiers() * 2 * 3 + 4 +
                    getNumberOfMeasurementsInOneDirection(litPoint);
        } else {
            totalAmountOfDataInTheDirection = getNumberOfTiers() * 2 * 3 + 4;
        }
        return totalAmountOfDataInTheDirection;
    }

    /**
     * Метод получения количества доп. измерений в одном направлении. Если было две стоянки в одном направлении, то
     * количество измерений увеличивается на одно. При этом прибавляется 10 String значений в листе и так далее.
     *
     * @param litPoint
     * @return Количества доп. измерений в одном направлении
     */
    int getNumberOfMeasurementsInOneDirection(String litPoint) {
        int numberOfMeasurementsInOneDirection = 10 * (mapQuantityPointsForEachDirection.get(litPoint) - 1);
        return numberOfMeasurementsInOneDirection;
    }

    /**
     * Метод получения дистанции по стоянкам.
     *
     * @param litPoint
     */
    double getDistance(String litPoint) throws DateIsNotReadyException {
        double distance = switch (litPoint) {
            case "A", "a" -> Double.parseDouble(inputData.get(inputData.indexOf("A") + 1));
            case "B", "b" -> Double.parseDouble(inputData.get(inputData.indexOf("B") + 1));
            case "C", "c" -> Double.parseDouble(inputData.get(inputData.indexOf("C") + 1));
            case "D", "d" -> Double.parseDouble(inputData.get(inputData.indexOf("D") + 1));
            default -> throw new DateIsNotReadyException("Не задана литера");
        };
        return distance;
    }

    /**
     * Метод получения дистанций для пояса, где две стоянки
     */
    public List<Double> getDistanceForTwoPoints(String litPoint) throws DateIsNotReadyException {
        List<List<String>> ListAnglesForTwoPoint = getListAnglesForTwoPoint(litPoint);
        List<String> list1 = ListAnglesForTwoPoint.get(0);
        List<String> list2 = ListAnglesForTwoPoint.get(1);

        Double distance1 = Double.valueOf(list1.get(1));
        Double distance2 = Double.valueOf(list2.get(1));
        List<Double> distanceForTwoPoints = new ArrayList<>();
        distanceForTwoPoints.add(distance1);
        distanceForTwoPoints.add(distance2);
        return distanceForTwoPoints;
    }

    /**
     * Метод для получения вертикальных или горизонтальных углов любой из стоянок при отсутствии дополнительных
     * измерений в направлении. Средние значения в радианах.
     * задаем параметры: стоянка - "А".."D" и какой нужен угол: "H" или "V".
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @param ang      must be -> h, H, v, V
     * @throws IllegalArgumentException if litPoint and ang another
     * @throws DateIsNotReadyException
     */
    public List<Double> getListAngle(String litPoint, String ang) throws DateIsNotReadyException {

        Pattern pattern = Pattern.compile("[abcdABCD]");
        Matcher matcher = pattern.matcher(litPoint);
        if (matcher.matches()) {
            this.litPoint = litPoint;
        } else {
            throw new IllegalArgumentException("litPoint must be -> A, B, C, D");
        }
        Pattern pattern2 = Pattern.compile("[vVhH]");
        Matcher matcher2 = pattern2.matcher(ang);
        if (matcher2.matches()) {
            this.ang = ang;
        } else {
            throw new IllegalArgumentException("ang must be -> h, H, v, V");
        }
        int angle = switch (ang) {
            case "V", "v" -> 1;
            case "H", "h" -> 2;
            default -> throw new DateIsNotReadyException("Не задана литера");
        };
        //Количество ярусов (проведенных измерений).
        int numberOfTiers = getNumberOfTiers();
        List<Double> listAngles = new ArrayList<>();
        // Получаем индекс первого элемента для формирования списка углов. Берется в зависимости от входных данных
        int j = (inputData.indexOf(litPoint) + 4) + angle; // индекс элемента после A, B, C, D.
        if (angle == 1) {// Означает, что получаем вертикальные углы
            //Нулевой вертикальный угол
            listAngles.add(Math.toRadians(90 - Double.valueOf(inputData.get(inputData.indexOf(litPoint) + 2))));
            for (int i = 0; i < numberOfTiers; i += 1) {
                listAngles.add(Math.toRadians(90 - (Double.parseDouble(inputData.get(j)) +
                        (Double.parseDouble(inputData.get(j + 3)))) / 2));
                j += 6;
            }
            return listAngles;
        } else {
            // Горизонтальные углы
            double firstHorAngl = (Double.parseDouble(inputData.get(j)) +
                    (Double.parseDouble(inputData.get(j + 3)))) / 2;
            listAngles.add(firstHorAngl);
            if ((Double.parseDouble(inputData.get(j - 5))) < 0) {
                for (int i = 1; i < numberOfTiers; i += 1) {
                    listAngles.add(firstHorAngl - ((Double.parseDouble(inputData.get(j + 6)) +
                            Double.parseDouble(inputData.get(j + 9))) / 2));
                    j += 6;
                }
                return listAngles;
            } else {
                for (int i = 1; i < numberOfTiers; i += 1) {
                    listAngles.add(Math.toRadians(((Double.parseDouble(inputData.get(j + 6)) +
                            Double.parseDouble(inputData.get(j + 9))) / 2) - firstHorAngl));
                    j += 6;
                }
                return listAngles;
            }
        }
    }

    /**
     * Метод получения средних углов для пояса, в котором было две стоянки
     *
     * @param litPoint must be -> a,A, b,B, c,C, d,D
     * @param ang      must be -> h, H, v, V
     */
    public List<List<Double>> getAverageAnglesForTwoPoints(String litPoint, String ang) throws DateIsNotReadyException {

        List<List<String>> ListAnglesForTwoPoint = getListAnglesForTwoPoint(litPoint);
        List<String> list1 = ListAnglesForTwoPoint.get(0);
        List<String> list2 = ListAnglesForTwoPoint.get(1);

        int sizeOneList = (list1.size() - 4) / 3 / 2; // Количество измерений в первом
        int sizeTwoList = (list2.size() - 4) / 3 / 2; // Количество измерений во втором
        List<Double> listAverageAnglesForTwoPoints1 = new ArrayList<>();
        List<Double> listAverageAnglesForTwoPoints2 = new ArrayList<>();

        if (ang == "v") {
            // вертикальный угол
            int j = 5;
            // Первый элемент листа, не зависящий от знака расстояния
            listAverageAnglesForTwoPoints1.add(Math.toRadians(90 - Double.valueOf(list1.get(2))));
            for (int i = 0; i < sizeOneList; i += 1) {
                listAverageAnglesForTwoPoints1.add(Math.toRadians(90 - (Double.parseDouble(list1.get(j)) +
                        (Double.parseDouble(list1.get(j + 3)))) / 2));
                j += 6;
            }
            int j2 = 5;
            listAverageAnglesForTwoPoints2.add(Math.toRadians(90 - Double.valueOf(list2.get(2))));
            for (int i = 0; i < sizeTwoList; i += 1) {
                listAverageAnglesForTwoPoints2.add(Math.toRadians(90 - (Double.parseDouble(list2.get(j2)) +
                        (Double.parseDouble(list2.get(j2 + 3)))) / 2));
                j2 += 6;
            }
            List<List<Double>> AverageAnglesForTwoPoints = new ArrayList<>();
            AverageAnglesForTwoPoints.add(listAverageAnglesForTwoPoints1);
            AverageAnglesForTwoPoints.add(listAverageAnglesForTwoPoints2);
            return AverageAnglesForTwoPoints;
        } else {
            // Горизонтальные углы для первой и второй стоянок
            // Угры для первой стоянки. Заполняем первый лист
            int j3 = 6;
            // Первый элемент листа, не зависящий от знака расстояния
            double firstHorAngl1 = (Double.parseDouble(list1.get(j3)) + (Double.parseDouble(list1.get(j3 + 3)))) / 2;
            listAverageAnglesForTwoPoints1.add(firstHorAngl1);
            // Если расстояние отрицательное
            if ((Double.parseDouble(list1.get(j3 - 5))) < 0) {
                for (int i = 1; i < sizeOneList; i += 1) {
                    listAverageAnglesForTwoPoints1.add(Math.toRadians(firstHorAngl1 -
                            ((Double.parseDouble(list1.get(j3 + 6)) +
                                    Double.parseDouble(list1.get(j3 + 9))) / 2)));
                    j3 += 6;
                }
            } else { // Если расстояние положительное
                int j4 = 6;
                for (int i = 1; i < sizeOneList; i += 1) {
                    listAverageAnglesForTwoPoints1.add(Math.toRadians(
                            ((Double.parseDouble(list1.get(j4 + 6)) +
                                    Double.parseDouble(list1.get(j4 + 9))) / 2) - firstHorAngl1));
                    j4 += 6;
                }
            }
            // Угры для второй стоянки. Заполняем второй лист
            int j5 = 6;
            // Первый элемент листа, не зависящий от знака расстояния
            double firstHorAngl2 = (Double.parseDouble(list2.get(j5)) + (Double.parseDouble(list2.get(j5 + 3)))) / 2;
            listAverageAnglesForTwoPoints2.add(firstHorAngl2);
            // Если расстояние отрицательное
            if ((Double.parseDouble(list2.get(j5 - 5))) < 0) {
                for (int i = 1; i < sizeTwoList; i += 1) {
                    listAverageAnglesForTwoPoints2.add(Math.toRadians(firstHorAngl2 -
                            ((Double.parseDouble(list2.get(j5 + 6)) + Double.parseDouble(list2.get(j5 + 9))) / 2)));
                    j5 += 6;
                }
            } else { // Если расстояние положительное
                int j6 = 6;
                for (int i = 1; i < sizeTwoList; i += 1) {
                    listAverageAnglesForTwoPoints2.add(Math.toRadians(((Double.parseDouble(list2.get(j6 + 6)) +
                                    Double.parseDouble(list2.get(j6 + 9))) / 2) - firstHorAngl2));
                    j6 += 6;
                }
            }
            // Объединяем оба листа в одну коллекцию
            List<List<Double>> AverageAnglesForTwoPoints = new ArrayList<>();
            AverageAnglesForTwoPoints.add(listAverageAnglesForTwoPoints1);
            AverageAnglesForTwoPoints.add(listAverageAnglesForTwoPoints2);
            return AverageAnglesForTwoPoints;
        }
    }

    @Override
    public String toString() {
        return "DataPreparer{" +
                "filePath='" + filePath + '\'' +
                ", quantityOfPoints=" + quantityOfPoints +
                ", methodNumber=" + methodNumber +
                ", ang='" + ang + '\'' +
                ", litPoint='" + litPoint + '\'' +
                ", listQuantityPointsForEachDirection=" + mapQuantityPointsForEachDirection +
                ", listRibs=" + listRibs +
                ", inputData=" + inputData +
                '}';
    }
}

