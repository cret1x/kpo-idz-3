package entities;

import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public record MenuDish(long id, long card, long price, boolean active) {
    public static MenuDish fromJson(JSONObject object){
        long id = (long) object.get("menu_dish_id");
        long card = (long) object.get("menu_dish_card");
        long price = (long) object.get("menu_dish_price");
        boolean active = (boolean) object.get("menu_dish_active");
        return new MenuDish(id ,card, price, active);
    }

}

