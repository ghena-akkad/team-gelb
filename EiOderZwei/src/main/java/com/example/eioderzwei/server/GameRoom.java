package com.example.eioderzwei.server;


// TODO: Folgende Methoden zu implementieren:
//  get_number_of_players, set_number_of_players,
//  get_number_of_bots, set_number_of_bots,
//  has_game_started(returns boolean), get_gamename, get_players,
//  add_player, remove_player
//  Konstruktor erzeugt DrawPile Instanz
//

import java.util.ArrayList;
import java.util.Objects;

public class GameRoom {
    private final String gameName;
    private final String gamePassword;
    private final int maxPlayers;
    private ArrayList<String> playerSet;
    //falls verlassen wiederbeitrittsmöglichkeit nach der Runde ?
    private ArrayList<String> waitingList;
    private Deck deck;
    private boolean round;
    private boolean game;
    private int botCounter;
    public GameRoom(String pGameName, String pGamePassword, int pMaxPlayers) {
        gameName = pGameName;
        gamePassword = pGamePassword;
        maxPlayers = pMaxPlayers;
        botCounter = 0;
        round = false;
        game = false;
        playerSet = new ArrayList<>();
        waitingList = new ArrayList<>();
        deck = new Deck();
    }
    public void setBotCounter(int botCounter) {
        this.botCounter = botCounter;
    }
    public void increaseBotCounter(){
        botCounter++;
    }
    public boolean hasGameStarted() {
        return game;
    }
    public boolean hasRoundStarted() {
        return round;
    }
    public void startGame() {
        game = true;
    }
    public void startRound() {
        round = true;
    }
    public void finishGame() {
        game = false;
    }
    public void finishRound() {
        round = false;
    }

    public int getBotCounter() {
        return botCounter;
    }

    public Deck getDeck() {
        return deck;
    }
    public String getGameName() {
        return gameName;
    }

    public String getGamePassword(){
        return gamePassword;
    }

    public int getMaxPlayers(){
        return maxPlayers;
    }

    public ArrayList<String> getPlayerSet(){
        return playerSet;
    }

    public ArrayList<String> getWaitingList(){
        return waitingList;
    }
    public void addPlayer(String userIdent){
        playerSet.add(userIdent);
    }
    public void addPlayerToWaitingList(String userIdent){
        waitingList.add(userIdent);
    }
    public void removePlayer(String userIdent) {
        playerSet.remove(userIdent);
    }
    public void removePlayerFromWaitingList(String userIdent){
        waitingList.remove(userIdent);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRoom gameRoom = (GameRoom) o;
        return Objects.equals(gameName, gameRoom.gameName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName);
    }


}
