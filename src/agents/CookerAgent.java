package agents;

import entities.Cooker;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import util.ACLHelper;
import util.ConversationTypes;
import util.DFHelper;
import util.JsonLogger;

import java.io.IOException;

public class CookerAgent extends Agent {
    public static final String AGENT_TYPE = "cooker";
    public static final String AGENT_NAME = "Cooker-agent";
    public Cooker cooker;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            cooker = (Cooker) args[0];
        }
        JsonLogger.logAgent(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.RESERVE_COOK);
                ACLMessage msg = myAgent.receive(messageTemplate);
                if (msg != null) {
                    try {
                        Object[] data = ACLHelper.getContent(msg);
                        cooker.setActive((boolean) data[0]);
                        if (!(boolean) data[0]) {
                            Thread.sleep((long) data[1] * 1000);
                            ACLMessage reply = msg.createReply();
                            reply.setContentObject(cooker.id());
                            myAgent.send(reply);
                        }
                    } catch (UnreadableException e) {
                        //throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        //throw new RuntimeException(e);
                    } catch (IOException e) {
                        //throw new RuntimeException(e);
                    }
                } else {
                    block();
                }
            }
        });
    }

    protected void takeDown() {
        JsonLogger.logAgent(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
