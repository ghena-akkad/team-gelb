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

    public int getWert(){
        String nam = getImagePath();
        int a = 0;
        String zeig1 = "One";
        String zeig2 = "Two";
        String zeig3 = "Three";
        String zeig4 = "Four";
        if (nam.contains(zeig1)){a=1;} else if (nam.contains(zeig2)){a=2;} else if (nam.contains(zeig3)){a=3;} else if (nam.contains(zeig4)){a=4;}
        return a;
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
}
