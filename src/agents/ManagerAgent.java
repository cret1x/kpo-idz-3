package agents;

import behaviours.ReceiveOrderBehaviour;
import jade.core.Agent;
import util.DFHelper;
import util.JsonLogger;

public class ManagerAgent extends Agent {
    public static final String AGENT_TYPE = "manager";
    public static final String AGENT_NAME = "Manager-agent";

    @Override
    protected void setup() {
        JsonLogger.logAgent(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        addBehaviour(new ReceiveOrderBehaviour());
    }

    protected void takeDown() {
        JsonLogger.logAgent(AGENT_NAME + " " + getAID().getName() + " terminating.");
        JsonLogger.saveAll();
    }
}
