package MathGeo;

import java.io.IOException;
import java.util.List;

public interface VerticalityStraightnessBuildable {

    // Метод, считающий среднюю высоту
    public abstract List<Double> getAverageHeights() throws IOException, DataPreparer.DateIsNotReadyException;

    //Метод считающий прямолинейность пояса
    public abstract List<Double> getStraightness(String litPoint) throws IOException, DataPreparer.DateIsNotReadyException;

    // Мето считающий вертикальность по осям
    // @param axis must be -> x, X или y, Y
    public abstract List<Double> getVerticality(String axis) throws IOException, DataPreparer.DateIsNotReadyException;

    // Метод, считающий смещение центра
    public abstract List<Double> getCenterOffset() throws IOException, DataPreparer.DateIsNotReadyException;

    // Метод, считающий допуск вертикальности 1/1000
    public abstract Double[][] getLineVerticalityBorder(int border) throws IOException, DataPreparer.DateIsNotReadyException;

    // Метод, считающий допуск прямолинейности 1/750
    public abstract Double[][] getLineStraightnessBorder() throws IOException, DataPreparer.DateIsNotReadyException;

    // Метод, считающий угол вектора смещения
    public abstract List<Double> getAngleDisplacementVector() throws IOException, DataPreparer.DateIsNotReadyException;

}
