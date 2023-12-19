package com.example.eioderzwei.server;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;


/**
 * Repräsentation einer Spielkarte
 */
public class Card {
    private final CardType type;
    private final String back = "EiOderZwei/src/main/java/com/example/eioderzwei/image/egg.png";


    public Card(CardType type) {

        this.type = type;
    }
    public CardType getType(){
        return type;
    }
    private static final Map<CardType, String> cardImageMap = new HashMap<>();
// TODO Ei noch hinzufügen ?

    static {
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
    public String getImagePath() {
        return cardImageMap.get(type);
    }

    public Map<CardType, String> getCardImageMap(){
        return cardImageMap;
    }
    public boolean isHahnCard(){
        return type == CardType.ROOSTER;

    }
    public String getDescription() {
        switch (type) {
            case TWONORMAL:
                return "2 Grain";
            case THREENORMAL:
                return "3 Grain";
            case FOURNORMAL:
                return "4 Grain";
            case ONEBIO:
                return "1 Bio Grain";
            case TWOBIO:
                return "2 Bio Grain";
            case THREEBIO:
                return "3 Bio Grain";
            case FOX:
                return "Fox";
            case CUCKOO:
                return "Cuckoo";
            case ROOSTER:
                return "Rooster";
            default:
                return "Unknown Card";
        }
    }
    public int getWert() {
        switch (type) {
            case TWONORMAL:
                return 2;
            case THREENORMAL:
                return 3;
            case FOURNORMAL:
                return 4;
            case ONEBIO:
                return 1;
            case TWOBIO:
                return 2;
            case THREEBIO:
                return 3;
            default:
                return 0; // Default value for cards without a specific grain value
        }
    }

    public boolean getBio() {
        return type == CardType.ONEBIO ||
               type == CardType.TWOBIO ||
               type == CardType.THREEBIO;
    }

    public boolean is_same(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return type == card.type;
    }
    public boolean isGrainCard() {
        return type == CardType.TWONORMAL ||
                type == CardType.THREENORMAL ||
                type == CardType.FOURNORMAL ||
                type == CardType.ONEBIO ||
                type == CardType.TWOBIO ||
                type == CardType.THREEBIO;
    }
    public boolean isBio() {
        return type == CardType.ONEBIO ||
                type == CardType.TWOBIO ||
                type == CardType.THREEBIO;
    }

}
