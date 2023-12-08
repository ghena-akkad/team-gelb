package com.example.eioderzwei.server.common;
import java.io.Serializable;
import com.example.eioderzwei.client.PlayerNameAlreadyExists;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginManagerInterface extends Remote, Serializable {


    void loginPlayer(String playerName) throws RemoteException, PlayerNameAlreadyExists;


    boolean isNameTaken(String playerName) throws RemoteException;


    void logoutPlayer(String playerName) throws RemoteException;
}
