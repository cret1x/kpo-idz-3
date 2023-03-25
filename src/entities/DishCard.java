package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class DishCard implements Serializable {
    private long card_id;
    private String dish_name;
    private String card_descr;
    private double card_time;
    private long equip_type;
    ArrayList<Operation> operations;

    public long id() {
        return card_id;
    }

    public String name() {
        return dish_name;
    }

    public String description() {
        return card_descr;
    }

    public double time() {
        return card_time;
    }

    public long equipType() {
        return equip_type;
    }

    public ArrayList<Operation> operations() {
        return operations;
    }
}
