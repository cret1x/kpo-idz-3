package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public record Operation(long type, double time, long point, ArrayList<OperProduct> products) implements Serializable {
    public static Operation fromJson(JSONObject object) {
        long oper_type = (long) object.get("oper_type");
        double oper_time = (double) object.get("oper_time");
        long oper_async_point = (long) object.get("oper_async_point");
        var p1 = ((JSONArray) object.get("oper_products")).stream().map(obj -> OperProduct.fromJson((JSONObject) obj)).toList();
        var p2 = new ArrayList<OperProduct>(p1);
        return new Operation(oper_type, oper_time, oper_async_point, p2);
    }
}
