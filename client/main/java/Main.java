import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);

    }
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Lobby");
        stage.setScene(scene);
        stage.show();
    }
}