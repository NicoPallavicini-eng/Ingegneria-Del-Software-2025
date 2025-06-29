package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUI;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

/**
 * GUI scene representing the waiting room before the game starts.
 * Allows users to connect, set their nickname, set the number of players, and proceed to the next phase.
 */
public class WaitingScene extends MyScene{

    private boolean isFirstPlayer;
    private Button connectButton;
    private Button setPlayersButton;
    private Label nicknameFeedbackLabel;
    private Label playersFeedbackLabel;
    private boolean nicknameSet = false;
    private boolean playersSet = false;
    private Button nextButton;
    private boolean ownerProceeded = false;
    private int playersNum;
    /**
     * Constructs the waiting scene with the given game and scene manager.
     *
     * @param game the game model
     * @param sceneManager the scene manager for navigation
     */
    public WaitingScene(Game game, SceneManager sceneManager) {
       super(game, sceneManager);

        nextButton = new Button("Next");
        styleButton(nextButton, "#cc5555");  // Red
        nextButton.setDisable(false);
        // Disabled until conditions met
        nextButton.setOnAction(e -> {
            sceneManager.next(this);
        });

        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        connectButton = new Button("Connect");
        styleButton(connectButton, "#3399cc");  // Blue
        connectButton.setOnAction(e -> handleConnect());

        setPlayersButton = new Button("Set Players");
        styleButton(setPlayersButton, "#875f87");  // Purple
        setPlayersButton.setOnAction(e -> handleSetPlayers());
        setPlayersButton.setDisable(true);

        nicknameFeedbackLabel = new Label();
        nicknameFeedbackLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: 500;");
        playersFeedbackLabel = new Label();
        playersFeedbackLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: 500;");

        menuBox.getChildren().addAll(connectButton, nicknameFeedbackLabel,
                setPlayersButton, playersFeedbackLabel, nextButton);

        root.getChildren().add(menuBox);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // default sizing for now
    }

    // Styling helper method with hover effect
    private void styleButton(Button button, String colorHex) {
        String baseStyle = "-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-size: 21px; -fx-padding: 12 24 12 24; -fx-background-radius: 5;";
        String hoverStyle = "-fx-background-color: derive(" + colorHex + ", 20%); -fx-text-fill: white; -fx-font-size: 21px; -fx-padding: 12 24 12 24; -fx-background-radius: 5;";
        button.setStyle(baseStyle);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setStyle(hoverStyle));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setStyle(baseStyle));
    }

    private void styleDialog(TextInputDialog dialog) {
        dialog.getDialogPane().getStylesheets().clear();
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/dialog-theme.css").toExternalForm());
    }

    private void handleConnect() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Connect");
        dialog.setHeaderText("Enter your nickname:");
        dialog.setContentText("Nickname:");

        styleDialog(dialog);
        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().clear();
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/misc/window_simple_icon.png")));

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            try {
                sendMessageToServer("/connect " + name, null);
                this.nickname = name;
                connectButton.setDisable(true);  // Disable after success
                nicknameFeedbackLabel.setText("Nickname set: " + nickname);
                sceneManager.setNickname(nickname);
                connectButton.setDisable(true);
            } catch (IllegalGUIEventException e){
                System.out.println(e.getMessage());
            }

            synchronized(game.getListOfPlayers()) {
                if (game.getListOfPlayers().size() == 1) { // TODO understand why "-1" -> prob sync problem
                    setPlayersButton.setDisable(false);
                    isFirstPlayer = true;
                }
            }
            nicknameSet = true;
            nextButton.setDisable(false);
        });
    }

    private void handleSetPlayers() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Set Players");
        dialog.setHeaderText("Set number of players:");
        dialog.setContentText("Players:");

        styleDialog(dialog);

        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().clear();
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/misc/window_simple_icon.png")));

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(numberStr -> {
            try {
                int number = Integer.parseInt(numberStr);
                try {
                    sendMessageToServer("/setnumberofplayers " + number, this.nickname);
                    this.playersNum = number;
                    setPlayersButton.setDisable(true);  // Disable after success
                    playersFeedbackLabel.setText("Number of players: " + number);
                    playersSet = true;
                } catch (IllegalGUIEventException e) {
                    System.out.println(e.getMessage());
                    setPlayersButton.setDisable(false);
                }
                nextButton.setDisable(false);

            } catch (NumberFormatException e) {
                playersFeedbackLabel.setText("Invalid number, try again");
            }
        });
    }
    /**
     * Returns the JavaFX scene for this waiting room.
     *
     * @return the JavaFX scene
     */

    public Scene getScene() {
        return scene;
    }
    /**
     * Updates the game model reference.
     *
     * @param game the new game model
     */
    public void updateGame(Game game) {
        this.game = game;
    }
}
