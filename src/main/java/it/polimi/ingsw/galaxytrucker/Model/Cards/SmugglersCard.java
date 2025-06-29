package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.List;


/**
 * SmugglersCard represent Smugglers Card of Board Game
 */
public class SmugglersCard extends Card implements Serializable {
    private final int firepower;
    private final List <Integer> blocks;
    private final int lostBlocksNumber;
    private final int daysToLose;
    private String name;

    /**
     * @param levelTwo
     * @param used
     * @param firepower
     * @param blocks
     * @param lostBlocksNumber
     * @param daysToLose
     */
    public SmugglersCard(boolean levelTwo, boolean used, int firepower, List <Integer> blocks, int lostBlocksNumber, int daysToLose) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.blocks = blocks;
        this.lostBlocksNumber = lostBlocksNumber;
        this.daysToLose = daysToLose;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }
    /**
     * These function return the number of credit that player can win,if he defeat smugglers
     * @return int
     */
    public int getFirepower() {
        return firepower;
    }

    /**
     * This function return an ArrayList of elements,that you can claim,if you defeat Smugglers
     * @return List <Integer>
     */
    public List <Integer> getBlocksList() {
        return blocks;
    }
    /**
     * This function returns a number of days,that player will loose if he decide to collect Smugglers Card reward
     * @return int
     */
    public int getDaysToLose() {
        return daysToLose;
    }

    /**
     * This function return a number of Blocks,that player need to loose,if he loose against Smugglers.
     * @return int
     */
    public int getLostBlocksNumber() {
        return lostBlocksNumber;
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
