package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public record DishCard(long id, String name, String description, double time, long equipId,
                       ArrayList<Operation> operations) implements Serializable {
    public static DishCard fromJson(JSONObject object) {
        long card_id = (long) object.get("card_id");
        String dish_name = (String) object.get("dish_name");
        String card_descr = (String) object.get("card_descr");
        double card_time = (double) object.get("card_time");
        long equip_type = (long) object.get("equip_type");
        var operations1 = ((JSONArray) object.get("operations")).stream().map(obj -> Operation.fromJson((JSONObject) obj)).toList();
        var operations = new ArrayList<Operation>(operations1);
        return new DishCard(card_id, dish_name, card_descr, card_time, equip_type, operations);
    }
}
