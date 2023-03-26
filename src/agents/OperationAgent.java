package agents;

import behaviours.ReserveCookerBehaviour;
import behaviours.ReserveEquipmentBehaviour;
import entities.Cooker;
import entities.DishCard;
import entities.Order;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import util.ACLHelper;
import util.ConversationTypes;
import util.DFHelper;
import util.JsonLogger;

import java.util.Date;

public class OperationAgent extends Agent {
    public static final String AGENT_TYPE = "operation";
    public static final String AGENT_NAME = "Operation-agent";

    private long oper_id;
    private long oper_proc;
    private DishCard oper_card;
    private long oper_time;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            oper_id = (long) args[0];
            oper_proc = (long) args[1];
            oper_card = (DishCard) args[2];
            var t = (double) args[3];
            oper_time = (long) (t * 100);
        }
        JsonLogger.logAgent(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                Date startDate = new Date();
                ACLHelper.sendMessageWithReply(myAgent, EquipmentAgent.AGENT_TYPE, ConversationTypes.RESERVE_EQ, new Object[]{false, oper_time}, (eqData -> {
                    ACLHelper.sendMessageWithReply(myAgent, CookerAgent.AGENT_TYPE, ConversationTypes.RESERVE_COOK, new Object[]{false, oper_time}, (coData -> {
                        ACLHelper.sendMessage(myAgent, EquipmentAgent.AGENT_TYPE, ConversationTypes.RESERVE_EQ, new Object[]{true});
                        ACLHelper.sendMessage(myAgent, CookerAgent.AGENT_TYPE, ConversationTypes.RESERVE_COOK, new Object[]{true});
                        Date enddate = new Date();
                        JsonLogger.logOperations(oper_id, oper_proc, oper_card.id(), startDate, enddate, (long) eqData, (long) coData);
                    }));
                }));
            }
        });
        //
    }

    protected void takeDown() {
        JsonLogger.logAgent(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
