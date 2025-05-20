package it.polimi.ingsw.galaxytrucker;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GalaxyTruckerApp extends Application {
    private static final int ROWS = 5;
    private static final int COLS = 7;
    private static final int TILE_SIZE = 124;
    private static final int TOP_BORDER = 29;
    private static final int BOTTOM_BORDER = 25;
    private static final int LEFT_BORDER = 35;
    private static final int RIGHT_BORDER = 27;
    private static final int RESERVED_TOP_BORDER = 21;
    private static final int RESERVED_LEFT_BORDER = 670;
    private static final int GRID_WIDTH = COLS * TILE_SIZE; // 868
    private static final int GRID_HEIGHT = ROWS * TILE_SIZE; // 620
    private static final int TOT_WIDTH = 937;
    private static final int TOT_HEIGHT = 679;

    @Override
    public void start(Stage primaryStage) {
        ShipGrid shipGrid = new ShipGrid(Color.BLUE);

        Pane root = new Pane();
        root.getChildren().add(shipGrid);  // shipGrid handles its own internal layout

        // uncomment to see the border of the main grid (red) and of the reserved grid (green)
//        Rectangle marker = new Rectangle(GRID_WIDTH, 1, javafx.scene.paint.Color.RED);
//        Rectangle marker2 = new Rectangle(1, GRID_HEIGHT, javafx.scene.paint.Color.RED);
//        Rectangle marker3 = new Rectangle(GRID_WIDTH, 1, javafx.scene.paint.Color.RED);
//        Rectangle marker4 = new Rectangle(1, GRID_HEIGHT, javafx.scene.paint.Color.RED);
//        marker.setLayoutX(LEFT_BORDER);
//        marker.setLayoutY(TOP_BORDER);
//        marker2.setLayoutX(LEFT_BORDER);
//        marker2.setLayoutY(TOP_BORDER);
//        marker3.setLayoutX(LEFT_BORDER);
//        marker3.setLayoutY(TOP_BORDER + GRID_HEIGHT);
//        marker4.setLayoutX(LEFT_BORDER + GRID_WIDTH);
//        marker4.setLayoutY(TOP_BORDER);
//        root.getChildren().add(marker);
//        root.getChildren().add(marker2);
//        root.getChildren().add(marker3);
//        root.getChildren().add(marker4);
//
//        Rectangle res1 = new Rectangle(TILE_SIZE * 2, 1, javafx.scene.paint.Color.GREEN);
//        Rectangle res2 = new Rectangle(1, TILE_SIZE, javafx.scene.paint.Color.GREEN);
//        Rectangle res3 = new Rectangle(TILE_SIZE * 2, 1, javafx.scene.paint.Color.GREEN);
//        Rectangle res4 = new Rectangle(1, TILE_SIZE, javafx.scene.paint.Color.GREEN);
//        res1.setLayoutX(RESERVED_LEFT_BORDER);
//        res1.setLayoutY(RESERVED_TOP_BORDER);
//        res2.setLayoutX(RESERVED_LEFT_BORDER);
//        res2.setLayoutY(RESERVED_TOP_BORDER);
//        res3.setLayoutX(RESERVED_LEFT_BORDER);
//        res3.setLayoutY(RESERVED_TOP_BORDER + TILE_SIZE);
//        res4.setLayoutX(RESERVED_LEFT_BORDER + TILE_SIZE * 2);
//        res4.setLayoutY(RESERVED_TOP_BORDER);
//        root.getChildren().add(res1);
//        root.getChildren().add(res2);
//        root.getChildren().add(res3);
//        root.getChildren().add(res4);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Galaxy Trucker - JavaFX");
        primaryStage.setResizable(false);

        primaryStage.getIcons().add(
                new javafx.scene.image.Image(getClass().getResource("/Images/icon/window_simple_icon.png").toExternalForm())
        );

        // Post-show correction for window decoration
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
