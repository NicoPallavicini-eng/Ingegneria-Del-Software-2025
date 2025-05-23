package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class BuildingScene {
    private Scene scene;
    private Game game;
    private String nickname;

    public BuildingScene(Game game, String nickname) {
        this.game = game;
        this.nickname = nickname;

        Player user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        Background background = new Background();
        UserShipGrid userShipGrid = new UserShipGrid(userShip.getColor());
        StackPane root = new StackPane();
        root.getChildren().addAll(background, userShipGrid);

        scene = new Scene(root, 1024, 750); // default sizing for now
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
