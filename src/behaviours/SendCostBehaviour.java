package behaviours;

import agents.MenuAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import util.ACLHelper;
import util.ConversationTypes;

import java.io.IOException;

public class SendCostBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.GET_COST);
        ACLMessage msg = myAgent.receive(messageTemplate);
        MenuAgent menu = (MenuAgent) myAgent;
        if (msg != null) {
            try {
                long id = ACLHelper.getContent(msg);
                var reply = msg.createReply();
                reply.setContentObject(menu.dishes.stream().filter(x -> x.id() == id).findFirst().get().price());
                myAgent.send(reply);
            } catch (UnreadableException e) {

            } catch (IOException e) {

            }
        } else {
            block();
        }
    }
}
