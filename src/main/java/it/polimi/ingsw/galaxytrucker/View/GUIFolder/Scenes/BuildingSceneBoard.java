package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.*;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.Trials.AnsiColor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Optional;

public class BuildingSceneBoard extends MyScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private BorderPane root;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 760;
    private SceneManager sceneManager;
    private BoardGrid boardGrid;
    private BuildingSceneUserShip buildingSceneUserShip = null;
    private BuildingSceneTilePile buildingSceneTilePile = null;
    private BuildingSceneOthersShip buildingSceneOthersShip = null;
    private List<Card> stackDeck1;
    private List<Card> stackDeck2;
    private List<Card> stackDeck3;
    private Deck tmpDeck;
    private it.polimi.ingsw.galaxytrucker.Model.Cards.Deck gameDeck;
    private Button d1Button;
    private Pane deck1;
    private Button d2Button;
    private Pane deck2;
    private Button d3Button;
    private Pane deck3;
    private boolean d1InView = false;
    private boolean d2InView = false;
    private boolean d3InView = false;
    private Button pos1Button;
    private Button pos2Button;
    private Button pos3Button;
    private Button pos4Button;
    private boolean pos1Taken = false;
    private boolean pos2Taken = false;
    private boolean pos3Taken = false;
    private boolean pos4Taken = false;
    private Button orangeAlienButton;
    private Button purpleAlienButton;
    private Button removeTileButton;

    public BuildingSceneBoard(Game game, String nickname, SceneManager sceneManager) {
        super(game, sceneManager);
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;
        this.gameDeck = game.getDeck();

        tmpDeck = new Deck(gameDeck);
        stackDeck1 = tmpDeck.getDeck1();
        stackDeck2 = tmpDeck.getDeck2();
        stackDeck3 = tmpDeck.getDeck3();

        Player user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        this.background = new Background();
        BorderPane layout = new BorderPane();

        // see board
        this.boardGrid = new BoardGrid(userShip.getColor(), this);

        d1Button = new Button("Deck 1");
        d2Button = new Button("Deck 2");
        d3Button = new Button("Deck 3");
        styleButton(d1Button, "#875f87");  // Purple
        styleButton(d2Button, "#875f87");
        styleButton(d3Button, "#875f87");
        d1Button.setOnAction(e -> handleD1Button());
        d2Button.setOnAction(e -> handleD2Button());
        d3Button.setOnAction(e -> handleD3Button());

        boardGrid.setLayoutX(50);
        boardGrid.setLayoutY(100);

        d1Button.setLayoutX(190 + 50);
        d1Button.setLayoutY(415 + 100);
        d1Button.setRotate(26);
        d2Button.setLayoutX(413 + 50);
        d2Button.setLayoutY(460 + 100);
        d3Button.setLayoutX(636 + 50);
        d3Button.setLayoutY(415 + 100);
        d3Button.setRotate(-26);

        // position buttons
        pos1Button = new Button("1");
        pos2Button = new Button("2");
        pos3Button = new Button("3");
        pos4Button = new Button("4");
        transparentCircleButton(pos1Button);
        transparentCircleButton(pos2Button);
        transparentCircleButton(pos3Button);
        transparentCircleButton(pos4Button);
        pos1Button.setOnAction(e -> handlePos1Button());
        pos2Button.setOnAction(e -> handlePos2Button());
        pos3Button.setOnAction(e -> handlePos3Button());
        pos4Button.setOnAction(e -> handlePos4Button());

        pos1Button.setLayoutX(648);
        pos1Button.setLayoutY(189);

        pos2Button.setLayoutX(480);
        pos2Button.setLayoutY(168);

        pos3Button.setLayoutX(370);
        pos3Button.setLayoutY(187);

        pos4Button.setLayoutX(316);
        pos4Button.setLayoutY(207);

        Pane base = new Pane(boardGrid, d1Button, d2Button, d3Button, pos1Button, pos2Button, pos3Button, pos4Button);

        // d1

        deck1 = new Pane();

        Card c0 = stackDeck1.get(0);
        Card c1 = stackDeck1.get(1);
        Card c2 = stackDeck1.get(2);
        c0.setLayoutX(60);
        c0.setLayoutY(80);
        c1.setLayoutX(394);
        c1.setLayoutY(100);
        c2.setLayoutX(728);
        c2.setLayoutY(80);
        deck1.getChildren().addAll(c0, c1, c2);
        deck1.setMouseTransparent(true);
        deck1.setVisible(false);

        // d2

        deck2 = new Pane();

        Card c3 = stackDeck2.get(0);
        Card c4 = stackDeck2.get(1);
        Card c5 = stackDeck2.get(2);
        c3.setLayoutX(60);
        c3.setLayoutY(80);
        c4.setLayoutX(394);
        c4.setLayoutY(100);
        c5.setLayoutX(728);
        c5.setLayoutY(80);
        deck2.getChildren().addAll(c3, c4, c5);
        deck2.setMouseTransparent(true);
        deck2.setVisible(false);

        // d3

        deck3 = new Pane();

        Card c6 = stackDeck3.get(0);
        Card c7 = stackDeck3.get(1);
        Card c8 = stackDeck3.get(2);
        c6.setLayoutX(60);
        c6.setLayoutY(80);
        c7.setLayoutX(394);
        c7.setLayoutY(100);
        c8.setLayoutX(728);
        c8.setLayoutY(80);
        deck3.getChildren().addAll(c6, c7, c8);
        deck3.setMouseTransparent(true);
        deck3.setVisible(false);

        StackPane centerContent = new StackPane(base, deck1, deck2, deck3);

        // --- Bottom Buttons ---
        Button viewUserButton = new Button("View User Ship");
        Button viewTilePileButton = new Button("View Tile Pile");
        Button viewOthersButton = new Button("View Others' Ships");
        Button travelButton = new Button("Done");
        orangeAlienButton = new Button("Orange Alien");
        purpleAlienButton = new Button("Purple Alien");
        removeTileButton = new Button("Remove Tile");
        viewUserButton.getStyleClass().add("bottom-button");
        viewTilePileButton.getStyleClass().add("bottom-button");
        viewOthersButton.getStyleClass().add("bottom-button");
        orangeAlienButton.getStyleClass().add("bottom-button");
        purpleAlienButton.getStyleClass().add("bottom-button");
        removeTileButton.getStyleClass().add("bottom-button");
        travelButton.getStyleClass().add("next-button");
        orangeAlienButton.setVisible(false);
        purpleAlienButton.setVisible(false);
        removeTileButton.setVisible(false);

        viewUserButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "UserShip");
        });
        viewTilePileButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "TilePile");
        });
        viewOthersButton.setOnAction(e -> {
            sceneManager.switchBuilding(this, "OthersShip");
        });
        travelButton.setOnAction(e -> {
            sendMessageToServer("/done");
            //sceneManager.next(this);
        });
        orangeAlienButton.setOnAction(e -> handleOrangeAlien());
        purpleAlienButton.setOnAction(e -> handlePurpleAlien());
        removeTileButton.setOnAction(e -> handleRemoveTile());


        HBox buttonBox = new HBox(100, viewUserButton, viewTilePileButton, viewOthersButton, travelButton);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        HBox alienBox = new HBox(50, orangeAlienButton, purpleAlienButton, removeTileButton);
        alienBox.setPadding(new Insets(5));
        alienBox.setAlignment(Pos.CENTER);

        VBox buttonsBoxWithAlien = new VBox(20, alienBox, buttonBox);
        buttonsBoxWithAlien.setPadding(new Insets(20));
        buttonsBoxWithAlien.setAlignment(Pos.CENTER);

        layout.setCenter(centerContent);
        layout.setBottom(buttonsBoxWithAlien);

        // Now wrap layout with background in a StackPane
        StackPane rootWithBackground = new StackPane();
        rootWithBackground.getChildren().addAll(background, layout);

        scene = new Scene(rootWithBackground, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        sceneManager.setBoardScene(this);
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

    public void setBuildingSceneOthersShip(BuildingSceneOthersShip buildingSceneOthersShip) {
        this.buildingSceneOthersShip = buildingSceneOthersShip;
    }

    public BuildingSceneOthersShip getBuildingSceneOthersShip() {
        return buildingSceneOthersShip;
    }

    public Scene getScene() {
        return scene;
    }

    public void updateGame(Game game) {
        this.game = game;
    }

    // Styling helper method with hover effect
    private void styleButton(Button button, String colorHex) {
        String baseStyle = "-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-size: 21px; -fx-padding: 12 24 12 24; -fx-background-radius: 5;";
        String hoverStyle = "-fx-background-color: derive(" + colorHex + ", 20%); -fx-text-fill: white; -fx-font-size: 21px; -fx-padding: 12 24 12 24; -fx-background-radius: 5;";
        button.setStyle(baseStyle);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setStyle(hoverStyle));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setStyle(baseStyle));
    }

    private void transparentCircleButton(Button button) {
        double radius = 10; // px

        String baseStyle = "-fx-background-color: transparent;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: " + radius + "px;" +
                "-fx-background-radius: " + radius + "px;" +
                "-fx-min-width: " + (radius * 2) + "px;" +
                "-fx-min-height: " + (radius * 2) + "px;" +
                "-fx-max-width: " + (radius * 2) + "px;" +
                "-fx-max-height: " + (radius * 2) + "px;";

        String hoverStyle = "-fx-background-color: rgba(255,255,255,0.2);" +
                "-fx-border-color: white;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: " + radius + "px;" +
                "-fx-background-radius: " + radius + "px;" +
                "-fx-min-width: " + (radius * 2) + "px;" +
                "-fx-min-height: " + (radius * 2) + "px;" +
                "-fx-max-width: " + (radius * 2) + "px;" +
                "-fx-max-height: " + (radius * 2) + "px;";

        button.setStyle(baseStyle);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setStyle(hoverStyle));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setStyle(baseStyle));
    }

    private void handleD1Button() {
        if (!d1InView && !deck1.isVisible() && !deck2.isVisible() && !deck3.isVisible()) { // TODO link to other players' view status
            deck1.setVisible(true);
            d1InView = true;
            styleButton(d1Button, "#cc5555"); // Red to close
        } else if (deck1.isVisible()) {
            deck1.setVisible(false);
            d1InView = false;
            styleButton(d1Button, "#875f87"); // Purple normal
        }
    }

    private void handleD2Button() {
        if (!d2InView && !deck1.isVisible() && !deck2.isVisible() && !deck3.isVisible()) { // TODO link to other players' view status
            deck2.setVisible(true);
            d2InView = true;
            styleButton(d2Button, "#cc5555"); // Red to close
        } else if (deck2.isVisible()) {
            deck2.setVisible(false);
            d2InView = false;
            styleButton(d2Button, "#875f87"); // Purple normal
        }
    }

    private void handleD3Button() {
        if (!d3InView && !deck1.isVisible() && !deck2.isVisible() && !deck3.isVisible()) { // TODO link to other players' view status
            Player player = checkPlayer(nickname);
            deck3.setVisible(true);
            d3InView = true;
            styleButton(d3Button, "#cc5555"); // Red to close
        } else if (deck3.isVisible()) {
            deck3.setVisible(false);
            d3InView = false;
            styleButton(d3Button, "#875f87"); // Purple normal
        }
    }

    private void handlePos1Button() {
        if (!pos1Taken) {
            sendMessageToServer("/setposition 1");
            orangeAlienButton.setVisible(true);
            purpleAlienButton.setVisible(true);
            removeTileButton.setVisible(true);
            Player player = checkPlayer(nickname);
            Color color = player.getShip().getColor();
            String colorStyle = "";
            if (color == Color.RED) {
                colorStyle = "-fx-background-color: red;";
            } else if (color == Color.BLUE) {
                colorStyle = "-fx-background-color: blue;";
            } else if (color == Color.GREEN) {
                colorStyle = "-fx-background-color: green;";
            } else if (color == Color.YELLOW) {
                colorStyle = "-fx-background-color: yellow;";
            }
            pos1Button.setStyle(colorStyle);
            pos1Taken = true;
        }
    }

    private void handlePos2Button() {
        if (!pos2Taken) {
            sendMessageToServer("/setposition 2");
            orangeAlienButton.setVisible(true);
            purpleAlienButton.setVisible(true);
            removeTileButton.setVisible(true);
            Player player = checkPlayer(nickname);
            Color color = player.getShip().getColor();
            String colorStyle = "";
            if (color == Color.RED) {
                colorStyle = "-fx-background-color: red;";
            } else if (color == Color.BLUE) {
                colorStyle = "-fx-background-color: blue;";
            } else if (color == Color.GREEN) {
                colorStyle = "-fx-background-color: green;";
            } else if (color == Color.YELLOW) {
                colorStyle = "-fx-background-color: yellow;";
            }
            pos2Button.setStyle(colorStyle);
            // set user in pos 2
            pos2Taken = true;
        }
    }

    private void handlePos3Button() {
        if (!pos3Taken) {
            sendMessageToServer("/setposition 3");
            orangeAlienButton.setVisible(true);
            purpleAlienButton.setVisible(true);
            removeTileButton.setVisible(true);
            Player player = checkPlayer(nickname);
            Color color = player.getShip().getColor();
            String colorStyle = "";
            if (color == Color.RED) {
                colorStyle = "-fx-background-color: red;";
            } else if (color == Color.BLUE) {
                colorStyle = "-fx-background-color: blue;";
            } else if (color == Color.GREEN) {
                colorStyle = "-fx-background-color: green;";
            } else if (color == Color.YELLOW) {
                colorStyle = "-fx-background-color: yellow;";
            }
            pos3Button.setStyle(colorStyle);
            // set user in pos 3
            pos3Taken = true;
        }
    }

    private void handlePos4Button() {
        if (!pos4Taken) {
            sendMessageToServer("/setposition 4");
            orangeAlienButton.setVisible(true);
            purpleAlienButton.setVisible(true);
            removeTileButton.setVisible(true);
            Player player = checkPlayer(nickname);
            Color color = player.getShip().getColor();
            String colorStyle = "";
            if (color == Color.RED) {
                colorStyle = "-fx-background-color: red;";
            } else if (color == Color.BLUE) {
                colorStyle = "-fx-background-color: blue;";
            } else if (color == Color.GREEN) {
                colorStyle = "-fx-background-color: green;";
            } else if (color == Color.YELLOW) {
                colorStyle = "-fx-background-color: yellow;";
            }
            pos4Button.setStyle(colorStyle);
            // set user in pos 4
            pos4Taken = true;
        }
    }

    private void handleOrangeAlien() {
        //TODO: finish
        if (buildingSceneUserShip != null){
            buildingSceneUserShip.enableAlienSelection("/placeorangealien", this.game);
            sceneManager.switchBuilding(this, "UserShip");
        }
    }

    private void handlePurpleAlien() {
        //TODO: finish
        if (buildingSceneUserShip != null){
            buildingSceneUserShip.enableAlienSelection("/placepurplealien", this.game);
            sceneManager.switchBuilding(this, "UserShip");
        }
    }

    private void handleRemoveTile(){
        if (buildingSceneUserShip != null) {
            buildingSceneUserShip.removeTile("/removetile", this.game);
            sceneManager.switchBuilding(this, "UserShip");
        }
    }
}
