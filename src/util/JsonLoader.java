package util;

import agents.*;
import com.google.gson.*;
import entities.*;
import jade.wrapper.StaleProxyException;
import json_entries.*;
import main.Restaurant;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonLoader {
    private String basePath;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    public void loadFiles(String basePath) {
        this.basePath = basePath;
        loadCustomersFromFile();
        loadMenuFromFile();
        loadSuppliesFromFile();
        loadEquipmentTypeFromFile();
        loadCooksFromFile();
        loadOperationsFromFile();
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

    private void loadEquipmentTypeFromFile() {
        try {
            ArrayList<EquipmentType> types = gson.fromJson(new FileReader(basePath + "equipment_type.txt"), EquipmentTypeEntry.class).equipmentTypes();
            ArrayList<Equipment> equipment = gson.fromJson(new FileReader(basePath + "equipment.txt"), EquipmentEntry.class).equipment();
            for(var eq:equipment) {
                Restaurant.containerController.createNewAgent(eq.name(), EquipmentAgent.class.getName(), new Object[]{eq}).start();
            }
        } catch (IOException | StaleProxyException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadCooksFromFile() {
        try {
            ArrayList<Cooker> cookers = gson.fromJson(new FileReader(basePath + "cookers.txt"), CookEntry.class).cooks();
            for(var c : cookers) {
                Restaurant.containerController.createNewAgent(c.name(), CookerAgent.class.getName(), new Object[] {c}).start();
            }
        } catch (IOException | StaleProxyException exception) {
            System.out.println("File not found or bad format!");
        }
    }

    private void loadOperationsFromFile() {
        try {
            ArrayList<OperationType> operations = gson.fromJson(new FileReader(basePath + "cookers.txt"), OperationTypeEntry.class).operations();
        } catch (IOException exception) {
            System.out.println("File not found or bad format!");
        }
    }
}
