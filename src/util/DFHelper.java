package util;

import agents.CookerAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;

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

    public static ArrayList<AID> search(Agent agent, String agentType) {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();

        serviceDescription.setType(agentType);
        template.addServices(serviceDescription);
        var agents = new ArrayList<AID>();
        try {
            DFAgentDescription[] result = DFService.search(agent, template);

            for (DFAgentDescription agentDescription: result) {
                agents.add(agentDescription.getName());
            }
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        return agents;
    }
}
