package agents;

import behaviours.MakeOrderBehaviour;
import behaviours.ReceiveOrderBehaviour;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.wrapper.ContainerController;

import java.time.Duration;
import java.time.LocalDateTime;

public class ManagerAgent extends Agent {
    public static final String AGENT_TYPE = "manager";
    public static final String AGENT_NAME = "Manager-agent";
    private ContainerController container;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            container = (ContainerController) args[0];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(AGENT_TYPE);
        serviceDescription.setName(AGENT_NAME);
        agentDescription.addServices(serviceDescription);
        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException ex) {
            System.out.println("Failed to register " + getAID().getName() + " for DF.");
        }
        addBehaviour(new ReceiveOrderBehaviour(container));
    }

    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
