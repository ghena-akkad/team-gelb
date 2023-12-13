package com.example.eioderzwei.server;

import com.example.eioderzwei.server.exceptions.PlayerNameAlreadyExistsException;

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

    public void loginPlayer(String playerName) throws PlayerNameAlreadyExistsException {
        if (!isNameTaken(playerName)) {
            Player newPlayer = new Player(playerName);
            players.put(playerName, newPlayer);

        }
    }


    public  Player getPlayer(String name)  {

        Player player = players.get(name);
        return player;

    }
}
