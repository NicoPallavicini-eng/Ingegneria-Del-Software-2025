package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;


/**
 * SlaversCard represent Slavers Cards of Board Game
 */
public class SlaversCard extends Card implements Serializable {
    private final int firepower;
    private final int credits;
    private final int crewLost;
    private final int daysToLose;
    private String name;

    /**
     * @param levelTwo
     * @param used
     * @param firepower
     * @param credits
     * @param crewLost
     * @param daysToLose
     */
    public SlaversCard(boolean levelTwo, boolean used, int firepower, int credits, int crewLost, int daysToLose) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.credits = credits;
        this.crewLost = crewLost;
        this.daysToLose = daysToLose;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }
    /**
     * This function return a firepower that is required to defeat Slavers
     * @return int
     */
    public int getFirepower() {
        return firepower;
    }
    /**
     * These function return the number of credit that player can win,if he defeat slavers
     * @return int
     */
    public int getNumberOfCredits() {
        return credits;
    }
    /**
     * This function return a number of crew that player is supposed to give to Slayers,if he loose.
     */
    public int getNumberOfCrewLost() {
        return crewLost;
    }
    /**
     * This function returns a number of days,that player will loose if he decide to collect Slavers Card reward
     * @return int
     */
    public int getNumberOfDaysToLose() {
        return daysToLose;
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
