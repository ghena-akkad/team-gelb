package com.example.eioderzwei.server;

import java.util.Stack;

public class DiscardPile {
    private Stack<Card> discardPile = new Stack<>();
    public void discard_card(Card card) {
        discardPile.push(card);
    }
    public Card viewTopCard() {
        return discardPile.isEmpty() ? null : discardPile.peek();
    }
    public int getNumCards() {
        return discardPile.size();
    }
}
