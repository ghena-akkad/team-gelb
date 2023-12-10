package com.example.eioderzwei.server;

import java.util.Collections;
import java.util.Stack;

/**
 * Die Klasse DrawPile repräsentiert den Ziehstapel im Kartenspiel.
 * Der Ziehstapel enthält Karten, von denen die Spieler in ihrem Zug Karten ziehen können.
 */
public class DrawPile {

    // Stack zur Speicherung der Karten im Ziehstapel
    private Stack<Card> drawPile = new Stack<>();

    public DrawPile() {
        create();
    }

    /**
     * Methode zum Erstellen eines neuen Ziehstapels und Hinzufügen von Karten gemäß den Spielregeln.
     */
    public void create() {
        addCards(CardType.TWONORMAL, 10);
        addCards(CardType.THREENORMAL, 10);
        addCards(CardType.FOURNORMAL, 10);

        addCards(CardType.ONEBIO, 8);
        addCards(CardType.TWOBIO, 10);
        addCards(CardType.THREEBIO, 10);

        addCards(CardType.FOX, 13);

        addCards(CardType.ROOSTER, 4);

        // Mischen des Ziehstapels
        Collections.shuffle(drawPile);
    }

    /**
     * Methode zum Ziehen einer obersten Karte vom Ziehstapel.
     */
    public Card drawCard() {
        return drawPile.isEmpty() ? null : drawPile.pop();
    }

    /**
     * Methode zur Überprüfung, ob der Ziehstapel leer ist.

     */
    public boolean drawPileIsEmpty() {
        return drawPile.isEmpty();
    }

    /**
     * Hilfsmethode zum Hinzufügen einer bestimmten Anzahl von Karten eines bestimmten Typs zum Ziehstapel.
     */
    private void addCards(CardType type, int count) {
        for (int i = 0; i < count; i++) {
            drawPile.push(new Card(type));
        }
    }
}

