package agents;

import behaviours.ReserveCookerBehaviour;
import behaviours.ReserveEquipmentBehaviour;
import entities.Cooker;
import entities.DishCard;
import entities.Order;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.StaleProxyException;
import main.Restaurant;
import util.ACLHelper;
import util.ConversationTypes;
import util.DFHelper;
import util.JsonLogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProcessAgent extends Agent {
    public static final String AGENT_TYPE = "process";
    public static final String AGENT_NAME = "Process-agent";

    private Order order;
    private long id;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            order = (Order) args[0];
            id = (long) args[1];
        }
        JsonLogger.logAgent(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        // create OperationAgent for each operation in card;
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                // Ask menu for dish card;
                ACLHelper.sendMessageWithReply(myAgent, MenuAgent.AGENT_TYPE, ConversationTypes.GET_DISH_CARD, order.dishId(), (data -> {
                    DishCard dc = (DishCard) data;
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    Date startDate = cal.getTime();
                    cal.add(Calendar.MINUTE, (int) (dc.time() * 100));
                    var operationIds = new ArrayList<Long>();
                    for (long i = Restaurant.operationCount; i < Restaurant.operationCount + dc.operations().size(); i++) {
                        operationIds.add(i);
                    }
                    JsonLogger.logProcess(id, order.id(), startDate, cal.getTime(), operationIds);
                    for (var op : dc.operations()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            Restaurant.containerController.createNewAgent("Operation" + Restaurant.operationCount++, OperationAgent.class.getName(), new Object[]{Restaurant.operationCount, id, dc, op.time()}).start();
                        } catch (StaleProxyException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }));
            }
        });

    }

    protected void takeDown() {
        JsonLogger.logAgent(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
