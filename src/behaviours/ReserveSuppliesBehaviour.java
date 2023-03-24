package behaviours;

import agents.CustomerAgent;
import agents.ManagerAgent;
import agents.StorageAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReserveSuppliesBehaviour extends OneShotBehaviour {
    private static final String CONVERSATION_ID = "reserve-supplies";
    private long cardId;
    public ReserveSuppliesBehaviour(long cardId) {
        this.cardId = cardId;
    }

    @Override
    public void action() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();

        serviceDescription.setType(StorageAgent.AGENT_TYPE);
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
        cfpMessage.setConversationId(CONVERSATION_ID);
        cfpMessage.setReplyWith("cpf" + System.currentTimeMillis());
        MessageTemplate messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchConversationId(CONVERSATION_ID),
                MessageTemplate.MatchInReplyTo(cfpMessage.getReplyWith()));
        myAgent.send(cfpMessage);
    }
}
