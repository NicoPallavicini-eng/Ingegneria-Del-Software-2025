package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class WaitingScene {
    private Scene scene;
    private Game game;
    private String nickname;

    public WaitingScene(Game game, String nickname) {
        this.game = game;
        this.nickname = nickname;
        Background background = new Background();
        StackPane root = new StackPane();
        root.getChildren().add(background);

        scene = new Scene(root, 1024, 750); // default sizing for now
    }

    public Scene getScene() {
        return scene;
    }
}
