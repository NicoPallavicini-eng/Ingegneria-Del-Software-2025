package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SlaversCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;

public class SlaversGUI extends CardInteractive {
    private final TravellingSceneDefault travellingScene;
    
    private Button activateCannonsButton;
    private Button ejectPeopleButton;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;
    private String nickname;

    SlaversGUI(SlaversCard card, TravellingSceneDefault travellingSceneDefault, String nickname) {
        super(card, travellingSceneDefault, nickname);
        super.doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
        this.travellingScene = travellingSceneDefault;
        this.nickname = nickname;
        doButtons();
    }

    public void doButtons() {
        activateCannonsButton = new Button("Activate Cannons");
        ejectPeopleButton = new Button("Eject People");

        activateCannonsButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activatecannons", this.nickname);
        });
        ejectPeopleButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/ejectpeople", this.nickname);
        });

        activateCannonsButton.getStyleClass().add("bottom-button");
        ejectPeopleButton.getStyleClass().add("bottom-button");

        activateCannonsButton.setVisible(true);
        ejectPeopleButton.setVisible(true);
    }
}
