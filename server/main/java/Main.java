import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Main implements Communication {
    public Main() {
        try {
            Communication stub = (Communication) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("server", stub);
            System.out.println("Server Main gestartet.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println("Nachricht vom Client: " + message);
    }

    public static void main(String[] args) {
        new Main();
    }
}