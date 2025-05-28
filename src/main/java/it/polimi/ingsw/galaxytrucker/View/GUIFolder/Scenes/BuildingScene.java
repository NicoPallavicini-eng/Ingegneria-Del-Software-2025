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

public class BuildingScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private UserShipGrid userShipGrid;
    private TilePileGrid tilePileGrid;
    private Background background;
    private OthersShipGrid othersShipGrid;

    public BuildingScene(Game game, String nickname) {
        this.game = game;
        this.nickname = nickname;

        Player user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        this.background = new Background();
        this.root = new StackPane();

        // see userShip
        this.userShipGrid = new UserShipGrid(userShip.getColor());
        // see TilePile
        this.tilePileGrid = new TilePileGrid();
        // see others' ships
        this.othersShipGrid = new OthersShipGrid(userShip.getColor());

        // userShip view (default)
        // root.getChildren().addAll(background, userShipGrid);
        // tilePile view
        // root.getChildren().addAll(background, tilePileGrid);
        // others' ships view
        root.getChildren().addAll(background, othersShipGrid);

        scene = new Scene(root, 1024, 750); // default sizing for now
    }

    public void viewTilePile() {
        root.getChildren().remove(userShipGrid);
        root.getChildren().add(tilePileGrid);
    }

    public void viewShip () {
        root.getChildren().remove(tilePileGrid);
        root.getChildren().add(userShipGrid);
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
