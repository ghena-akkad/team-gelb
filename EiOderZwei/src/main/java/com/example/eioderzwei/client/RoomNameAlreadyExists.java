package com.example.eioderzwei.client;

public class RoomNameAlreadyExists extends Exception {


    public RoomNameAlreadyExists(String message) {
        super(message);
    }


    public RoomNameAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

}