package agents;

import behaviours.MakeOrderBehaviour;
import behaviours.WaitForOrderBehaviour;
import entities.Customer;
import entities.Order;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import util.DFHelper;

import java.util.ArrayList;
import java.util.Date;

public class CustomerAgent extends Agent {
    public static final String AGENT_TYPE = "customer";
    public static final String AGENT_NAME = "Customer-agent";

    Customer customer;

    public ArrayList<Order> getOrdersList() {
        return customer.dishes();
    }

    @Override
    protected void setup() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            customer = (Customer) args[0];
        }
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);

        long delay = (customer.startTime().getTime() - new Date().getTime());
        System.out.println("Delay: " + delay);

        var seqBeh = new SequentialBehaviour();
        seqBeh.addSubBehaviour(new MakeOrderBehaviour(this, delay < 2000 ? 2000 : delay));
        seqBeh.addSubBehaviour(new WaitForOrderBehaviour());
        addBehaviour(seqBeh);
    }

    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
