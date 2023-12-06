package com.example.eioderzwei.server;


import java.io.Serializable;
import java.util.ArrayList;
/**
 * Klasse für Spieler
 */

// TODO: Speichern wie viele Eier Karten der Spieler gesammelt hat

public class Player {
    private  String name;
    private ArrayList<Card> hand;
    private boolean playersTurn;
    private boolean ready;
    private boolean wonGame;
    private int playedCards;

    public Player(String username){
        name = username;
        wonGame = false;
        playersTurn = false;
        ready = false;
        playedCards = 0;
        hand = new ArrayList<>();
    }

    public String getUsername() {
        return name;
    }
    public ArrayList<Card> getHand() {
        return hand;
    }
    public boolean hasHahnCard(){
        for(Card card: hand){
            if( card.isHahnCard()){
                return true;
            }
        }
        return false;
    }
    public void addCardToHand(Card card){
        hand.add(card);
    }
    public void removeCard(Card card) {
        hand.remove(card);
    }

    public void setWonGame(boolean state){
        wonGame = state;
    }

}

