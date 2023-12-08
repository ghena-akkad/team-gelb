package com.example.eioderzwei.client;

public class PlayerNameAlreadyExists extends Exception {


    public PlayerNameAlreadyExists(String message) {
        super(message);
    }


    public PlayerNameAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

}