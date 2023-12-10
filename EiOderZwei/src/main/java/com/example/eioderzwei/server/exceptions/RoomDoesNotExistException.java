package com.example.eioderzwei.server.exceptions;
/**
        * Exception, die ausgelöst wird, wenn versucht wird, auf einen Spielraum zuzugreifen, der nicht existiert.
        */
public class RoomDoesNotExistException extends Exception {


    public RoomDoesNotExistException(String message) {
        super(message);
    }


}
