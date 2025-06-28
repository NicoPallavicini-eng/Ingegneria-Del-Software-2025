package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PiratesCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class PiratesGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button activateShieldsButton;
    private Button chooseSubshipButton;
    private String nickname;

    PiratesGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        activateShieldsButton = new Button("Activate Shields");
        chooseSubshipButton = new Button("Choose Subship");

        activateCannonsButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("cannon row:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();
            dialog.setContentText("cannon column:");
            Stage dialogStage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col = dialog.showAndWait();
            dialog.setContentText("battery row:");
            Stage dialogStage3 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row2 = dialog.showAndWait();
            dialog.setContentText("battery column:");
            Stage dialogStage4 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col2 = dialog.showAndWait();
            dialog.setContentText("battery num:");
            Stage dialogStage5 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> bat = dialog.showAndWait();

            String msg = "/activatecannons " + row + "," + col + ";" + row2 + "," + col2 + "," + bat;

            travellingScene.sendMessageToServer(msg, this.nickname);
        });
        activateShieldsButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("shield row:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();
            dialog.setContentText("shield column:");
            Stage dialogStage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col = dialog.showAndWait();
            dialog.setContentText("battery row:");
            Stage dialogStage3 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row2 = dialog.showAndWait();
            dialog.setContentText("battery column:");
            Stage dialogStage4 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col2 = dialog.showAndWait();

            String msg = "/activateshield " + row + "," + col + "," + row2 + "," + col2;

            travellingScene.sendMessageToServer(msg, this.nickname);
        });
        chooseSubshipButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("bit row:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();
            dialog.setContentText("bit column:");
            Stage dialogStage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col = dialog.showAndWait();

            String msg = "/choosesubship " + row + "," + col;

            travellingScene.sendMessageToServer(msg, this.nickname);
        });

        activateCannonsButton.getStyleClass().add("bottom-button");
        activateShieldsButton.getStyleClass().add("bottom-button");
        chooseSubshipButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        activateShieldsButton.setVisible(true);
        chooseSubshipButton.setVisible(true);

        box.getChildren().addAll(activateCannonsButton, activateShieldsButton, chooseSubshipButton);
        box.setAlignment(Pos.CENTER);
    }
}
