package com.example.eioderzwei;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    }
}
