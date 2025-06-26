package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;

public class BuildingSceneUserShip extends MyScene {
    private Scene scene;
    private Game game;
    private Player user;
    private String nickname;
    private BorderPane root;
    private UserShipGrid userShipGrid;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 760;
    private SceneManager sceneManager;
    private BuildingSceneOthersShip buildingSceneOthersShip = null;
    private BuildingSceneTilePile buildingSceneTilePile = null;
    private BuildingSceneBoard buildingSceneBoard = null;
    private boolean rotateVisible = false;

    public BuildingSceneUserShip(Game game, String nickname, SceneManager sceneManager) {
        super(game, sceneManager);
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;

        user = checkPlayer(nickname); // TODO fix, rn is null
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
        Button viewBoardButton = new Button("View Board");
        Button travelButton = new Button("Travel");
        viewOthersButton.getStyleClass().add("bottom-button");
        viewTilePileButton.getStyleClass().add("bottom-button");
        viewBoardButton.getStyleClass().add("bottom-button");
        travelButton.getStyleClass().add("next-button");

        viewOthersButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "OthersShip");
        });
        viewTilePileButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "TilePile");
        });
        viewBoardButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "Board");
        });
        travelButton.setOnAction(e -> {
            sceneManager.next(this);
        });

        HBox buttonBox = new HBox(100, viewOthersButton, viewTilePileButton, viewBoardButton, travelButton);
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

    public Game getGame(){
        return game;
    }

    public Player getUser(){
        return user;
    }

    public Player checkPlayer(String nickname) {
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }

    public void setInHand(Tile tile, int rotation) {
        userShipGrid.setHandTile(tile, rotation);
        rotateVisible = true;
        userShipGrid.updateRotateVisible(rotateVisible);
    }

    public boolean getRotateVisible() {
        return rotateVisible;
    }

    public void setRotateVisible(boolean rotateVisible) {
        this.rotateVisible = rotateVisible;
    }

    public void emptyHand() {
        userShipGrid.getHandTile().setLogicTile(null);
        userShipGrid.getHandTile().setClickable(false);
        userShipGrid.updateRotateVisible(false);
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

    public void setBuildingSceneBoard(BuildingSceneBoard buildingSceneBoard) {
        this.buildingSceneBoard = buildingSceneBoard;
    }

    public BuildingSceneBoard getBuildingSceneBoard() {
        return buildingSceneBoard;
    }

    public Scene getScene() {
        return scene;
    }

    public void updateGame(Game game) {
        this.game = game;
    }


}
