package com.example.eioderzwei.server.common;
import com.example.eioderzwei.server.exceptions.*;


import java.io.Serializable;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RoomsManagerInterface extends Remote, Serializable {


    void createRoom(String roomName,  int botNumber, int playerNumber) throws RemoteException;


    void joinRoom(String roomName, String userIdent) throws RemoteException, RoomDoesNotExistException;


    void deleteRoom(String roomName) throws RemoteException;



     void ifRoomIsFull(String roomName) throws RemoteException, RoomIsFullException;

     void ifRoomExists(String roomName) throws RemoteException, RoomNameAlreadyExistsException;

     int getPlayersNumber (String roomName);

    }
