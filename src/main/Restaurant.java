package main;

import agents.CustomerAgent;
import agents.ManagerAgent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Customer;
import entities.MenuDish;
import entities.Order;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import util.JsonLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String basePath;
    public static ContainerController containerController;

    public Restaurant(String basePath, ContainerController container) {
        this.basePath = basePath;
        Restaurant.containerController = container;
    }

    public void initAgents() {
        JsonLoader jl = new JsonLoader();
        jl.loadFiles(basePath);
        try {
           containerController.createNewAgent("Manager 1",
                   ManagerAgent.class.getName(), null).start();
        } catch (StaleProxyException ex) {
            System.out.println("Failed to create agents!");
        }
    }


}
