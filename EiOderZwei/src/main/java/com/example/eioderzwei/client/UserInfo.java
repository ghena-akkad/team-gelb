package com.example.eioderzwei.client;

/**
 * Diese Klasse speichert Informationen über den Benutzer, wie Benutzername,
 * Spielraumname und Anzahl der Spieler.
 */
public class UserInfo {

    // Benutzername des Spielers
    private static String username;

    // Spielraumname, in dem der Spieler sich befindet
    private static String roomname;

    // Anzahl der Spieler, die der Benutzer im Spiel haben möchte
    private static int playerNumber;

    /**
     * Gibt den Benutzernamen des Spielers zurück.

     */
    public static String getUsername() {
        return username;
    }

    /**
     * Setzt den Benutzernamen des Spielers.

     */
    public static void setUserName(String username) {
        UserInfo.username = username;
    }

    /**
     * Gibt die gewünschte Anzahl der Spieler im Spiel zurück.

     */
    public static int getPlayNumber() {
        return playerNumber;
    }

    /**
     * Setzt die gewünschte Anzahl der Spieler im Spiel.

     */
    public static void setPlayNumber(int p) {
        UserInfo.playerNumber = p;
    }

    /**
     * Gibt den Namen des Spielraums zurück, in dem sich der Spieler befindet.

     */
    public static String getRoomname() {
        return roomname;
    }

    /**
     * Setzt den Namen des Spielraums, in dem sich der Spieler befindet.
     */
    public static void setRoomname(String roomname) {
        UserInfo.roomname = roomname;
    }
}
