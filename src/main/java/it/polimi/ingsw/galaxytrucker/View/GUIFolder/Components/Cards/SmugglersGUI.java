package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;

public class SmugglersGUI extends CardInteractive {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button addCargoButton;
    private Button removeCargoButton;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;

    SmugglersGUI(SmugglersCard card, TravellingSceneDefault travellingSceneDefault) {
        super(card, travellingSceneDefault);
        super.doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
        this.travellingScene = travellingSceneDefault;
        doButtons();
    }

    public void doButtons() {
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
    }
}
