package com.example.eioderzwei;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.fxml.Initializable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    @FXML
    private Label eier,playerNameInGameRoom;
    @FXML
    private ImageView Karte1, Karte2, Karte3, Karte4, Karte5, Karte6,Karte7,Karte8, Karte9, Karte10, Karte11,Karte12;
    @FXML
    private ImageView deck;
    @FXML
    private ImageView ablage;

    public int i=0, x=100, y=0;
    private String cardNameSelected;
    private ImageView selectedCard;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (FileInputStream input = new FileInputStream("src/image/egg.png");) {
            Image img = new Image(input);
            deck.setImage(img);
            ablage.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    //Die ImageViews, die die Hand des Spielers ausmachen als Arraylist anordnen
    public ArrayList<ImageView> hand (){
        ArrayList <ImageView> A = new ArrayList<>();
        A.add(Karte1);A.add(Karte2);A.add(Karte3);A.add(Karte4);A.add(Karte5);A.add(Karte6);A.add(Karte7);A.add(Karte8);A.add(Karte9);A.add(Karte10);A.add(Karte11);A.add(Karte12);
        return A;
    }

    public void removeEffect() {
        hand().forEach(imageView -> imageView.setEffect(null));
    }

    //Die Ausgewählte Karte hervorheben
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

    //Vorübergehende Funktion um zu Ziehen (später über Math.rdm das Array, das das Deck darstellt durchgehen und daraus Random ziehen)
    public void hallo(MouseEvent actionEvent) throws FileNotFoundException {
        FileInputStream input = new FileInputStream("src/image/2Körner.png");
        Image img = new Image(input);
        FileInputStream input2 = new FileInputStream("src/image/2BioKörner.png");
        Image img2 = new Image(input2);

        if(i<12){
            i++;
        }
        switch (i) {
            case 1:
                Karte1.setImage(img2);
                break;
            case 2:
                Karte2.setImage(img);
                break;
            case 3:
                Karte3.setImage(img2);
                break;
            case 4:
                Karte4.setImage(img);
                break;
            case 5:
                Karte5.setImage(img2);
                break;
            case 6:
                Karte6.setImage(img);
                break;
            case 7:
                Karte7.setImage(img2);
                break;
            case 8:
                Karte8.setImage(img);
                break;
            case 9:
                Karte9.setImage(img2);
                break;
            case 10:
                Karte10.setImage(img);
                break;
            case 11:
                Karte11.setImage(img2);
                break;
            case 12:
                Karte12.setImage(img);
                break;
        }
    }


    //Abwerf Funktion(so gut wie final)
    public void abwerfen(MouseEvent actionEvent) throws IOException {
        ArrayList<ImageView> B = new ArrayList<>(hand());
        removeEffect();
        if ((x>=0)&&(x<12)) {
            for (int j = x-1; j < 11; j++) {
                ImageView view = new ImageView();
                view = B.get(j);
                ImageView view1 = new ImageView();
                view1 = B.get(j + 1);
                view.setImage(view1.getImage());
                view1.setImage(null);
            }
            ImageView last = new ImageView();
            last.setImage(null);
            x=100;
            i--;
        }
    }

    //Bestimmt welche Karte ausgewählt ist
    public void selectCard(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        ArrayList<ImageView> B = new ArrayList<>(hand());
        for(int j=0; j<12;j++){
            if (image==B.get(j)){
                x=j+1;break;
            }
        }
        removeEffect();
        addEffect(image);
    }


    public void addEi (ActionEvent event) {
        if(y<=8) {
            y++;
            eier.setText("Eier:" + y + "/9");
        }
        else{System.out.println("You won");}


    }

    public void setPlayerName(String playerName) {
        playerNameInGameRoom.setText("Player Name: " + playerName);
    }

}