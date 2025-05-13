module it.polimi.ingsw.galaxytrucker {
    exports it.polimi.ingsw.galaxytrucker.Network.Server;
    exports it.polimi.ingsw.galaxytrucker.Network.Client;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.rmi;
    requires jdk.jfr;
    requires jackson.core;
    requires jackson.databind;
    requires jackson.annotations;
    requires com.google.gson;

    requires java.sql;

    opens it.polimi.ingsw.galaxytrucker to javafx.fxml, com.google.gson;
    opens it.polimi.ingsw.galaxytrucker.Model.Tiles to com.google.gson;


    exports it.polimi.ingsw.galaxytrucker;
    exports it.polimi.ingsw.galaxytrucker.Controller.Server;
}
