package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class SmugglersGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button addCargoButton;
    private Button removeCargoButton;

    SmugglersGUI(TravellingSceneDefault travellingScene) {
        this.travellingScene = travellingScene;
    }

    public void doButtons(HBox box) {
        activateCannonsButton = new Button("Activate Cannons");
        addCargoButton = new Button("Add Cargo");
        removeCargoButton = new Button("Remove Cargo");

        activateCannonsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activatecannons");
        });
        addCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/addcargo");
        });
        removeCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/removecargo");
        });

        activateCannonsButton.getStyleClass().add("bottom-button");
        addCargoButton.getStyleClass().add("bottom-button");
        removeCargoButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        addCargoButton.setVisible(true);
        removeCargoButton.setVisible(true);

        box.getChildren().addAll(activateCannonsButton, addCargoButton, removeCargoButton);
    }
}
