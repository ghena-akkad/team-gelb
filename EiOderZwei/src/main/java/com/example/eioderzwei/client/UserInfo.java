package com.example.eioderzwei.client;

public class UserInfo {

    private static String username;
    private static String roomname;

    private static int playerNumber;





    public static String getUsername() {
        return username;
    }
    public static void setUserName(String username) {
        UserInfo.username = username;
    }

    public static int getPlayNumber() {
        return playerNumber;
    }
    public static void setPlayNumber(int p) {
        UserInfo.playerNumber = p;
    }

    public static String getRoomname() {
        return roomname;
    }
    public static void setRoomname(String roomname) {
        UserInfo.roomname = roomname;
    }
}
