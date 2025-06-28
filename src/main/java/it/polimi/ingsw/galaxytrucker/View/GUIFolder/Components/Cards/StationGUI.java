package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;

public class StationGUI extends CardInteractive {
    private final TravellingSceneDefault travellingScene;
    
    private Button addCargoButton;
    private Button switchCargoButton;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;
    private String nickname;

    StationGUI(StationCard card, TravellingSceneDefault travellingSceneDefault, String nickname) {
        super(card, travellingSceneDefault, nickname);
        super.doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
        this.travellingScene = travellingSceneDefault;
        this.nickname = nickname;
        doButtons();
    }

    public void doButtons() {
        addCargoButton = new Button("Add Cargo");
        switchCargoButton = new Button("Switch Cargo");
        
        addCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/addcargo", this.nickname);
        });
        switchCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/switchcargo", this.nickname);
        });

        addCargoButton.getStyleClass().add("bottom-button");
        switchCargoButton.getStyleClass().add("bottom-button");
        
        addCargoButton.setVisible(true);
        switchCargoButton.setVisible(true);
    }
}
