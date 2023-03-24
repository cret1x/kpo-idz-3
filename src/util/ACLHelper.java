package util;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.io.Serializable;

public class ACLHelper {
    public static MessageTemplate sendMessage(Agent agent, String receiver, String conversationId, Serializable data) {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();

        serviceDescription.setType(receiver);
        template.addServices(serviceDescription);
        ACLMessage cfpMessage = new ACLMessage(ACLMessage.CFP);
        try {
            DFAgentDescription[] result = DFService.search(agent, template);
            for (DFAgentDescription agentDescription: result) {
                cfpMessage.addReceiver(agentDescription.getName());
            }
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        try {
            cfpMessage.setContentObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cfpMessage.setConversationId(conversationId);
        cfpMessage.setReplyWith("cfp" + System.currentTimeMillis());
        MessageTemplate messageTemplate = MessageTemplate.and(
                MessageTemplate.MatchConversationId(conversationId),
                MessageTemplate.MatchInReplyTo(cfpMessage.getReplyWith()));
        agent.send(cfpMessage);
        return messageTemplate;
    }

    public static <T> T getContent(ACLMessage message) throws UnreadableException {
        return (T)message.getContentObject();
    }
}
