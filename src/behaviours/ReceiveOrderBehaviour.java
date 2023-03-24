package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;

public class ReceiveOrderBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        ACLMessage msg = myAgent.receive(messageTemplate);

        if (msg != null) {
            JSONParser parser = new JSONParser();
            String order = msg.getContent();
            try {
                JSONArray orderArray = (JSONArray) parser.parse(order);

            } catch (ParseException ex) {

            }
            System.out.println("Got from " + msg.getSender().getName() + ": " + order);
        } else {
            block();
        }
    }

    private boolean checkPosition(long id) {
        return true;
    }


}
