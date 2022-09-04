import java.io.IOException;
import java.util.List;

public class TestCalculator {
    public static void main(String[] args) throws IOException {
        DataPreparer dataAMS = new DataPreparer("d:\\JOB8.txt", 0.461,
                0.461, 0.461, 0.461, 0.461, 0.461, 0.461,
                0.461, 0.461, 0.461, 0.461, 0.461);

        CalculatorFourBelt calculatorFourBelt = new CalculatorFourBelt(dataAMS);
        List<String> inputDataList = dataAMS.getInputData();

        System.out.println(calculatorFourBelt.getVerticality("Y"));



    }
}
