package agents;

import Util.DFHelper;
import behaviours.ReceiveMenuRequestBehaviour;
import entities.MenuDish;
import entities.Order;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

public class MenuAgent extends Agent {
    public static final String AGENT_TYPE = "manu";
    public static final String AGENT_NAME = "Menu-agent";
    private ContainerController container;

    public ArrayList<MenuDish> dishes;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            dishes = (ArrayList<MenuDish>) args[0];
            container = (ContainerController) args[1];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        addBehaviour(new ReceiveMenuRequestBehaviour());
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
