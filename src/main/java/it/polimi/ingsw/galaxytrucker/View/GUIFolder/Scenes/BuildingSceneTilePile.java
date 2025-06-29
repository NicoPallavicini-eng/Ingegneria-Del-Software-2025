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

/**
 * GUI scene representing the tile pile during the building phase.
 * Displays all available tiles, navigation buttons, and handles user interactions
 * for picking up and putting down tiles.
 */
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

    /**
     * Constructs the tile pile scene with the given game, user nickname, and scene manager.
     *
     * @param game the game model
     * @param nickname the user's nickname
     * @param sceneManager the scene manager for navigation
     */
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
    /**
     * Sets the reference to the user's ship building scene.
     *
     * @param buildingSceneUserShip the user ship scene
     */
    public void setBuildingSceneUserShip (BuildingSceneUserShip buildingSceneUserShip) {
        this.buildingSceneUserShip = buildingSceneUserShip;
    }
    /**
     * Returns the user's ship building scene.
     *
     * @return the user ship scene
     */
    public BuildingSceneUserShip getBuildingSceneUserShip() {
        return buildingSceneUserShip;
    }
    /**
     * Sets the reference to the other players' ships building scene.
     *
     * @param buildingSceneOthersShip the others' ships scene
     */
    public void setBuildingSceneOthersShip (BuildingSceneOthersShip buildingSceneOthersShip) {
        this.buildingSceneOthersShip = buildingSceneOthersShip;
    }
    /**
     * Returns the other players' ships building scene.
     *
     * @return the others' ships scene
     */
    public BuildingSceneOthersShip getBuildingSceneOthersShip() {
        return buildingSceneOthersShip;
    }
    /**
     * Sets the reference to the board building scene.
     *
     * @param buildingSceneBoard the board scene
     */
    public void setBuildingSceneBoard(BuildingSceneBoard buildingSceneBoard) {
        this.buildingSceneBoard = buildingSceneBoard;
    }
    /**
     * Returns the board building scene.
     *
     * @return the board scene
     */

    public BuildingSceneBoard getBuildingSceneBoard() {
        return buildingSceneBoard;
    }
    /**
     * Returns the JavaFX scene for this tile pile.
     *
     * @return the JavaFX scene
     */
    public Scene getScene() {
        return scene;
    }
    /**
     * Handles picking up a tile from the pile.
     * Sends a message to the server to pick up the specified tile.
     *
     * @param tile the tile view to pick up
     */
    public void pickUpTile(TilePileTileView tile) {
        int index = tilePile.indexOf(tile.getLogicTile());
        try {
            sendMessageToServer("/pickuptile " + (index / 16) + "," + (index % 16), this.nickname);
        }
        catch (IllegalGUIEventException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Handles putting down a tile from the user's hand back to the pile.
     * Sends a message to the server to put down the tile.
     *
     * @param tile the reserved tile view to put down
     */
    public void putDownTile(ReservedTileView tile) {
        int index = tilePileGrid.getFirstEmpty();
        tile.setFull(false);
        //ImageView img = tilePileGrid.getTileImageView(buildingSceneUserShip.getUserShipGrid().getHandTile());
        int rotation = buildingSceneUserShip.getUserShipGrid().getHandTile().getRotation();
        Tile t = tile.getLogicTile();
        try {
            sendMessageToServer("/putdowntile", this.nickname);
        } catch (IllegalGUIEventException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the game model reference and refreshes the tile pile.
     *
     * @param game the new game model
     */
    public void updateGame(Game game) {
        this.game = game;
        update();
    }

    /**
     * Refreshes the tile pile grid and updates the center content.
     */
    public void update() {
        this.tilePile = game.getTilePile().getTilePile();
        this.tilePileGrid = new TilePileGrid(this, tilePile);
        Platform.runLater(() -> {
            centerContent = new StackPane(tilePileGrid);
            this.layout.setCenter(centerContent);
        });
    }
}
