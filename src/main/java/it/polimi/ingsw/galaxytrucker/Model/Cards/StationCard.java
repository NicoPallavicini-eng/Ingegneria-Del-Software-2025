package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.List;


/**
 * StationCard represent a Station Card of Board Game
 */
public class StationCard extends Card implements Serializable {
    private final int crewNumberNeeded;
    private final List <Integer> blocks; // Integer
    private final int daysToLose;
    private String name;

    /**
     * @param levelTwo
     * @param used
     * @param crewNumberNeeded
     * @param blocks
     * @param daysToLose
     */
    public StationCard(boolean levelTwo, boolean used, int crewNumberNeeded, List <Integer> blocks, int daysToLose) {
        super(levelTwo, used);
        this.crewNumberNeeded = crewNumberNeeded;
        this.blocks = blocks;
        this.daysToLose = daysToLose;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * This function returns a list of Cargo that player can collect,if he decide to land
     * @return List <Integer>
     */
    public List <Integer> getBlockList() {
        return blocks;
    }

    /**
     * This function return a number of days to loose,if you decide to claim the reward
     * @return int
     */
    public int getDaysToLose() {
        return daysToLose;
    }

    /**
     * This function return a number of Inhabitants required in order to collect reward
     * @return int
     */
    public int getCrewNumberNeeded() {
        return crewNumberNeeded;
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
