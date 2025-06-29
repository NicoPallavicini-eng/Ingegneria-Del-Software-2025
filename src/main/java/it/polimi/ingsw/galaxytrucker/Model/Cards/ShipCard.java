package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;


/**
 * ShipCard is representation of Ship Card of Board Game
 */
public class ShipCard extends Card implements Serializable {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;
    private String name;

    /**
     * @param levelTwo
     * @param used
     * @param crewNumberLost
     * @param credits
     * @param daysToLose
     */
    public ShipCard(boolean levelTwo, boolean used, int crewNumberLost, int credits, int daysToLose) {
        super(levelTwo, used);
        this.crewNumberLost = crewNumberLost;
        this.credits = credits;
        this.daysToLose = daysToLose;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * This function returns the number of Credits that player will collect,if he decide to claim the reward
     * @return int
     */
    public int getCredits() {
        return credits;
    }

    /**
     * This function returns a number of days,that player will loose if he decide to collect Ship Card reward
     * @return int
     */
    public int getDaysToLose() {
        return daysToLose;
    }

    /**
     * This function return a number of crew that player is supposed to give in order to claim the reward
     * @return int
     */
    public int getCrewNumberLost() {
        return crewNumberLost;
    }
    /**
     * This function is used to create a specific state,that depends on the type of the Card
     * @param game
     * @return GameState
     */
    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
