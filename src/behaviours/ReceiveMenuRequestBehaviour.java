package behaviours;

import util.ACLHelper;
import agents.MenuAgent;
import entities.MenuDish;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public class ReceiveMenuRequestBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchConversationId("check-menu");
        ACLMessage msg = myAgent.receive(messageTemplate);
        long mId = 0;
        if (msg != null) {
            System.out.println(msg.getSender());
            try {
                mId = ACLHelper.getContent(msg);
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
            long finalMId = mId;
            var d = ((MenuAgent)myAgent).dishes.stream().filter(x -> x.id() == finalMId).findFirst();
            boolean active = d.map(MenuDish::active).orElse(false);
            ACLMessage reply = msg.createReply();
            try {
                reply.setContentObject(active);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(reply);
            myAgent.send(reply);
        } else {
            block();
        }
    }
}
