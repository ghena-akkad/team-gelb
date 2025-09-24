package com.example.eioderzwei.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.eioderzwei.server.Player;
/* TODO: rankList als Map/Hashmap, initialize im Konstruktor,
    1) methode für update von rankList
    2)methode get_rankList, die rankList zurückgibt


*/

public class GameRoom {
    private final String gameName;
    private final int requiredNumberOfPlayers;

    private int numberPlayers;
    private List<String> playerIds;
    private Map<String, Player> playerMap;
    private String roosterCardHolder;
    private String victim;
    private String stealer;
    private boolean gezogen;

    private DrawPile drawPile;
    private DiscardPile discardPile;

    private boolean gameStarted;
    private int botNumber;
    private int currentPlayerIndex = 0;
    private Player winner;
    private boolean fox_prompt;
    private boolean need_prompt;
    private boolean fox_karte_gezogen;
    private HashMap<String, Integer> rankList;

    public GameRoom(String gameName, int botNumber, int requiredNumberOfPlayers) {

        rankList = new HashMap<>();
        this.gameName = gameName;
        this.botNumber = botNumber;
        this.requiredNumberOfPlayers = requiredNumberOfPlayers;

        this.gameStarted = false;
        this.playerIds = new ArrayList<>();
        this.playerMap = new HashMap<>();
        this.drawPile = new DrawPile();
        this.discardPile = new DiscardPile();
        this.fox_prompt = false;
        this.need_prompt=false;
        this.fox_karte_gezogen = false;

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
    public int getPlayersIndex(String name){
        int i = 0;
        for(String id : playerIds){
            if(id.equals(name)){
                return i;
            }
            i++;
        }
        return -1;

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
    public boolean hasFoxPrompt(){
        return fox_prompt;
    }
    public void set_foxPrompt(boolean b){
        this.fox_prompt = b;
    }
    public boolean ifPromptRequired(){
        return need_prompt;
    }
    public void set_foxKarteGezogen(boolean b){
        this.fox_karte_gezogen = b;
    }
    public boolean get_foxKarteGezogen(){
        return fox_karte_gezogen;
    }
    public void setPromptRequired(boolean b){

        this.need_prompt = b;
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
    public DiscardPile getDiscardPile() {


        return discardPile;
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

    public String get_victim() {
        return victim;
    }
    public void set_victim(String victim) {
        this.victim= victim;
    }
    public String get_stealer() {
        return stealer;
    }
    public void set_stealer(String stealer) {
        this.stealer= stealer;
    }

    public boolean getGezogen() {
        return gezogen;
    }

    public void setGezogen(boolean gezogen) {
        this.gezogen = gezogen;
    }
    public List<Map.Entry<String, Integer>> getRankList() {
        return rankList.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }
    public void updateRankList() {
        rankList.clear();
        for (String playerId : playerIds) {
            Player player = playerMap.get(playerId);
            rankList.put(playerId, player.getScore());
        }
    }


}
