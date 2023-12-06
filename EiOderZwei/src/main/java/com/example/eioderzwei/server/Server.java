package com.example.eioderzwei.server;
import com.example.eioderzwei.server.common.Communication;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;


public class Server {
    public Server() {
        try {
            ComImpl com = new ComImpl();
            LoginManager lm = new LoginManager();
            RoomsManager rm = new RoomsManager();

            Communication stub = (Communication) UnicastRemoteObject.exportObject(com, 0);
            Communication stub1 = (Communication) UnicastRemoteObject.exportObject(lm, 0);
            Communication stub2 = (Communication) UnicastRemoteObject.exportObject(rm, 0);

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("server", stub);
            registry.rebind("logman", stub1);
            registry.rebind("roomman", stub2);

            System.out.println("Server Main gestartet.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new Server();
    }
}


