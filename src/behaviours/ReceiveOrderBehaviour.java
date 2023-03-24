package behaviours;

import Util.ACLHelper;
import agents.CustomerAgent;
import agents.ManagerAgent;
import agents.MenuAgent;
import com.sun.tools.javac.Main;
import entities.Order;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
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
        ACLMessage reply = myAgent.receive(t);
        if (reply != null) {

            try {
                return ACLHelper.getContent(reply);
            } catch (UnreadableException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            block();
        }
        return false;
    }


}
