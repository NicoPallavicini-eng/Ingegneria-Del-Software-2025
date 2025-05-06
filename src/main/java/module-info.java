module it.polimi.ingsw.galaxytrucker {
    exports it.polimi.ingsw.galaxytrucker.Network.Server;
    exports it.polimi.ingsw.galaxytrucker.Network.Client;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.rmi;
    requires jdk.jfr;

    opens it.polimi.ingsw.galaxytrucker to javafx.fxml;
    exports it.polimi.ingsw.galaxytrucker;

    exports it.polimi.ingsw.galaxytrucker.Controller.Server;
}