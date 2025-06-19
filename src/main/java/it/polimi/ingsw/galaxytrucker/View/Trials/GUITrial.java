package it.polimi.ingsw.galaxytrucker.View.Trials;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

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
    private static Stage stage;
    private SceneManager sceneManager;
    private MyScene currentScene;

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        game = new Game();
        stage = new Stage();
        currentScene = null;
        Player player = new Player("mewmew", "mew", Color.BLUE);
        game.addPlayer(player);
        sceneManager = new SceneManager(game, stage,null, null);

        sceneManager.start(stage);
        currentScene = sceneManager.getScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
