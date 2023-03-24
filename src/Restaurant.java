import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Restaurant {
    private String basePath;
    private ContainerController container;
    private ArrayList<AgentController> agents = new ArrayList<>();
    JSONParser parser = new JSONParser();

    public Restaurant(String basePath, ContainerController container) {
        this.basePath = basePath;
        this.container = container;
    }

    public void initAgents() {
        loadCustomersFromFile();
        try {
            agents.add(container.createNewAgent("Manager 1",
                    "agents.ManagerAgent", null));
        } catch (StaleProxyException ex) {
            System.out.println("Failed to create manager!");
        }

        for (AgentController ac : agents) {
            try {
                ac.start();
            } catch (StaleProxyException ex) {
                System.out.println("Failed to start agent!");
            }
        }
    }

    private void loadCustomersFromFile() {
        try {
            JSONObject visitorsOrdersObject = (JSONObject) parser.parse(new FileReader(basePath + "visitors_orders.txt"));
            JSONArray visitorsOrders = (JSONArray) visitorsOrdersObject.get("visitors_orders");
            for (Object o : visitorsOrders) {
                JSONObject visitor = (JSONObject) o;
                String name = (String) visitor.get("vis_name");
                LocalDateTime orderStartDate = LocalDateTime.parse((String) visitor.get("vis_ord_started"));
                LocalDateTime orderEndDate = LocalDateTime.parse((String) visitor.get("vis_ord_ended"));
                long orderTotal = (long) visitor.get("vis_ord_total");
                String orderArray = (String) visitor.get("vis_ord_dishes").toString();
                agents.add(container.createNewAgent(name,
                        "agents.CustomerAgent", new Object[]{orderStartDate, orderEndDate, orderTotal, orderArray}));
            }
        } catch (IOException | ParseException | StaleProxyException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadMenuFromFile() {
        try{
            JSONObject MenuObject = (JSONObject) parser.parse(new FileReader(basePath + "menu_dishes.txt"));
            JSONArray Menu = (JSONArray) MenuObject.get("menu_dishes");
            for (Object o : Menu) {
                JSONObject menu_dish = (JSONObject) o;
                long id = (long) menu_dish.get("menu_dish_id");
                long card = (long) menu_dish.get("menu_dish_card");
                long price = (long) menu_dish.get("menu_dish_price");
                boolean active = (boolean) menu_dish.get("menu_dish_active");
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadRecipesFromFile() {
        try{
            JSONObject cardObject = (JSONObject) parser.parse(new FileReader(basePath + "dish_cards.txt"));
            JSONArray dish_cards = (JSONArray) cardObject.get("dish_cards");
            for (Object o : dish_cards){
                JSONObject dish_card = (JSONObject) o;
                long card_id = (long) dish_card.get("card_id");
                String dish_name = (String) dish_card.get("dish_name");
                String card_descr = (String) dish_card.get("card_descr");
                double card_time = (double) dish_card.get("card_time");
                long equip_type = (long) dish_card.get("equip_type");
                String operations = (String) dish_card.get("operations").toString();
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadSuppliesTypeFromFile() {
        try{
            JSONObject productObject = (JSONObject) parser.parse(new FileReader(basePath + "product_types.txt"));
            JSONArray product_types = (JSONArray) productObject.get("product_types");
            for (Object o : product_types){
                JSONObject product_type = (JSONObject) o;
                long prod_id = (long) product_type.get("prod_id");
                String prod_type_name = (String) product_type.get("prod_type_name");
                boolean prod_is_food = (boolean) product_type.get("prod_is_food");
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadSuppliesFromFile() {
        try{
            JSONObject productsObject = (JSONObject) parser.parse(new FileReader(basePath + "products.txt"));
            JSONArray products = (JSONArray) productsObject.get("products");
            for (Object o: products){
                JSONObject product = (JSONObject) o;
                long prod_item_id = (long) product.get("prod_item_id");
                long prod_item_type = (long) product.get("prod_item_type");
                String prod_item_name = (String) product.get("prod_item_name");
                String prod_item_company = (String) product.get("prod_item_company");
                String prod_item_unit = (String) product.get("prod_item_unit");
                double prod_item_quantity = (double) product.get("prod_item_quantity");
                long prod_item_cost = (long) product.get("prod_item_cost");
                LocalDateTime prod_item_delivered = LocalDateTime.parse((String) product.get("prod_item_delivered"));
                LocalDateTime prod_item_valid_until = LocalDateTime.parse((String) product.get("prod_item_valid_until"));
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadEquipmentTypeFromFile() {
        try{
            JSONObject equipment_typeObject = (JSONObject) parser.parse(new FileReader(basePath + "equipment_type.txt"));
            JSONArray equipment_types = (JSONArray) equipment_typeObject.get("equipment_type");
            for (Object o: equipment_types){
                JSONObject equipment_type = (JSONObject) o;
                long equip_type_id = (long) equipment_type.get("equip_type_id");
                String equip_type_name = (String) equipment_type.get("equip_type_name");
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadEquipmentFromFile(){
        try{
            JSONObject equipmentObject = (JSONObject) parser.parse(new FileReader(basePath + "equipment.txt"));
            JSONArray equipments = (JSONArray) equipmentObject.get("equipment");
            for (Object o: equipments){
                JSONObject equipment = (JSONObject) o;
                long equip_type = (long) equipment.get("equip_type");
                String equip_name = (String) equipment.get("equip_name");
                boolean equip_active = (boolean) equipment.get("equip_active");
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }
    private void loadCooksFromFile() {
        try{
            JSONObject cookerObject = (JSONObject) parser.parse(new FileReader(basePath + "cookers.txt"));
            JSONArray cookers = (JSONArray) cookerObject.get("cookers");
            for (Object o: cookers){
                JSONObject cooker = (JSONObject) o;
                long cook_id = (long) cooker.get("cook_id");
                String cook_name = (String) cooker.get("cook_name");
                boolean cook_active = (boolean) cooker.get("cook_active");
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadOperationsFromFile() {
        try{
            JSONObject operationObject = (JSONObject) parser.parse(new FileReader(basePath + "operation_types.txt"));
            JSONArray operation_types = (JSONArray) operationObject.get("operation_types");
            for (Object o: operation_types){
                JSONObject operation_type = (JSONObject) o;
                long oper_type_id = (long) operation_type.get("oper_type_id");
                String oper_type_name = (String) operation_type.get("oper_type_name");
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }
}
