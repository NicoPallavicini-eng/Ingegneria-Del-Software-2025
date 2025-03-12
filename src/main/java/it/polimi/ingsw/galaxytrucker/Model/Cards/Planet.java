package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

public class Planet {
    // TODO add Ship link
    private final List <Integer> blocks;
    private Ship shipLanded;

    public Planet(List <Integer> blocks) {
        this.blocks = blocks;
    }

    public List <Integer> getBlocks() {
        return blocks;
    }

    public Ship getShipLanded() {
        return shipLanded;
    }
}
