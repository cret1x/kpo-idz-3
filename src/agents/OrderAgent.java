package agents;

import main.Restaurant;
import util.DFHelper;
import entities.Order;
import jade.core.Agent;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class OrderAgent extends Agent {
    public static final String AGENT_TYPE = "order";
    public static final String AGENT_NAME = "Order-agent";
    private Order order;
    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            order = (Order) args[0];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready." + order.dishId());
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);

        //addBehaviour(new ReserveSuppliesBehaviour());
        try {
            Restaurant.containerController.createNewAgent("Process" + order.id(), "agents.ProcessAgent", new Object[]{order}).start();
        } catch (StaleProxyException e) {

        }
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
