package behaviours;

import agents.CustomerAgent;
import agents.ManagerAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Date;

public class MakeOrderBehaviour extends WakerBehaviour {
    private static final String CONVERSATION_ID = "make-order";
    public MakeOrderBehaviour(Agent a, long timeout) {
        super(a, timeout);
    }

    protected void onWake() {
        System.out.println(myAgent.getAID().getName() + ": I am going to make an order!");
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();

        serviceDescription.setType(ManagerAgent.AGENT_TYPE);
        template.addServices(serviceDescription);
        ACLMessage cfpMessage = new ACLMessage(ACLMessage.CFP);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            for (DFAgentDescription agentDescription: result) {
                cfpMessage.addReceiver(agentDescription.getName());
            }
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        cfpMessage.setContent(((CustomerAgent)myAgent).orderStringObject);
        cfpMessage.setConversationId(CONVERSATION_ID);
        cfpMessage.setReplyWith("cpf" + System.currentTimeMillis());
        MessageTemplate messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchConversationId(CONVERSATION_ID),
                MessageTemplate.MatchInReplyTo(cfpMessage.getReplyWith()));
        myAgent.send(cfpMessage);
    }
}
