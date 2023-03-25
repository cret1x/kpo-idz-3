package behaviours;

import agents.OrderAgent;
import agents.StorageAgent;
import entities.Order;
import jade.core.behaviours.OneShotBehaviour;
import util.ACLHelper;
import util.ConversationTypes;

public class ReserveSuppliesBehaviour extends OneShotBehaviour {

    @Override
    public void action() {
        Order order = ((OrderAgent) myAgent).order;
        ACLHelper.sendMessage(myAgent, StorageAgent.AGENT_TYPE, ConversationTypes.RESERVE_SUPP, order.dishId());
    }
}
