import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Server - main: Registry created");


        } catch (Exception e) {
            System.err.println("RMI Server exception");
            e.printStackTrace();
        }
        System.out.println("RMI Server - main Terminierung");
    }


    }
