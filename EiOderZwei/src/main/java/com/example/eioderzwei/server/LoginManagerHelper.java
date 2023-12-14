package com.example.eioderzwei.server;

import com.example.eioderzwei.server.exceptions.PlayerNameAlreadyExistsException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginManagerHelper {

    private static Map<String, Player> players;


    public LoginManagerHelper() {
        players = new HashMap<>();


    }
    public boolean isNameTaken(String playerName) {
        return players.containsKey(playerName);
    }

    public void loginPlayer(String playerName, String password) throws PlayerNameAlreadyExistsException, NoSuchAlgorithmException {
        if (!isNameTaken(playerName)) {
            String hashedPassword = hashPassword(password);
            Player newPlayer = new Player(playerName, hashedPassword);
            players.put(playerName, newPlayer);
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public  Player getPlayer(String name)  {

        Player player = players.get(name);
        return player;

    }
}
