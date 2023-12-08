package com.example.eioderzwei.client.common;
import java.io.Serializable;




import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginManagerInterface extends Remote, Serializable {


    void loginPlayer(String playerName) throws RemoteException;


    boolean isNameTaken(String playerName) throws RemoteException;


    void logoutPlayer(String playerName) throws RemoteException;
}
