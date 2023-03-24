package entities;

import java.util.Date;

public class Product {
    private int prod_item_id;
    private int prod_item_type;
    private String prod_item_name;
    private String prod_item_company;
    private String prod_item_unit;
    private double prod_item_quantity;
    private double prod_item_cost;
    private Date prod_item_delivered;
    private Date prod_item_valid_until;

    public int id() {
        return prod_item_id;
    }

    public int type() {
        return prod_item_type;
    }

    public String name() {
        return prod_item_name;
    }

    public String company() {
        return prod_item_company;
    }

    public String unit() {
        return prod_item_unit;
    }

    public double quantity() {
        return prod_item_quantity;
    }

    public double cost() {
        return prod_item_cost;
    }

    public Date delivered() {
        return prod_item_delivered;
    }

    public Date validUntil() {
        return prod_item_valid_until;
    }
}