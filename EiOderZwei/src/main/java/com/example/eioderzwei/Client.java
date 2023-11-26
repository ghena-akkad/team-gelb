package com.example.eioderzwei;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Client extends Application {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            System.out.println("Registry located");
            Communication server = (Communication) registry.lookup("server");
            server.sendMessage("hello, Server!");

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        launch(args);

    }

    @Override
    public void start(Stage stage) throws IOException {

        /**  Hier wird das Lobby Fenster eröffnet */

    }
}
