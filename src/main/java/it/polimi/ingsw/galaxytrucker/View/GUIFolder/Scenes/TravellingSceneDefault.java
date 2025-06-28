package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.*;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards.CardInteractive;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TravellingSceneDefault extends MyScene {
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
    private Pane shipPane;
    private BoardGrid boardGrid;
    private TravellingSceneOthersShip travellingSceneOthersShip;
    private Circle day23Circle;
    private Circle day22Circle;
    private Circle day21Circle;
    private Circle day20Circle;
    private Circle day19Circle;
    private Circle day18Circle;
    private Circle day17Circle;
    private Circle day16Circle;
    private Circle day15Circle;
    private Circle day14Circle;
    private Circle day13Circle;
    private Circle day12Circle;
    private Circle day11Circle;
    private Circle day10Circle;
    private Circle day9Circle;
    private Circle day8Circle;
    private Circle day7Circle;
    private Circle day6Pos1Circle;
    private Circle day5Circle;
    private Circle day4Circle;
    private Circle day3Pos2Circle;
    private Circle day2Circle;
    private Circle day1Pos3Circle;
    private Circle day0Pos4Circle; // 24 buttons: [0; 23]
    private List <Circle> circles = new ArrayList<>();
    private int bluePlayerDays = 0;
    private int greenPlayerDays = 0;
    private int yellowPlayerDays = 0;
    private int redPlayerDays = 0;
    private it.polimi.ingsw.galaxytrucker.Model.Cards.Card currentCard;
    private Card guiCard;
    private CardInteractive cardInteractive;
    private int currentCardNum = 0;
    private Button drawCard;
    private HBox buttonBox;
    private Button finish;

    public TravellingSceneDefault(Game game, String nickname, SceneManager sceneManager) {
        super(game, sceneManager);
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;
        this.gameDeck = game.getDeck();

        deck = new Deck(gameDeck);

        user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        this.background = new Background();

        // TODO everything here:
        /*
        __________________________
        | plancia     |  card    |
        | (stack)     |  (stack) |
        |_____________|   +      |
        | ship        |  buttons |
        |             |          |
        __________________________
        __________________________ <- button to switch to view of other ships here
         */

        //--------------------

        boardPane = new StackPane();

        this.boardGrid = new BoardGrid(userShip.getColor(), null); // TODO understand if okay
        boardGrid.setLayoutX(-140 - 80);
        boardGrid.setLayoutY(-50 - 30);

        makeCircles();

        Pane base = new Pane(boardGrid, day0Pos4Circle, day1Pos3Circle, day2Circle,
                day3Pos2Circle, day4Circle, day5Circle, day6Pos1Circle, day7Circle,
                day8Circle, day9Circle, day10Circle, day11Circle, day12Circle,
                day13Circle, day14Circle, day15Circle, day16Circle, day17Circle,
                day18Circle, day19Circle, day20Circle, day21Circle, day22Circle, day23Circle);

        base.setScaleX(0.5);
        base.setScaleY(0.5);
        boardPane.setPrefSize(500, 350);
        boardPane.getChildren().add(base);

        //--------------------

        cardPane = new StackPane();
        cardPane.setPrefSize(350, 500);
        GameState gameState = game.getGameState();
        currentCard = ((TravellingState)gameState).getCurrentCard();
        guiCard = new Card(currentCard);
        cardInteractive = new CardInteractive(guiCard, this, nickname);

        drawCard = new Button("Draw"); // TODO remove, this is for testing
        drawCard.getStyleClass().add("action-button");
        drawCard.setOnAction(event -> {
            drawCard();
        });

        cardPane.setPrefSize(350, 500);

        cardPane.getChildren().addAll(cardInteractive, drawCard);

        //--------------------

        shipPane = new Pane(); // TODO do

        //--------------------

        // LEFT: BoardPane (stacked) + LogPane
        VBox leftColumn = new VBox(10, boardPane, shipPane);

        // RIGHT: CardPane (spans full height)
        VBox rightColumn = new VBox(cardPane);
        cardPane.prefWidthProperty().bind(rightColumn.widthProperty());
        cardPane.prefHeightProperty().bind(rightColumn.heightProperty());
        rightColumn.setAlignment(Pos.CENTER);
        rightColumn.setPadding(new Insets(0, 20, 0, 20)); // optional spacing

        // TOP/MID: Left and Right side-by-side
        HBox centerContent = new HBox(20, leftColumn, rightColumn);
        centerContent.setPadding(new Insets(20));

        // For debugging layout: temporary borders TODO remove when done
        boardPane.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-border-style: dashed;");
        cardPane.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-border-style: dashed;");
        shipPane.setStyle("-fx-border-color: orange; -fx-border-width: 2; -fx-border-style: dashed;");
        leftColumn.setStyle("-fx-border-color: purple; -fx-border-width: 2; -fx-border-style: dashed;");
        rightColumn.setStyle("-fx-border-color: darkred; -fx-border-width: 2; -fx-border-style: dashed;");
        centerContent.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid;");

        Button othersShip = new Button("Others' Ship");
        finish = new Button("Finish");
        othersShip.getStyleClass().add("bottom-button");
        finish.getStyleClass().add("next-button");

        othersShip.setOnAction(e -> {
            sceneManager.switchTravelling(this);
        });

        finish.setOnAction(e -> {
            // TODO introduce checks
            sceneManager.next(this);
        });

        buttonBox = new HBox(300, othersShip);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane layout = new BorderPane();
        layout.setCenter(centerContent);
        layout.setBottom(buttonBox);

        StackPane rootWithBackground = new StackPane();
        rootWithBackground.getChildren().addAll(background, layout);

        scene = new Scene(rootWithBackground, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        sceneManager.setTravellingSceneDefault(this);
    }

    private void makeCircles() {
        // circles (immense pain)
        day0Pos4Circle = new Circle(10);
        day1Pos3Circle = new Circle(10);
        day2Circle = new Circle(10);
        day3Pos2Circle = new Circle(10);
        day4Circle = new Circle(10);
        day5Circle = new Circle(10);
        day6Pos1Circle = new Circle(10);
        day7Circle = new Circle(10);
        day8Circle = new Circle(10);
        day9Circle = new Circle(10);
        day10Circle = new Circle(10);
        day11Circle = new Circle(10);
        day12Circle = new Circle(10);
        day13Circle = new Circle(10);
        day14Circle = new Circle(10);
        day15Circle = new Circle(10);
        day16Circle = new Circle(10);
        day17Circle = new Circle(10);
        day18Circle = new Circle(10);
        day19Circle = new Circle(10);
        day20Circle = new Circle(10);
        day21Circle = new Circle(10);
        day22Circle = new Circle(10);
        day23Circle = new Circle(10);

        circles.add(day0Pos4Circle);
        circles.add(day1Pos3Circle);
        circles.add(day2Circle);
        circles.add(day3Pos2Circle);
        circles.add(day4Circle);
        circles.add(day5Circle);
        circles.add(day6Pos1Circle);
        circles.add(day7Circle);
        circles.add(day8Circle);
        circles.add(day9Circle);
        circles.add(day10Circle);
        circles.add(day11Circle);
        circles.add(day12Circle);
        circles.add(day13Circle);
        circles.add(day14Circle);
        circles.add(day15Circle);
        circles.add(day16Circle);
        circles.add(day17Circle);
        circles.add(day18Circle);
        circles.add(day19Circle);
        circles.add(day20Circle);
        circles.add(day21Circle);
        circles.add(day22Circle);
        circles.add(day23Circle);

        for (Circle circle : circles) {
            styleCircle(circle, null);
        }

        day0Pos4Circle.setLayoutX(326 - 190 - 80);
        day0Pos4Circle.setLayoutY(217 - 150 - 30);
        day1Pos3Circle.setLayoutX(380 - 190 - 80);
        day1Pos3Circle.setLayoutY(197 - 150 - 30);
        day2Circle.setLayoutX(435 - 190 - 80);
        day2Circle.setLayoutY(185 - 150 - 30);
        day3Pos2Circle.setLayoutX(490 - 190 - 80);
        day3Pos2Circle.setLayoutY(178 - 150 - 30);
        day4Circle.setLayoutX(547 - 190 - 80);
        day4Circle.setLayoutY(178 - 150 - 30);
        day5Circle.setLayoutX(603 - 190 - 80);
        day5Circle.setLayoutY(185 - 150 - 30);
        day6Pos1Circle.setLayoutX(659 - 190 - 80);
        day6Pos1Circle.setLayoutY(198 - 150 - 30);
        day7Circle.setLayoutX(711 - 190 - 80);
        day7Circle.setLayoutY(221 - 150 - 30);
        day8Circle.setLayoutX(759 - 190 - 80);
        day8Circle.setLayoutY(257 - 150 - 30);
        day9Circle.setLayoutX(794 - 190 - 80);
        day9Circle.setLayoutY(310 - 150 - 30);

        day10Circle.setLayoutX(790 - 190 - 80);
        day10Circle.setLayoutY(374 - 150 - 30);
        day11Circle.setLayoutX(752 - 190 - 80);
        day11Circle.setLayoutY(423 - 150 - 30);
        day12Circle.setLayoutX(702 - 190 - 80);
        day12Circle.setLayoutY(454 - 150 - 30);
        day13Circle.setLayoutX(648 - 190 - 80);
        day13Circle.setLayoutY(476 - 150 - 30);
        day14Circle.setLayoutX(593 - 190 - 80);
        day14Circle.setLayoutY(488 - 150 - 30);
        day15Circle.setLayoutX(538 - 190 - 80);
        day15Circle.setLayoutY(494 - 150 - 30);
        day16Circle.setLayoutX(481 - 190 - 80);
        day16Circle.setLayoutY(493 - 150 - 30);
        day17Circle.setLayoutX(423 - 190 - 80);
        day17Circle.setLayoutY(487 - 150 - 30);
        day18Circle.setLayoutX(372 - 190 - 80);
        day18Circle.setLayoutY(472 - 150 - 30);
        day19Circle.setLayoutX(317 - 190 - 80);
        day19Circle.setLayoutY(451 - 150 - 30);

        day20Circle.setLayoutX(268 - 190 - 80);
        day20Circle.setLayoutY(417 - 150 - 30);
        day21Circle.setLayoutX(234 - 190 - 80);
        day21Circle.setLayoutY(362 - 150 - 30);
        day22Circle.setLayoutX(239 - 190 - 80);
        day22Circle.setLayoutY(298 - 150 - 30);
        day23Circle.setLayoutX(278 - 190 - 80);
        day23Circle.setLayoutY(249 - 150 - 30);
    }

    private void styleCircle(Circle circle, String hexColor) {
        double radius = 10;
        circle.setRadius(radius);

        if (hexColor != null) {
            circle.setFill(Paint.valueOf("#" + hexColor));
        } else {
            circle.setFill(javafx.scene.paint.Color.TRANSPARENT);
        }

        circle.setStroke(javafx.scene.paint.Color.WHITE);
        circle.setStrokeWidth(1);
    }

    public Scene getScene() {
        return scene;
    }

    public void drawCard() {
        cardPane.getChildren().remove(cardInteractive);
        currentCardNum++;
        if (currentCardNum == 11) {
            cardPane.getChildren().remove(drawCard);
            buttonBox.getChildren().add(finish);
        }
        guiCard = deck.getGameDeck().get(currentCardNum);
        currentCard = guiCard.getLogicCard();
        cardInteractive = new CardInteractive(guiCard, this, nickname);
        cardPane.getChildren().addFirst(cardInteractive);
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

    public void setTravellingSceneOthersShip(TravellingSceneOthersShip travellingSceneOthersShip) {
        this.travellingSceneOthersShip = travellingSceneOthersShip;
    }

    private void fillPos(Circle circle, boolean fill, Color color) {
        if (fill) {
            String hexColor = switch (color) {
                case RED -> "FF0000";
                case YELLOW -> "FFFF00";
                case GREEN -> "00FF00";
                case BLUE -> "0087FF";
                default -> null; // fallback transparent
            };
            styleCircle(circle, hexColor);
        } else {
            styleCircle(circle, null);
        }
    }

    public void setDay(int day, Player player) {
        Color color = player.getShip().getColor();
        switch (color) {
            case BLUE:
                // empty old one
                fillPos(circles.get(bluePlayerDays), false, color);
                bluePlayerDays = day;
                // fill new one
                fillPos(circles.get(bluePlayerDays), true, color);
                break;
            case GREEN:
                fillPos(circles.get(greenPlayerDays), false, color);
                greenPlayerDays = day;
                fillPos(circles.get(greenPlayerDays), true, color);
                break;
            case YELLOW:
                fillPos(circles.get(yellowPlayerDays), false, color);
                yellowPlayerDays = day;
                fillPos(circles.get(yellowPlayerDays), true, color);
                break;
            case RED:
                fillPos(circles.get(redPlayerDays), false, color);
                redPlayerDays = day;
                fillPos(circles.get(redPlayerDays), true, color);
                break;
            default:
                fillPos(circles.get(day), false, color);
                break;
        }
    }
    public SceneManager getSceneManager() {
        return this.sceneManager;
    }
}