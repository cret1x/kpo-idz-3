package agents;

import entities.DishCard;
import util.DFHelper;
import behaviours.ReceiveMenuRequestBehaviour;
import entities.MenuDish;
import jade.core.Agent;
import jade.wrapper.ContainerController;

import java.util.ArrayList;

public class MenuAgent extends Agent {
    public static final String AGENT_TYPE = "menu";
    public static final String AGENT_NAME = "Menu-agent";
    public ArrayList<MenuDish> dishes;
    public ArrayList<DishCard> recipes;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 1) {
            dishes = (ArrayList<MenuDish>) args[0];
            recipes = (ArrayList<DishCard>) args[1];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        addBehaviour(new ReceiveMenuRequestBehaviour());
    }
    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
