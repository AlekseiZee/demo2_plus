import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

// Клас, формирующий данные для расчета
public class DataPreparer {

    String filePath; // Адрес, нашего файла с прибора
    String ang; // h или v. Вертикальный или горизонтальный
    String litPoint; // A, B, C, D
    List<Double> listRibs = new ArrayList<>();

    public DataPreparer(String filePath, Double... ribs) {
        // Задали регулярное выражение и сравниваем с ним введенный нами путь.
        Pattern pattern1 = Pattern.compile("([a-zA-Z]\\:)\\\\([\\w-]+\\\\)*\\w([\\w-.])+");
        Matcher matcher1 = pattern1.matcher(filePath);
        if (matcher1.matches()) {
            this.filePath = filePath;
        } else {
            System.out.println("Вы ввели не верный путь к файлу");
        }
        for (Double r : ribs) {
            listRibs.add(r);
        }
    }

    public List<Double> getListRibs() {
        return listRibs;
    }

    public void setListRibs(Double... ribs) {
        for (Double r : ribs) {
            listRibs.add(r);
        }
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

    // Метод для считывания данных из файла
    List<String> getInputData() throws IOException {
        BufferedReader buffer = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath)));
        for (int i = 0; i < 5; i++) {
            buffer.readLine();
        }
        List<String> value1 = new ArrayList<>();
        List<String> value2 = new ArrayList<>();
        while (buffer.ready()) {
            StringBuilder line = new StringBuilder(buffer.readLine()).delete(0, 31);
            String line2 = valueOf(line); // получили строку с нужными значениями
            Scanner scanner = new Scanner(line2); // читаем строку сканером посимвольно
            while (scanner.hasNext()) { // пока есть символы в строке, продолжаем читать
                String symbol = scanner.next();
                Character symbolChar = symbol.charAt(0);
                // Сортируем. Стоянки (символы А, В, С, D) в одну коллекцию, углы (вместе с расстоянием) в другую.
                if (Character.isLetter(symbolChar)) {
                    value1.add(symbol);
                } else {
                    value2.add(symbol);
                }
            }
            scanner.close();
        }
        buffer.close();
        // Прибавляем последним значением число стоянок
        value2.add(String.valueOf(value1.size()));
        return value2;
    }

    // Метод получения количества стоянок
    int getQuantityPoint(List<String> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Входные данные отсутсвуют");
        } else {
            int quantityPoint = Integer.parseInt(data.get(data.size() - 1));
            return quantityPoint;
        }
    }

    // Метод для получения количества измерений
    int getNumberOfMeasurements(List<String> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Входные данные отсутсвуют");
        } else {
            int numberOfMeasurements = ((((data.size() - 1) - 3 * getQuantityPoint(data)) /
                    getQuantityPoint(data)) / 3) / 2;
            return numberOfMeasurements;
        }
    }

    //Метод получения дистанции по стоянкам
    double getDistance(List<String> data, String litPoint){
        double distance = switch (litPoint) {
            case "A", "a" -> Double.parseDouble(data.get(data.indexOf("101") - 3));
            case "B", "b" -> Double.parseDouble(data.get(data.indexOf("201") - 3));
            case "C", "c" -> Double.parseDouble(data.get(data.indexOf("301") - 3));
            case "D", "d" -> Double.parseDouble(data.get(data.indexOf("401") - 3));
            default -> 0;
        };
        return distance;
    }

    // Метод для получения вертикальных или горизонтальных углов любой из стоянок. Средние значения в радианах.
    // задаем параметры: стоянка - "А".."D" и какой нужен угол: "H" или "V".
    // @param litPoint must be -> a,A, b,B, c,C, d,D. ang must be -> h, H, v, V
    // @throws IllegalArgumentException if litPoint and ang another
    public List<Double> getListAngle(List<String> data, String litPoint, String ang) throws IOException {

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

        String value = switch (litPoint) {
            case "A", "a" -> "101";
            case "B", "b" -> "201";
            case "C", "c" -> "301";
            case "D", "d" -> "401";
            default ->"0";
        };

        int angle = switch (ang) {
            case "V", "v" -> 1;
            case "H", "h" -> 2;
            default -> 0;
        };

        List<Double> listAngles = new ArrayList<>();
        //Получаем количество проведенных измерений.
        int numberOfMeasurements = getNumberOfMeasurements(data);
        // Получаем индекс первого элемента для формирования списка углов. Берется в зависимости от входных данных
        int j = data.indexOf(value) + angle; // индекс элемента после 101, 201, 301, ....
        if (angle == 1) {
            listAngles.add(Math.toRadians(90 - Double.valueOf(data.get(data.indexOf(value) - 2)))); //Нулевой вертикальный угол
            for (int i = 0; i < numberOfMeasurements; i += 1) {
                listAngles.add(Math.toRadians(90 - (Double.parseDouble(data.get(j)) +
                        (Double.parseDouble(data.get(j + 3)))) / 2));
                j += 6;
            }
            return listAngles;
        } else {
            listAngles.add(Double.valueOf(data.get(data.indexOf(value) - 1))); // Первый горизонтальный угол (сред)
            double firstHorAngl = (Double.parseDouble(data.get(j)) +
                    (Double.parseDouble(data.get(j + 3)))) / 2;
            listAngles.add(firstHorAngl);
            if ((Double.parseDouble(data.get(j - 5))) < 0) {
                for (int i = 1; i < numberOfMeasurements; i += 1) {
                    listAngles.add(firstHorAngl - ((Double.parseDouble(data.get(j + 6)) +
                            Double.parseDouble(data.get(j + 9))) / 2));
                    j += 6;
                }
                return listAngles;
            } else {
                for (int i = 1; i < numberOfMeasurements; i += 1) {
                    listAngles.add(Math.toRadians(((Double.parseDouble(data.get(j + 6)) +
                            Double.parseDouble(data.get(j + 9))) / 2) - firstHorAngl));
                    j += 6;
                }
                return listAngles;
            }
        }

    }
}