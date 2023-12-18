package com.example.eioderzwei.server.common;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatManagerInterface extends Remote, Serializable {
    void sendMessage(String gameRoomName, String playerId, String msg) throws RemoteException;
    List<String> receiveMessages(String gameRoomName) throws RemoteException;
}
