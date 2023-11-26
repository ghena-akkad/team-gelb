package com.example.eioderzwei;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PlayTableController {
    @FXML
    private Label nameLabel;

    public void setUserName(String userName) {
        nameLabel.setText(userName);
    }
}

