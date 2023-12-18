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
    public void addGameRoom(String roomname){
        messages.put(roomname, new ArrayList<String>());
    }
    public synchronized void sendMessage(String roomname, String username, String message) {
        if (messages.containsKey(roomname)) {
            ArrayList<String> chatMessages = messages.get(roomname);
            chatMessages.add(username + ": " + message);
            messages.replace(roomname, chatMessages);
            List<String> mentionedUsers = parseMentions(message);
            MessageObject newMessage = new MessageObject(username, message, mentionedUsers);
        }
    }
    private List<String> parseMentions(String message) {
        List<String> mentionedUsers = new ArrayList<>();
        String[] words = message.split("\\s+");
        for (String word : words) {
            if (word.startsWith("@")) {
                mentionedUsers.add(word.substring(1));
            }
        }
        return mentionedUsers;
    }
    public synchronized ArrayList<String> getChatMessages(String roomname) {
        if (messages.containsKey(roomname)) {
            return messages.get(roomname);
        } else {
            return new ArrayList<>();
        }
    }
}

