package agents;

import util.DFHelper;
import entities.Product;
import jade.core.Agent;

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
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
