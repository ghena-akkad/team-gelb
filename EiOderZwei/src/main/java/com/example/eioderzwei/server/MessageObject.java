package com.example.eioderzwei.server;
import java.io.Serializable;
import java.util.List;
public class MessageObject implements Serializable {
    private String senderId;
    private String messageText;
    private List<String> mentionedUsers;
    public MessageObject(String senderId, String messageText, List<String> mentionedUsers) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.mentionedUsers = mentionedUsers;
    }
    public MessageObject(){

    }
    // Getters and Setters
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId){
        this.senderId = senderId;
    }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText){
        this.messageText = messageText;
    }
    public List<String> getMentionedUsers() { return mentionedUsers; }
    public void setMentionedUsers(List<String> mentionedUsers){
        this.mentionedUsers = mentionedUsers;
    }

}