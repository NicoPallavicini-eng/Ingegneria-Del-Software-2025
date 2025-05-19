package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.io.Serializable;
import java.util.List;

public class Planet implements Serializable {
    private final List <Integer> blocks;
    public Planet(List <Integer> blocks) {
        this.blocks = blocks;
    }

    public List <Integer> getBlocks() {
        return blocks;
    }


}
