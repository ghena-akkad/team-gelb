package com.example.eioderzwei.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Klasse für Spieler
 */

public class Player {
    public  final String userName;
    private ArrayList<Card> hand;
    private boolean turn;
    private boolean won;
    private int eggCount;
    public Player(String username){
        userName = username;
        won = false;
        turn = false;
        hand = new ArrayList<>();
        eggCount = 0;
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
    public int getEggCount(){
        return eggCount;
    }
    public void setEiNum(int eggCount1){
        eggCount = eggCount1;
    }
    public void incrementEggCountBy(int eggs) {
        this.eggCount += eggs; // Add the specified number of eggs to the current egg count
    }
    //TODO IllegalMoveException und NotThePlayersTurnException implementieren und einbetten
    public Card getRandomGrainCard() {
        ArrayList<Card> grainCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.isGrainCard()) { // Assuming isGrainCard method exists in Card class
                grainCards.add(card);
            }
        }
        if (!grainCards.isEmpty()) {
            Random rand = new Random();
            return grainCards.get(rand.nextInt(grainCards.size()));
        }
        return null;
    }
    public void removeCard(Card card) {
        hand.remove(card);
    }
    public Card chooseCardToSteal() {
        if (!hand.isEmpty()) {
            Random rand = new Random();
            return hand.get(rand.nextInt(hand.size()));
        }
        return null;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Player other = (Player) obj;
        return Objects.equals(userName, other.userName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}

