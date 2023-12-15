package com.example.eioderzwei.client;
import com.example.eioderzwei.server.Card;
import com.example.eioderzwei.server.MessageObject; //TODO ist es ok diese Klasse zu importieren so ?
import com.example.eioderzwei.server.common.ChatManagerInterface;
import com.example.eioderzwei.server.common.GameManagerInterface;
import com.example.eioderzwei.server.common.RoomsManagerInterface;
import com.example.eioderzwei.server.exceptions.RoomDoesNotExistException;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
    private String deck_card;

    @FXML
    private ImageView Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10,
            Karte11, Karte12, Karte13, Karte14, Karte15, Karte16, Karte17, Karte18, Karte19, Karte20,
            Karte21, Karte22, Karte23, Karte24, Karte25, Karte26, Karte27, Karte28, Karte29, Karte30,
            Karte31, Karte32, Karte33, Karte34, Karte35, Karte36, small0, small1, small2, small3;
    ImageView[] cardInHandImageViews = {

            Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10,
            Karte11, Karte12};
    ImageView[] cardWestImageViews = {
            Karte13, Karte14, Karte15, Karte16, Karte17, Karte18, Karte19, Karte20,
            Karte21, Karte22};
    ImageView[] cardNorthImageViews = {
            Karte23, Karte24, Karte25, Karte26, Karte27, Karte28, Karte29};
    ImageView[] cardEastImageViews = {
            Karte30, Karte31, Karte32, Karte33, Karte34, Karte35, Karte36};
    @FXML
    private Label eggNumberNorth, eggNumberEast, eggNumberWest, eggNumberSouth;
    @FXML
    public int i = 0, deck_number_of_cards = 12, ablage_number_of_cards = 0;
    private String cardNameSelected;
    private ImageView selectedCard;
    GameManagerInterface gameman;
    RoomsManagerInterface roomman;
    ChatManagerInterface chatman;
    @FXML
    private ListView<String> messagesListView; //TODO add to fxml file accordingly
    @FXML
    private TextField messageInputField;
    @FXML
    private Button sendButton;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gameman = Client.getGameManager();
        this.roomman = Client.getRoomsManager();
        this.chatman = Client.getChatManager();
        username = UserInfo.getUsername();
        eggNumberSouth.setVisible(false);
        eggNumberNorth.setVisible(false);
        eggNumberEast.setVisible(false);
        eggNumberWest.setVisible(false);
        small0.setVisible(false);
        small1.setVisible(false);
        small2.setVisible(false);
        small3.setVisible(false);
        System.out.println(username);
        roomName = UserInfo.getRoomname();
        System.out.println(roomName);
        initThread();
        labelThread();
        cardsThread();
        startChatThread();
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
                                if (!(gameman.getRequiredNumberOfPlayers(roomName) > gameman.getNumberOfPlayers(roomName))) {
                                    gameStatus.setText("Warte auf die anderen Spieler");
                                } else {
                                    gameStatus.setText("Game started");
                                    start();
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
            eggNumberSouth.setVisible(true);
            eggNumberNorth.setVisible(true);
            eggNumberEast.setVisible(true);
            eggNumberWest.setVisible(true);
            small0.setVisible(true);
            small1.setVisible(true);
            small2.setVisible(true);
            small3.setVisible(true);
            gameman.start(roomName);
            List<String> joinedPlayers = roomman.getPlayers(roomName);
            System.out.println(joinedPlayers);
            gameman.start_turn(joinedPlayers.get(0), roomName);
            zuglabel.setText(joinedPlayers.get(0));
            String i = gameman.showTopCard(roomName);
            FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/" + i);
            Image img = new Image(input);
            deck.setImage(img);
            gameman.initialize_rooster(roomName);
            System.out.println(gameman.get_rooster_holder(roomName));
        } catch (RoomDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    private List<String> lastKnownSouthCards = new ArrayList<>();
    private List<String> lastKnownWestCards = new ArrayList<>();
    private List<String> lastKnownNorthCards = new ArrayList<>();
    private List<String> lastKnownEastCards = new ArrayList<>();

    public void cardsThread() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    List<String> currentSouthCards = gameman.get_players_card(username);
                    List<String> currentWestCards = gameman.get_players_card(nameLabelWest.getText());
                    List<String> currentNorthCards = gameman.get_players_card(nameLabelNorth.getText());
                    List<String> currentEastCards = gameman.get_players_card(nameLabelEast.getText());

                    // Check if there are any changes in the card lists
                    if (!currentSouthCards.equals(lastKnownSouthCards)) {
                        Platform.runLater(() -> {
                            try {
                                updateCardsSouth(currentSouthCards);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        });
                        lastKnownSouthCards = currentSouthCards;
                    }

                    if (!currentWestCards.equals(lastKnownWestCards)) {
                        Platform.runLater(() -> {
                            try {
                                updateCardsWest(currentWestCards);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        });
                        lastKnownWestCards = currentWestCards;
                    }

                    if (!currentNorthCards.equals(lastKnownNorthCards)) {
                        Platform.runLater(() -> {
                            try {
                                updateCardsNorth(currentNorthCards);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        });
                        lastKnownNorthCards = currentNorthCards;
                    }

                    if (!currentEastCards.equals(lastKnownEastCards)) {
                        Platform.runLater(() -> {
                            try {
                                updateCardsEast(currentEastCards);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        });
                        lastKnownEastCards = currentEastCards;
                    }

                    String topDeckCard = gameman.showTopCard(roomName);
                    String topAblageCard = gameman.showTopCardAblage(roomName);
                    Platform.runLater(() -> {
                        try {
                            updateDecks(topDeckCard, topAblageCard);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    });

                    Thread.sleep(220);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break; // Exit the loop if the thread is interrupted
                } catch (RemoteException | RoomDoesNotExistException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }


    private void updateDecks(String s, String string) throws FileNotFoundException {
        if (string.equals("")) {
            ablage.setImage(null);
        } else {
            FileInputStream input2 = new FileInputStream("EiOderZwei/src/main/java/" + string);
            Image img2 = new Image(input2);
            ablage.setImage(img2);
        }
        FileInputStream input1 = new FileInputStream("EiOderZwei/src/main/java/" + s);
        Image img1 = new Image(input1);
        deck.setImage(img1);
    }

    private void updateCardsNorth(List<String> playersCard) throws FileNotFoundException {
        Karte23.setImage(null);
        Karte24.setImage(null);
        Karte25.setImage(null);
        Karte26.setImage(null);
        Karte27.setImage(null);
        Karte28.setImage(null);
        Karte29.setImage(null);
        if (!playersCard.isEmpty()) {
            int n = 1;
            for (String card : playersCard) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/" + card);
                Image img = new Image(input);
                if (n == 1) {
                    Karte23.setImage(img);
                }
                if (n == 2) {
                    Karte24.setImage(img);
                }
                if (n == 3) {
                    Karte25.setImage(img);
                }
                if (n == 4) {
                    Karte26.setImage(img);
                }
                if (n == 5) {
                    Karte27.setImage(img);
                }
                if (n == 6) {
                    Karte28.setImage(img);
                }
                if (n == 7) {
                    Karte29.setImage(img);
                }
                n++;
            }
        }
    }

    private void updateCardsEast(List<String> playersCard) throws FileNotFoundException {
        Karte30.setImage(null);
        Karte31.setImage(null);
        Karte32.setImage(null);
        Karte33.setImage(null);
        Karte34.setImage(null);
        Karte35.setImage(null);
        Karte36.setImage(null);
        if (!playersCard.isEmpty()) {
            int n = 1;
            for (String card : playersCard) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/" + card);
                Image img = new Image(input);
                if (n == 1) {
                    Karte30.setImage(img);
                }
                if (n == 2) {
                    Karte31.setImage(img);
                }
                if (n == 3) {
                    Karte32.setImage(img);
                }
                if (n == 4) {
                    Karte33.setImage(img);
                }
                if (n == 5) {
                    Karte34.setImage(img);
                }
                if (n == 6) {
                    Karte35.setImage(img);
                }
                if (n == 7) {
                    Karte36.setImage(img);
                }
                n++;
            }
        }
    }
    private void updateCardsWest(List<String> playersCard) throws FileNotFoundException {
        Karte13.setImage(null);
        Karte14.setImage(null);
        Karte15.setImage(null);
        Karte16.setImage(null);
        Karte17.setImage(null);
        Karte18.setImage(null);
        Karte19.setImage(null);
        Karte20.setImage(null);
        Karte21.setImage(null);
        Karte22.setImage(null);
        if (!playersCard.isEmpty()) {
            int n = 1;
            for (String card : playersCard) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/" + card);
                Image img = new Image(input);
                if (n == 1) {
                    Karte13.setImage(img);
                }
                if (n == 2) {
                    Karte14.setImage(img);
                }
                if (n == 3) {
                    Karte15.setImage(img);
                }
                if (n == 4) {
                    Karte16.setImage(img);
                }
                if (n == 5) {
                    Karte17.setImage(img);
                }
                if (n == 6) {
                    Karte18.setImage(img);
                }
                if (n == 7) {
                    Karte19.setImage(img);
                }
                if (n == 8) {
                    Karte20.setImage(img);
                }
                if (n == 9) {
                    Karte21.setImage(img);
                }
                if (n == 10) {
                    Karte22.setImage(img);
                }
                n++;
            }
        }
    }
    private void updateCardsSouth(List<String> playersCard) throws FileNotFoundException {
        Karte1.setImage(null);
        Karte2.setImage(null);
        Karte3.setImage(null);
        Karte4.setImage(null);
        Karte5.setImage(null);
        Karte6.setImage(null);
        Karte7.setImage(null);
        Karte8.setImage(null);
        Karte9.setImage(null);
        Karte10.setImage(null);
        Karte11.setImage(null);
        Karte12.setImage(null);
        if (!playersCard.isEmpty()) {
            System.out.println("not empty");
            int n = 1;
            for (String card : playersCard) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/" + card);
                Image img = new Image(input);
                if (n == 1) {
                    Karte1.setImage(img);
                }
                if (n == 2) {
                    Karte2.setImage(img);
                }
                if (n == 3) {
                    Karte3.setImage(img);
                }
                if (n == 4) {
                    Karte4.setImage(img);
                }
                if (n == 5) {
                    Karte5.setImage(img);
                }
                if (n == 6) {
                    Karte6.setImage(img);
                }
                if (n == 7) {
                    Karte7.setImage(img);
                }
                if (n == 8) {
                    Karte8.setImage(img);
                }
                if (n == 9) {
                    Karte9.setImage(img);
                }
                if (n == 10) {
                    Karte10.setImage(img);
                }
                if (n == 11) {
                    Karte10.setImage(img);
                }
                if (n == 12) {
                    Karte10.setImage(img);
                }
                n++;
            }
        }
    }
    private List<String> lastKnownPlayerNames = new ArrayList<>();
    public void labelThread() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    List<String> currentPlayerNames = roomman.getPlayers(roomName);
                    // Check if there are any changes in the player names
                    if (!currentPlayerNames.equals(lastKnownPlayerNames)) {
                        Platform.runLater(() -> updatePlayerLabels(currentPlayerNames));
                        lastKnownPlayerNames = currentPlayerNames;
                    }
                    Thread.sleep(210);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break; // Exit if the thread is interrupted
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }
    private void updatePlayerLabels(List<String> playerNames) {
        switch (playerNames.size()) {
            case 1:
                nameLabelWest.setText("Waiting");
                nameLabelEast.setText("Waiting");
                nameLabelNorth.setText("Waiting");
                break;
            case 2:
                if (playerNames.get(1).equals(username)) {
                    nameLabelWest.setText(playerNames.get(0));
                } else {
                    nameLabelWest.setText(playerNames.get(1));
                }
                nameLabelEast.setText("Waiting");
                nameLabelNorth.setText("Waiting");
                break;
            case 3:
                if (playerNames.get(1).equals(username)) {
                    nameLabelNorth.setText("Waiting");
                    nameLabelWest.setText(playerNames.get(2));
                    nameLabelEast.setText(playerNames.get(0));
                } else if (playerNames.get(0).equals(username)) {
                    nameLabelNorth.setText(playerNames.get(2));
                    nameLabelWest.setText(playerNames.get(1));
                    nameLabelEast.setText("Waiting");
                } else {
                    nameLabelNorth.setText(playerNames.get(0));
                    nameLabelEast.setText(playerNames.get(1));
                    nameLabelWest.setText("Waiting");
                }
                break;
            case 4:
                for (String name : playerNames) {
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
                break;
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
            if (b) {
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
    public void setUserName(String userName) {
        nameLabel.setText(userName);
    }

    public void setRoomName(String roomname) {
        roomnamelabel.setText(roomname);
        this.roomName = roomname;
    }
    public void drawCard(MouseEvent actionEvent) throws RoomDoesNotExistException {
        try {
            String drawnCard = gameman.draw_card(roomName);
            // Check if drawn
            if (drawnCard != null && !drawnCard.isEmpty()) {
                FileInputStream input = new FileInputStream("EiOderZwei/src/main/java/" + drawnCard);
                Image img = new Image(input);
                ImageView im = addCardToHand(img);
                moveCardAnimationDeckToHand(deck, im, img);
                deck_number_of_cards--;
                if(drawnCard.contains("Hahnkarte")) {
                    if (gameman.get_rooster_holder(roomName).equals(username)) {
                        String additionalCard = gameman.draw_card(roomName);
                        // Load the image of the additional card
                        FileInputStream additionalCardInput = new FileInputStream("EiOderZwei/src/main/java/" + additionalCard);
                        Image additionalCardImage = new Image(additionalCardInput);
                        // Add the additional card to the player's hand
                        ImageView additionalCardImageView = addCardToHand(additionalCardImage);
                        moveCardAnimationDeckToHand(deck, additionalCardImageView, additionalCardImage);
                        deck_number_of_cards--;
                    }
                    //handleRoosterCard();
                } else if(drawnCard.contains("Fucks")) {
                    // Handle fox card logic
                    handleFoxCard();
                }
            }
        } catch (FileNotFoundException | RemoteException e) {
            e.printStackTrace();
        }
    }
    private ImageView selectedCard1 = null;
    private List<ImageView> selectedCards = new ArrayList<>();
    public void discardCard(MouseEvent actionEvent) {
        ImageView card = (ImageView) actionEvent.getSource();
        if (selectedCards.contains(card)) {
            selectedCards.remove(card);
            card.setStyle(""); // Reset style for deselection
        } else {
            selectedCards.add(card);
            card.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0)"); // Style for selection
        }
    }
    @FXML
    private void onDiscardPileClicked(MouseEvent event) {
        // Check if selected cards meet exchange criteria
        if (canExchangeForEgg(selectedCards)) {
            exchangeCardsForEgg(selectedCards);
            updateUIAfterExchange(); // Implement this method to update the UI
            resetCardSelection();
        } else {
            // Show error or message if exchange criteria are not met
            showError("Selected cards do not meet the criteria for exchanging for an egg.");
        }
    }
    private boolean canExchangeForEgg(List<ImageView> selectedCards) {
        int totalGrainCount = calculateGrainCount(selectedCards);
        return totalGrainCount >= 5; // 5 grains needed per egg
    }
    private void exchangeCardsForEgg(List<ImageView> selectedCards) {
        try {
            if (gameman.lay_eggs(username, roomName)) {
                // Successfully exchanged cards for an egg
                // Remove cards from UI and update egg count
            }
        } catch (RemoteException | RoomDoesNotExistException e) {
            e.printStackTrace();
            showError("Error exchanging cards: " + e.getMessage());
        }
    }
    private void updateUIAfterExchange() {
        // Update UI to reflect new egg count and removed cards from the hand
    }
    private void resetCardSelection() {
        for (ImageView card : selectedCards) {
            card.setStyle(""); // Reset style
        }
        selectedCards.clear();
    }
    //TODO alle comments vom code entfernen und weitermachen mit getCardFromImageView
    private int calculateGrainCount(List<ImageView> selectedCards) {
        int totalGrainCount = 0;
        for (ImageView cardImageView : selectedCards) {
            //Card card = getCardFromImageView(cardImageView); //TODO Implement this method and implement isBio
            //int cardValue = card.getWert();
            //if (card.isBio()) {
            //    cardValue *= 2;  // Bio grains count double
            //}
            //totalGrainCount += cardValue;
        }
        return totalGrainCount;
    }
    //diese methode wollte ich impl damit man wenn man auf
    // imageviews clicken kann und sie werden zu cards umgeschrieben was man
    // braucht um karten zu selektieren und dann ei zu legen

    private void getCardFromImageView(ImageView imageView) { //anstelle von void muss Card hin
        // Implementation depends on how you're associating ImageViews with Card objects
        // For example, you might use imageView.getId() or a Map<ImageView, Card>

    }
    private void handleRoosterCard() {
        try {
            boolean canClaimRooster = gameman.want_rooster_card(username, roomName);
            if (canClaimRooster) {
                // Show dialog to ask player if they want to claim the rooster card
                boolean claimDecision = showRoosterCardClaimDialog();
                if (claimDecision) {
                    // Player decided to claim the rooster card
                    gameman.give_rooster_card(username, roomName);
                    // Update UI to show the player now has the rooster card
                }
            }
        } catch (RemoteException | RoomDoesNotExistException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
    @FXML
    private void handleRoosterCardAction(ActionEvent event) {
        handleRoosterCard();
    }
    private boolean showRoosterCardClaimDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Claim Rooster Card");
        alert.setHeaderText("Do you want to claim the Rooster Card?");
        alert.setContentText("If you have fewer eggs than the current holder, you can claim the Rooster Card.");
        // Add buttons
        ButtonType buttonYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonYes;
    }
    public void handleFoxCard() {
        try {
            List<String> availablePlayers = gameman.getAvailablePlayersToSteal(username, roomName);
            String chosenVictimId = promptPlayerToChooseVictim(availablePlayers); // UI method to choose a victim

            List<Card> availableCards = gameman.getAvailableCardsToSteal(username, chosenVictimId, roomName);
            Card chosenCard = promptPlayerToChooseCard(availableCards); // UI method to choose a card

            // Send the final choices back to the server
            gameman.steal_card(username, chosenVictimId, chosenCard, roomName);
        } catch (RemoteException | RoomDoesNotExistException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private String promptPlayerToChooseVictim(List<String> availablePlayers) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(availablePlayers.get(0), availablePlayers);
        dialog.setTitle("Choose a Victim");
        dialog.setHeaderText("Select a player to steal from:");
        dialog.setContentText("Available Players:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null; // Handle the case where the player doesn't make a choice
        }
    }

    private Card promptPlayerToChooseCard(List<Card> availableCards) {
        List<String> cardDescriptions = availableCards.stream()
                .map(card -> card.getDescription())
                .collect(Collectors.toList());
        ChoiceDialog<String> dialog = new ChoiceDialog<>(cardDescriptions.get(0), cardDescriptions);
        dialog.setTitle("Choose a Card");
        dialog.setHeaderText("Select a card to steal:");
        dialog.setContentText("Available Cards:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedDescription = result.get();
            return availableCards.stream()
                    .filter(card -> card.getDescription().equals(selectedDescription))
                    .findFirst()
                    .orElse(null);
        } else {
            return null; // Handle the case where the player doesn't make a choice
        }
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
                showError("Error sending message: " + e.getMessage());
            }
        }
    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private int lastMessageIndex = 0;

    public void startChatThread() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    List<String> chatMessages = chatman.getMessages(roomName);
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
                    Platform.runLater(() -> showError("Chat thread interrupted: " + e.getMessage()));
                    break; // Exit if the thread is interrupted
                } catch (RemoteException e) {
                    Platform.runLater(() -> showError("Error fetching messages: " + e.getMessage()));
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

}