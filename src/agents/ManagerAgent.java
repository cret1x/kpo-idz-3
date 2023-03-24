package agents;

import Util.DFHelper;
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
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        addBehaviour(new ReceiveOrderBehaviour(container));
    }

    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
