import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import main.Restaurant;

public class Main {

    public static void main(String[] args) {
        final Runtime rt = Runtime.instance();
        final Profile p = new ProfileImpl();
        rt.setCloseVM(true);
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "8080");
        p.setParameter(Profile.GUI, "false");
        ContainerController containerController = rt.createMainContainer(p);
        Restaurant restaurant = new Restaurant("D:\\fuck_me\\", containerController);
        restaurant.initAgents();
    }
}