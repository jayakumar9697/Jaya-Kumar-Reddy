import java.util.*;

public class ShamirSecretSharing {

   
    private static long decodeYValue(String base, String value) {
        int baseInt = Integer.parseInt(base);
        return Long.parseLong(value, baseInt);
    }

    public static List<long[]> parsePoints(Map<String, Map<String, String>> jsonObject) {
        List<long[]> points = new ArrayList<>();
        Map<String, String> keys = jsonObject.get("keys");
        int n = Integer.parseInt(keys.get("n"));
        int k = Integer.parseInt(keys.get("k"));

        for (String key : jsonObject.keySet()) {
            if (!key.equals("keys")) {
                int x = Integer.parseInt(key);
                Map<String, String> root = jsonObject.get(key);
                String base = root.get("base");
                String value = root.get("value");

                long y = decodeYValue(base, value);
                points.add(new long[]{x, y});
            }
        }
        return points;
    }

    public static double lagrangeInterpolation(List<long[]> points) {
        double constantTerm = 0.0;
        int k = points.size();  

        
        for (int i = 0; i < k; i++) {
            long[] pointI = points.get(i);
            double yi = pointI[1];
            double term = yi;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    long[] pointJ = points.get(j);
                    term *= ((0 - pointJ[0]) * 1.0) / (pointI[0] - pointJ[0]);
                }
            }
            constantTerm += term;
        }
        return constantTerm;
    }

    public static void main(String[] args) {
       
        Map<String, Map<String, String>> jsonObject = new HashMap<>();

        Map<String, String> keys = new HashMap<>();
        keys.put("n", "9");
        keys.put("k", "6");
        jsonObject.put("keys", keys);
        jsonObject.put("1", createPoint("10", "2873561972383"));
        jsonObject.put("2", createPoint("16", "1A228867F0CA"));
        jsonObject.put("3", createPoint("12", "32811A4AA0B7B"));
        jsonObject.put("4", createPoint("11", "917978721331A"));
        jsonObject.put("5", createPoint("16", "1A17978721331"));
        jsonObject.put("6", createPoint("10", "2873561965470"));
        jsonObject.put("7", createPoint("14", "7873561965470"));
        jsonObject.put("8", createPoint("9", "12266258154167"));
        jsonObject.put("9", createPoint("8", "64212103003760"));

        List<long[]> points = parsePoints(jsonObject);

        double constantTerm = lagrangeInterpolation(points);

        System.out.println("The constant term (c) is: " + Math.round(constantTerm));
    }

    
    private static Map<String, String> createPoint(String base, String value) {
        Map<String, String> point = new HashMap<>();
        point.put("base", base);
        point.put("value", value);
        return point;
    }
}


