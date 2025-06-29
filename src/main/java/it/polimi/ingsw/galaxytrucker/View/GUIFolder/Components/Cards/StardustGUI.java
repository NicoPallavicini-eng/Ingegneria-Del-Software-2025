package it.polimi.ingsw.galaxytrucker.View.GUIFolder.Components.Cards;

import it.polimi.ingsw.galaxytrucker.View.GUIFolder.Scenes.TravellingSceneDefault;

/**
 * GUI component for handling Stardust card actions in the game.
 * Provides the interface for interacting with Stardust events.
 */
public class StardustGUI {
    private final TravellingSceneDefault travellingScene;

    /**
     * Constructs a StardustGUI with the specified travelling scene and player nickname.
     *
     * @param travellingScene the scene to interact with the server
     * @param nickname the player's nickname
     */
    StardustGUI(TravellingSceneDefault travellingScene, String nickname) {
        this.travellingScene = travellingScene;
    }
}
