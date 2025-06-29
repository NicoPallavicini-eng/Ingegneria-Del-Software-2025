package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.List;


/**
 * MeteorsCard represent Meteors Card of Board Game
 */
public class MeteorsCard extends Card implements Serializable {
    private final List <Meteor> meteors;
    private String name;

    /**
     * @param levelTwo
     * @param used
     * @param meteors
     */
    public MeteorsCard(boolean levelTwo, boolean used, List <Meteor> meteors) {
        super(levelTwo, used);
        this.meteors = meteors;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * <This function returns a List of Meteors that current card has
     * @return List <Meteor>
     */
    public List <Meteor> getMeteorsList() {
        return meteors;
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
