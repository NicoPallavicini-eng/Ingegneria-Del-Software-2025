package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class TravellingScene extends MyScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 800;
    private SceneManager sceneManager;

    public TravellingScene(Game game, String nickname, SceneManager sceneManager) {
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;
        this.background = new Background();
        this.root = new StackPane();
        root.getChildren().add(background);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
    }

    public Scene getScene() {
        return scene;
    }
}