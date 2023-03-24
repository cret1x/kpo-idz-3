package agents;

import behaviours.ReceiveOrderBehaviour;
import behaviours.ReserveSuppliesBehaviour;
import entities.Order;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
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
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(AGENT_TYPE);
        serviceDescription.setName(AGENT_NAME);
        agentDescription.addServices(serviceDescription);
        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException ex) {
            System.out.println("Failed to register " + getAID().getName() + " for DF.");
        }

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
