package MathGeo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static MathGeo.TypeAMS.MAST;

public class Test {
    public static void main(String[] args) throws IOException, DataPreparer.DateIsNotReadyException {
        LinkedHashMap<String, Integer> mapQuantityPointsForEachDirection = new LinkedHashMap<>();
        mapQuantityPointsForEachDirection.put("A", 1);
        mapQuantityPointsForEachDirection.put("B", 1);
//        mapQuantityPointsForEachDirection.put("C", 0);
//        mapQuantityPointsForEachDirection.put("D", 0);

        //System.out.println(mapQuantityPointsForEachDirection.keySet());

        List<Double> listRibs = new ArrayList<>();
        listRibs.add(1.0);
        listRibs.add(1.0);
        listRibs.add(1.0);
        listRibs.add(1.0);
        listRibs.add(1.0);
//        listRibs.add(3.5);
//        listRibs.add(2.6);
//        listRibs.add(1.7);
//        listRibs.add(1.7);

        DataPreparer dataPreparer = new DataPreparer("d:\\JOB1.txt", 2,
                mapQuantityPointsForEachDirection);
        try {
            dataPreparer.buildInputData();
        } catch (DataPreparer.DateIsNotReadyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(dataPreparer.getInputData());
        //System.out.println(dataPreparer.getListAnglesForTwoPoint("A"));
        ShootingOnTheBelts shootingOnBelt = new ShootingOnTheBelts(dataPreparer, MAST);
        System.out.println(dataPreparer.getListAngle("a", "v"));
        System.out.println(dataPreparer.getListAngle("a", "h"));


    }
}

