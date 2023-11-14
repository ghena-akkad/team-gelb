import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class Main {
    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry();}
        catch(Exception e){
            System.out.println("RMI Client exception");
            e.printStackTrace();
        }

        System.out.println("RMI Client - main Terminierung");

    }
}