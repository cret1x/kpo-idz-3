package agents;

import util.DFHelper;
import entities.Order;
import jade.core.Agent;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class OrderAgent extends Agent {
    public static final String AGENT_TYPE = "order";
    public static final String AGENT_NAME = "Order-agent";
    private ContainerController container;
    private Order order;
    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            order = (Order) args[0];
            container = (ContainerController) args[1];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready." + order.menuDishId());
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);

        //addBehaviour(new ReserveSuppliesBehaviour());
        try {
            container.createNewAgent("Process" + order.orderId(), "agents.ProcessAgent", new Object[]{order, container}).start();
        } catch (StaleProxyException e) {

        }
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
