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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class BuildingSceneUserShip extends MyScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private BorderPane root;
    private UserShipGrid userShipGrid;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 800;
    private SceneManager sceneManager;
    private BuildingSceneOthersShip buildingSceneOthersShip = null;
    private BuildingSceneTilePile buildingSceneTilePile = null;

    public BuildingSceneUserShip(Game game, String nickname, SceneManager sceneManager) {
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;

        Player user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        this.background = new Background();
        BorderPane layout = new BorderPane();
        this.root = new BorderPane();

        // see userShip
        this.userShipGrid = new UserShipGrid(userShip.getColor(), this);
        StackPane centerContent = new StackPane(userShipGrid);

        // --- Bottom Buttons ---
        Button viewOthersButton = new Button("View Others' Ships");
        Button viewTilePileButton = new Button("View Tile Pile");
        viewOthersButton.getStyleClass().add("bottom-button");
        viewTilePileButton.getStyleClass().add("bottom-button");

        viewOthersButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "OthersShip");
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
        sceneManager.setUserShipScene(this);
    }

    public Player checkPlayer(String nickname) {
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }

    public void setReserved(ImageView img, int slot) { // TODO check if useful
        userShipGrid.setResTile(slot, img);
    }

    public void setInHand(ImageView img) {
        userShipGrid.setHandTile(img);
    }

    public void emptyHand() {
        userShipGrid.getHandTile().clearTileImage();
        userShipGrid.getHandTile().setClickable(false);
    }

    public UserShipGrid getUserShipGrid() {
        return userShipGrid;
    }

    public void setBuildingSceneOthersShip(BuildingSceneOthersShip buildingSceneOthersShip) {
        this.buildingSceneOthersShip = buildingSceneOthersShip;
    }

    public BuildingSceneOthersShip getBuildingSceneOthersShip() {
        return buildingSceneOthersShip;
    }

    public void setBuildingSceneTilePile(BuildingSceneTilePile buildingSceneTilePile) {
        this.buildingSceneTilePile = buildingSceneTilePile;
    }

    public BuildingSceneTilePile getBuildingSceneTilePile() {
        return buildingSceneTilePile;
    }

    public Scene getScene() {
        return scene;
    }
}
