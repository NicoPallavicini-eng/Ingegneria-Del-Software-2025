package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.*;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class BuildingSceneTilePile extends MyScene {
    private Scene scene;
    private Game game;
    private List<Tile> tilePile;
    private String nickname;
    private BorderPane root;
    private TilePileGrid tilePileGrid;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 760;
    private SceneManager sceneManager;
    private BuildingSceneUserShip buildingSceneUserShip = null;
    private BuildingSceneOthersShip buildingSceneOthersShip = null;
    private BuildingSceneBoard buildingSceneBoard = null;
    private BorderPane layout;
    private Button viewOthersButton;
    private Button viewUserButton;
    private Button viewBoardButton;
    private Button travelButton;
    private StackPane centerContent;
    private HBox buttonBox;
    private StackPane rootWithBackground;

    public BuildingSceneTilePile(Game game, String nickname, SceneManager sceneManager) {
        super(game,sceneManager);
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;
        this.tilePile = game.getTilePile().getTilePile();

        this.background = new Background();
        layout = new BorderPane();

        // see TilePile
        this.tilePileGrid = new TilePileGrid(this, tilePile);
        centerContent = new StackPane(tilePileGrid);

        // --- Bottom Buttons ---
        viewOthersButton = new Button("View Others' Ships");
        viewUserButton = new Button("View User Ship");
        viewBoardButton = new Button("View Board");
        travelButton = new Button("Travel");
        viewOthersButton.getStyleClass().add("bottom-button");
        viewUserButton.getStyleClass().add("bottom-button");
        viewBoardButton.getStyleClass().add("bottom-button");
        travelButton.getStyleClass().add("next-button");

        viewOthersButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "OthersShip");
        });
        viewUserButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "UserShip");
        });
        viewBoardButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "Board");
        });
        travelButton.setOnAction(e -> {
            sceneManager.next(this);
        });

        buttonBox = new HBox(100, viewOthersButton, viewUserButton, viewBoardButton, travelButton);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        layout.setCenter(centerContent);
        layout.setBottom(buttonBox);

        // Now wrap layout with background in a StackPane
        rootWithBackground = new StackPane();
        rootWithBackground.getChildren().addAll(background, layout);

        scene = new Scene(rootWithBackground, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.sceneManager.setTilePileScene(this);
    }

    public void setBuildingSceneUserShip (BuildingSceneUserShip buildingSceneUserShip) {
        this.buildingSceneUserShip = buildingSceneUserShip;
    }

    public BuildingSceneUserShip getBuildingSceneUserShip() {
        return buildingSceneUserShip;
    }

    public void setBuildingSceneOthersShip (BuildingSceneOthersShip buildingSceneOthersShip) {
        this.buildingSceneOthersShip = buildingSceneOthersShip;
    }

    public BuildingSceneOthersShip getBuildingSceneOthersShip() {
        return buildingSceneOthersShip;
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

    public void pickUpTile(TilePileTileView tile) {
        int index = tilePile.indexOf(tile.getLogicTile());
        try {
            sendMessageToServer("/pickuptile " + (index / 16) + "," + (index % 16), this.nickname);
        }
        catch (IllegalGUIEventException e){
            errorPopUp(e);
        }
    }

    public void putDownTile(ReservedTileView tile) {
        int index = tilePileGrid.getFirstEmpty();
        tile.setFull(false);
        //ImageView img = tilePileGrid.getTileImageView(buildingSceneUserShip.getUserShipGrid().getHandTile());
        int rotation = buildingSceneUserShip.getUserShipGrid().getHandTile().getRotation();
        Tile t = tile.getLogicTile();
        try {
            sendMessageToServer("/putdowntile", this.nickname);
        } catch (IllegalGUIEventException e){
            errorPopUp(e);
        }
    }

    public void updateGame(Game game) {
        this.game = game;
        update();
    }

    public void update() {
        this.tilePile = game.getTilePile().getTilePile();
        this.tilePileGrid = new TilePileGrid(this, tilePile);
        Platform.runLater(() -> {
            centerContent = new StackPane(tilePileGrid);
            this.layout.setCenter(centerContent);
        });
    }

    private void errorPopUp(IllegalGUIEventException e){
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert errorAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setContentText(e.getMessage());

            Stage errorStage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
            errorStage.getIcons().clear();
            errorStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/misc/window_simple_icon.png"))));
            errorAlert.showAndWait();
        });
    }
}
