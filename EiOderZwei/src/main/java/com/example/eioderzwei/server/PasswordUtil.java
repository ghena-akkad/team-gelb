package com.example.eioderzwei.server;

import com.example.eioderzwei.server.common.PasswordUtilInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.rmi.RemoteException;

public class PasswordUtil implements PasswordUtilInterface {
    @Override
    public String hashPassword(String password) throws RemoteException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RemoteException("Error hashing password", e);
        }
    }
}
