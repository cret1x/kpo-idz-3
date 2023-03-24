package util;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class DFHelper {
    public static void register(Agent a, String agentType, String agentName) {
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(a.getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(agentType);
        serviceDescription.setName(agentName);
        agentDescription.addServices(serviceDescription);
        try {
            DFService.register(a, agentDescription);
        } catch (FIPAException ex) {
            System.out.println("Failed to register " + a.getAID().getName() + " for DF.");
        }
    }
}
