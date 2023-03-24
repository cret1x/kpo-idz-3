package entities;

import org.json.simple.JSONObject;

import java.io.Serializable;

public record Order(long orderId, long menuDishId) implements Serializable {
    public static Order fromJson(JSONObject object) {
        long oId = (long) object.get("ord_dish_id");
        long mId = (long) object.get("menu_dish");
        return new Order(oId, mId);
    }
}
