package com.example.eioderzwei.server;
import com.example.eioderzwei.server.common.LoginManagerInterface;
import com.example.eioderzwei.server.exceptions.PlayerNameAlreadyExistsException;
import com.example.eioderzwei.server.exceptions.RoomDoesNotExistException;

import java.util.HashMap;
import java.util.Map;



public class LoginManager implements LoginManagerInterface {


    private LoginManagerHelper helper;


    public LoginManager() {
        this.helper = new LoginManagerHelper();

    }
    public void loginPlayer(String playerName) throws PlayerNameAlreadyExistsException {
       if(!helper.isNameTaken(playerName)) {
           helper.loginPlayer(playerName);


        } else {
            throw new PlayerNameAlreadyExistsException("Der Name ist bereits besetzt. Bitte wähle einen anderen Namen.");
        }
    }

    /*
    public void logoutPlayer(String playerName) {
        if (players.containsKey(playerName)) {
            players.remove(playerName);
        } else {
            System.out.println("Der Spieler mit dem Namen " + playerName + " existiert nicht.");
        }
    }

*/



}
