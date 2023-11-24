package com.example.eioderzwei;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class gameRoomController {
    @FXML
    public Button ab1;
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10, Karte11, Karte12;
    @FXML
    private Button deck;
    @FXML
    private Button ablage;

    private int i = 0;
    private int x = 2;
    private String cardNameSelected;
    private ImageView selectedCard;

    @FXML
    public List<ImageView> hand() {
        return List.of(Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10, Karte11, Karte12);
    }

    public void addCardToHand(Image img) {
        if (i < 10) {
            i++;
            hand().get(i - 1).setImage(img);
        }
    }

    public void removeCardFromHand(int index) {
        if (index >= 0 && index < hand().size()) {
            hand().get(index).setImage(null);
            i--;
        }
    }

    public void hallo(ActionEvent actionEvent) throws IOException {
        try (FileInputStream input = new FileInputStream("/Users/akkad/Downloads/jpg123.png")) {
            Image img = new Image(input);
            addCardToHand(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public void tschuss(ActionEvent actionEvent) {
        removeCardFromHand(i - 1);
    }

    public void abwerfen(ActionEvent actionEvent) {
        List<ImageView> hand = hand();
        for (int j = x; j < 12; j++) {
            hand.get(j).setImage(hand.get(j + 1).getImage());
            hand.get(j + 1).setImage(null);
        }
    }

    public void removeEffect() {
        hand().forEach(imageView -> imageView.setEffect(null));
    }

    public void setEffect(MouseEvent event, Color color) {
        ImageView image = (ImageView) event.getSource();
        removeEffect();
        int depth = 70;
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(color);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);
        image.setEffect(borderGlow);
    }

    public void selectedCardInHand(@NotNull MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        cardNameSelected = ShowImage.readCardName(image.getImage());
        setEffect(event, Color.RED);
    }
    public void moveCard(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();

        if (selectedCard == null) {
            // First click (selecting the source card)
            selectedCard = clickedImageView;
            setEffect(event, Color.RED);
        } else {
            // Second click (moving the card to the destination)
            selectedCard.setEffect(null); // Remove the highlight from the source card
            clickedImageView.setImage(selectedCard.getImage());
            selectedCard.setImage(null);
            selectedCard = null; // Reset the selectedCard for the next move
        }
    }
}
