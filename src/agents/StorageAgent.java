package agents;

import entities.Order;
import entities.Product;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

public class StorageAgent extends Agent {
    public static final String AGENT_TYPE = "storage";
    public static final String AGENT_NAME = "Storage-agent";
    public ArrayList<Product> products;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            products = (ArrayList<Product>) args[0];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
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
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
