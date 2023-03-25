package agents;

import entities.Cooker;
import jade.core.Agent;
import util.DFHelper;

public class CookerAgent extends Agent {
    public static final String AGENT_TYPE = "cooker";
    public static final String AGENT_NAME = "Cooker-agent";
    private Cooker cooker;
    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            cooker = (Cooker) args[0];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
