package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class FinalScene extends MyScene{
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 760;
    private SceneManager sceneManager;
    private Button claim;

    public FinalScene(Game game, String nickname, SceneManager sceneManager) {
        super(game,sceneManager);
        this.game = game;
        this.nickname = nickname;
        this.sceneManager = sceneManager;
        this.background = new Background();
        this.root = new StackPane();

        claim = new Button("Claim");
        styleButton(claim, "#875f87");
        claim.setOnMouseClicked(e -> {
            sendMessageToServer("/claimreward", this.nickname);
        });

        root.getChildren().addAll(background, claim);
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        sceneManager.setFinalScene(this);
    }

    // Styling helper method with hover effect
    private void styleButton(Button button, String colorHex) {
        String baseStyle = "-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-size: 21px; -fx-padding: 12 24 12 24; -fx-background-radius: 5;";
        String hoverStyle = "-fx-background-color: derive(" + colorHex + ", 20%); -fx-text-fill: white; -fx-font-size: 21px; -fx-padding: 12 24 12 24; -fx-background-radius: 5;";
        button.setStyle(baseStyle);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setStyle(hoverStyle));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setStyle(baseStyle));
    }

    public Scene getScene() {
        return scene;
    }

    public void updateGame(Game game) {
        this.game = game;
    }
}
