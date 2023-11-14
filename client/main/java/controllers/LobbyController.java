package controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;



public class LobbyController  {
    @FXML
    private TextField nameField;


    @FXML
    protected void onNextButtonClick() throws Exception{

            String userName = nameField.getText();
            System.out.print(userName);
            openPlayTableWindow(userName);


        }

    protected void openPlayTableWindow(String userName) {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gametable.fxml"));
            Parent root =  fxmlLoader.load();
            PlayTableController playroomController = fxmlLoader.getController();
            playroomController.setUserName(userName);

            Stage stage = new Stage();

            stage.setTitle("Spieltisch");
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) nameField.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

    }


}
