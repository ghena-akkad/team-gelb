package com.example.eioderzwei;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Communication extends Remote {
    void sendMessage(String message) throws RemoteException;
}

