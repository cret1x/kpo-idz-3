package behaviours;

import agents.OrderAgent;
import main.Restaurant;
import util.ACLHelper;
import agents.MenuAgent;
import entities.Order;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import util.ConversationTypes;

import java.util.ArrayList;

public class ReceiveOrderBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.MAKE_ORDER);
        ACLMessage msg = myAgent.receive(messageTemplate);
        ArrayList<Order> orders = null;
        if (msg != null) {
            try {
                orders = ACLHelper.getContent(msg);
            } catch (UnreadableException e) {

            }
            if (orders != null) {
                try {
                    for (Order o: orders) {
                        if (checkPosition(o.dishId())) {
                            Restaurant.containerController.createNewAgent("Order" + o.id(), OrderAgent.class.getName(), new Object[]{o}).start();
                        }
                    }
                } catch (StaleProxyException ex) {

                }
            }

            System.out.println("Got from " + msg.getSender().getName());
        } else {
            block();
        }
    }

    private boolean checkPosition(long id) {
        var t = ACLHelper.sendMessage(myAgent, MenuAgent.AGENT_TYPE, ConversationTypes.CHECK_MENU, id);
        ACLMessage reply = myAgent.receive(t);

        // Патч Костыль от Антона
        do {
            if (reply == null) {
                reply = myAgent.receive(t);
            } else {
                block();
            }
        } while (reply == null);

        try {
            return ACLHelper.getContent(reply);
        } catch (UnreadableException e) {
            e.printStackTrace();
            return false;
        }
    }
}
