package com.example.eioderzwei.server;
import com.example.eioderzwei.server.common.Communication;
import com.example.eioderzwei.server.common.GameManagerInterface;
import com.example.eioderzwei.server.common.LoginManagerInterface;
import com.example.eioderzwei.server.common.RoomsManagerInterface;

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
            GameManager gm = new GameManager();

            Communication stub = (Communication) UnicastRemoteObject.exportObject(com, 0);

            LoginManagerInterface stub1 = (LoginManagerInterface) UnicastRemoteObject.exportObject(lm, 0);

            RoomsManagerInterface stub2 = (RoomsManagerInterface) UnicastRemoteObject.exportObject(rm, 0);

            GameManagerInterface stub3 = (GameManagerInterface) UnicastRemoteObject.exportObject(gm, 0);

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("server", stub);

            registry.rebind("logman", stub1);

            registry.rebind("roomman", stub2);

            registry.rebind("gameman", stub3);




            System.out.println("Server Main gestartet.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new Server();
    }
}


