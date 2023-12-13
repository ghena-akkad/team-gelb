package com.example.eioderzwei.server;

import com.example.eioderzwei.server.exceptions.RoomDoesNotExistException;
import com.example.eioderzwei.server.exceptions.RoomNameAlreadyExistsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomsManagerHelper {
    private static Map<String, GameRoom>  gameRooms; // Hier werden die Spiele gespeichert


    public RoomsManagerHelper() {

        gameRooms = new HashMap<>();
    }
    /**
     * Erstellt ein neues Spielzimmer mit dem angegebenen Namen.
     *
     * @param roomName Der Name des neuen Spielzimmers.
     */

    public void createRoom(String roomName, int botNumber, int playerNumber)  {

        if (!gameRooms.containsKey(roomName)) {
            GameRoom newRoom = new GameRoom(roomName, botNumber, playerNumber);

            gameRooms.put(roomName, newRoom);

        } else {
            System.out.println("Der Raum mit diesem Namen existiert bereits. Bitte wähle einen anderen Namen.");
        }
    }
    /**
     * Überprüft, ob ein Spielraum mit dem angegebenen Namen bereits existiert.
     */

    public boolean ifRoomExists(String roomName)   {
        if (gameRooms.containsKey(roomName)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean ifRoomIsFull(String roomName)   {
        if (!(getPlayersNumber(roomName) == gameRooms.get(roomName).getRequiredNumberOfPlayers())) {
            return false;
        } else {
            return true;
        }
    }

    public  GameRoom getGameroom(String roomname) throws RoomDoesNotExistException {
        System.out.println("starting getGameeroom");

        if (gameRooms.containsKey(roomname)) {
            GameRoom gameroom = gameRooms.get(roomname);
            System.out.println("getGameroom ok");

            return gameroom;
        } else {
            throw new RoomDoesNotExistException("Der Raum mit diesem Namen existiert nicht.");

        }
    }
    public int getPlayersNumber(String roomName) {
        return gameRooms.get(roomName).getPlayersNumber();
    }

    public int getRequiredPlayersNumber(String roomName) {
        return gameRooms.get(roomName).getRequiredNumberOfPlayers();
    }

    public List<String> getPlayers(String roomName) {
        return gameRooms.get(roomName).getPlayers();
    }


    public void deleteRoom(String roomName) {
        if (gameRooms.containsKey(roomName)) {
            gameRooms.remove(roomName);
        } else {
            System.out.println("Der Raum mit dem Namen " + roomName + " existiert nicht.");
        }
    }
}
