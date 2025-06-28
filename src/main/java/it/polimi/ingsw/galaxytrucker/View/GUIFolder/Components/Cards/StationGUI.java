package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class StationGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button addCargoButton;
    private Button switchCargoButton;
    private String nickname;

    StationGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        addCargoButton = new Button("Add Cargo");
        switchCargoButton = new Button("Switch Cargo");
        
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
        switchCargoButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("first cargo row:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();
            dialog.setContentText("first cargo column:");
            Stage dialogStage2 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col = dialog.showAndWait();
            dialog.setContentText("cargo num:");
            Stage dialogStage3 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> num = dialog.showAndWait();
            dialog.setContentText("second cargo row:");
            Stage dialogStage4 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row2 = dialog.showAndWait();
            dialog.setContentText("second cargo column:");
            Stage dialogStage5 = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> col2 = dialog.showAndWait();

            String msg = "/switchcargo " + row + "," + col + "," + num + ";" + row2 + "," + col2;

            travellingScene.sendMessageToServer(msg, this.nickname);
        });

        addCargoButton.getStyleClass().add("bottom-button");
        switchCargoButton.getStyleClass().add("bottom-button");
        
        addCargoButton.setVisible(true);
        switchCargoButton.setVisible(true);

        box.getChildren().addAll(addCargoButton, switchCargoButton);
        box.setAlignment(Pos.CENTER);
    }
}
