package com.example.eioderzwei.server.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface PasswordUtilInterface extends Remote {
    String hashPassword(String password) throws RemoteException;
}