package main;

import agents.ManagerAgent;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import util.JsonLoader;
import util.JsonLogger;

public class Restaurant {
    private String basePath;
    public static ContainerController containerController;

    public static long operationCount = 0;
    public static long processCount = 0;

    public Restaurant(String basePath, ContainerController container) {
        this.basePath = basePath;
        Restaurant.containerController = container;
    }

    public void initAgents() {
        JsonLogger.setBasePath("D:\\fuck_me\\logs\\");
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
