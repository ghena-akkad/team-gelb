package com.example.eioderzwei.client;
import com.example.eioderzwei.server.MessageObject; //TODO ist es ok diese Klasse zu importieren so ?
import com.example.eioderzwei.server.common.ChatManagerInterface;
import com.example.eioderzwei.server.common.GameManagerInterface;
import com.example.eioderzwei.server.common.RoomsManagerInterface;
import com.example.eioderzwei.server.exceptions.RoomDoesNotExistException;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

import javafx.scene.control.ButtonType;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.effect.DropShadow;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/* TODO neue Runde: Button hinzufügen



 */
public class gameRoomController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label nameLabelWest;
    @FXML
    private Label nameLabelNorth;
    @FXML
    private Label nameLabelEast;
    @FXML
    private ImageView deck;
    @FXML
    private ImageView ablage;
    @FXML
    private Label roomnamelabel;
    @FXML
    private Label gameStatus;
    @FXML
    private Label zuglabel;
    @FXML
    private Button roosterCardButton;
    private String roomName;
    private String username;

    @FXML
    private ImageView Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10,
            Karte11, Karte12, Karte13, Karte14, Karte15, Karte16, Karte17, Karte18, Karte19, Karte20,
            Karte21, Karte22, Karte23, Karte24, Karte25, Karte26, Karte27, Karte28, Karte29, Karte30,
            Karte31, Karte32, Karte33, Karte34, Karte35, Karte36, Karte37, Karte38, Karte39, Karte40, small0, small1, small2, small3;
    ImageView[] cardInHandImageViews;
    ImageView[] cardWestImageViews;
    ImageView[] cardNorthImageViews ;
    ImageView[] cardEastImageViews ;
    private List<ImageView> selectedCards;
    private ArrayList<String> selectedCardsString;
    private ImageView cardToSave;
    private String cardToSaveString;
    private ImageView cardToSteal;
    private String cardToStealString;

    @FXML
    private Label eggNumberNorth, eggNumberEast, eggNumberWest, eggNumberSouth;


    boolean used1;
    boolean used2;
    boolean used3;





    GameManagerInterface gameman;
    RoomsManagerInterface roomman;
    ChatManagerInterface chatman;
    @FXML
    private ListView<String> messagesListView; //TODO add to fxml file accordingly
    @FXML
    private TextField messageInputField;
    @FXML
    private Button sendButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button confirmButton2;
    @FXML
    private Label playerScoreLabelNorth;
    @FXML
    private Label playerScoreLabelSouth;
    @FXML
    private Label playerScoreLabelEast;
    @FXML
    private Label playerScoreLabelWest;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gameman = Client.getGameManager();
        this.roomman = Client.getRoomsManager();
        this.chatman = Client.getChatManager();
        username = UserInfo.getUsername();
        used1 = false;
        used2= false;
        used3=false;
        confirmButton.setVisible(false);
        confirmButton2.setVisible(false);

        eggNumberSouth.setText("0");
        eggNumberNorth.setText("0");
        eggNumberEast.setText("0");
        eggNumberWest.setText("0");
        eggNumberSouth.setVisible(false);
        eggNumberNorth.setVisible(false);
        eggNumberEast.setVisible(false);
        eggNumberWest.setVisible(false);
        small0.setVisible(false);
        small1.setVisible(false);
        small2.setVisible(false);
        small3.setVisible(false);
        roosterCardButton.setVisible(false);
        selectedCards = new ArrayList<>();
        selectedCardsString = new ArrayList<>();
        cardInHandImageViews = new ImageView[]{
                Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10};
        cardWestImageViews = new ImageView[]{
                Karte11, Karte12, Karte13, Karte14, Karte15, Karte16, Karte17, Karte18, Karte19, Karte20};
        cardNorthImageViews =new ImageView[] {
                Karte21, Karte22, Karte23, Karte24, Karte25, Karte26, Karte27, Karte28, Karte29, Karte30};
        cardEastImageViews =new ImageView[] {
                Karte31, Karte32, Karte33, Karte34, Karte35, Karte36, Karte37, Karte38, Karte39, Karte40};


        roomName = UserInfo.getRoomname();
        initThread();
        cardsThread();
        //startChatThread();
        try (FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/com/example/eioderzwei/image/egg.png")) {
            Image img = new Image(input);
            small0.setImage(img);
            small1.setImage(img);
            small2.setImage(img);
            small3.setImage(img);
            deck.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUserName(String userName) {
        nameLabel.setText(userName);
    }

    public void setRoomName(String roomname) {
        roomnamelabel.setText(roomname);
        this.roomName = roomname;
    }
    public void initThread() {
        synchronized (this) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Runnable updater = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<String> joinedPlayers = roomman.getPlayers(roomName);
                                updatePlayerLabels(joinedPlayers);
                                //if(gameman.getNumberOfPlayers(roomName)!=2){

                                if ((gameman.getRequiredNumberOfPlayers(roomName) > gameman.getNumberOfPlayers(roomName))) {
                                    gameStatus.setText("Warte auf die anderen Spieler");
                                } else {
                                    gameStatus.setText("Spiel läuft");


                                    eggNumberSouth.setVisible(true);
                                    //eggNumberNorth.setVisible(true);
                                    //eggNumberEast.setVisible(true);
                                    eggNumberWest.setVisible(true);
                                    small0.setVisible(true);
                                    small1.setVisible(true);
                                    small2.setVisible(true);
                                    small3.setVisible(true);
                                    roosterCardButton.setVisible(true);
                                    if(!gameman.has_game_started(roomName)){
                                        start();

                                    }else{
                                        if(gameman.get_rooster_holder(roomName).equals(username)){
                                            roosterCardButton.setDisable(true);
                                        }else{
                                            roosterCardButton.setDisable(false);

                                        }
                                        if(gameman.get_fox_karte_gezogen(roomName)){
                                            used3=true;
                                            if(gameman.get_victim(roomName).equals(username)&&!gameman.get_fox_prompt(roomName)&& gameman.get_prompt_required(roomName)){
                                                gameman.set_fox_prompt(roomName, true);
                                                promptVictimToChooseCard();

                                            }

                                        }else{
                                            used3=false;
                                        }


                                        zuglabel.setText(gameman.whose_turn(roomName));
                                        //int a = (gameman.howManyEggs(nameLabelEast.getText(),roomName));
                                        int b = (gameman.howManyEggs(nameLabelWest.getText(),roomName));
                                        int c = (gameman.howManyEggs(username,roomName));
                                        //int d = (gameman.howManyEggs(nameLabelNorth.getText(),roomName));

                                        //eggNumberEast.setText(Integer.toString(a));
                                        eggNumberWest.setText(Integer.toString(b));
                                        eggNumberSouth.setText(Integer.toString(c));
                                       // eggNumberNorth.setText(Integer.toString(d));


                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            } catch (RoomDoesNotExistException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                    while (true) {
                        try {
                            Thread.sleep(210);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Platform.runLater(updater);
                    }
                }
            });
            t1.start();
        }
    }

    public void start() {
        try {

            gameman.start(roomName);
            List<String> joinedPlayers = roomman.getPlayers(roomName);
            gameman.start_turn(joinedPlayers.get(0), roomName);
            zuglabel.setText(joinedPlayers.get(0));
            String i = gameman.showTopCard(roomName);
            FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/" + i);
            Image img = new Image(input);
            deck.setImage(img);
            gameman.initialize_rooster(roomName);
            gameman.set_victim(roomName, "");

        } catch (RoomDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void cardsThread(){
        synchronized (this) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Runnable updater = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(gameman.has_game_started(roomName)) {
                                    updateCardsSouth(gameman.get_players_card(username));

                                    updateCardsWest(gameman.get_players_card(nameLabelWest.getText()));
                                    //updateCardsEast(gameman.get_players_card(nameLabelEast.getText()));
                                    //updateCardsNorth(gameman.get_players_card(nameLabelNorth.getText()));
                                    updateDecks(gameman.showTopCard(roomName), gameman.showTopCardAblage(roomName));
                                }

                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            } catch (FileNotFoundException | RoomDoesNotExistException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
                    while (true) {
                        try {
                            Thread.sleep(220);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Platform.runLater(updater);
                    }
                }
            });
            t1.start();
        }
    }
    private void updateDecks(String s, String string) throws FileNotFoundException {
        if (string.isEmpty()) {
            ablage.setImage(null);
        } else {
            FileInputStream input2 = new FileInputStream("EiOderZwei/src/main/java/" + string);
            Image img2 = new Image(input2);
            ablage.setImage(img2);
        }
        if(s.isEmpty()){
            deck.setImage(null);
        }
        else{
            FileInputStream input1 = new FileInputStream("EiOderZwei/src/main/java/" + s);
            Image img1 = new Image(input1);
            deck.setImage(img1);
        }

    }

    private void updateCardsNorth(List<String> playersCard) throws FileNotFoundException {
        for (ImageView cardNorthImageView : cardNorthImageViews) {
            cardNorthImageView.setImage(null);
        }
        if (!playersCard.isEmpty()) {
            int n = 0;
            for (String card : playersCard) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/"+card);

                Image img = new Image(input);
                cardNorthImageViews[n].setImage(img);
                cardNorthImageViews[n].setUserData(card);
                n++;



            }
        }
    }

    private void updateCardsEast(List<String> playersCard) throws FileNotFoundException {
        for (ImageView cardEastImageView : cardEastImageViews) {
            cardEastImageView.setImage(null);
        }
        if (!playersCard.isEmpty()) {
            int n = 0;
            for (String card : playersCard) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/"+card);

                Image img = new Image(input);
                cardEastImageViews[n].setImage(img);
                cardEastImageViews[n].setUserData(card);
                n++;



            }
        }
    }
    private void updateCardsWest(List<String> playersCard) throws FileNotFoundException {
        for (ImageView cardWestImageView : cardWestImageViews) {
            cardWestImageView.setImage(null);
        }
        if (!playersCard.isEmpty()) {
            int n = 0;
            for (String card : playersCard) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/"+card);

                Image img = new Image(input);
                cardWestImageViews[n].setImage(img);
                cardWestImageViews[n].setUserData(card);
                n++;



            }
        }
    }
    private void updateCardsSouth(List<String> playersCard) throws FileNotFoundException {

        for (ImageView cardInHandImageView : cardInHandImageViews) {
            cardInHandImageView.setImage(null);
        }
        if (!playersCard.isEmpty()) {
            int n = 0;
            for (String card : playersCard) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/"+card);

                Image img = new Image(input);
                    cardInHandImageViews[n].setImage(img);
                    cardInHandImageViews[n].setUserData(card);
                    n++;



            }
        }
    }

    private void updatePlayerLabels(List<String> playerNames) {
        switch (playerNames.size()) {
            case 1:
                nameLabelWest.setText("Warten...");
                //nameLabelEast.setText("Warten...");
                //nameLabelNorth.setText("Warten...");
                break;
            case 2:
                if (playerNames.get(1).equals(username)) {
                    nameLabelWest.setText(playerNames.get(0));
                } else {
                    nameLabelWest.setText(playerNames.get(1));
                }
                //nameLabelEast.setText("Warten...");
                //nameLabelNorth.setText("Warten...");
                break;
            case 3:
                if (playerNames.get(1).equals(username)) {
                    nameLabelNorth.setText("Warten...");
                    nameLabelWest.setText(playerNames.get(2));
                    nameLabelEast.setText(playerNames.get(0));
                } else if (playerNames.get(0).equals(username)) {
                    nameLabelNorth.setText(playerNames.get(2));
                    nameLabelWest.setText(playerNames.get(1));
                    nameLabelEast.setText("Warten...");
                } else {
                    nameLabelNorth.setText(playerNames.get(0));
                    nameLabelEast.setText(playerNames.get(1));
                    nameLabelWest.setText("Warten...");
                }
                break;
            case 4:

                    if (playerNames.get(1).equals(username)) {
                        nameLabelNorth.setText(playerNames.get(3));
                        nameLabelWest.setText(playerNames.get(2));
                        nameLabelEast.setText(playerNames.get(0));
                    } else if (playerNames.get(0).equals(username)) {
                        nameLabelNorth.setText(playerNames.get(2));
                        nameLabelWest.setText(playerNames.get(1));
                        nameLabelEast.setText(playerNames.get(3));
                    } else if (playerNames.get(2).equals(username)) {
                        nameLabelNorth.setText(playerNames.get(0));
                        nameLabelWest.setText(playerNames.get(3));
                        nameLabelEast.setText(playerNames.get(1));
                    } else {
                        nameLabelNorth.setText(playerNames.get(1));
                        nameLabelEast.setText(playerNames.get(2));
                        nameLabelWest.setText(playerNames.get(0));
                    }

        }
    }


    public void chooseCard(MouseEvent actionEvent) throws RoomDoesNotExistException, RemoteException {
        if(gameman.has_game_started(roomName)) {

            if (gameman.get_victim(roomName).equals(username)) {
                ImageView card = (ImageView) actionEvent.getSource();
                String path = (String) card.getUserData();
                if (card.getImage() != null && !card.equals(cardToSave)) {
                    if (path.equals("com/example/eioderzwei/image/Hahnkarte.png")) {
                        showErrorPopup("Nur Körnerkarten können ausgewählt werden");
                    } else {
                        if (cardToSave != null) {
                            cardToSave.setEffect(null);
                            cardToSave.setStyle("");
                        }

                        cardToSave = card;
                        cardToSaveString = path;
                        DropShadow dropShadow = new DropShadow();
                        dropShadow.setRadius(15.0);
                        dropShadow.setColor(Color.rgb(5, 74, 15, 0.8));
                        dropShadow.setSpread(0.5);
                        card.setEffect(dropShadow);
                        card.setStyle("-fx-border-color: red; -fx-border-width: 4px;");
                    }

                } else if (card.getImage() != null) {
                    cardToSave.setEffect(null);
                    cardToSave.setStyle("");
                    cardToSave = null;
                    cardToSaveString = "";

                }

            } else {
                ImageView card = (ImageView) actionEvent.getSource();
                String path = (String) card.getUserData();
                if (card.getImage() != null && selectedCards.contains(card)) {
                    selectedCards.remove(card);
                    selectedCardsString.remove(path);
                    card.setEffect(null);
                    card.setStyle("");
                } else {
                    if (card.getImage() != null) {
                        selectedCards.add(card);
                        selectedCardsString.add(path);
                        DropShadow dropShadow = new DropShadow();
                        dropShadow.setRadius(15.0);
                        dropShadow.setColor(Color.rgb(0, 0, 255, 0.8));
                        dropShadow.setSpread(0.5);
                        card.setEffect(dropShadow);
                        card.setStyle("-fx-border-color: red; -fx-border-width: 4px;");

                    }

                }
            }
        }
    }
    public void chooseCardToSteal(MouseEvent actionEvent) throws RoomDoesNotExistException, RemoteException {
        if(gameman.has_game_started(roomName)) {
            if (gameman.get_stealer(roomName).equals(username)) {
                ImageView card = (ImageView) actionEvent.getSource();
                String path = (String) card.getUserData();
                if (card.getImage() != null && !card.equals(cardToSteal)) {
                    if (false) {
                        showErrorPopup("Du darfst nur bei " + gameman.get_victim(roomName) + " klauen");
                    }else if(path.equals("com/example/eioderzwei/image/Hahnkarte.png")){
                        showErrorPopup("Du darfst nur Körnerkarten klauen");

                    }else {
                        if (cardToSteal != null) {
                            cardToSteal.setEffect(null);
                            cardToSteal.setStyle("");
                        }

                        cardToSteal = card;
                        cardToStealString = path;
                        DropShadow dropShadow = new DropShadow();
                        dropShadow.setRadius(15.0);
                        dropShadow.setColor(Color.rgb(0, 0, 255, 0.8));
                        dropShadow.setSpread(0.5);
                        card.setEffect(dropShadow);
                        card.setStyle("-fx-border-color: red; -fx-border-width: 4px;");
                    }


                } else if (card.getImage() != null) {
                    cardToSteal.setEffect(null);
                    cardToSteal.setStyle("");
                    cardToSteal = null;
                    cardToStealString = "";

                }


            }
        }

    }

        @FXML
    private void onDiscardPileClicked(MouseEvent event) throws RoomDoesNotExistException, RemoteException {
        if(gameman.has_game_started(roomName)) {
            if (gameman.is_turn(username, roomName)) {
                if (!used2) {
                    if (!gameman.lay_eggs(username, roomName, selectedCardsString)) {
                        showErrorPopup("Keine Eier wurden gelegt");
                        resetCardSelection();
                    } else {
                        resetCardSelection();

                        if (gameman.get_rooster_holder(roomName).equals(username)) {
                            used2 = true;
                        } else {
                            gameman.give_turn(roomName);


                        }

                    }
                } else {
                    showErrorPopup("Nur Karte ziehen möglich");

                }
            } else {
                showErrorPopup("Nicht dein Zug!");
            }
        }


        }
    public void drawCard(MouseEvent actionEvent) throws IOException, RoomDoesNotExistException {
        if(gameman.has_game_started(roomName)) {

            if (gameman.is_turn(username, roomName)) {
                String card = gameman.draw_card(roomName, username);

                if (card.equals("com/example/eioderzwei/image/Kuckuck.png")) {
                    gameman.incEggNumber(username, roomName, 1);
                    gameman.discard_card(roomName, username, "com/example/eioderzwei/image/Kuckuck.png");
                }
                if (card.equals("com/example/eioderzwei/image/Fucks.png")) {
                    gameman.set_fox_karte_gezogen(roomName, true);
                    handleFoxCard();

                }


                if (gameman.get_rooster_holder(roomName).equals(username) && !used1 && !used2) {
                    used1 = true;  // one draw happened, second left
                    gameman.setGezogen(roomName, false);


                } else {

                    gameman.setGezogen(roomName, true);

                    if (!used3) {

                        // both draw or egglay+draw or rooster+draw or fox happened, either way give turn
                        used1 = false;
                        used2 = false;
                        gameman.give_turn(roomName);
                    }


                }


            } else {
                showErrorPopup("Nicht dein Zug!");
            }
        }
    }




    private void resetCardSelection() {
        for (ImageView card : selectedCards) {
            card.setEffect(null);
            card.setStyle("");
        }
        selectedCards.clear();
        selectedCardsString.clear();
    }


    private void disable_cards() throws RoomDoesNotExistException, RemoteException {
        if(nameLabelWest.getText().equals(gameman.get_victim(roomName))){
            for (ImageView cardInHandImageView : cardInHandImageViews) {
                cardInHandImageView.setOpacity(0.34);
                cardInHandImageView.setDisable(true);
            }

        }



    }
    private void enable_cards() throws RoomDoesNotExistException, RemoteException {
            for (ImageView cardInHandImageView : cardInHandImageViews) {
                cardInHandImageView.setOpacity(1.0);
                cardInHandImageView.setDisable(false);
            }
        for (ImageView c : cardWestImageViews) {
            c.setOpacity(1.0);
            c.setDisable(false);
        }






    }



    private void handleRoosterCard() {


        try {
            boolean canClaimRooster = gameman.want_rooster_card(username, roomName);
            if (canClaimRooster) {

                boolean claimDecision = showRoosterCardClaimDialog();

                if (claimDecision) {

                    gameman.give_rooster_card(username, roomName);
                    used2 = true;
                }
            }else{
                showErrorPopup("Sie haben mehr Eier als der Besitzer von Hahnkarte!");
            }

        } catch (RemoteException | RoomDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleRoosterCardAction(ActionEvent event) throws RoomDoesNotExistException, RemoteException {
        if(gameman.is_turn(username, roomName ) ) {
            if (!used2) {
                handleRoosterCard();

            } else {
                showErrorPopup("Nur Karte ziehen möglich");
            }
        }else{
            showErrorPopup("Nicht dein Zug!");

        }


    }
    private boolean showRoosterCardClaimDialog() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hahnkarte beanspruchen");
        alert.setHeaderText("Möchten Sie die Rooster Card anfordern?");
        alert.setContentText("Wenn Sie weniger Eier haben als der aktuelle Besitzer, können Sie die Hahnenkarte beanspruchen.");
        ButtonType buttonYes = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType buttonNo = new ButtonType("Nein", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonYes;
    }
    public void handleFoxCard() {
        try {
            gameman.set_stealer(roomName, username);
            List<String> availablePlayers = gameman.getAvailablePlayersToSteal(username, roomName);
            String chosenVictimId = promptPlayerToChooseVictim(availablePlayers);
            boolean option = promptPlayerToChooseOption();
            if(!option){ //    2 option alle karten bis auf eine

                gameman.set_prompt_required(roomName,true);
            }
            else{
                disable_cards();
                confirmButton2.setVisible(true);

            }
        } catch (RemoteException | RoomDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    private String promptPlayerToChooseVictim(List<String> availablePlayers) throws RoomDoesNotExistException, RemoteException {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(availablePlayers.get(0), availablePlayers);
        dialog.setTitle("Spieler Auswahl");
        dialog.setHeaderText("Wähle einen Spieler aus, von dem du klauen möchtest:");
        dialog.setContentText("Verfügbare Spieler:");
        dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            gameman.set_victim(roomName, result.get());

            return result.get();
        } else {

            return null;
        }
    }
    private boolean promptPlayerToChooseOption(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Option");
        alert.setHeaderText("Wähle eine Option:");

        ButtonType option1Button = new ButtonType("Ich wähle eine beliebige Karte");
        ButtonType option2Button = new ButtonType("Ich wähle alle bis auf eine Körnerkarte");


        alert.getButtonTypes().setAll(option1Button, option2Button);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == option1Button;

    }

    private void promptVictimToChooseCard() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Karte Auswahl");
        alert.setHeaderText(null);

        alert.setContentText("Achtung! Du wurdest als Opfer ausgewählt." +
                " Wähle eine Karte aus, die du behalten möchtest");

        ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okButton) {
            }
        });
        confirmButton.setVisible(true);
    }



    public void displayMessage(MessageObject message) {
        // Check if the current user is mentioned in the message
        if (message.getMentionedUsers().contains(UserInfo.getUsername())) {
            highlightMessage(message);
        } else {
            displayNormalMessage(message);
        }
    }
    private void highlightMessage(MessageObject message) {
        Label messageLabel = new Label(message.getMessageText());
        messageLabel.setTextFill(Color.ORANGE);
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        messagesListView.getItems().add(String.valueOf(messageLabel));
    }
    private void displayNormalMessage(MessageObject message) {
        Label messageLabel = new Label(formatMessageDisplay(message));
        messagesListView.getItems().add(messageLabel.getText());
    }
    private String formatMessageDisplay(MessageObject message) {
        return message.getSenderId() + ": " + message.getMessageText();
    }
    public void sendButtonOnAction() {
        String message = messageInputField.getText().trim();
        if (!message.isEmpty()) {
            try {
                chatman.sendMessage(roomName, username, message);
                messagesListView.getItems().add("You: " + message);
                messageInputField.clear();
            } catch (RemoteException e) {
                showErrorPopup("Error sending message: " + e.getMessage());
            }
        }
    }
    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);

        alert.showAndWait();
    }
    private int lastMessageIndex = 0;

    public void startChatThread() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    List<String> chatMessages = chatman.receiveMessages(roomName);
                    // Process and add only new messages
                    for (int i = lastMessageIndex; i < chatMessages.size(); i++) {
                        String messageString = chatMessages.get(i);
                        // Split into senderId and messageText after the first :
                        String[] parts = messageString.split(":", 2);
                        if (parts.length == 2) {
                            String senderId = parts[0];
                            String messageText = parts[1];
                            List<String> mentionedUsers = extractMentionedUsers(messageText);
                            MessageObject messageObj = new MessageObject();
                            messageObj.setSenderId(senderId);
                            messageObj.setMessageText(messageText);
                            messageObj.setMentionedUsers(mentionedUsers);
                            Platform.runLater(() -> {
                                if (messageObj.getMentionedUsers().contains(UserInfo.getUsername())) {
                                    highlightMessage(messageObj);
                                } else {
                                    displayNormalMessage(messageObj);
                                }
                            });
                        }
                    }
                    lastMessageIndex = chatMessages.size();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Platform.runLater(() -> showErrorPopup("Chat thread interrupted: " + e.getMessage()));
                    break; // Exit if the thread is interrupted
                } catch (RemoteException e) {
                    Platform.runLater(() -> showErrorPopup("Error fetching messages: " + e.getMessage()));
                }
            }
        });
        t1.setDaemon(true);
        t1.start();
    }

    private List<String> extractMentionedUsers(String messageText) {
        List<String> mentionedUsers = new ArrayList<>();
        Matcher matcher = Pattern.compile("@(\\w+)").matcher(messageText);
        while (matcher.find()) {
            mentionedUsers.add(matcher.group(1));
        }
        return mentionedUsers;
    }

    public void onConfirmButtonClicked(ActionEvent actionEvent) throws RoomDoesNotExistException, RemoteException {
        if(cardToSave!=null){
            gameman.steal_all_cards_except_one(roomName, cardToSaveString);
            confirmButton.setVisible(false);
            cardToSave.setEffect(null);
            cardToSave.setStyle("");

        }else{
            showErrorPopup("Wähle eine Karte aus, die du behalten möchtest!");
        }


    }
    public void onConfirmButtonClicked2(ActionEvent actionEvent) throws RoomDoesNotExistException, RemoteException {
        if(cardToSteal!=null){
            gameman.steal_card(cardToStealString, roomName);
            confirmButton2.setVisible(false);
            cardToSteal.setEffect(null);
            cardToSteal.setStyle("");
            enable_cards();

        }else{
            showErrorPopup("Wähle eine Karte aus, die du klauen möchtest!");
        }


    }
    public void updateScoreAndRankList() throws RemoteException {
        // Assuming gameman can give you the current player's score
        int score = gameman.getPlayerScore(username);
        playerScoreLabel.setText("Score: " + score);

        // Assuming gameman can give you the rank list
        List<String> rankList = gameman.getRankList();
        rankListView.getItems().clear();
        rankListView.getItems().addAll(rankList.stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.toList()));
    }

}