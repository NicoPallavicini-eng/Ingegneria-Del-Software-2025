package it.polimi.ingsw.galaxytrucker;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GalaxyTruckerApp extends Application {
    private final int SCENE_WIDTH = 937;
    private final int SCENE_HEIGHT = 679;

    @Override
    public void start(Stage primaryStage) {
        // Create the ship grid
        ShipGrid shipGrid = new ShipGrid(Color.BLUE);

        // Optionally: wrap in root container if you want a background or other layers
        Pane root = new Pane();
        shipGrid.setLayoutX(0);  // absolute positioning
        shipGrid.setLayoutY(0);
        root.getChildren().add(shipGrid);

        // Create the scene and add it to the stage
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setTitle("Galaxy Trucker - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
