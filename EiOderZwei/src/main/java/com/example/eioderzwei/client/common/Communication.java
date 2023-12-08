package com.example.eioderzwei.client.common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;


public interface Communication extends Remote , Serializable{
    void sendMessage(String message) throws RemoteException;
}

