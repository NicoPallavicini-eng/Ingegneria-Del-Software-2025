package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;

public class CombatZoneGUI extends CardInteractive {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button activateShieldsButton;
    private Button chooseSubshipButton;
    private Button ejectPeopleButton;
    private Button activateEnginesButton;
    private Button removeCargoButton;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;

    CombatZoneGUI(CombatZoneCard card, TravellingSceneDefault travellingSceneDefault) {
        super(card, travellingSceneDefault);
        super.doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
        this.travellingScene = travellingSceneDefault;
        doButtons();
    }

    public void doButtons() {
        activateCannonsButton = new Button("Activate Cannons");
        activateShieldsButton = new Button("Activate Shields");
        chooseSubshipButton = new Button("Choose Subship");
        ejectPeopleButton = new Button("Eject People");
        activateEnginesButton = new Button("Activate Engines");
        removeCargoButton = new Button("Remove Cargo");

        activateCannonsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activatecannons");
        });
        activateShieldsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateshield");
        });
        chooseSubshipButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/choosesubship");
        });
        ejectPeopleButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/ejectpeople");
        });
        activateEnginesButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateengines");
        });
        removeCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/removecargo");
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
    }
}
