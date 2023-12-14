package com.example.eioderzwei.server;

import com.example.eioderzwei.server.common.ChatManagerInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
public class ChatManager implements ChatManagerInterface {
    private ChatManagerHelper chatManagerHelper;

    public ChatManager(){
        this.chatManagerHelper = new ChatManagerHelper();
    }

    @Override
    public synchronized void sendMessage(String gameRoomName, String userID, String message) throws RemoteException{
            chatManagerHelper.sendTheMessage(gameRoomName, userID, message);
    }
    @Override
    public synchronized ArrayList<String> getMessages(String gameRoomName) throws RemoteException {
        return chatManagerHelper.getChatMessages(gameRoomName);
    }
}
