package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;

import java.io.Serializable;
import java.util.List;


public class SmugglersCard extends Card implements Serializable {
    private final int firepower;
    private final List <Integer> blocks;
    private final int lostBlocksNumber;
    private final int daysToLose;
    private String name;

    public SmugglersCard(boolean levelTwo, boolean used, int firepower, List <Integer> blocks, int lostBlocksNumber, int daysToLose) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.blocks = blocks;
        this.lostBlocksNumber = lostBlocksNumber;
        this.daysToLose = daysToLose;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public int getFirepower() {
        return firepower;
    }

    public List <Integer> getBlocksList() {
        return blocks;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getLostBlocksNumber() {
        return lostBlocksNumber;
    }

    public GameState createGameState(Game game){
        return TravellingStateFactory.createGameState(game, this);
    }

}
