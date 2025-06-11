package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;

public class ShipCard extends Card implements Serializable {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;
    private String name;

    public ShipCard(boolean levelTwo, boolean used, int crewNumberLost, int credits, int daysToLose) {
        super(levelTwo, used);
        this.crewNumberLost = crewNumberLost;
        this.credits = credits;
        this.daysToLose = daysToLose;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public int getCredits() {
        return credits;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getCrewNumberLost() {
        return crewNumberLost;
    }

    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }
}
