package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class WaitingScene extends MyScene{
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 750;

    public WaitingScene(Game game, String nickname) {
        this.game = game;
        this.nickname = nickname;
        this.background = new Background();
        this.root = new StackPane();
        root.getChildren().add(background);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
    }

    public Scene getScene() {
        return scene;
    }
}
