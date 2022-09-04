package MathGeo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Newtest {

    static List<Integer> getNumberOfValuesMapQuantityPointsForEachDirection(HashMap<String, Integer> mapQuantityPointsForEachDirection) {
        List<Integer> numberOfValuesMapQuantityPointsForEachDirection = new ArrayList<>();
        for (String key : mapQuantityPointsForEachDirection.keySet()){
            numberOfValuesMapQuantityPointsForEachDirection.add(mapQuantityPointsForEachDirection.get(key));
        }
        return numberOfValuesMapQuantityPointsForEachDirection;
    }
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> mapQuantityPointsForEachDirection = new LinkedHashMap<>();
        mapQuantityPointsForEachDirection.put("A", 2);
        mapQuantityPointsForEachDirection.put("B", 1);
        mapQuantityPointsForEachDirection.put("C", 1);
        mapQuantityPointsForEachDirection.put("D", 1);

        for (String key : mapQuantityPointsForEachDirection.keySet()){
            Integer value = mapQuantityPointsForEachDirection.get(key);
            System.out.println(key + " --> " + value);
        }

        System.out.println(getNumberOfValuesMapQuantityPointsForEachDirection(mapQuantityPointsForEachDirection));
    }
}
