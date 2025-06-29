package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.List;


/**
 * PiratesCard represent Pirates Card of Board Game
 */
public class PiratesCard extends Card implements Serializable {
    private final int firepower;
    private final int credits;
    private final int daysToLose;
    private final List <Cannonball> cannonballList;
    private String name;

    /**
     * @param levelTwo
     * @param used
     * @param firepower
     * @param credits
     * @param daysToLose
     * @param cannonballList
     */
    public PiratesCard(boolean levelTwo, boolean used, int firepower, int credits, int daysToLose, List <Cannonball> cannonballList) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.credits = credits;
        this.daysToLose = daysToLose;
        this.cannonballList = cannonballList;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * This function return a firepower that is required to defeat Pirates
     * @return int
     */
    public int getFirepower() {
        return firepower;
    }

    /**
     * These function return the number of credit that player can win,if he defeat pirates
     * @return int
     */
    public int getCredits() {
        return credits;
    }
    /**
     * These function return the number of days that player need to loose in order to. claim reward.
     * @return int
     */
    public int getDaysToLose() {
        return daysToLose;
    }

    /**
     * This function returns a List of Cannonballs that Pirates will shoot
     * @return List <Cannonball>
     */
    public List <Cannonball> getCannonballList() {
        return cannonballList;
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
