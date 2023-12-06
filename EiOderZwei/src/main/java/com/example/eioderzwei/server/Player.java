package com.example.eioderzwei.server;

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
    private DrawPile drawPile;
    private DiscardPile discardPile;


    // ID von User im Spiel

    public int playerId;


    public Player(String username, DrawPile drawPile1, DiscardPile discardPile1){
        userName = username;
        wonGame = false;
        playersTurn = false;
        ready = false;
        playedCards = 0;
        hand = new ArrayList<>();
        eiNum = 0;
        this.drawPile = drawPile1;
        this.discardPile = discardPile1;
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

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCardToHand(Card card){
        hand.add(drawPile.drawCard()); //hier wird eine karte gezogen, richtig so ?
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
    public void eiLegen(Card card)  {
        if (isPlayersTurn()) {
            if (getHand().contains(card)) { // Check if the card is in the player's hand
                getHand().remove(card);
                discardPile.discard_card(card);
                playedCards++;
                eiNum++;
            }
        }
    }
    public Card transferRoosterCard() {
        for (Card card : hand) {
            if (card.getType() == CardType.ROOSTER) {
                hand.remove(card);
                return card;
            }
        }
        return null; // No ROOSTER card found in the hand
    }
    public void hahnBeanspruchen(Player otherPlayer) {
        if (isPlayersTurn() && hasHahnCard() && eiNum < otherPlayer.getEiNum()) {
            Card roosterCard = otherPlayer.transferRoosterCard();
            hand.add(roosterCard);
        }
    }
    public void karteZiehen(){
        if (isPlayersTurn()) {
            Card drawnCard = drawPile.drawCard();
            if (drawnCard != null) {
                hand.add(drawnCard);
            }
        }
    }
    public void endTurnOfPlayer(){
        if (isPlayersTurn()) {
            setPlayersTurn(false);
        }
    }
    public void receiveCard(Card card) {
        hand.add(card);
    }
    //this method is for stealing cards from other players
    public void transferCard(Player recipient, Card card) {
        if (hand.contains(card)) {
            hand.remove(card); // remove from source player
            recipient.receiveCard(card); // add to recipient player
        }
    }
}
