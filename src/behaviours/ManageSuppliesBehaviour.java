package behaviours;

import agents.MenuAgent;
import agents.StorageAgent;
import entities.OperProduct;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import util.ACLHelper;
import util.ConversationTypes;

import java.util.ArrayList;

public class ManageSuppliesBehaviour extends CyclicBehaviour {



    @Override
    public void action() {
        StorageAgent storage = (StorageAgent) myAgent;
        MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.RESERVE_SUPP);
        ACLMessage msg = myAgent.receive(messageTemplate);
        if (msg != null) {
            try {
                ACLHelper.sendMessageWithReply(myAgent, MenuAgent.AGENT_TYPE, ConversationTypes.GET_PROD_BY_MID, ACLHelper.getContent(msg), (data ->  {
                    ArrayList<OperProduct> products = (ArrayList<OperProduct>) data;
                    var empty = storage.reserve(products);
                    ACLHelper.sendMessage(myAgent, MenuAgent.AGENT_TYPE, ConversationTypes.MAKE_INACTIVE, empty);
                }));
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
        } else {
            block();
        }
    }
}
