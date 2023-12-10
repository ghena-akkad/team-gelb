package com.example.eioderzwei.server.exceptions;
/**
 * Exception, die ausgelöst wird, wenn versucht wird, einen Spieler mit einem bereits
 * vorhandenen Benutzernamen zu erstellen.
 */
public class PlayerNameAlreadyExistsException extends Exception {


    public PlayerNameAlreadyExistsException(String message) {
        super(message);
    }
}
