package com.example.eioderzwei.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatManagerHelper implements Serializable {
    private LoginManagerHelper loginManagerHelper;
    private RoomsManagerHelper roomsManagerHelper;
    private static HashMap<String, ArrayList<String>> messages;
    public ChatManagerHelper(){
        messages = new HashMap<>();
    }
    public void addGameRoom(String gameRoomName){
        messages.put(gameRoomName, new ArrayList<String>());
    }
    public synchronized void sendTheMessage(String gameRoomName, String userID, String message) {
        if (messages.containsKey(gameRoomName)) {
            String userName = String.valueOf(loginManagerHelper.getPlayer(userID));
            ArrayList<String> chatRoomMessages = messages.get(gameRoomName);
            chatRoomMessages.add(userName + ": " + message);
            messages.replace(gameRoomName, chatRoomMessages);
            List<String> mentionedUsers = parseMentions(message);
            MessageObject newMessage = new MessageObject(userID, message, mentionedUsers);
        }
    }
    private List<String> parseMentions(String message) {
        List<String> mentionedUsers = new ArrayList<>();
        String[] words = message.split("\\s+");
        for (String word : words) {
            if (word.startsWith("@")) {
                mentionedUsers.add(word.substring(1)); // Remove '@' and add to list
            }
        }
        return mentionedUsers;
    }
    public synchronized ArrayList<String> getChatMessages(String gameRoomName) {
        if (messages.containsKey(gameRoomName)) {
            return messages.get(gameRoomName);
        } else {
            return new ArrayList<>(); // Return an empty list if the game room name is not found
        }
    }
}

