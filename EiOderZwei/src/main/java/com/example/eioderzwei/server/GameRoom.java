package com.example.eioderzwei.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.example.eioderzwei.server.Player;

public class GameRoom {
    private final String gameName;
    private final int requiredNumberOfPlayers;

    private int numberPlayers;
    private List<String> playerIds;
    private Map<String, Player> playerMap = new HashMap<>();
    private String roosterCardHolder;
    private DrawPile drawPile;
    private DiscardPile discardPile;

    private boolean gameStarted;
    private int botNumber;
    private int currentPlayerIndex = 0;
    private Player winner;

    public GameRoom(String gameName, int botNumber, int requiredNumberOfPlayers) {
        this.gameName = gameName;
        this.botNumber = botNumber;
        this.requiredNumberOfPlayers = requiredNumberOfPlayers;

        this.gameStarted = false;
        this.playerIds = new ArrayList<>();
        this.playerMap = new HashMap<>();
        this.drawPile = new DrawPile();
    }
    public boolean isRoomFull() {
        return playerIds.size() >= requiredNumberOfPlayers;
    }

    public void setBotNumber(int botNumber) {
        this.botNumber = botNumber;
    }

    public void increaseBotNumber() {
        botNumber++;
    }

    public int getPlayersNumber() {
        return numberPlayers;
    }

    public List<String> getPlayerIds() {
        return new ArrayList<>(playerIds);
    }
    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }
    public String getRoosterCardHolder() {
        return roosterCardHolder;
    }
    public void setRoosterCardHolder(String playerId) {
        this.roosterCardHolder = playerId;
    }

    public boolean hasGameStarted() {
        return gameStarted;
    }

    public void startGame() {
        gameStarted = true;
    }

    public void finishGame() {
        gameStarted = false;
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


    public int getRequiredNumberOfPlayers() {
        return requiredNumberOfPlayers;
    }

    public List<String> getPlayers() {
        return playerIds;
    }

    public void addPlayer(String username, Player player) {
        playerIds.add(username);
        playerMap.put(username, player);
        numberPlayers++;
    }

    public void removePlayer(String username) {
        playerIds.remove(username);
        playerMap.remove(username);
        numberPlayers--;
    }

    public void nextPlayerTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerIds.size();
    }
    public Player getCurrentPlayer () {
        String currentPlayerId = playerIds.get(currentPlayerIndex);
        return playerMap.get(currentPlayerId);
    }

    public void setCurrentPlayer(String playerId) {
        int index = playerIds.indexOf(playerId);
        if (index != -1) {
            currentPlayerIndex = index;
        }
    }

    public Player getWinner () {
        return winner;
    }

    public void setWinner (Player player){
        this.winner = player;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRoom gameRoom = (GameRoom) o;
        return Objects.equals(gameName, gameRoom.gameName);
    }
}
