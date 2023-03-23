import entities.Customer;
import jade.core.Agent;
import org.json.simple.parser.*;
import org.json.simple.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        var customers = new ArrayList<Customer>();
        try {
            JSONObject visitorsOrders = (JSONObject) parser.parse(new FileReader("D:\\visitors_orders.txt"));
            JSONArray visitors = (JSONArray) visitorsOrders.get("visitors_orders");
            for (Object o: visitors) {
                customers.add(Customer.fromJson((JSONObject) o));
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
        for (Customer c: customers) {
            System.out.println(c);
        }
    }
}