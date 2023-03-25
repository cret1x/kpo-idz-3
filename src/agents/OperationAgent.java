package agents;

import behaviours.ReserveCookerBehaviour;
import behaviours.ReserveEquipmentBehaviour;
import entities.Cooker;
import entities.DishCard;
import entities.Order;
import jade.core.Agent;
import util.DFHelper;
import util.JsonLogger;

public class OperationAgent extends Agent {
    public static final String AGENT_TYPE = "operation";
    public static final String AGENT_NAME = "Operation-agent";

    private long oper_id;
    private long oper_proc;
    private DishCard oper_card;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
           oper_id = (long) args[0];
           oper_proc = (long) args[1];
           oper_card = (DishCard) args[2];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        JsonLogger.logOperations(oper_id, oper_proc, oper_card.id());
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
