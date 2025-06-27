package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.ShipCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;

public class ShipGUI extends CardInteractive {
    private final TravellingSceneDefault travellingScene;

    private Button ejectPeopleButton;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;

    ShipGUI(ShipCard card, TravellingSceneDefault travellingSceneDefault) {
        super(card, travellingSceneDefault);
        super.doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
        this.travellingScene = travellingSceneDefault;
        doButtons();
    }

    public void doButtons() {
        ejectPeopleButton = new Button("Eject People");

        ejectPeopleButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/ejectpeople");
        });

        ejectPeopleButton.getStyleClass().add("bottom-button");

        ejectPeopleButton.setVisible(true);
    }
}
