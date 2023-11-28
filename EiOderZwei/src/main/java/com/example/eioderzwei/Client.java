package com.example.eioderzwei;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;


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
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/eioderzwei/lobby.fxml"));

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Lobby");

        primaryStage.show();
    }

}
