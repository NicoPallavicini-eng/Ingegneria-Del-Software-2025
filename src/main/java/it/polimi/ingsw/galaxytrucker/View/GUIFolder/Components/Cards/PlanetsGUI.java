package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Optional;

public class PlanetsGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button addCargoButton;
    private Button choosePlanetButton;
    private String nickname;

    PlanetsGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
        this.nickname = nickname;
    }

    public void doButtons(HBox box) {
        addCargoButton = new Button("Add Cargo");
        choosePlanetButton = new Button("Choose Planet");
        
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
        choosePlanetButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("planet num:");
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            Optional<String> row = dialog.showAndWait();

            String msg = "/chooseplanet " + row;

            travellingScene.sendMessageToServer(msg, this.nickname);
        });

        addCargoButton.getStyleClass().add("bottom-button");
        choosePlanetButton.getStyleClass().add("bottom-button");

        addCargoButton.setVisible(true);
        choosePlanetButton.setVisible(true);

        box.getChildren().addAll(addCargoButton, choosePlanetButton);
        box.setAlignment(Pos.CENTER);
    }
}
