package behaviours;

import Util.ACLHelper;
import agents.CustomerAgent;
import agents.ManagerAgent;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;


public class MakeOrderBehaviour extends WakerBehaviour {
    private static final String CONVERSATION_ID = "make-order";
    public MakeOrderBehaviour(Agent a, long timeout) {
        super(a, timeout);
    }

    protected void onWake() {
        System.out.println(myAgent.getAID().getName() + ": I am going to make an order!");

        ACLHelper.sendMessage(myAgent, ManagerAgent.AGENT_TYPE, CONVERSATION_ID, ((CustomerAgent)myAgent).orders);
    }
}
