package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Deck;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.OthersShipGrid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class TravellingSceneOthersShip extends MyScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private BorderPane root;
    private it.polimi.ingsw.galaxytrucker.Model.Cards.Deck gameDeck;
    private Deck deck;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 760;
    private SceneManager sceneManager;
    private Player user;
    private StackPane boardPane;
    private StackPane cardPane;
    private Pane logPane;
    private TravellingSceneDefault travellingSceneDefault;
    private OthersShipGrid othersShipGrid;

    public TravellingSceneOthersShip(Game game, String nickname, SceneManager sceneManager) {
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;
        this.gameDeck = game.getDeck();

        deck = new Deck(gameDeck);

        user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        this.background = new Background();
        BorderPane layout = new BorderPane();

        // see others' ships
        this.othersShipGrid = new OthersShipGrid(userShip.getColor(), null); // TODO check that null is ok
        StackPane centerContent = new StackPane(othersShipGrid);

        Button defaultView = new Button("Default View");
        Button finish = new Button("Finish");
        defaultView.getStyleClass().add("bottom-button");
        finish.getStyleClass().add("next-button");

        defaultView.setOnAction(e -> {
            sceneManager.switchTravelling(this);
        });

        finish.setOnAction(e -> {
            // TODO introduce checks
            sceneManager.next(this);
        });

        HBox buttonBox = new HBox(300, defaultView, finish);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        layout.setCenter(centerContent);
        layout.setBottom(buttonBox);

        StackPane rootWithBackground = new StackPane();
        rootWithBackground.getChildren().addAll(background, layout);

        scene = new Scene(rootWithBackground, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        sceneManager.setTravellingSceneOthersShip(this);
    }

    public Scene getScene() {
        return scene;
    }

    public Player checkPlayer(String nickname) {
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }

    public void updateGame(Game game) {
        this.game = game;
    }

    public void setTravellingSceneDefault(TravellingSceneDefault travellingSceneDefault) {
        this.travellingSceneDefault = travellingSceneDefault;
    }
}
