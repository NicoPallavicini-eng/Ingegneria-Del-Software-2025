package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class WaitingScene {
    private Scene scene;

    public WaitingScene() {
        Background background = new Background();
        StackPane root = new StackPane();
        root.getChildren().add(background);

        scene = new Scene(root, 1300, 750); // default sizing for now
    }

    public Scene getScene() {
        return scene;
    }
}
