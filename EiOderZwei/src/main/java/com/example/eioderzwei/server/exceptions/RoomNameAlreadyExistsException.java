package com.example.eioderzwei.server.exceptions;


/**
 * Exception, die ausgelöst wird, wenn versucht wird, einen neuen Spielraum zu erstellen,
 * aber der gewählte Name bereits von einem vorhandenen Spielraum verwendet wird.
 */

public class RoomNameAlreadyExistsException extends Exception {




    public RoomNameAlreadyExistsException(String message) {
        super(message);
    }

}