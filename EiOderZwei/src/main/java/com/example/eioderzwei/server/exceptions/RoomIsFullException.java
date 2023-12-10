package com.example.eioderzwei.server.exceptions;


/**
 * Eine Exception, die ausgelöst wird, wenn versucht wird, einem Spielraum einen Spieler hinzuzufügen,
 * aber der Spielraum bereits voll ist.
 */
public class RoomIsFullException extends Exception {




    public RoomIsFullException(String message) {
        super(message);
    }

}
