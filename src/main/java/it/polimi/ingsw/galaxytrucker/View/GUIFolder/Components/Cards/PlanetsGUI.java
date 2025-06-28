package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;

public class PlanetsGUI extends CardInteractive {
    private final TravellingSceneDefault travellingScene;
    
    private Button addCargoButton;
    private Button choosePlanetButton;

    private Button doneButton;
    private Button noChoiceButton;
    private Button giveUpButton;
    private Button inventoryButton;
    private String nickname;

    PlanetsGUI(PlanetsCard card, TravellingSceneDefault travellingSceneDefault, String nickname) {
        super(card, travellingSceneDefault, nickname);
        super.doMainButtons(doneButton, noChoiceButton, giveUpButton, inventoryButton);
        this.travellingScene = travellingSceneDefault;
        this.nickname = nickname;
        doButtons();
    }

    public void doButtons() {
        addCargoButton = new Button("Add Cargo");
        choosePlanetButton = new Button("Choose Planet");
        
        addCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/addcargo", this.nickname);
        });
        choosePlanetButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/chooseplanet", this.nickname);
        });

        addCargoButton.getStyleClass().add("bottom-button");
        choosePlanetButton.getStyleClass().add("bottom-button");

        addCargoButton.setVisible(true);
        choosePlanetButton.setVisible(true);
    }
}
