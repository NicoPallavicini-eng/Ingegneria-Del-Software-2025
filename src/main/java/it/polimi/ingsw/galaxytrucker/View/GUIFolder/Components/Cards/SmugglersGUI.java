package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class SmugglersGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button addCargoButton;
    private Button removeCargoButton;
    private String nickname;

    SmugglersGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        addCargoButton = new Button("Add Cargo");
        removeCargoButton = new Button("Remove Cargo");

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
        addCargoButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("cargo row:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();
            dialog.setContentText("cargo column:");
            Stage dialogStage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col = dialog.showAndWait();
            dialog.setContentText("cargo num:");
            Stage dialogStage3 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> num = dialog.showAndWait();

            String msg = "/addcargo " + row + "," + col + "," + num;

            travellingScene.sendMessageToServer(msg, this.nickname);
        });
        removeCargoButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("cargo row:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();
            dialog.setContentText("cargo column:");
            Stage dialogStage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col = dialog.showAndWait();
            dialog.setContentText("cargo num:");
            Stage dialogStage3 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> num = dialog.showAndWait();

            String msg = "/removecargo " + row + "," + col + "," + num;

            travellingScene.sendMessageToServer(msg, this.nickname);
        });

        activateCannonsButton.getStyleClass().add("bottom-button");
        addCargoButton.getStyleClass().add("bottom-button");
        removeCargoButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        addCargoButton.setVisible(true);
        removeCargoButton.setVisible(true);

        box.getChildren().addAll(activateCannonsButton, addCargoButton, removeCargoButton);
        box.setAlignment(Pos.CENTER);
    }
}
