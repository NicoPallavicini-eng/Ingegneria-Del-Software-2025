package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;


/**
 * Abstract Class Card is used to represent different Cards,that there is during the Game
 */
public abstract class Card implements Serializable {
    private final boolean levelTwo;
    private boolean used;
    private String name;

    /**
     * Constructor of Card,with some defaukt values
     * @param levelTwo
     * @param used
     */
    public Card(boolean levelTwo, boolean used) {
        this.levelTwo = levelTwo;
        this.used = false;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * This function tells whether Card is used during a Game or not
     * @return boolean
     */
    public boolean isUsed() {
        return used;
    }

    /**This function update used parameter
     * @param used
     */
    public void updateUsed(boolean used) {
        this.used = used;
    }

    /**This function tells you whether the Card is level 2 or not
     * @return boolean
     */
    public boolean isLevelTwo() {
        return levelTwo;
    }

    public void process () {}

    /**
     * This function is used to create a specific state,that depends on the type of the Card
     * @param game
     * @return GameState
     */
    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
