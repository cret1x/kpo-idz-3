package util;

import agents.CustomerAgent;
import agents.MenuAgent;
import agents.StorageAgent;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entities.*;
import jade.wrapper.StaleProxyException;
import json_entries.*;
import main.Restaurant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class JsonLoader {
    private String basePath;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    public void loadFiles(String basePath) {
        this.basePath = basePath;
        loadCustomersFromFile();
        loadMenuFromFile();
        loadSuppliesFromFile();
    }

    private void loadCustomersFromFile() {
        try {
            ArrayList<Customer> customers = gson.fromJson(new FileReader(basePath + "visitors_orders.txt"), CustomerEntry.class).customers();
            for (Customer c : customers) {
                Restaurant.containerController.createNewAgent(c.name(), CustomerAgent.class.getName(), new Object[]{c}).start();
            }
        } catch (IOException | StaleProxyException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadMenuFromFile() {
        try {
            ArrayList<MenuDish> dishes = gson.fromJson(new FileReader(basePath + "menu_dishes.txt"), MenuEntry.class).dishes();
            ArrayList<DishCard> recipes = gson.fromJson(new FileReader(basePath + "dish_cards.txt"), DishCardEntry.class).cards();
            Restaurant.containerController.createNewAgent("Menu 1", MenuAgent.class.getName(), new Object[]{dishes, recipes}).start();
        } catch (IOException | StaleProxyException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadSuppliesFromFile() {
        try {
            ArrayList<ProductType> types = gson.fromJson(new FileReader(basePath + "product_types.txt"), ProductTypeEntry.class).productTypes();
            ArrayList<Product> products = gson.fromJson(new FileReader(basePath + "products.txt"), ProductEntry.class).products();
            Restaurant.containerController.createNewAgent("Storage 1", StorageAgent.class.getName(), new Object[] {types, products}).start();
        } catch (IOException | StaleProxyException exception) {
            System.out.println("File not found or bad format!");
        }
    }
    /*
    private void loadSuppliesFromFile() {
        try {
            JSONObject productsObject = (JSONObject) parser.parse(new FileReader(basePath + "products.txt"));
            JSONArray products = (JSONArray) productsObject.get("products");
            for (Object o : products) {
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
        try {
            JSONObject equipment_typeObject = (JSONObject) parser.parse(new FileReader(basePath + "equipment_type.txt"));
            JSONArray equipment_types = (JSONArray) equipment_typeObject.get("equipment_type");
            for (Object o : equipment_types) {
                JSONObject equipment_type = (JSONObject) o;
                long equip_type_id = (long) equipment_type.get("equip_type_id");
                String equip_type_name = (String) equipment_type.get("equip_type_name");
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadEquipmentFromFile() {
        try {
            JSONObject equipmentObject = (JSONObject) parser.parse(new FileReader(basePath + "equipment.txt"));
            JSONArray equipments = (JSONArray) equipmentObject.get("equipment");
            for (Object o : equipments) {
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
        try {
            JSONObject cookerObject = (JSONObject) parser.parse(new FileReader(basePath + "cookers.txt"));
            JSONArray cookers = (JSONArray) cookerObject.get("cookers");
            for (Object o : cookers) {
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
        try {
            JSONObject operationObject = (JSONObject) parser.parse(new FileReader(basePath + "operation_types.txt"));
            JSONArray operation_types = (JSONArray) operationObject.get("operation_types");
            for (Object o : operation_types) {
                JSONObject operation_type = (JSONObject) o;
                long oper_type_id = (long) operation_type.get("oper_type_id");
                String oper_type_name = (String) operation_type.get("oper_type_name");
            }
        } catch (IOException | ParseException exception) {
            System.out.println("File not found or bad format!");
        }
    }
     */
}
