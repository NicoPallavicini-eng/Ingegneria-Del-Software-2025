package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.List;

public class StationCard extends Card implements Serializable {
    private final int crewNumberNeeded;
    private final List <Integer> blocks; // Integer
    private final int daysToLose;
    private String name;

    public StationCard(boolean levelTwo, boolean used, int crewNumberNeeded, List <Integer> blocks, int daysToLose) {
        super(levelTwo, used);
        this.crewNumberNeeded = crewNumberNeeded;
        this.blocks = blocks;
        this.daysToLose = daysToLose;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public List <Integer> getBlockList() {
        return blocks;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getCrewNumberNeeded() {
        return crewNumberNeeded;
    }

    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }

}
