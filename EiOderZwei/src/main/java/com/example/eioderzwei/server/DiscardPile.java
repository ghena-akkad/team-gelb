package com.example.eioderzwei.server;

import java.util.Stack;

/**
 * Die Klasse DiscardPile repräsentiert den Ablagestapel.
 * Auf dem Ablagestapel werden Karten abgelegt, die nicht mehr im Spiel sind.
 */
public class DiscardPile {

    // Stack zur Speicherung der abgelegten Karten
    private Stack<Card> discardPile = new Stack<>();

    /**
     * Methode zum Ablegen einer Karte auf dem Ablagestapel.

     */
    public void discard_card(Card card) {
        discardPile.push(card);
    }

    /**
     * Methode zum Anzeigen der obersten Karte auf dem Ablagestapel.

     */
    public Card viewTopCard() {
        return discardPile.isEmpty() ? null : discardPile.peek();
    }

    /**
     * Methode zur Abfrage der Anzahl der Karten auf dem Ablagestapel.

     */
    public int getNumCards() {
        return discardPile.size();
    }
}
