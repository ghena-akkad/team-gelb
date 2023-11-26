package com.example.eioderzwei;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;





public class LobbyController {

    @FXML
    private TextField playerNameField;

    @FXML
    private Button startGameButton;


    @FXML
    private void initialize() {
        // Diese Methode wird beim Laden der FXML aufgerufen
        // Hier die Initialisierungen vornehmen
    }

    @FXML
    private void onStartGameButtonClicked() {
        // aufrufen, wenn der "Start Game"-Button geklickt wird
        String playerName = playerNameField.getText();
        System.out.println("Starte Spiel für Spieler: " + playerName);

        openPlayTableWindow(playerName);


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
