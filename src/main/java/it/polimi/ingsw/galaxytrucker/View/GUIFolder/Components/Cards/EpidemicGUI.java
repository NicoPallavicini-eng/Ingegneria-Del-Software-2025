package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.EpidemicCard;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Card;
import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class EpidemicGUI {
    private final TravellingSceneDefault travellingScene;

    EpidemicGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
    }
}
