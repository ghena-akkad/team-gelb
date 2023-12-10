package com.example.eioderzwei.client;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;


import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.eioderzwei.server.common.*;

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
    /**
     *  Methode, um eine Instanz der LoginManager-Schnittstelle vom RMI-Registry zu erhalten.

     * @throws RuntimeException Falls ein Fehler beim Zugriff auf den RMI-Registry oder beim Suchen des LoginManagers auftritt.
     */

    public static LoginManagerInterface getLoginManager(){
        try {
            // Zugriff auf den RMI-Registry
            Registry registry = LocateRegistry.getRegistry(1099);

            // Suchen der LoginManager-Instanz im Registry
            LoginManagerInterface logman = (LoginManagerInterface) registry.lookup("logman");

            return logman;
        } catch (Exception e) {
            // Fehlerbehandlung und Weitergabe als RuntimeException
            throw new RuntimeException(e);
        }
    }

    /**
     *  Methode, um eine Instanz der RoomsManager-Schnittstelle vom RMI-Registry zu erhalten.
     * @throws RuntimeException Falls ein Fehler beim Zugriff auf den RMI-Registry oder beim Suchen des RoomsManagers auftritt.
     */

    public static RoomsManagerInterface getRoomsManager(){
        try {
            // Zugriff auf den RMI-Registry
            Registry registry = LocateRegistry.getRegistry(1099);

            // Suchen der RoomsManager-Instanz im Registry
            RoomsManagerInterface roomman = (RoomsManagerInterface) registry.lookup("roomman");

            return roomman;
        } catch (Exception e) {
            // Fehlerbehandlung und Weitergabe als RuntimeException
            throw new RuntimeException(e);
        }
    }

    }

