package agents;

import behaviours.ReceiveMenuRequestBehaviour;
import behaviours.SendDishCardBehaviour;
import behaviours.SendProductsByMenuIdBehaviour;
import behaviours.UpdateActiveMenuBehaviour;
import entities.DishCard;
import entities.MenuDish;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import util.DFHelper;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MenuAgent extends Agent {
    public static final String AGENT_TYPE = "menu";
    public static final String AGENT_NAME = "Menu-agent";
    public ArrayList<MenuDish> dishes;
    public ArrayList<DishCard> recipes;

    public AtomicBoolean hasToUpdate = new AtomicBoolean();

    @Override
    protected void setup() {
        Object[] args = getArguments();
        hasToUpdate.set(false);
        if (args != null && args.length > 1) {
            dishes = (ArrayList<MenuDish>) args[0];
            recipes = (ArrayList<DishCard>) args[1];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        var pb = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
        pb.addSubBehaviour(new ReceiveMenuRequestBehaviour());
        pb.addSubBehaviour(new SendProductsByMenuIdBehaviour());
        pb.addSubBehaviour(new UpdateActiveMenuBehaviour());
        pb.addSubBehaviour(new SendDishCardBehaviour());
        addBehaviour(pb);
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
