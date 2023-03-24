package agents;

import util.DFHelper;
import behaviours.MakeOrderBehaviour;
import behaviours.WaitForOrderBehaviour;
import entities.Order;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;

import java.time.Duration;
import java.time.LocalDateTime;
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
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);

        Duration diff = Duration.between(LocalDateTime.now(), orderStartDate);
        long delay = diff.toSeconds() * 1000;
        System.out.println("Delay: " + delay);
        var seqBeh = new SequentialBehaviour();
        seqBeh.addSubBehaviour(new MakeOrderBehaviour(this, delay));
        seqBeh.addSubBehaviour(new WaitForOrderBehaviour());
        addBehaviour(seqBeh);
    }

    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
