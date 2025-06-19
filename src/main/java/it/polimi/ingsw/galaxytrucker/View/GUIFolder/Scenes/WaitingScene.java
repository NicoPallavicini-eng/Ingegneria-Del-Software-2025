package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.SceneManager;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Background;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.UserShipGrid;
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

public class WaitingScene extends MyScene{
    private Scene scene;
    private Game game;
    private String nickname;
    private StackPane root;
    private Background background;
    private final int SCENE_WIDTH = 1024;
    private final int SCENE_HEIGHT = 760;
    private boolean isFirstPlayer;
    private Button connectButton;
    private Button setPlayersButton;
    private Label nicknameFeedbackLabel;
    private Label playersFeedbackLabel;
    private boolean nicknameSet = false;
    private boolean playersSet = false;
    private Button nextButton;
    private SceneManager sceneManager;
    private boolean ownerProceeded = false;
    private VirtualClient rmiClient;
    private SocketClient socketClient;
    private int playersNum;

    public WaitingScene(Game game, SceneManager sceneManager) {
        this.game = game;
        this.background = new Background();
        this.root = new StackPane();
        this.sceneManager = sceneManager;
        root.getChildren().add(background);
        this.rmiClient = sceneManager.getRmiClient();
        this.socketClient = sceneManager.getSocketClient();

        nextButton = new Button("Next");
        styleButton(nextButton, "#cc5555");  // Red
        nextButton.setDisable(true);          // Disabled until conditions met
        nextButton.setOnAction(e -> {
            if (isFirstPlayer) {
                if (playersNum == game.getListOfPlayers().size()) {
                    // TODO link with building here
                    sceneManager.next(this);
                } else {
                    // TODO update for others too
                    // smt like waitForFirst();
                }
            }
        });

        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        connectButton = new Button("Connect");
        styleButton(connectButton, "#3399cc");  // Blue
        connectButton.setOnAction(e -> handleConnect());

        setPlayersButton = new Button("Set Players");
        styleButton(setPlayersButton, "#875f87");  // Purple
        setPlayersButton.setOnAction(e -> handleSetPlayers());
        setPlayersButton.setVisible(false);

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
            this.nickname = name;
            connectButton.setDisable(true);  // Disable after success
            nicknameFeedbackLabel.setText("Nickname set: " + nickname);
            String message = "/connect " + nickname;
            sceneManager.setNickname(nickname);

            if (rmiClient != null) {
                try {
                    sceneManager.getRmiClient().getServer().handleUserInput(sceneManager.getRmiClient(), message);
                    sceneManager.getRmiClient().getServer().showMessage(sceneManager.getRmiClient() + message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            else if (socketClient != null) {
                try {
                    socketClient.getServerSocket().sendMessageToServer(message, nickname);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            else {
                throw new IllegalEventException("No client connection available to send nickname.");
            }

            if(game.getListOfPlayers().size() == 1){ // TODO understand why "-1" -> prob sync problem
                setPlayersButton.setVisible(true);
                isFirstPlayer = true;
            }

            nicknameSet = true;
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
                this.playersNum = number;
                setPlayersButton.setDisable(true);  // Disable after success
                playersFeedbackLabel.setText("Number of players: " + number);
                String message = "/setnumberofplayers " + number;

                if (rmiClient != null) {
                    try {
                        sceneManager.getRmiClient().getServer().handleUserInput(sceneManager.getRmiClient(), message);
                        sceneManager.getRmiClient().getServer().showMessage(sceneManager.getRmiClient() + message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else if (socketClient != null) {
                    try {
                        socketClient.getServerSocket().sendMessageToServer(message, nickname);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else {
                    throw new IllegalEventException("No client connection available to send players number.");
                }

                playersSet = true;

            } catch (NumberFormatException e) {
                playersFeedbackLabel.setText("Invalid number, try again");
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
}
