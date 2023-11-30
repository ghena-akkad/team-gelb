package com.example.eioderzwei;
import javafx.animation.TranslateTransition;
import javafx.animation.Interpolator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.util.List;
public class gameRoomController implements Initializable {
    @FXML
    public Button ab1;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView deck;
    @FXML
    private ImageView ablage;


    @FXML
    private ImageView Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10, Karte11, Karte12;
    @FXML


    public int i = 0, deck_number_of_cards = 12, ablage_number_of_cards = 0;
    private String cardNameSelected;
    private ImageView selectedCard;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/com/example/eioderzwei/image/egg.png");) {
            Image img = new Image(input);
            deck.setImage(img);
            //ablage.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public List<ImageView> hand() {
        return List.of(Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10, Karte11, Karte12);
    }

    public ImageView addCardToHand(Image img) {

        i++;

        return hand().get(i - 1);



    }


    public void hallo(MouseEvent actionEvent) throws IOException {
        try (FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/com/example/eioderzwei/image/egg.png")) {
            Image img = new Image(input);
            ImageView im = addCardToHand(img);
            moveCardAnimationDeckToHand(deck, im, img);
            deck_number_of_cards--;

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }

    private void moveCardAnimationDeckToHand(ImageView source, ImageView destination, Image cardImage) {
        ImageView movingCard = new ImageView(cardImage);
        movingCard.setFitHeight(100);
        movingCard.setFitWidth(75);
        movingCard.setPreserveRatio(true);



        movingCard.setLayoutX(source.getLayoutX());
        movingCard.setLayoutY(source.getLayoutY());
        AnchorPane anchorPane = (AnchorPane) source.getParent();
        anchorPane.getChildren().add(movingCard);

        TranslateTransition transition = new TranslateTransition(Duration.millis(400), movingCard);

        transition.setToX(destination.getLayoutX() - source.getLayoutX());
        transition.setToY(destination.getLayoutY() - source.getLayoutY());

        transition.setOnFinished(event -> {
            anchorPane.getChildren().remove(movingCard);
            destination.setImage(cardImage);
            if (deck_number_of_cards == 0) {
                source.setImage(null);
            }

        });
        transition.play();
    }

    public void removeCardFromHand() {

        ablage_number_of_cards++;


    }


    private void moveCardAnimationHandToAblage(ImageView source, ImageView destination, Image cardImage, Boolean b) {
        ImageView movingCard = new ImageView(cardImage);

        movingCard.setFitHeight(100);
        movingCard.setFitWidth(75);
        movingCard.setPreserveRatio(true);

        movingCard.setLayoutX(source.getLayoutX());
        movingCard.setLayoutY(source.getLayoutY());
        AnchorPane anchorPane = (AnchorPane) source.getParent();
        anchorPane.getChildren().add(movingCard);

        TranslateTransition transition = new TranslateTransition(Duration.millis(400), movingCard);

        transition.setToX(destination.getLayoutX() - source.getLayoutX());
        transition.setToY(destination.getLayoutY() - source.getLayoutY());


        transition.setOnFinished(event -> {
            anchorPane.getChildren().remove(movingCard);


            destination.setImage(cardImage);
            source.setImage(null);



            if(b) {
                for (int k = i; k < 12; k++) {
                    hand().get(k).setImage(null);

                }


                for (int k = 0; k < i; k++) {
                    hand().get(k).setImage(cardImage);

                }
            }

        });
        transition.play();
    }


    public void tschuss(MouseEvent actionEvent) throws IOException {
        try (FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/com/example/eioderzwei/image/egg.png")) {
            Image img = new Image(input);
            removeCardFromHand();
            ImageView im = (ImageView) actionEvent.getSource();
            i--;


            moveCardAnimationHandToAblage(im, ablage, img, true);




        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }


    public void abwerfen(MouseEvent actionEvent) throws IOException {
        try (FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/com/example/eioderzwei/image/egg.png")) {
            Image img = new Image(input);
            while (i > 0) {
                removeCardFromHand();
                i--;
                ImageView im = hand().get(i - 1);

                moveCardAnimationHandToAblage(im, ablage, img, false);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();

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
            selectedCard = clickedImageView;
            setEffect(event, Color.RED);
        } else {
            selectedCard.setEffect(null);
            clickedImageView.setImage(selectedCard.getImage());
            selectedCard.setImage(null);
            selectedCard = null;
        }
    }

    public void setUserName(String userName) {
        nameLabel.setText(userName);
    }
}
