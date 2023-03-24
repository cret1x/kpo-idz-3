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
    }

    private void loadRecipesFromFile() {

    }

    private void loadSuppliesFromFile() {

    }

    private void loadEquipmentTypeFromFile() {

    }

    private void loadEquipmentFromFile() {

    }

    private void loadCooksFromFile() {

    }

    private void loadOperationsFromFile() {

    }
}
