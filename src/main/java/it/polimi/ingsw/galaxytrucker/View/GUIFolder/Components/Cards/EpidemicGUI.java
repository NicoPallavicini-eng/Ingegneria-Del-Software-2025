package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;
/**
 * The EpidemicGUI class represents the graphical user interface for handling epidemic cards in the game.
 * It provides methods and components to manage user interactions related to epidemic events.
 */
public class EpidemicGUI {
    private final TravellingSceneDefault travellingScene;
    /**
     * Constructs an EpidemicGUI instance with the specified travelling scene and player nickname.
     *
     * @param travellingScene The travelling scene associated with this epidemic GUI.
     * @param nickname        The nickname of the player.
     */
    EpidemicGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
    }
}
