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
    public synchronized void sendMessage(String room, String username, String message) throws RemoteException{
            chatManagerHelper.sendMessage(room, username, message);
    }
    @Override
    public synchronized ArrayList<String> receiveMessages(String room) throws RemoteException {
        return chatManagerHelper.getChatMessages(room);
    }
}
