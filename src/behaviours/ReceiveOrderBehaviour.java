package behaviours;

import util.ACLHelper;
import agents.MenuAgent;
import entities.Order;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

public class ReceiveOrderBehaviour extends CyclicBehaviour {
    private ContainerController container;
    private static final String CONVERSATION_ID = "make-order";

    public ReceiveOrderBehaviour(ContainerController container) {
        this.container = container;
    }
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(CONVERSATION_ID);
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
                        if (checkPosition(o.menuDishId())) {
                            container.createNewAgent("Order" + o.orderId(), "agents.OrderAgent", new Object[]{o, container}).start();
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
        var t = ACLHelper.sendMessage(myAgent, MenuAgent.AGENT_TYPE, "check-menu", id);
        MessageTemplate mt =  MessageTemplate.MatchConversationId("check-menu");

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
