package com.example.eioderzwei.server;




import java.util.ArrayList;
import java.util.Objects;

public class GameRoom {
    private final String gameName;
    private final int maxPlayers = 5 ;
    private final int minPlayers = 2 ;

    private  int numberPlayers ;

    private ArrayList<String> players;
    private DrawPile drawPile;
    private DiscardPile discardPile;

    private boolean gamestarted;
    private int botNumber;

    public GameRoom(String GameName, int botNumber) {
        this.gameName = GameName;
        this.botNumber = botNumber;
        gamestarted = false;
        players = new ArrayList<>();
        numberPlayers = 0;
        drawPile = new DrawPile();
    }
    public void setBotCounter(int botNumber) {
        this.botNumber = botNumber;
    }
    public void increaseBotCounter(){
        botNumber++;
    }
    public int getPlayersNumber() {
       return numberPlayers;
    }

    public boolean hasGameStarted() {
        return gamestarted;
    }

    public void startGame() {
        gamestarted = true;
    }

    public void finishGame() {
        gamestarted = false;
    }


    public int getBotNumber() {
        return botNumber;
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }
    public String getGameName() {
        return gameName;
    }


    public int getMaxPlayers(){
        return maxPlayers;
    }

    public ArrayList<String> getPlayerSet(){
        return players;
    }

    public void addPlayer(String userIdent){
        players.add(userIdent);
        numberPlayers++;
    }
    public void removePlayer(String userIdent) {
        players.remove(userIdent);
        numberPlayers--;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRoom gameRoom = (GameRoom) o;
        return Objects.equals(gameName, gameRoom.gameName);
    }





}
