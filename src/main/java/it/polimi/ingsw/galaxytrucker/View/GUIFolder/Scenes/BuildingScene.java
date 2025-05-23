package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.ShipGrid;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class BuildingScene {
    private final Scene scene;

    public BuildingScene() {
        Pane pane = new Pane();

        pane.getChildren();


        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }
}