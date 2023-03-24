package entities;

import org.json.simple.JSONObject;

import java.io.Serializable;

public record OperProduct(long type, double quantity) implements Serializable {
    public static OperProduct fromJson(JSONObject object) {
        long prod_type = (long) object.get("prod_type");
        double prod_quantity = (double) object.get("prod_quantity");
        return new OperProduct(prod_type, prod_quantity);
    }
}
