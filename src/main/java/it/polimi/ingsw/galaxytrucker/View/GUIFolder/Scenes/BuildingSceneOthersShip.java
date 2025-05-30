package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.OthersShipGrid;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.TilePileGrid;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class BuildingSceneOthersShip extends MyScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private Background background;
    private OthersShipGrid othersShipGrid;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 750;

    public BuildingSceneOthersShip(Game game, String nickname) {
        this.game = game;
        this.nickname = nickname;

        Player user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        this.background = new Background();
        this.root = new StackPane();

        // see others' ships
        this.othersShipGrid = new OthersShipGrid(userShip.getColor());

        // others' ships view
        root.getChildren().addAll(background, othersShipGrid);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
    }

    public Player checkPlayer(String nickname) {
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }

    public Scene getScene() {
        return scene;
    }
}
