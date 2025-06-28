package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;

public class OpenSpaceGUI extends CardInteractive {
    private final TravellingSceneDefault travellingScene;

    private Button activateEnginesButton;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;
    private String nickname;

    OpenSpaceGUI(OpenSpaceCard card, TravellingSceneDefault travellingSceneDefault, String nickname) {
        super(card, travellingSceneDefault, nickname);
        super.doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
        this.travellingScene = travellingSceneDefault;
        this.nickname = nickname;
        doButtons();
    }

    public void doButtons() {
        activateEnginesButton = new Button("Activate Engines");

        activateEnginesButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/activateengines", this.nickname);
        });

        activateEnginesButton.getStyleClass().add("bottom-button");

        activateEnginesButton.setVisible(true);
    }
}
