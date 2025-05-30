package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.OthersShipGrid;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.TilePileGrid;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class BuildingSceneTilePile extends MyScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private TilePileGrid tilePileGrid;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 750;
    private SceneManager sceneManager;

    public BuildingSceneTilePile(Game game, String nickname, SceneManager sceneManager) {
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;

        this.background = new Background();
        this.root = new StackPane();

        // see TilePile
        this.tilePileGrid = new TilePileGrid();

        // tilePile view
        root.getChildren().addAll(background, tilePileGrid);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
    }

    public Scene getScene() {
        return scene;
    }
}
