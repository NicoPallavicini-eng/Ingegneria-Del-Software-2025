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
    private String nickname;

    ShipGUI(ShipCard card, TravellingSceneDefault travellingSceneDefault, String nickname) {
        super(card, travellingSceneDefault, nickname);
        super.doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
        this.travellingScene = travellingSceneDefault;
        this.nickname = nickname;
        doButtons();
    }

    public void doButtons() {
        ejectPeopleButton = new Button("Eject People");

        ejectPeopleButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/ejectpeople", this.nickname);
        });

        ejectPeopleButton.getStyleClass().add("bottom-button");

        ejectPeopleButton.setVisible(true);
    }
}
