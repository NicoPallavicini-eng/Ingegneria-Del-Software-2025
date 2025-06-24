package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

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
    private Button day23Button;
    private Button day22Button;
    private Button day21Button;
    private Button day20Button;
    private Button day19Button;
    private Button day18Button;
    private Button day17Button;
    private Button day16Button;
    private Button day15Button;
    private Button day14Button;
    private Button day13Button;
    private Button day12Button;
    private Button day11Button;
    private Button day10Button;
    private Button day9Button;
    private Button day8Button;
    private Button day7Button;
    private Button day6Pos1Button;
    private Button day5Button;
    private Button day4Button;
    private Button day3Pos2Button;
    private Button day2Button;
    private Button day1Pos3Button;
    private Button day0Pos4Button; // 24 buttons: [0; 23]

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

        List <Button> buttons = new ArrayList<>();

        // buttons (immense pain)
        day0Pos4Button = new Button("4");
        day1Pos3Button = new Button("3");
        day2Button = new Button("");
        day3Pos2Button = new Button("2");
        day4Button = new Button("");
        day5Button = new Button("");
        day6Pos1Button = new Button("1");
        day7Button = new Button("");
        day8Button = new Button("");
        day9Button = new Button("");
        day10Button = new Button("");
        day11Button = new Button("");
        day12Button = new Button("");
        day13Button = new Button("");
        day14Button = new Button("");
        day15Button = new Button("");
        day16Button = new Button("");
        day17Button = new Button("");
        day18Button = new Button("");
        day19Button = new Button("");
        day20Button = new Button("");
        day21Button = new Button("");
        day22Button = new Button("");
        day23Button = new Button("");

        buttons.add(day0Pos4Button);
        buttons.add(day1Pos3Button);
        buttons.add(day2Button);
        buttons.add(day3Pos2Button);
        buttons.add(day4Button);
        buttons.add(day5Button);
        buttons.add(day6Pos1Button);
        buttons.add(day7Button);
        buttons.add(day8Button);
        buttons.add(day9Button);
        buttons.add(day10Button);
        buttons.add(day11Button);
        buttons.add(day12Button);
        buttons.add(day13Button);
        buttons.add(day14Button);
        buttons.add(day15Button);
        buttons.add(day16Button);
        buttons.add(day17Button);
        buttons.add(day18Button);
        buttons.add(day19Button);
        buttons.add(day20Button);
        buttons.add(day21Button);
        buttons.add(day22Button);
        buttons.add(day23Button);

        for (Button button : buttons) {
            styleCircularButton(button, null);
            // TODO setup color logic around the handlePos
            button.setOnAction(e -> handlePos(button, "875f87"));
        }

        day0Pos4Button.setLayoutX(316);
        day0Pos4Button.setLayoutY(207);
        day1Pos3Button.setLayoutX(370);
        day1Pos3Button.setLayoutY(187);
        day2Button.setLayoutX(430);
        day2Button.setLayoutY(175);
        day3Pos2Button.setLayoutX(480);
        day3Pos2Button.setLayoutY(168);
        day4Button.setLayoutX(544);
        day4Button.setLayoutY(167);
        day5Button.setLayoutX(599);
        day5Button.setLayoutY(177);
        day6Pos1Button.setLayoutX(648);
        day6Pos1Button.setLayoutY(189);
        day7Button.setLayoutX(706);
        day7Button.setLayoutY(215);
        day8Button.setLayoutX(754);
        day8Button.setLayoutY(248);
        day9Button.setLayoutX(790);
        day9Button.setLayoutY(298);

        day10Button.setLayoutX(786);
        day10Button.setLayoutY(364);
        day11Button.setLayoutX(747);
        day11Button.setLayoutY(419);
        day12Button.setLayoutX(699);
        day12Button.setLayoutY(449);
        day13Button.setLayoutX(645);
        day13Button.setLayoutY(468);
        day14Button.setLayoutX(590);
        day14Button.setLayoutY(485);
        day15Button.setLayoutX(538);
        day15Button.setLayoutY(489);
        day16Button.setLayoutX(479);
        day16Button.setLayoutY(489);
        day17Button.setLayoutX(422);
        day17Button.setLayoutY(480);
        day18Button.setLayoutX(365);
        day18Button.setLayoutY(464);
        day19Button.setLayoutX(308);
        day19Button.setLayoutY(446);

        day20Button.setLayoutX(262);
        day20Button.setLayoutY(407);
        day21Button.setLayoutX(227);
        day21Button.setLayoutY(352);
        day22Button.setLayoutX(230);
        day22Button.setLayoutY(288);
        day23Button.setLayoutX(270);
        day23Button.setLayoutY(239);

        Pane base = new Pane(boardGrid, day0Pos4Button, day1Pos3Button, day2Button,
                day3Pos2Button, day4Button, day5Button, day6Pos1Button, day7Button,
                day8Button, day9Button, day10Button, day11Button, day12Button,
                day13Button, day14Button, day15Button, day16Button, day17Button,
                day18Button, day19Button, day20Button, day21Button, day22Button, day23Button);

        boardPane.getChildren().add(base);
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

    private void styleCircularButton(Button button, String hexColor) {
        double radius = 10;

        String baseColor = (hexColor != null) ? hexColor : "transparent";
        String hoverColor = (hexColor != null)
                ? "derive(" + hexColor + ", 20%)"
                : "rgba(255,255,255,0.2)";

        String baseStyle = "-fx-background-color: " + baseColor + ";" +
                "-fx-border-color: white;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: " + radius + "px;" +
                "-fx-background-radius: " + radius + "px;" +
                "-fx-min-width: " + (radius * 2) + "px;" +
                "-fx-min-height: " + (radius * 2) + "px;" +
                "-fx-max-width: " + (radius * 2) + "px;" +
                "-fx-max-height: " + (radius * 2) + "px;";

        String hoverStyle = "-fx-background-color: " + hoverColor + ";" +
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

    private void handlePos(Button button, String hexColor) {
        styleCircularButton(button, hexColor); // TODO setup logic around
    }
}