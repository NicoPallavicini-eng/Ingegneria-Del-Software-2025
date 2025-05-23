package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TravellingScene {
    private Scene scene;

    public TravellingScene() {Background background = new Background();
        StackPane root = new StackPane();
        root.getChildren().add(background);

        scene = new Scene(root, 1300, 750); // default sizing for now
    }

    public Scene getScene() {
        return scene;
    }
}