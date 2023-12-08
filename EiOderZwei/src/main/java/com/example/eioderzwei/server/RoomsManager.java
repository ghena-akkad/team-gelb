package com.example.eioderzwei.server;

import com.example.eioderzwei.client.PlayerNameAlreadyExists;
import com.example.eioderzwei.client.RoomDoesNotExist;
import com.example.eioderzwei.client.RoomNameAlreadyExists;
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
    public void createRoom(String roomName, int botNumber)  {
        if (!gameRooms.containsKey(roomName)) {
            GameRoom newRoom = new GameRoom(roomName, botNumber);
            gameRooms.put(roomName, newRoom);
        } else {
            System.out.println("Der Raum mit diesem Namen existiert bereits. Bitte wähle einen anderen Namen.");
        }
    }

    /**
     * Tritt einem bestehenden Spielzimmer bei.
     *
     * @param roomName Der Name des Spielzimmers, dem beigetreten werden soll.
     * @return Das Spielzimmer, dem beigetreten wurde, oder null, wenn das Spielzimmer nicht existiert.
     */
    public void joinRoom(String roomName, String username) throws RoomDoesNotExist {
        if (gameRooms.containsKey(roomName)) {
             GameRoom gameroom = gameRooms.get(roomName);
             gameroom.addPlayer(username);
        } else {
            throw new RoomDoesNotExist("Der Raum mit diesem Namen existiert nicht.");

        }
    }
    public void ifRoomExists(String roomName) throws RoomNameAlreadyExists{
        if (!gameRooms.containsKey(roomName)) {
    } else {
            throw new RoomNameAlreadyExists("Der Raum mit diesem Namen existiert bereits. Bitte wähle einen anderen Namen.");
    }

    }

    /**
     * Löscht ein Spielzimmer.
     *
     * @param roomName Der Name des zu löschenden Spielzimmers.
     * @return Das gelöschte Spielzimmer oder null, wenn das Spielzimmer nicht existiert.
     */
    public void deleteRoom(String roomName) {
        if (gameRooms.containsKey(roomName)) {
            gameRooms.remove(roomName);
        } else {
            System.out.println("Der Raum mit dem Namen " + roomName + " existiert nicht.");
        }
    }
}
