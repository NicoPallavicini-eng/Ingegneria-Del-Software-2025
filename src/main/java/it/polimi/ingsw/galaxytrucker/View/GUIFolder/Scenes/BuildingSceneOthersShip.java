package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.OthersShipGrid;
import javafx.application.Platform;
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
    private final int SCENE_HEIGHT = 760;
    private SceneManager sceneManager;
    private BuildingSceneUserShip buildingSceneUserShip = null;
    private BuildingSceneTilePile buildingSceneTilePile = null;
    private BuildingSceneBoard buildingSceneBoard = null;
    private Ship userShip;
    private Player user;
    private BorderPane layout;
    private StackPane centerContent;
    private Button viewUserButton;
    private Button viewTilePileButton;
    private Button viewBoardButton;
    private Button travelButton;
    private HBox buttonBox;
    private StackPane rootWithBackground;

    public BuildingSceneOthersShip(Game game, String nickname, SceneManager sceneManager) {
        super(game, sceneManager);
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;

        this.user = checkPlayer(nickname);
        this.userShip = user.getShip();

        this.background = new Background();
        this.layout = new BorderPane();

        // see others' ships
        this.othersShipGrid = new OthersShipGrid(userShip.getColor(), this, game.getListOfActivePlayers());
        this.centerContent = new StackPane(othersShipGrid);

        // --- Bottom Buttons ---
        viewUserButton = new Button("View User Ship");
        viewTilePileButton = new Button("View Tile Pile");
        viewBoardButton = new Button("View Board");
        travelButton = new Button("Travel");
        viewUserButton.getStyleClass().add("bottom-button");
        viewTilePileButton.getStyleClass().add("bottom-button");
        viewBoardButton.getStyleClass().add("bottom-button");
        travelButton.getStyleClass().add("next-button");

        viewUserButton.setOnAction(e -> {;
            sceneManager.switchBuilding(this, "UserShip");
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

        buttonBox = new HBox(100, viewUserButton, viewTilePileButton, viewBoardButton, travelButton);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        layout.setCenter(centerContent);
        layout.setBottom(buttonBox);

        // Now wrap layout with background in a StackPane
        rootWithBackground = new StackPane();
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
        update();
    }

    private void update(){
       this.othersShipGrid = new OthersShipGrid(userShip.getColor(), this,game.getListOfActivePlayers());
//       for (Player player : game.getListOfPlayers()) {
//           if (player != user){
//               this.othersShipGrid.populateGrid(player.getShip());
//           }
//       }
       Platform.runLater(() -> {
            this.centerContent = new StackPane(othersShipGrid);
            this.layout.setCenter(centerContent);
        });
    }
}
