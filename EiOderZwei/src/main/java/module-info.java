module com.example.eioderzwei {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.rmi;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires annotations;

    opens com.example.eioderzwei to javafx.fxml;
    exports com.example.eioderzwei.server;
    exports com.example.eioderzwei.client;
    opens com.example.eioderzwei.client to javafx.fxml;
}