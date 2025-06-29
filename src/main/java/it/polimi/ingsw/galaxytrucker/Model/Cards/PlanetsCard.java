package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.List;


/**
 * PlanetCard is represenation of Planet Card of Board Game
 */
public class PlanetsCard extends Card implements Serializable {
    private final List <Planet> planets;
    private final int daysToLose;
    private String name;

    /**
     * @param levelTwo
     * @param used
     * @param planets
     * @param daysToLose
     */
    public PlanetsCard(boolean levelTwo, boolean used, List <Planet> planets, int daysToLose) {
        super(levelTwo, used);
        this.planets = planets;
        this.daysToLose = daysToLose;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * This function returns a list of planets
     * @return List <Planet>
     */
    public List <Planet> getPlanetsList() {
        return planets;
    }

    /**
     * This function returns a number of days that player will loose if he decide to collect cargo from a specific planet
     * @return int
     */
    public int getDaysToLose() {
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
