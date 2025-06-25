package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.BoardGrid;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Deck;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Pane logPane;
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

    public TravellingSceneDefault(Game game, String nickname, SceneManager sceneManager) {
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;
        this.gameDeck = game.getDeck();

        deck = new Deck(gameDeck);

        user = checkPlayer(nickname);
        Ship userShip = user.getShip();

        this.background = new Background();
        BorderPane layout = new BorderPane();

        // TODO everything here:
        /*
        __________________________
        | plancia     |  card    |
        | (stack)     |  (stack) |
        |_____________|   +      |
        | log         |  buttons |
        | (pane?)     |          |
        __________________________
        __________________________ <- button to switch to view of other ships here
         */

        boardPane = new StackPane();

        /////////
        this.boardGrid = new BoardGrid(userShip.getColor(), null); // TODO understand if okay

        boardGrid.setLayoutX(50);
        boardGrid.setLayoutY(100);

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

        day0Pos4Circle.setLayoutX(316);
        day0Pos4Circle.setLayoutY(207);
        day1Pos3Circle.setLayoutX(370);
        day1Pos3Circle.setLayoutY(187);
        day2Circle.setLayoutX(430);
        day2Circle.setLayoutY(175);
        day3Pos2Circle.setLayoutX(480);
        day3Pos2Circle.setLayoutY(168);
        day4Circle.setLayoutX(544);
        day4Circle.setLayoutY(167);
        day5Circle.setLayoutX(599);
        day5Circle.setLayoutY(177);
        day6Pos1Circle.setLayoutX(648);
        day6Pos1Circle.setLayoutY(189);
        day7Circle.setLayoutX(706);
        day7Circle.setLayoutY(215);
        day8Circle.setLayoutX(754);
        day8Circle.setLayoutY(248);
        day9Circle.setLayoutX(790);
        day9Circle.setLayoutY(298);

        day10Circle.setLayoutX(786);
        day10Circle.setLayoutY(364);
        day11Circle.setLayoutX(747);
        day11Circle.setLayoutY(419);
        day12Circle.setLayoutX(699);
        day12Circle.setLayoutY(449);
        day13Circle.setLayoutX(645);
        day13Circle.setLayoutY(468);
        day14Circle.setLayoutX(590);
        day14Circle.setLayoutY(485);
        day15Circle.setLayoutX(538);
        day15Circle.setLayoutY(489);
        day16Circle.setLayoutX(479);
        day16Circle.setLayoutY(489);
        day17Circle.setLayoutX(422);
        day17Circle.setLayoutY(480);
        day18Circle.setLayoutX(365);
        day18Circle.setLayoutY(464);
        day19Circle.setLayoutX(308);
        day19Circle.setLayoutY(446);

        day20Circle.setLayoutX(262);
        day20Circle.setLayoutY(407);
        day21Circle.setLayoutX(227);
        day21Circle.setLayoutY(352);
        day22Circle.setLayoutX(230);
        day22Circle.setLayoutY(288);
        day23Circle.setLayoutX(270);
        day23Circle.setLayoutY(239);

        Pane base = new Pane(boardGrid, day0Pos4Circle, day1Pos3Circle, day2Circle,
                day3Pos2Circle, day4Circle, day5Circle, day6Pos1Circle, day7Circle,
                day8Circle, day9Circle, day10Circle, day11Circle, day12Circle,
                day13Circle, day14Circle, day15Circle, day16Circle, day17Circle,
                day18Circle, day19Circle, day20Circle, day21Circle, day22Circle, day23Circle);

        boardPane.getChildren().add(base);
        // boardPane.resize(100, 200); TODO find a way to do this
        /////////

        cardPane = new StackPane();

        logPane = new Pane();


        StackPane centerContent = new StackPane(boardPane, cardPane, logPane);

        Button othersShip = new Button("Others' Ship");
        Button finish = new Button("Finish");
        othersShip.getStyleClass().add("bottom-button");
        finish.getStyleClass().add("next-button");

        othersShip.setOnAction(e -> {
            sceneManager.switchTravelling(this);
        });

        finish.setOnAction(e -> {
            // TODO introduce checks
            sceneManager.next(this);
        });

        HBox buttonBox = new HBox(300, othersShip, finish);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        layout.setCenter(centerContent);
        layout.setBottom(buttonBox);

        StackPane rootWithBackground = new StackPane();
        rootWithBackground.getChildren().addAll(background, layout);

        scene = new Scene(rootWithBackground, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        sceneManager.setTravellingSceneDefault(this);
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

    public void setCardImage(Image image) {
        // TODO Optionally expose this method to update the right-side card
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
            String hexColor;
            switch (color) {
                case RED:
                    hexColor = "FF0000";
                    break;
                case YELLOW:
                    hexColor = "FFFF00";
                    break;
                case GREEN:
                    hexColor = "00FF00";
                    break;
                case BLUE:
                    hexColor = "0087FF";
                    break;
                default:
                    hexColor = null; // fallback transparent
                    break;
            }
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
}