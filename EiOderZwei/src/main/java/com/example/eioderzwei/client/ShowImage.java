package com.example.eioderzwei.client;

import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//TODO Diese Klasse muss noch überprüft werden
/**
 * Dies ist die Klasse, die jeden Kartennamen mit seinem Bild verknüpft.
 */
public enum ShowImage {
    DUMMY_CARD("dummyCard", "/Users/akkad/Downloads/jpg123.png");
    private String cardName;
    private Image cardImage;

    ShowImage(String cardName, String imageLoc) {
        this.cardName = cardName;
        try {
            this.cardImage = new Image(new FileInputStream(imageLoc));
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Handle the exception according to game logic later on
        }
    }

    public String getCardName() {
        return cardName;
    }
    public Image getImage() {
        return cardImage;
    }
    public static String readCardName(Image image) {
        for (ShowImage s: ShowImage.values()) {
            if (s.getImage().equals(image)) {
                return s.getCardName();
            }
        }
        return null;
    }
    public static Image findImage(String cardName) {
        for (ShowImage s : ShowImage.values()) {
            if (s.getCardName().equals(cardName)) {
                return s.getImage();
            }
        }
        return DUMMY_CARD.getImage(); // Return the dummy card image if not found
    }
}