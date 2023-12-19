package com.example.eioderzwei.server;

import com.example.eioderzwei.server.exceptions.*;
import com.example.eioderzwei.server.common.RoomsManagerInterface;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomsManager implements RoomsManagerInterface {

    private RoomsManagerHelper helper;
    private LoginManagerHelper loginManager;


    public RoomsManager() {
        this.helper = new RoomsManagerHelper();
        this.loginManager = new LoginManagerHelper();

    }

    /**
     * Erstellt ein neues Spielzimmer mit dem angegebenen Namen.
     *
     * @param roomName Der Name des neuen Spielzimmers.
     */
    public void createRoom(String roomName, int botNumber, int playerNumber) {
        helper.createRoom(roomName, botNumber, playerNumber);
    }

    //TODO RoomDoesNotExistException still needs to be implemented


    /**
     * Tritt einem bestehenden Spielzimmer bei.
     */
    public void joinRoom(String roomName, String username) throws RoomDoesNotExistException, RoomIsFullException {
        if (helper.ifRoomExists(roomName)) {
            GameRoom gameroom = helper.getGameroom(roomName);
            if (!gameroom.isRoomFull()) {
                Player player = loginManager.getPlayer(username);
                gameroom.addPlayer(username, player);
            } else {
                throw new RoomIsFullException("Raum ist schon voll");
            }
        } else {
            throw new RoomDoesNotExistException("Der Raum mit diesem Namen existiert nicht.");
        }
    }

    /**
     * Überprüft, ob ein Spielraum mit dem angegebenen Namen bereits existiert.
     */
    public void ifRoomExists(String roomName) throws RoomNameAlreadyExistsException {
        if (!helper.ifRoomExists(roomName)) {
        } else {
            throw new RoomNameAlreadyExistsException("Der Raum mit diesem Namen existiert bereits. Bitte wähle einen anderen Namen.");
        }
    }


    /**
     * Gibt die Anzahl der Spieler in einem bestimmten Spielraum zurück.
     */
    public int getPlayersNumber(String roomName) {
        return helper.getPlayersNumber(roomName);
    }
    public int getRequiredPlayersNumber(String roomName) {
        return helper.getRequiredPlayersNumber(roomName);
    }

    public List<String> getPlayers(String roomName) {
        return helper.getPlayers(roomName);
    }


    /**
     * Löscht ein Spielzimmer.
     */
    public void deleteRoom(String roomName) {
        helper.deleteRoom(roomName);
    }
}
