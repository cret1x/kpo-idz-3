package agents;

import jade.core.AID;
import jade.core.Agent;

public class CustomerAgent extends Agent {
    private String name;
    private AID id;

    CustomerAgent(String name) {
        this.name = name;
        this.id = new AID(name, AID.ISLOCALNAME);
    }
}
