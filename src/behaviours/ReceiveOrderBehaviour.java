package behaviours;

import agents.MenuAgent;
import agents.OrderAgent;
import entities.Order;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.StaleProxyException;
import main.Restaurant;
import util.ACLHelper;
import util.ConversationTypes;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
                        Thread.sleep(1000);
                        if (checkPosition(o.dishId())) {
                            Restaurant.containerController.createNewAgent("Order" + o.id(), OrderAgent.class.getName(), new Object[]{o}).start();
                        }
                    }
                } catch (StaleProxyException ex) {

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Got from " + msg.getSender().getName());
        } else {
            block();
        }
    }

    private boolean checkPosition(long id) {
        AtomicBoolean ret = new AtomicBoolean(false);
        ACLHelper.sendMessageWithReply(myAgent, MenuAgent.AGENT_TYPE, ConversationTypes.CHECK_MENU, id, (data -> {
            ret.set((boolean) data);
        }));
        return ret.get();
    }
}
