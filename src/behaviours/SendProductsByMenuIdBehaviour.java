package behaviours;

import agents.MenuAgent;
import entities.DishCard;
import entities.OperProduct;
import entities.Operation;
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

public class SendProductsByMenuIdBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        MenuAgent menuAgent = (MenuAgent) myAgent;
        MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.GET_PROD_BY_MID);
        ACLMessage msg = myAgent.receive(messageTemplate);
        if (msg != null) {
            ArrayList<OperProduct> products = new ArrayList<>();
            try {
                final long menuId = ACLHelper.getContent(msg);
                var tmp = menuAgent.dishes.stream().filter(x -> x.id() == menuId).findFirst();
                if (tmp.isPresent()) {
                    final long cardId = tmp.get().cardId();
                    products = menuAgent.recipes.stream()
                            .filter(x -> x.id() == cardId)
                            .map(DishCard::operations)
                            .flatMap(Collection::stream)
                            .map(Operation::products)
                            .flatMap(Collection::stream).collect(Collectors.toCollection(ArrayList::new));
                }
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
            ACLMessage reply = msg.createReply();
            try {
                reply.setContentObject(products);
            } catch (IOException e) {
                e.printStackTrace();
            }
            myAgent.send(reply);
        } else {
            block();
        }
    }
}
