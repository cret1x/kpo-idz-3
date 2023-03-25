package agents;

import behaviours.ManageSuppliesBehaviour;
import entities.OperProduct;
import entities.Product;
import entities.ProductType;
import jade.core.Agent;
import util.DFHelper;

import java.util.ArrayList;

public class StorageAgent extends Agent {
    public static final String AGENT_TYPE = "storage";
    public static final String AGENT_NAME = "Storage-agent";
    public ArrayList<Product> products;
    public ArrayList<ProductType> productsTypes;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 1) {
            products = (ArrayList<Product>) args[0];
            productsTypes = (ArrayList<ProductType>) args[1];
        }
        System.out.println(AGENT_NAME + " " + getAID().getName() + " is ready.");
        DFHelper.register(this, AGENT_TYPE, AGENT_NAME);
        addBehaviour(new ManageSuppliesBehaviour());
        System.out.println("Current Storage: ");
        for(var a : this.products) {
            System.out.println(a.type() + ": " + a.name() + " : " + a.quantity());
        }
    }

    /**
     * Reserves specific products form storage and returns List of products that are empty
     * @param products Products to update
     * @return Empty products
     */
    public ArrayList<Long> reserve(ArrayList<OperProduct> products) {
        ArrayList<Long> empty = new ArrayList<>();
        for(var p : products) {
            this.products.stream().filter(x -> x.type() == p.type()).forEach(x -> x.rmQuantity(p.quantity()));
            if (this.products.stream().filter(x -> x.type() == p.type()).anyMatch(x -> x.quantity() <= 0)) {
                empty.addAll(this.products.stream().map(Product::type).filter(type -> type == p.type()).toList());
            }
        }
        /*System.out.println("Current Storage: ");
        for(var a : this.products) {
            System.out.println(a.type() + ": " + a.name() + " : " + a.quantity());
        }
        System.out.println(empty);*/
        return empty;
    }


    protected void takeDown() {
        System.out.println(AGENT_NAME + " " + getAID().getName() + " terminating.");
    }
}
