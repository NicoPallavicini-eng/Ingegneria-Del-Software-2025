package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PlanetsGUI {
    private final TravellingSceneDefault travellingScene;
    
    private Button addCargoButton;
    private Button choosePlanetButton;

    PlanetsGUI(TravellingSceneDefault travellingScene) {
        this.travellingScene = travellingScene;
    }

    public void doButtons(HBox box) {
        addCargoButton = new Button("Add Cargo");
        choosePlanetButton = new Button("Choose Planet");
        
        addCargoButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/addcargo");
        });
        choosePlanetButton.setOnAction(e -> {
            travellingScene.sendMessageToServer("/chooseplanet");
        });

        addCargoButton.getStyleClass().add("bottom-button");
        choosePlanetButton.getStyleClass().add("bottom-button");

        addCargoButton.setVisible(true);
        choosePlanetButton.setVisible(true);

        box.getChildren().addAll(addCargoButton, choosePlanetButton);
    }
}
