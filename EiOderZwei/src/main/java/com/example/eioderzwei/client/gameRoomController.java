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
import javafx.geometry.Insets;

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
            Karte31, Karte32, Karte33, Karte34, Karte35, Karte36, small0, small1, small2, small3;
    ImageView[] cardInHandImageViews;
    ImageView[] cardWestImageViews;
    ImageView[] cardNorthImageViews ;
    ImageView[] cardEastImageViews ;
    private List<ImageView> selectedCards;
    private ArrayList<String> selectedCardsString;
    @FXML
    private Label eggNumberNorth, eggNumberEast, eggNumberWest, eggNumberSouth;

    boolean used1;
    boolean used2;



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
        used1 = false;
        used2= false;
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
        cardInHandImageViews = new ImageView[]{ Karte1, Karte2, Karte3, Karte4, Karte5, Karte6, Karte7, Karte8, Karte9, Karte10,
                Karte11, Karte12 };
        cardWestImageViews = new ImageView[]{
                Karte13, Karte14, Karte15, Karte16, Karte17, Karte18, Karte19, Karte20,
                Karte21, Karte22};
        cardNorthImageViews =new ImageView[] {
                Karte23, Karte24, Karte25, Karte26, Karte27, Karte28, Karte29};
        cardEastImageViews =new ImageView[] {
                Karte30, Karte31, Karte32, Karte33, Karte34, Karte35, Karte36};

        roomName = UserInfo.getRoomname();
        initThread();
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

                                if ((gameman.getRequiredNumberOfPlayers(roomName) > gameman.getNumberOfPlayers(roomName))) {
                                    gameStatus.setText("Warte auf die anderen Spieler");
                                } else {
                                    gameStatus.setText("Spiel läuft");
                                    eggNumberSouth.setVisible(true);
                                    eggNumberNorth.setVisible(true);
                                    eggNumberEast.setVisible(true);
                                    eggNumberWest.setVisible(true);
                                    small0.setVisible(true);
                                    small1.setVisible(true);
                                    small2.setVisible(true);
                                    small3.setVisible(true);
                                    roosterCardButton.setVisible(true);
                                    if(!gameman.has_game_started(roomName)){
                                        start();

                                    }else{
                                        zuglabel.setText(gameman.whose_turn(roomName));
                                        int a = (gameman.howManyEggs(nameLabelEast.getText(),roomName));
                                        int b = (gameman.howManyEggs(nameLabelWest.getText(),roomName));
                                        int c = (gameman.howManyEggs(username,roomName));
                                        int d = (gameman.howManyEggs(nameLabelNorth.getText(),roomName));

                                        eggNumberEast.setText(Integer.toString(a));
                                        eggNumberWest.setText(Integer.toString(b));
                                        eggNumberSouth.setText(Integer.toString(c));
                                        eggNumberNorth.setText(Integer.toString(d));


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
                                    updateCardsEast(gameman.get_players_card(nameLabelEast.getText()));
                                    updateCardsNorth(gameman.get_players_card(nameLabelNorth.getText()));
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
        FileInputStream input1 = new FileInputStream("EiOderZwei/src/main/java/" + s);
        Image img1 = new Image(input1);
        deck.setImage(img1);
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
                nameLabelEast.setText("Warten...");
                nameLabelNorth.setText("Warten...");
                break;
            case 2:
                if (playerNames.get(1).equals(username)) {
                    nameLabelWest.setText(playerNames.get(0));
                } else {
                    nameLabelWest.setText(playerNames.get(1));
                }
                nameLabelEast.setText("Warten...");
                nameLabelNorth.setText("Warten...");
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







    public void chooseCard(MouseEvent actionEvent) {
        ImageView card = (ImageView) actionEvent.getSource();
        String path = (String) card.getUserData();
        if (selectedCards.contains(card)) {
            selectedCards.remove(card);
            selectedCardsString.remove(path);
            card.setEffect(null);
            card.setStyle("");
        } else {
            selectedCards.add(card);
            selectedCardsString.add(path);
            System.out.println(selectedCardsString);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(15.0);
            dropShadow.setColor(Color.rgb(0, 0, 255, 0.8));
            dropShadow.setSpread(0.5);
            card.setEffect(dropShadow);
            card.setStyle("-fx-border-color: red; -fx-border-width: 4px;");
        }
    }
    @FXML
    private void onDiscardPileClicked(MouseEvent event) throws RoomDoesNotExistException, RemoteException {
        if(gameman.is_turn(username, roomName )) {
            if (!gameman.lay_eggs(username, roomName, selectedCardsString)) {
                showErrorPopup("Keine Eier wurden gelegt");
                resetCardSelection();
            } else {
                resetCardSelection();

                if(gameman.get_rooster_holder(roomName).equals(username) )
                {
                    used2 = true;
                }
                else{
                    gameman.give_turn(roomName);


                }

            }
        }
        else{
            showErrorPopup("Nicht dein Zug!");
        }


        }
    public void drawCard(MouseEvent actionEvent) throws IOException, RoomDoesNotExistException {
        if(gameman.is_turn(username, roomName )){
            gameman.draw_card(roomName, username);

            if(gameman.get_rooster_holder(roomName).equals(username) && !used1 &&!used2)
            {
                used1 = true;
            }
            else{
                used1 = false;
                used2 = false;
                gameman.give_turn(roomName);


            }


        }
        else{
            showErrorPopup("Nicht dein Zug!");
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

}