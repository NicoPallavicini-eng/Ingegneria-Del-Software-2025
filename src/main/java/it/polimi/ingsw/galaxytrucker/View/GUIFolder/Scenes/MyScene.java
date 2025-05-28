package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class MyScene {
    private Scene scene;
    private Game game;
    private String nickname;

    public MyScene(Game game, String nickname) {
        this.game = game;
        this.nickname = nickname;
    }

    public Scene getScene() {
        return scene;
    }
}
