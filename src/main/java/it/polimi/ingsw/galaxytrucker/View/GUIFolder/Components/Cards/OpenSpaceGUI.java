package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class OpenSpaceGUI {
    private final TravellingSceneDefault travellingScene;

    private Button activateEnginesButton;
    private String nickname;

    OpenSpaceGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        activateEnginesButton = new Button("Activate Engines");

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

        activateEnginesButton.getStyleClass().add("bottom-button");

        activateEnginesButton.setVisible(true);

        box.getChildren().add(activateEnginesButton);
        box.setAlignment(Pos.CENTER);
    }
}
