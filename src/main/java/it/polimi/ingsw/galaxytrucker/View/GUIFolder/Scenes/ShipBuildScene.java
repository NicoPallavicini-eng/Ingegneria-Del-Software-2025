package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.ShipGrid;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ShipBuildScene {
    private final Scene scene;

    public ShipBuildScene() {
        StackPane root = new StackPane();

        // Set background image
        ImageView background = new ImageView(new Image(getClass().getResource("/images/bg/ship-bg.png").toExternalForm()));
        background.setFitWidth(1125); // 9 * 125
        background.setFitHeight(875); // 7 * 125

        Color color = Color.BLUE; // TODO set properly

        ShipGrid grid = new ShipGrid(color);

        root.getChildren().addAll(background, grid);

        this.scene = new Scene(root, 1125, 875);
    }

    public Scene getScene() {
        return scene;
    }
}