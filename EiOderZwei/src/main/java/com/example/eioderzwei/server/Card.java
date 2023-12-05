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
        cardImageMap.put(CardType.TWONORMAL, "path/to/two_normal_image.png");
        cardImageMap.put(CardType.THREENORMAL, "path/to/three_normal_image.png");
        cardImageMap.put(CardType.FOURNORMAL, "path/to/four_normal_image.png");
        cardImageMap.put(CardType.ONEBIO, "path/to/one_bio_image.png");
        cardImageMap.put(CardType.TWOBIO, "path/to/two_bio_image.png");
        cardImageMap.put(CardType.THREEBIO, "path/to/three_bio_image.png");
        cardImageMap.put(CardType.FOX, "path/to/fox_image.png");
        cardImageMap.put(CardType.CUCKOO, "path/to/cuckoo_image.png");
        cardImageMap.put(CardType.ROOSTER, "path/to/rooster_image.png");
    }
    public String getImagePath() {
        return cardImageMap.get(type);
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
