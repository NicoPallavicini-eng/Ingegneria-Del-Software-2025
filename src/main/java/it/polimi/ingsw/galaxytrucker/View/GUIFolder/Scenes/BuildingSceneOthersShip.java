package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.OthersShipGrid;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.TilePileGrid;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class BuildingSceneOthersShip extends MyScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private BorderPane root;
    private Background background;
    private OthersShipGrid othersShipGrid;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 800;
    private SceneManager sceneManager;
    private BuildingSceneUserShip buildingSceneUserShip = null;
    private BuildingSceneTilePile buildingSceneTilePile = null;

    public BuildingSceneOthersShip(Game game, String nickname, SceneManager sceneManager) {
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;

        Player user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        this.background = new Background();
        BorderPane layout = new BorderPane();
        this.root = new BorderPane();

        // see others' ships
        this.othersShipGrid = new OthersShipGrid(userShip.getColor(), this);
        StackPane centerContent = new StackPane(othersShipGrid);

        // --- Bottom Buttons ---
        Button viewOthersButton = new Button("View User Ship");
        Button viewTilePileButton = new Button("View Tile Pile");
        viewOthersButton.getStyleClass().add("bottom-button");
        viewTilePileButton.getStyleClass().add("bottom-button");

        viewOthersButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "UserShip");
        });

        viewTilePileButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "TilePile");
        });

        HBox buttonBox = new HBox(600, viewOthersButton, viewTilePileButton);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        layout.setCenter(centerContent);
        layout.setBottom(buttonBox);

        // Now wrap layout with background in a StackPane
        StackPane rootWithBackground = new StackPane();
        rootWithBackground.getChildren().addAll(background, layout);

        scene = new Scene(rootWithBackground, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        sceneManager.setOthersShipScene(this);
    }

    public Player checkPlayer(String nickname) {
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }

    public void setBuildingSceneTilePile(BuildingSceneTilePile buildingSceneTilePile) {
        this.buildingSceneTilePile = buildingSceneTilePile;
    }

    public BuildingSceneTilePile getBuildingSceneTilePile() {
        return buildingSceneTilePile;
    }

    public void setBuildingSceneUserShip(BuildingSceneUserShip buildingSceneUserShip) {
        this.buildingSceneUserShip = buildingSceneUserShip;
    }

    public BuildingSceneUserShip getBuildingSceneUserShip() {
        return buildingSceneUserShip;
    }

    public Scene getScene() {
        return scene;
    }
}
