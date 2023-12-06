package com.example.eioderzwei.server.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RoomsManagerInterface extends Remote {


    void createRoom(String roomName,  int botNumber) throws RemoteException;


    void joinRoom(String roomName, String userIdent) throws RemoteException;


    void deleteRoom(String roomName) throws RemoteException;
}
