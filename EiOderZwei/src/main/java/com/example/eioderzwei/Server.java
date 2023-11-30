package com.example.eioderzwei;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;
import java.rmi.RemoteException;


public class Server {
    public Server() {
        try {
            ComImpl com = new ComImpl();
            Communication stub = (Communication) UnicastRemoteObject.exportObject(com, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("server", stub);
            System.out.println("Server Main gestartet.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new Server();
    }
}


