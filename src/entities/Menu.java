package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    private ArrayList<MenuDish> menuDishes;
    Menu(){};
    public static Menu fromJson(JSONObject object){
        Menu menu = new Menu();
        menu.menuDishes = new ArrayList<>();
        JSONArray arr = (JSONArray) object.get("menu_dishes");
        for (Object o:arr) {
            JSONObject daddy = (JSONObject) o;
            menu.menuDishes.add(MenuDish.fromJson(daddy));
        }
        return menu;
    }

}
