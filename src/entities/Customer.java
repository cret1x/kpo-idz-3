package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {
    private String name;
    private LocalDateTime orderStartDate;
    private LocalDateTime orderEndDate;
    private long orderTotal;
    private ArrayList<Order> orderDishes;

    Customer() {}

    @Override
    public String toString() {
        return name + " " + orderTotal;
    }

    Customer(String name, LocalDateTime orderStartDate, LocalDateTime orderEndDate, int ordersCount, ArrayList<Order> orderDishes) {
        this.name = name;
        this.orderStartDate = orderStartDate;
        this.orderEndDate = orderEndDate;
        this.orderTotal = ordersCount;
        this.orderDishes = orderDishes;
    }

    public static Customer fromJson(JSONObject object) {
        Customer customer = new Customer();
        customer.name = (String) object.get("vis_name");
        customer.orderStartDate = LocalDateTime.parse((String) object.get("vis_ord_started"));
        customer.orderEndDate = LocalDateTime.parse((String) object.get("vis_ord_ended"));
        customer.orderTotal = (long) object.get("vis_ord_total");
        customer.orderDishes = new ArrayList<>();
        JSONArray orders = ((JSONArray)object.get("vis_ord_dishes"));
        for (Object order: orders) {
            JSONObject obj = (JSONObject) order;
            customer.orderDishes.add(new Order((long) obj.get("ord_dish_id"), (long) obj.get("menu_dish")));
        }
        return customer;
    }
}
