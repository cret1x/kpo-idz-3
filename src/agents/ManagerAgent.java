package agents;

import util.DFHelper;
import behaviours.ReceiveOrderBehaviour;
import jade.core.Agent;
import jade.wrapper.ContainerController;

public class ManagerAgent extends Agent {
    public static final String AGENT_TYPE = "manager";
    public static final String AGENT_NAME = "Manager-agent";

    @Override
    protected void setup() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        addBehaviour(new ReceiveOrderBehaviour());
    }

    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
