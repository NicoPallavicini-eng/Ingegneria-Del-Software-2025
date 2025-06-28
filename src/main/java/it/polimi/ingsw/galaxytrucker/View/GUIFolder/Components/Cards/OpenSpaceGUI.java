package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OpenSpaceGUI {
    private final TravellingSceneDefault travellingScene;

    private Button activateEnginesButton;

    OpenSpaceGUI(TravellingSceneDefault travellingScene) {
        this.travellingScene = travellingScene;
    }

    public void doButtons(HBox box) {
        activateEnginesButton = new Button("Activate Engines");

        activateEnginesButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateengines");
        });

        activateEnginesButton.getStyleClass().add("bottom-button");

        activateEnginesButton.setVisible(true);

        box.getChildren().add(activateEnginesButton);
        box.setAlignment(Pos.CENTER);
    }
}
