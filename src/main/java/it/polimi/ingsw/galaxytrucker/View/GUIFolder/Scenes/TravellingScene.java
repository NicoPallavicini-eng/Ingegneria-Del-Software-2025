package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TravellingScene {
    private Scene scene;

    public TravellingScene() {
        VBox root = new VBox(20);
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            // Switch to ship build scene
        });

        root.getChildren().add(startButton);
        this.scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }
}