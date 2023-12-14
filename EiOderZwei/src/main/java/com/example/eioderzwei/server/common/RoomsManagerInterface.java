package com.example.eioderzwei.server.common;
import com.example.eioderzwei.server.exceptions.*;


import java.io.Serializable;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RoomsManagerInterface extends Remote, Serializable {


    void createRoom(String roomName,  int botNumber, int playerNumber) throws RemoteException;


    void joinRoom(String roomName, String userIdent, String hashedPassword) throws RemoteException, RoomDoesNotExistException, RoomIsFullException;


    void deleteRoom(String roomName) throws RemoteException;

    List<String> getPlayers(String roomName) throws RemoteException;

     int getRequiredPlayersNumber(String roomName)throws RemoteException;




    void ifRoomExists(String roomName) throws RemoteException, RoomNameAlreadyExistsException;

     int getPlayersNumber (String roomName) throws RemoteException;

    }
