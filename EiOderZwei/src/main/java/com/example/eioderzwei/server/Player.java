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
    private boolean turn;
    private boolean won;
    private int eiNum;


    // ID von User im Spiel

    public int playerId;


    public Player(String username){
        userName = username;
        won = false;
        turn = false;
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

    public boolean won() {
        return won;
    }

    public void setWon(boolean state){
        won = state;
    }
    public boolean ifPlayersTurn() {
        return turn;
    }
    public synchronized void setPlayersTurn(Boolean state) {
        turn = state;
    }



    public int getEiNum(){
        return eiNum;
    }
    public void setEiNum(int eiNum1){
        eiNum = eiNum1;
    }

    //TODO IllegalMoveException und NotThePlayersTurnException implementieren und einbetten

    public void eiLegen(Card card)  {
        if (ifPlayersTurn()){
            getHand().remove(card);
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

