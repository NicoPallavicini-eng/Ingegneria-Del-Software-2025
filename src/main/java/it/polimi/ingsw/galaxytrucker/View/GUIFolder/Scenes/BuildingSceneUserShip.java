package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.application.Platform;
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

/**
 * GUI scene representing the user's ship during the building phase.
 * Displays the user's ship grid, navigation buttons, and handles user interactions
 * for placing, rotating, and managing tiles on the ship.
 */
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
    private Ship userShip;
    private StackPane centerContent;
    private Button viewOthersButton;
    private Button viewTilePileButton;
    private Button viewBoardButton;
    private Button hourglassButton;
    private HBox buttonBox;
    private StackPane rootWithBackground;
    private BorderPane layout;
    /**
     * Constructs the user ship scene with the given game, user nickname, and scene manager.
     *
     * @param game the game model
     * @param nickname the user's nickname
     * @param sceneManager the scene manager for navigation
     */
    public BuildingSceneUserShip(Game game, String nickname, SceneManager sceneManager) {
        super(game, sceneManager);
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;

        user = checkPlayer(nickname);
        this.userShip = user.getShip();

        this.background = new Background();
        layout = new BorderPane();
        this.root = new BorderPane();

        // see userShip
        this.userShipGrid = new UserShipGrid(userShip.getColor(), this);
        centerContent = new StackPane(userShipGrid);

        // --- Bottom Buttons ---
        viewOthersButton = new Button("View Others' Ships");
        viewTilePileButton = new Button("View Tile Pile");
        viewBoardButton = new Button("View Board");
        hourglassButton = new Button("Hourglass");
        viewOthersButton.getStyleClass().add("bottom-button");
        viewTilePileButton.getStyleClass().add("bottom-button");
        viewBoardButton.getStyleClass().add("bottom-button");
        hourglassButton.getStyleClass().add("bottom-button");

        viewOthersButton.setOnAction(e -> {
            sendMessageToServer("/viewships", this.nickname);
            sceneManager.switchBuilding(this, "OthersShip");
        });
        viewTilePileButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "TilePile");
        });
        viewBoardButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "Board");
        });
        hourglassButton.setOnAction(e -> {
            try {
                sendMessageToServer("/fliphourglass", this.nickname);
            } catch (IllegalGUIEventException ex) {
                System.out.println(ex.getMessage());
            }
        });

        buttonBox = new HBox(100, viewOthersButton, viewTilePileButton, viewBoardButton, hourglassButton);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        layout.setCenter(centerContent);
        layout.setBottom(buttonBox);

        // Now wrap layout with background in a StackPane
        rootWithBackground = new StackPane();
        rootWithBackground.getChildren().addAll(background, layout);

        scene = new Scene(rootWithBackground, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.sceneManager.setUserShipScene(this, userShipGrid);
    }

    /**
     * Returns the game model.
     *
     * @return the game model
     */
    public Game getGame(){
        return game;
    }
    /**
     * Returns the user/player object.
     *
     * @return the user/player
     */
    public Player getUser(){
        return user;
    }

    /**
     * Returns the Player object for the given nickname.
     *
     * @param nickname the nickname to search for
     * @return the Player object, or null if not found
     */
    public Player checkPlayer(String nickname) {
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }
    /**
     * Sets the tile in hand and updates the hand tile view.
     *
     * @param tile the tile to set in hand
     * @param rotation the rotation to apply to the hand tile
     */
    public void setInHand(Tile tile, int rotation) {
        userShipGrid.setHandTile(tile, rotation);
        rotateVisible = true;
        userShipGrid.updateRotateVisible(rotateVisible);
    }
    /**
     * Returns whether the rotate buttons are visible.
     *
     * @return true if rotate buttons are visible, false otherwise
     */
    public boolean getRotateVisible() {
        return rotateVisible;
    }
    /**
     * Sets the visibility of the rotate buttons.
     *
     * @param rotateVisible true to show rotate buttons, false to hide
     */
    public void setRotateVisible(boolean rotateVisible) {
        this.rotateVisible = rotateVisible;
    }

    /**
     * Empties the hand tile view and disables it.
     */
    public void emptyHand() {
        userShipGrid.getHandTile().setLogicTile(null);
        userShipGrid.getHandTile().setClickable(false);
        userShipGrid.updateRotateVisible(false);
    }
    /**
     * Returns the user ship grid component.
     *
     * @return the user ship grid
     */
    public UserShipGrid getUserShipGrid() {
        return userShipGrid;
    }
    /**
     * Sets the reference to the other players' ships building scene.
     *
     * @param buildingSceneOthersShip the others' ships scene
     */
    public void setBuildingSceneOthersShip(BuildingSceneOthersShip buildingSceneOthersShip) {
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
     * Sets the reference to the tile pile building scene.
     *
     * @param buildingSceneTilePile the tile pile scene
     */
    public void setBuildingSceneTilePile(BuildingSceneTilePile buildingSceneTilePile) {
        this.buildingSceneTilePile = buildingSceneTilePile;
    }
    /**
     * Returns the tile pile building scene.
     *
     * @return the tile pile scene
     */
    public BuildingSceneTilePile getBuildingSceneTilePile() {
        return buildingSceneTilePile;
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
     * Returns the JavaFX scene for this user ship.
     *
     * @return the JavaFX scene
     */
    public Scene getScene() {
        return scene;
    }
    /**
     * Updates the game model reference and refreshes the user ship grid.
     *
     * @param game the new game model
     */
    public void updateGame(Game game) {
        this.game = game;
        update();

    }

    /**
     * Enables alien selection mode, allowing the user to select a tile for alien placement.
     *
     * @param message the message to send to the server on selection
     * @param game the current game model
     */
    public void enableAlienSelection(String message, Game game){
        this.game = game;
        this.userShip = user.getShip();
        userShipGrid.getTiles().forEach(tile -> {
            tile.getOverlayButton().setOnAction(e -> {
                tile.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                sendMessageToServer(message + " " + (userShip.findTileOnFloorplanRow(tile.getLogicTile()) +5) + "," + (userShip.findTileOnFloorPlanColumn(tile.getLogicTile()) + 4), this.nickname);
                disableTileSelection();
                sceneManager.switchBuilding(this, "Board");
            });
        });
    }

    /**
     * Enables tile removal mode, allowing the user to select a tile to remove.
     *
     * @param message the message to send to the server on removal
     * @param game the current game model
     */
    public void removeTile(String message, Game game){
        this.game = game;
        this.userShip = user.getShip();
        userShipGrid.getTiles().forEach(tile -> {
            tile.getOverlayButton().setOnAction(e -> {
                tile.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                sendMessageToServer(message + " " + (userShip.findTileOnFloorplanRow(tile.getLogicTile()) +5) + "," + (userShip.findTileOnFloorPlanColumn(tile.getLogicTile()) + 4), this.nickname);
                disableTileSelection();
            });
        });
    }

    private void disableTileSelection() {
        userShipGrid.getTiles().forEach(tile -> {
            tile.getOverlayButton().setOnAction(e -> {
                        tile.setStyle("-fx-border-color: transparent; -fx-border-width: 0px;");
                    });
            });
    }
    /**
     * Updates the user/player and user ship grid from the current game state.
     */

    public void update(){
        this.user = game.getListOfActivePlayers()
                .stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
        if (user != null) {
            this.userShipGrid = new UserShipGrid(userShip.getColor(), this);
            Platform.runLater(() -> {
                this.centerContent = new StackPane(userShipGrid);
                this.layout.setCenter(centerContent);
            });
        }
    }


}
