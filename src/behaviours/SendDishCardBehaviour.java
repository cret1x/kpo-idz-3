package behaviours;

import agents.MenuAgent;
import entities.DishCard;
import entities.Operation;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import util.ACLHelper;
import util.ConversationTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class SendDishCardBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        MenuAgent menu = (MenuAgent) myAgent;
        MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.GET_DISH_CARD);
        ACLMessage msg = myAgent.receive(messageTemplate);
        DishCard dick = null;
        if (msg  != null) {
            try {
                final long menuId = ACLHelper.getContent(msg);
                var tmp = menu.dishes.stream().filter(x -> x.id() == menuId).findFirst();
                if (tmp.isPresent()) {
                    final long cardId = tmp.get().cardId();
                    dick = menu.recipes.stream().filter(x -> x.id() == cardId).findFirst().get();
                }
            } catch (UnreadableException e) {

            }
            ACLMessage reply = msg.createReply();
            try {
                reply.setContentObject(dick);
            } catch (IOException e) {
                e.printStackTrace();
            }
            myAgent.send(reply);
        } else {
            block();
        }
    }
}
