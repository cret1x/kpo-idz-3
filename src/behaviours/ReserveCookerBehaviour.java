package behaviours;

import agents.CookerAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.ControllerException;
import main.Restaurant;
import util.DFHelper;

import java.util.ArrayList;

public class ReserveCookerBehaviour extends Behaviour {

    int step = 0;

    @Override
    public void action() {
        switch (step) {
            case 0: {
                var cookers = DFHelper.search(myAgent, CookerAgent.AGENT_TYPE);
                var cooker = cookers.stream().filter(x -> {
                    try {
                        return ((CookerAgent)Restaurant.containerController.getAgent(x.getName())).cooker.isActive();
                    } catch (ControllerException e) {
                        return false;
                    }
                }).findAny();
                if (cooker.isPresent()) {
                    try {
                        var cock = (CookerAgent)Restaurant.containerController.getAgent(cooker.get().getName());
                        cock.cooker.setActive(false);

                    } catch (ControllerException e) {
                        throw new RuntimeException(e);
                    }
                }
                step = 1;
            }
            case 1: {
                //TODO recv messaage from operationAgent
                step = 2;
            }
        }

    }

    @Override
    public boolean done() {
        return step == 2;
    }
}
