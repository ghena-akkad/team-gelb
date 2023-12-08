package com.example.eioderzwei.client;
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
    private Label nameLabel, eier;
    @FXML
    private ImageView deck;
    @FXML
    private ImageView ablage;


    @FXML
    private ImageView Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10, Karte11, Karte12;
    @FXML


    public int i = 0, deck_number_of_cards = 12, ablage_number_of_cards = 0, x=100;
    private String cardNameSelected;
    private ImageView selectedCard;
    private ArrayList<Card> handwerte = new ArrayList<>() ;//Karten des Spielers
    //selected Cards merkt sich nach dem auswählen den Index der ausgewählten Karte dann kann man mit diesem index Hand und Handwerte nach dem richtigen ELement durchsuchen
    private ArrayList<Integer> selectedCards= new ArrayList<>();
    private DrawPile

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

    public void draw(MouseEvent actionEvent) throws FileNotFoundException {
        Card crnt = Deck1.drawCard();
        ArrayList<ImageView> crnthand = hand();
        if(i<11){
            if (crnt.getType()==CardType.FOX){
                System.out.println("steal");
            } else if (crnt.getType()==CardType.CUCKOO) {
                y++;
                eier.setText("Eier:" + y + "/9");
            }
            else{
                FileInputStream input = new FileInputStream(crnt.getImagePath());
                Image img = new Image(input);
                ImageView view=crnthand.get(i);
                view.setImage(img);
                crnthand.set(i, view);
                i++;

                handwerte.add(new Card(crnt.getType()));
            }
        }
    }
    public void abwerfen(MouseEvent actionEvent) throws IOException {
        ArrayList<ImageView> B = new ArrayList<>(hand());
        removeEffect();
        int eierW =eierAusgabe();
        if (eierW == 0){
            System.out.println("stopp");
            selectedCards.clear();
        }
        else {
            for (int k = 0; k < selectedCards.size(); k++) {
                int a = selectedCards.get(k);
                System.out.println(a);
                if ((a >= 0) && (a < 12)) {
                    //Images korrigieren alle nach a eins nach links
                    for (int j = a - 1; j < 11; j++) {
                        ImageView view = new ImageView();
                        view = B.get(j);
                        ImageView view1 = new ImageView();
                        view1 = B.get(j + 1);
                        view.setImage(view1.getImage());
                        view1.setImage(null);
                    }
                    //Handwerte korrigieren alle werte nach a eins nach links(iwas is falsch)
                    for(int j= a-1;j+1<handwerte.size();j++) {
                        Card tmp2 = handwerte.get(j + 1);
                        handwerte.set(j, tmp2);
                        handwerte.set(j+1, null);
                    }
                    i--;
                }
                reduce(a);
            }
            selectedCards.clear();
            //Eier eingeben y ist die Anzahl der Eier
            y=y+eierW;
            if (y >= 9) {
                System.out.println("You won");
                y=9;
            }
            eier.setText("Eier:" + y + "/9");
        }
    }

    public int eierAusgabe(){
        int sum=0;
        boolean bio = true;
        for (int i=0; i<selectedCards.size(); i++){
            int b = selectedCards.get(i)-1;
            Card wert1 = handwerte.get(b);
            sum = sum + wert1.getWert();
            if (!(wert1.getBio())) {bio=false;}
        }
        int a=1;
        if (sum <5){
            //throw Exception;
        }
        for(int j=10;j<=100;j=j+5){
            if((sum>=0)&&(sum<j)&&(bio)){
                return a*2;
            } else if ((sum>=0)&&(sum<j)) {
                return a;
            }
            a++;
        }
        return a;
    }

    public void reduce(int a){
        for (int i=0; i<selectedCards.size(); i++){
            if (a<selectedCards.get(i)) {
                selectedCards.set(i, selectedCards.get(i) - 1);
            }
        }
    }

    public void selectCard(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        addEffect(image);
        ArrayList<ImageView> B = new ArrayList<>(hand());
        for(int j=0; j<12;j++){
            if ((image==B.get(j))&& !(selectedCards.contains(j+1))){
                x=j+1;
                selectedCards.add(x);
                break;
            }
        }
        addEffect(image);
        System.out.println(selectedCards);
    }

    public void addEi (ActionEvent event) {
        if(y<=8) {
            y++;
            eier.setText("Eier:" + y + "/9");
        }
        if (y == 9) {
            System.out.println("You won");

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

    public void addEffect(ImageView image){
        int depth = 70;
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.BLUE);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);
        image.setEffect(borderGlow);
    }
/*
    public void selectedCardInHand(@NotNull MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        cardNameSelected = ShowImage.readCardName(image.getImage());
        setEffect(event, Color.RED);
    }
*/
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
    public void addEi (ActionEvent event) {
        if(y<=8) {
            y++;
            eier.setText("Eier:" + y + "/9");
        }
        if (y == 9) {
            System.out.println("You won");

        }
    }

    public void setUserName(String userName) {
        nameLabel.setText(userName);
    }
}
