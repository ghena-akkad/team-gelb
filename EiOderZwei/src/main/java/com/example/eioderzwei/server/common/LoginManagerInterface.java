package com.example.eioderzwei.server.common;
import java.io.Serializable;

import com.example.eioderzwei.server.exceptions.PlayerNameAlreadyExistsException;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface LoginManagerInterface extends Remote, Serializable {


    void loginPlayer(String playerName, String hashedPassword) throws RemoteException, PlayerNameAlreadyExistsException, NoSuchAlgorithmException;





}
