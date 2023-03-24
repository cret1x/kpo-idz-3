package agents;

import behaviours.MakeOrderBehaviour;
import entities.Order;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import org.json.simple.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class CustomerAgent extends Agent {
    public static final String AGENT_TYPE = "customer";
    public static final String AGENT_NAME = "Customer-agent";
    private LocalDateTime orderStartDate;
    private LocalDateTime orderEndDate;
    private long orderTotal;
    public ArrayList<Order> orders;

    @Override
    protected void setup() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");

        Object[] args = getArguments();
        if (args != null && args.length > 3) {
            orderStartDate = (LocalDateTime) args[0];
            orderEndDate = (LocalDateTime) args[1];
            orderTotal = (long) args[2];
            orders = (ArrayList<Order>) args[3];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " has " + orderTotal + " orders!");
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
        Duration diff = Duration.between(LocalDateTime.now(), orderStartDate);
        long delay = diff.toSeconds() * 1000;
        System.out.println("Delay: " + delay);
        addBehaviour(new MakeOrderBehaviour(this, delay));
    }

    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
