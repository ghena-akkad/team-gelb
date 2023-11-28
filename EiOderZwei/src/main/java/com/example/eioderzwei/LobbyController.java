package com.example.eioderzwei;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
public class LobbyController {
    @FXML
    private TextField playerNameField;
    @FXML
    private Button goToGameRoom;
    @FXML
    private void initialize() {
    }
    @FXML
    private void switchToGameRoom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameRoom.fxml"));
            Parent secondRoot = loader.load();
            gameRoomController gameRoomController = loader.getController();
            gameRoomController.setPlayerName(playerNameField.getText());
            Scene secondScene = new Scene(secondRoot);
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(secondScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        openPlayTableWindow(playerName);
    private void switchToGameRoom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameRoom.fxml"));
            Parent secondRoot = loader.load();
            gameRoomController gameRoomController = loader.getController();
            gameRoomController.setPlayerName(playerNameField.getText());
            Scene secondScene = new Scene(secondRoot);
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(secondScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    private void switchToGameRoom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameRoom.fxml"));
            Parent secondRoot = loader.load();
            gameRoomController gameRoomController = loader.getController();
            gameRoomController.setPlayerName(playerNameField.getText());
            Scene secondScene = new Scene(secondRoot);
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(secondScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void openPlayTableWindow(String playerName) {

        try {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gametable.fxml"));
            Parent root =  fxmlLoader.load();
            PlayTableController playroomController = fxmlLoader.getController();
            playroomController.setUserName(playerName);

            Stage stage = new Stage();

            stage.setTitle("Spieltisch");
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) playerNameField.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;



    }

}
