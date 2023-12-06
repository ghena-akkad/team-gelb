package com.example.eioderzwei.server;

import java.util.Collections;
import java.util.Stack;

public class DrawPile {
    private Stack<Card> drawPile = new Stack<>();

    public DrawPile() {
        create();
    }

    // Erstellt einen neuen Ziehstapel
    public void create() {
        addCards(CardType.TWONORMAL, 10);
        addCards(CardType.THREENORMAL, 10);
        addCards(CardType.FOURNORMAL, 10);

        addCards(CardType.ONEBIO, 8);
        addCards(CardType.TWOBIO, 10);
        addCards(CardType.THREEBIO, 10);

        addCards(CardType.FOX, 13);

        addCards(CardType.ROOSTER, 4);

        Collections.shuffle(drawPile);
    }

    // Zieht eine oberste Karte vom Ziehstapel.
    public Card drawCard() {
        return drawPile.isEmpty() ? null : drawPile.pop();
    }

    // Überprüft, ob der Ziehstapel leer ist
    public boolean drawPileIsEmpty() {
        return drawPile.isEmpty();
    }

    // Helper method to add a specified number of cards of a given type to the drawPile
    private void addCards(CardType type, int count) {
        for (int i = 0; i < count; i++) {
            drawPile.push(new Card(type));
        }
    }
}
