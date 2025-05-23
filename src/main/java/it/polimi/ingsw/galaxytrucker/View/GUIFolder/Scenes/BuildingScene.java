package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.ShipGrid;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class BuildingScene {
    private Scene scene;
    private Game game;
    private String nickname;

    public BuildingScene(Game game, String nickname) {
        this.game = game;
        this.nickname = nickname;

        Background background = new Background();
        ShipGrid shipGrid = new ShipGrid();
        StackPane root = new StackPane();
        root.getChildren().add(background);

        scene = new Scene(root, 1300, 750); // default sizing for now


    }

    public Scene getScene() {
        return scene;
    }
}
