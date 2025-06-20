package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class TravellingScene extends MyScene {
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 760;
    private SceneManager sceneManager;

    public TravellingScene(Game game, String nickname, SceneManager sceneManager) {
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;
        this.background = new Background();
        this.root = new StackPane();
        root.getChildren().add(background);

        // Create HBox for board (left) and card (right)
        HBox mainLayout = new HBox();
        mainLayout.setSpacing(50);
        mainLayout.setAlignment(Pos.CENTER);

        // Left side: board with clickable slots
        Pane boardPane = new Pane();
        ImageView boardImage = new ImageView(new Image("/Images/cardboard/cardboard-5.png"));
        boardImage.setFitWidth(500);
        boardImage.setPreserveRatio(true);
        boardPane.getChildren().add(boardImage);

//        // Create 28 buttons and manually position them
//        for (int i = 0; i < 28; i++) {
//            Button slot = new Button();
//            slot.setStyle("-fx-background-color: transparent; -fx-border-color: yellow;");
//            slot.setPrefSize(30, 30);
//            slot.setLayoutX(100 + (i % 7) * 40); // 🔧 adjust manually to match your board
//            slot.setLayoutY(100 + (i / 7) * 40);
//            int finalI = i;
//            slot.setOnAction(e -> {
//                System.out.println("Clicked slot " + finalI);
//                // trigger game logic here
//            });
//            boardPane.getChildren().add(slot);
//        }
//
//        // Right side: card display
//        VBox cardBox = new VBox();
//        cardBox.setAlignment(Pos.CENTER);
//        cardBox.setSpacing(10);
//        ImageView cardImage = new ImageView(); // initially empty
//        cardImage.setFitWidth(300);
//        cardImage.setPreserveRatio(true);
//        cardBox.getChildren().add(cardImage);

        // Add board and card to layout
        mainLayout.getChildren().addAll(boardPane);
        root.getChildren().add(mainLayout);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
    }

    public Scene getScene() {
        return scene;
    }

    public void setCardImage(Image image) {
        // TODO Optionally expose this method to update the right-side card
    }

    public void updateGame(Game game) {
        this.game = game;
    }
}