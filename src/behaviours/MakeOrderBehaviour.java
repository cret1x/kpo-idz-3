package behaviours;

import util.ACLHelper;
import agents.CustomerAgent;
import agents.ManagerAgent;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import util.ConversationTypes;


public class MakeOrderBehaviour extends WakerBehaviour {
    public MakeOrderBehaviour(Agent a, long timeout) {
        super(a, timeout);
    }

    protected void onWake() {
        System.out.println(myAgent.getAID().getName() + ": I am going to make an order!");

        ACLHelper.sendMessage(myAgent, ManagerAgent.AGENT_TYPE, ConversationTypes.MAKE_ORDER, ((CustomerAgent)myAgent).getOrdersList());
    }
}
