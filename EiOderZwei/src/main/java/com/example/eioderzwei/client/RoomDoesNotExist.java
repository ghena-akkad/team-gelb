package com.example.eioderzwei.client;

public class RoomDoesNotExist extends Exception {


    public RoomDoesNotExist(String message) {
        super(message);
    }


    public RoomDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

}