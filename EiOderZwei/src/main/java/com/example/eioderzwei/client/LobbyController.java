package com.example.eioderzwei.client;

import com.example.eioderzwei.server.exceptions.*;
import com.example.eioderzwei.server.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.example.eioderzwei.client.Client;
import com.example.eioderzwei.server.common.*;
import java.rmi.RemoteException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

import static com.example.eioderzwei.client.UserInfo.getUsername;


public class LobbyController {

    @FXML
    private TextField playerNameField;
    @FXML
    private Label l2;
    @FXML
    private Label l3;

    @FXML
    private TextField roomNameField;
    @FXML
    private TextField newRoomNameField;

    @FXML
    private TextField playerNumberField;
    @FXML
    private TextField botNumberField;

    @FXML
    private Button goToGameRoom;
    @FXML
    private Button enterButton;
    @FXML
    private Button next1;
    @FXML
    private Button next2;

    @FXML
    private Button createGameButton;




    @FXML
    private void initialize() {
        roomNameField.setVisible(false);
        newRoomNameField.setVisible(false);
        goToGameRoom.setVisible(false);
        next1.setVisible(false);
        next2.setVisible(false);
        createGameButton.setVisible(false);
        playerNumberField.setVisible(false);
        botNumberField.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);


    }
    public static void showErrorPopup(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

    @FXML
    private void onEnterButtonClicked() {
        String playerName = playerNameField.getText();
        LoginManagerInterface logman = Client.getLoginManager();
        if (!playerName.isEmpty()){
            try {
                logman.loginPlayer(playerName);
                System.out.println("Spieler: " + playerName);
                UserInfo.setUserName(playerName);
                roomNameFieldAction();


            } catch (RemoteException e ) {
                throw new RuntimeException(e);
            } catch (PlayerNameAlreadyExistsException e) {
                showErrorPopup("Dieser Name ist schon belegt");

            }
        } else {
            showErrorPopup("Bitte Benutzernamen eingeben");

        }
        }

        @FXML
    private void onStartGameButtonClicked() {
            String room_name = roomNameField.getText();
            RoomsManagerInterface roomman = Client.getRoomsManager();
            LoginManagerInterface logman = Client.getLoginManager();

            if (!room_name.isEmpty()) {
                try {

                    roomman.joinRoom(room_name, UserInfo.getUsername());
                    logman.setPlayerId( UserInfo.getUsername(), roomman.getPlayersNumber(room_name));
                    UserInfo.setRoomname(room_name);



                }
                catch (RemoteException e ) {
                    throw new RuntimeException(e);
                }
                catch (RoomIsFullException e ) {
                    showErrorPopup("Der Raum ist schon voll!");
                }
                catch (RoomDoesNotExistException e) {
                    showErrorPopup("Der Raum mit diesem Namen existiert nicht");




                }


            }
            else {
                showErrorPopup("Bitte Spielraum Nummer eingeben");


            }
        }
    @FXML
    public void onNextButtonClicked1() {
        String room_name = newRoomNameField.getText();
        RoomsManagerInterface roomman = Client.getRoomsManager();
        LoginManagerInterface logman = Client.getLoginManager();

        if (!room_name.isEmpty()) {
            try {

                roomman.ifRoomExists(room_name);
                UserInfo.setRoomname(room_name);
                next1.setVisible(false);
                next2.setVisible(true);
            } catch (RoomNameAlreadyExistsException e) {
                showErrorPopup("Der Raum mit diesem Namen existiert bereits. Bitte wähle einen anderen Namen.");

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            showErrorPopup("Bitte Spielraum Namen eingeben");

        }
    }
    @FXML
    public void onNextButtonClicked2() {
        String pn = playerNumberField.getText();
        if (!pn.isEmpty()) {
            try {
                int playerNumber = Integer.parseInt(playerNumberField.getText());
                if (playerNumber > 1 && playerNumber < 6) {
                    UserInfo.setPlayNumber(playerNumber);
                    next2.setVisible(false);
                    createGameButton.setVisible(true);
                } else {
                    showErrorPopup("Ungültige Eingaben. Überprüfe die Anzahl der Spieler (2 bis 5 erlaubt)");
                }
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer
                showErrorPopup("Ungültige Eingabe. Bitte nur Zahlen eingeben.");
            }
        } else {
            showErrorPopup("Bitte Anzahl von Spieler eingeben");
        }
    }



        @FXML
        private void onCreateGameButtonClicked() {
            String bn = botNumberField.getText();
            RoomsManagerInterface roomman = Client.getRoomsManager();
            LoginManagerInterface logman = Client.getLoginManager();


            if (!bn.isEmpty()) {
                try {
                    int botNumber = Integer.parseInt(botNumberField.getText());
                    if (botNumber < UserInfo.getPlayNumber()) {
                        try {
                            roomman.createRoom(UserInfo.getRoomname(), botNumber, UserInfo.getPlayNumber());
                            roomman.joinRoom(UserInfo.getRoomname(), UserInfo.getUsername());
                            logman.setPlayerId( UserInfo.getUsername(), roomman.getPlayersNumber(UserInfo.getRoomname()));
                            openPlayTableWindow(UserInfo.getUsername(), UserInfo.getRoomname(),UserInfo.getPlayNumber() );


                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        } catch (RoomDoesNotExistException e) {
                        }
                        catch (RoomIsFullException e) {
                        }

                    } else {
                        showErrorPopup("Die Anzahl von Bots muss weniger als " + UserInfo.getPlayNumber() + " sein!");
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid integer
                    showErrorPopup("Ungültige Eingabe. Bitte nur Zahlen eingeben.");
                }
            } else {
                showErrorPopup("Bitte Anzahl von Bots eingeben");
            }









}



    protected void roomNameFieldAction() {
        roomNameField.setVisible(true);
        newRoomNameField.setVisible(true);
        goToGameRoom.setVisible(true);
        next1.setVisible(true);
        playerNumberField.setVisible(true);
        botNumberField.setVisible(true);
        l2.setVisible(true);
        l3.setVisible(true);





    }



    protected void openPlayTableWindow(String playerName, String roomName, int desiredPlayerNumber) {

        try {

            switch (desiredPlayerNumber) {
                case 2:
                    FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/com/example/eioderzwei/gameRoom2.fxml"));
                    Parent root2 =  fxmlLoader2.load();
                    gameRoomController c2 = fxmlLoader2.getController();
                    c2.setUserName(playerName);
                    c2.setRoomName(roomName);
                    Stage stage2 = new Stage();

                    stage2.setTitle("Spieltisch");
                    stage2.setScene(new Scene(root2));
                    stage2.show();
                    ((Stage) playerNameField.getScene().getWindow()).close();

                    break;

                case 3:
                    FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/com/example/eioderzwei/gameRoom3.fxml"));
                    Parent root3 =  fxmlLoader3.load();
                    gameRoomController c3 = fxmlLoader3.getController();
                    c3.setUserName(playerName);
                    c3.setRoomName(roomName);

                    Stage stage3 = new Stage();

                    stage3.setTitle("Spieltisch");
                    stage3.setScene(new Scene(root3));
                    stage3.show();
                    ((Stage) playerNameField.getScene().getWindow()).close();

                    break;
                case 4:
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/eioderzwei/gameRoom.fxml"));
                    Parent root =  fxmlLoader.load();
                    gameRoomController playroomController = fxmlLoader.getController();
                    playroomController.setUserName(playerName);
                    playroomController.setRoomName(roomName);
                    Stage stage = new Stage();

                    stage.setTitle("Spieltisch");
                    stage.setScene(new Scene(root));
                    stage.show();
                    ((Stage) playerNameField.getScene().getWindow()).close();

                    break;

        } }catch (Exception e) {
            e.printStackTrace();
        }
        ;



    }


}
