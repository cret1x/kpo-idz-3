package entities;

import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MenuDish {
    private long id;
    private long card;
    private long price;
    private boolean active;
    MenuDish(long id, long card, long price, boolean active){
        this.id = id;
        this.card = card;
        this.price = price;
        this.active = active;
    }
    MenuDish(){}
    public static MenuDish fromJson(JSONObject object){
        MenuDish menuDish = new MenuDish();
        menuDish.id = (long) object.get("menu_dish_id");
        menuDish.card = (long) object.get("menu_dish_card");
        menuDish.price = (long) object.get("menu_dish_price");
        menuDish.active = (boolean) object.get("menu_dish_active");
        return menuDish;
    }

}

