package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class CombatZoneGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button activateShieldsButton;
    private Button chooseSubshipButton;
    private Button ejectPeopleButton;
    private Button activateEnginesButton;
    private Button removeCargoButton;
    private String nickname;

    public CombatZoneGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        activateShieldsButton = new Button("Activate Shields");
        chooseSubshipButton = new Button("Choose Subship");
        ejectPeopleButton = new Button("Eject People");
        activateEnginesButton = new Button("Activate Engines");
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
        ejectPeopleButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("cabin row:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();
            dialog.setContentText("cabin column:");
            Stage dialogStage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col = dialog.showAndWait();
            dialog.setContentText("astronaut num:");
            Stage dialogStage3 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> num = dialog.showAndWait();

            String msg = "/ejectpeople " + row + "," + col + "," + num;

            travellingScene.sendMessageToServer(msg, this.nickname);
        });
        activateEnginesButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("engine row:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();
            dialog.setContentText("engine column:");
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

            String msg = "/activateengines " + row + "," + col + ";" + row2 + "," + col2 + "," + bat;

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
        activateShieldsButton.getStyleClass().add("bottom-button");
        chooseSubshipButton.getStyleClass().add("bottom-button");
        ejectPeopleButton.getStyleClass().add("bottom-button");
        activateEnginesButton.getStyleClass().add("bottom-button");
        removeCargoButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        activateShieldsButton.setVisible(true);
        chooseSubshipButton.setVisible(true);
        ejectPeopleButton.setVisible(true);
        activateEnginesButton.setVisible(true);
        removeCargoButton.setVisible(true);

        HBox h1 = new HBox(activateCannonsButton, activateShieldsButton, chooseSubshipButton);
        HBox h2 = new HBox(ejectPeopleButton, activateEnginesButton, removeCargoButton);
        h1.setSpacing(10);
        h2.setSpacing(10);
        VBox v = new VBox(h1, h2);
        h1.setAlignment(Pos.CENTER);
        h2.setAlignment(Pos.CENTER);

        box.getChildren().add(v);
    }
}
