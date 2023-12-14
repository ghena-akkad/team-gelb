package com.example.eioderzwei.client;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatManagerInterface extends Remote, Serializable {
    void sendMessage(String gameRoomName, String playerId, String msg) throws RemoteException;
    List<String> getMessages(String gameRoomName) throws RemoteException;
}