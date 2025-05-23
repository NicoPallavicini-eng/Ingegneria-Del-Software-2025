package it.polimi.ingsw.galaxytrucker.View.Trials;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.*;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.BuildingScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUITrial extends Application {
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
    private Game game;
    private String nickname;

    @Override
    public void start(Stage primaryStage) {
        game = new Game();
        Player player = new Player("mewmew", "mew", Color.BLUE);
        game.addPlayer(player);
        nickname = player.getNickname();
//        UserShipGrid shipGrid = new UserShipGrid(Color.BLUE);
//
//        Pane root = new Pane();
//        root.getChildren().add(shipGrid);  // shipGrid handles its own internal layout

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

        Scene scene = new BuildingScene(game, nickname).getScene(); // tmp to test

        primaryStage.setScene(scene);
        primaryStage.setTitle("Galaxy Trucker - JavaFX");
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("/Images/misc/window_simple_icon.png").toExternalForm()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
