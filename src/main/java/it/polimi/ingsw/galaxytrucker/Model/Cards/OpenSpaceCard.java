package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;


/**
 * OpenSpaceCard represent a Open Space Card of Board Game
 */
public class OpenSpaceCard extends Card implements Serializable {
    private String name;

    /**
     * @param levelTwo
     * @param used
     */
    public OpenSpaceCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * This function is used to create a specific state,that depends on the type of the Card
     * @param game
     * @return GameState
     */
    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
