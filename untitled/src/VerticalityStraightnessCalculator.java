import java.io.IOException;
import java.util.List;

abstract class VerticalityStraightnessCountAble {

    DataPreparer dataPreparer;
    int border;

    public VerticalityStraightnessCountAble(DataPreparer dataPreparer){
        this.dataPreparer = dataPreparer;
    }

    // Метод, считающий проекцию
    public abstract List<Double> getProjection(String litPoint) throws IOException;

    // Метод, считающий высоты для каждой стоянки
    public abstract List<Double> getHeights(String litPoint) throws IOException;

    // Метод, считающий среднюю высоту
    public abstract List<Double> getAverageHeights() throws IOException;

    // Метод, считаем смещение
    public abstract List<Double> getOffset(String litPoint) throws IOException;

    // Метод, считающий углы смещения
    public abstract List<Double> getOffsetAngles(String litPoint) throws IOException;

    //Метод считающий прямолинейность пояса
    public abstract List<Double> getStraightness(String litPoint) throws IOException;

    // Мето считающий вертикальность по осям
    // @param axis must be -> x, X или y, Y
    public abstract List<Double> getVerticality(String axis) throws IOException;

    // Метод, считающий смещение центра
    public abstract List<Double> пуеCenterOffset() throws IOException;

    // Метод, считающий допуск вертикальности 1/1000
    public abstract Double[][] getLineVerticalityborder(int border) throws IOException;

    // Метод, считающий допуск прямолинейности 1/750
    public abstract Double[][] getLineStraightnessBorder() throws IOException;

    // Метод, считающий угол вектора смещения
    public abstract Double[] getAngleDisplacementVector() throws IOException;

/*
    // public abstract List<Double> angleDisplacementVector();
*/
}
