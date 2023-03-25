package agents;

import entities.Equipment;
import entities.EquipmentType;
import entities.Order;
import jade.core.Agent;
import jade.wrapper.StaleProxyException;
import main.Restaurant;
import util.DFHelper;

public class EquipmentAgent extends Agent {
    public static final String AGENT_TYPE = "equipment";
    public static final String AGENT_NAME = "Equipment-agent";
    private Equipment equipment;
    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            equipment = (Equipment) args[0];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
