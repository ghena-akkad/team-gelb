package com.example.eioderzwei.server;

import java.util.Map;

/**
 * Spiellogik
 */


// TODO: Folgende Methoden zu implementieren:
//  .
//  distribute_cards: Verteilt die Startkarten an die Spieler zu Beginn des Spiels
//  has_game_started: Überprüft, ob das Spiel bereits begonnen hat.
//  get_players_card: Gibt die Karten des angegebenen Spielers zurück.
//  give_turn: Wechselt den Spielzug zum nächsten Spieler.
//  start_turn : Startet den Spielzug für einen bestimmten Spieler
//  is_turn: Überprüft, ob es derzeit der Spielzug eines bestimmten Spielers ist.
//  draw_card: Zieht eine Karte vom Ziehstapel.
//  getNumberOfPlayers : Gibt die Anzahl der Spieler im Spiel zurück.
//  getWinner : Gibt den  des Spiels zurück, falls vorhanden.
//  winnerExists:  Überprüft, ob es bereits einen Gewinner gibt
//  howManyEggs(Spieler): Gibt die Anzahl der Eier von Spieler zurück
//  ei_legen Methode und die Methode die überprüft ob das möglich ist(genug Kernkarten vorhanden)
//  give_rooster_card: Ein Spieler bekommt die Hahnkarte.
//  want_rooster_card: Hahnkarte nehmen, falls erlaubt
//  steal_card
//  choosePlayerToSteal: Wer eine Fuchskarte zieht, darf bei einem Mitspieler Körnerkarte klauen
//  choose_cards_to_steal

//

//

//


public class GameManager {

    private RoomsManager roomsManager;
    private LoginManager loginManager;

    private Map<CardType, String> cardImageMap;

    public void  get_map(){
        cardImageMap.put(CardType.TWONORMAL, "com/example/eioderzwei/image/2Korn.png");
        cardImageMap.put(CardType.THREENORMAL, "com/example/eioderzwei/image/3Korn.png");
        cardImageMap.put(CardType.FOURNORMAL, "com/example/eioderzwei/image/4Korn.png");
        cardImageMap.put(CardType.ONEBIO, "com/example/eioderzwei/image/1BioKorn.png");
        cardImageMap.put(CardType.TWOBIO, "com/example/eioderzwei/image/2BioKörner.png");
        cardImageMap.put(CardType.THREEBIO, "com/example/eioderzwei/image/3BioKorn.png");
        cardImageMap.put(CardType.FOX, "com/example/eioderzwei/image/Fucks.png");
        cardImageMap.put(CardType.CUCKOO, "com/example/eioderzwei/image/Kuckuck.png");
        cardImageMap.put(CardType.ROOSTER, "com/example/eioderzwei/image/Hahnkarte.png");
    }




}

