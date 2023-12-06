package com.example.eioderzwei.server;

public class Bot extends Player implements Runnable{


    private GameRoom gameRoom;
    private String difficulty;
    private boolean flag;

    public Bot(String pUsername, String pPassword){
        super(pUsername, pPassword);
    }

    public String getDifficulty() {
        return difficulty;
    }
    public GameRoom getGameRoom() {
        return gameRoom;
    }
    public synchronized void setFlag(boolean pFlag){
        flag = pFlag;
    }
    public synchronized boolean getFlag(){
        return flag;
    }
    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void run() {
    }
}
