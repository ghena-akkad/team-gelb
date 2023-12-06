package com.example.eioderzwei.server;
import com.example.eioderzwei.server.common.LoginManagerInterface;

import java.util.HashMap;
import java.util.Map;



public class LoginManager implements LoginManagerInterface {


    // Hier werden die Spieler gespeichert

    private Map<String, Player> players;


    public LoginManager() {
        players = new HashMap<>();

    }
    public void loginPlayer(String playerName) {
        if (!isNameTaken(playerName)) {
            Player newPlayer = new Player(playerName);
            players.put(playerName, newPlayer);

        } else {
            System.out.println("Der Name ist bereits besetzt. Bitte wähle einen anderen Namen.");
        }
    }
    public boolean isNameTaken(String playerName) {
        return players.containsKey(playerName);
    }
    public void logoutPlayer(String playerName) {
        if (players.containsKey(playerName)) {
            players.remove(playerName);
        } else {
            System.out.println("Der Spieler mit dem Namen " + playerName + " existiert nicht.");
        }
    }
}
