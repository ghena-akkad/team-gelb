package com.example.eioderzwei.server;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;


enum CardType {
    TWONORMAL,
    THREENORMAL,
    FOURNORMAL,
    ONEBIO,
    TWOBIO,
    THREEBIO,
    FOX,
    CUCKOO,
    ROOSTER
}
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
// TODO Paths zu Images anpassen

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

    public boolean is_same(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return type == card.type;
    }
}
