module com.example.eioderzwei {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.rmi;


    //requires eu.hansolo.tilesfx;

    opens com.example.eioderzwei to javafx.fxml;
    exports com.example.eioderzwei;
}