package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class WaitingScene extends MyScene{
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 750;
    private boolean isFirstPlayer;
    private Button connectButton;
    private Button setPlayersButton;
    private Label nicknameFeedbackLabel;
    private Label playersFeedbackLabel;

    public WaitingScene(Game game, String nickname, boolean isFirstPlayer) {
        this.game = game;
        this.nickname = nickname;
        this.isFirstPlayer = isFirstPlayer;
        this.background = new Background();
        this.root = new StackPane();
        root.getChildren().add(background);

        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        connectButton = new Button("Connect");
        connectButton.setOnAction(e -> handleConnect());

        setPlayersButton = new Button("Set Players");
        setPlayersButton.setOnAction(e -> handleSetPlayers());
        setPlayersButton.setDisable(!isFirstPlayer);
        setPlayersButton.setVisible(isFirstPlayer);

        nicknameFeedbackLabel = new Label();
        nicknameFeedbackLabel.setStyle("-fx-text-fill: white;");
        playersFeedbackLabel = new Label();
        playersFeedbackLabel.setStyle("-fx-text-fill: white;");

        menuBox.getChildren().addAll(connectButton, nicknameFeedbackLabel,
                setPlayersButton, playersFeedbackLabel);

        root.getChildren().add(menuBox);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
    }

    private void handleConnect() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Connect");
        dialog.setHeaderText("Enter your nickname:");
        dialog.setContentText("Nickname:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            this.nickname = name;
            connectButton.setDisable(true);  // Disable after success
            nicknameFeedbackLabel.setText("Nickname set: " + nickname);
        });
    }

    private void handleSetPlayers() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Set Players");
        dialog.setHeaderText("Set number of players:");
        dialog.setContentText("Players:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(numberStr -> {
            try {
                int number = Integer.parseInt(numberStr);
                setPlayersButton.setDisable(true);  // Disable after success
                playersFeedbackLabel.setText("Number of players: " + number);
            } catch (NumberFormatException e) {
                playersFeedbackLabel.setText("❌ Invalid number.");
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
}
