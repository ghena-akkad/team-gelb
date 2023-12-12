package com.example.eioderzwei.server;

import com.example.eioderzwei.server.exceptions.*;
import com.example.eioderzwei.server.common.RoomsManagerInterface;
import java.util.HashMap;
import java.util.Map;

public class RoomsManager implements RoomsManagerInterface {

    private Map<String, GameRoom> gameRooms; // Hier werden die Spiele gespeichert

    public RoomsManager() {
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

    //TODO RoomDoesNotExistException still needs to be implemented
    public  GameRoom getGameroom(String roomname) throws RoomDoesNotExistException {
        if (gameRooms.containsKey(roomname)) {
            GameRoom gameroom = gameRooms.get(roomname);
            return gameroom;
        } else {
            throw new RoomDoesNotExistException("Der Raum mit diesem Namen existiert nicht.");

        }
    }

    /**
     * Tritt einem bestehenden Spielzimmer bei.

     */
    public void joinRoom(String roomName, String username) throws RoomDoesNotExistException, RoomIsFullException {
        if (gameRooms.containsKey(roomName)) {
            GameRoom gameroom = gameRooms.get(roomName);

            // Check if the room is full before adding a new player
            if (!gameroom.isRoomFull()) {
                // Create a new Player object. Adjust this based on your Player class constructor.
                Player newPlayer = new Player(username);

                // Add the player to the GameRoom
                gameroom.addPlayer(username, newPlayer);
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
        if (!gameRooms.containsKey(roomName)) {
        } else {
            throw new RoomNameAlreadyExistsException("Der Raum mit diesem Namen existiert bereits. Bitte wähle einen anderen Namen.");
        }
    }

    /**
     * Überprüft, ob ein Spielraum bereits die maximale Anzahl von Spielern erreicht hat.

     */
    public boolean ifRoomIsFull(String roomName)   {
        if (!(getPlayersNumber(roomName) == gameRooms.get(roomName).getRequiredNumberOfPlayers())) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Gibt die Anzahl der Spieler in einem bestimmten Spielraum zurück.

     */
    public int getPlayersNumber(String roomName) {
        return gameRooms.get(roomName).getPlayersNumber();
    }


    /**
     * Löscht ein Spielzimmer.

     */
    public void deleteRoom(String roomName) {
        if (gameRooms.containsKey(roomName)) {
            gameRooms.remove(roomName);
        } else {
            System.out.println("Der Raum mit dem Namen " + roomName + " existiert nicht.");
        }
    }
}
