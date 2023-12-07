package com.example.eioderzwei.server;


import java.io.Serializable;
import java.util.ArrayList;
/**
 * Klasse für Spieler
 */

public class Player {
    private  final String userName;
    //private final String passWord;
    private ArrayList<Card> hand;
    private boolean playersTurn;
    private boolean ready;
    private boolean wonGame;
    private int playedCards;
    private int eiNum;


    // ID von User im Spiel

    public int playerId;


    public Player(String username){
        userName = username;
        wonGame = false;
        playersTurn = false;
        ready = false;
        playedCards = 0;
        hand = new ArrayList<>();
        eiNum = 0;
    }



    public int getPlayerId(){
        return playerId ;
    }
    public void setPlayerId(int id){
         playerId = id;
    }


    public boolean hasHahnCard(){
        for(Card card: hand){
            if( card.isHahnCard()){
                return true;
            }
        }
        return false;
    }

    public String getUsername() {
        return userName;
    }

    /*public String getPassword() {
        return passWord;
    }*/
    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCardToHand(Card card){
        hand.add(card);
    }

    public void setWonGame(boolean state){
        wonGame = state;
    }
    public boolean isPlayersTurn() {
        return playersTurn;
    }
    public synchronized void setPlayersTurn(Boolean state) {
        playersTurn = state;
    }
    public boolean hasWonGame() {
        return wonGame;
    }
    public boolean isReady() {
        return ready;
    }
    public void setReady() {
        ready = true;
    }
    public void setUnReady() {
        ready = false;
    }
    public int getPlayedCards() {
        return playedCards;
    }
    public void resetPlayedCards() {
        playedCards = 0;
    }
    public int getEiNum(){
        return eiNum;
    }
    public void setEiNum(int eiNum1){
        eiNum = eiNum1;
    }
    //TODO IllegalMoveException und NotThePlayersTurnException implementieren und einbetten
    public void eiLegen(Card card)  {
        if (isPlayersTurn()){
            getHand().remove(card);
            playedCards++;
            eiNum++;
        }
    }
    public void hahnBeanspruchen(){

    }
    public void karteZiehen(){

    }
    public void endTurnOfPlayer(){

    }
}

