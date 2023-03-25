package behaviours;

import agents.MenuAgent;
import entities.Operation;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import util.ACLHelper;
import util.ConversationTypes;

import java.util.ArrayList;
import java.util.Collection;

public class UpdateActiveMenuBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        MenuAgent menu = (MenuAgent) myAgent;
        MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.MAKE_INACTIVE);
        ACLMessage msg = myAgent.receive(messageTemplate);
        if (msg  != null) {
            try {
                ArrayList<Long> empty = ACLHelper.getContent(msg);
                menu.dishes.stream().forEach(x -> {
                    if (menu.recipes.stream().filter(y -> y.id() == x.cardId()).anyMatch(z -> (z.operations().stream().map(Operation::products).flatMap(Collection::stream).anyMatch(f -> empty.contains(f.type()))))) {
                        x.setInactive();
                    }
                });
            } catch (UnreadableException e) {

            }
            for (var m : menu.dishes) {
                System.out.println(m.id() + " " + m.cardId() + " " + m.isActive());
            }
           // synchronized(((MenuAgent)myAgent).hasToUpdate) {
                ((MenuAgent)myAgent).hasToUpdate.set(false);
           // }
        } else {
            block();
        }
    }
}
