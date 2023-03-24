package entities;

import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MenuDish {
    private long menu_dish_id;
    private long menu_dish_card;
    private long menu_dish_price;
    private boolean menu_dish_active;

    public long id() {
        return menu_dish_id;
    }

    public long cardId() {
        return menu_dish_card;
    }

    public long price() {
        return menu_dish_price;
    }

    public boolean isActive() {
        return menu_dish_active;
    }
}

