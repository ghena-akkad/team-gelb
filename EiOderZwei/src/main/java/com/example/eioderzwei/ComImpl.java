package com.example.eioderzwei;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

/** Beispiel Klasse, um zu zeigen, dass die Kommunikation zwischen Client und Server funktioniert*/

public class ComImpl implements Communication {


    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println("Nachricht vom Client: " + message);
    }


}

