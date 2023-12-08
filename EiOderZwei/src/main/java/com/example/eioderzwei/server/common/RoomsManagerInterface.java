package com.example.eioderzwei.server.common;
import com.example.eioderzwei.client.PlayerNameAlreadyExists;
import com.example.eioderzwei.client.RoomDoesNotExist;
import com.example.eioderzwei.client.RoomNameAlreadyExists;


import java.io.Serializable;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RoomsManagerInterface extends Remote, Serializable {


    void createRoom(String roomName,  int botNumber) throws RemoteException;


    void joinRoom(String roomName, String userIdent) throws RemoteException, RoomDoesNotExist;


    void deleteRoom(String roomName) throws RemoteException;

     void ifRoomExists(String roomName) throws RemoteException, RoomNameAlreadyExists;

    }
