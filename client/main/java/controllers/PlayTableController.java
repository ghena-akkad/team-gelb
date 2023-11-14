package controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;


public class PlayTableController {
    @FXML
    private Label nameLabel;

    public void setUserName(String userName) {
        nameLabel.setText(userName);
    }
}
