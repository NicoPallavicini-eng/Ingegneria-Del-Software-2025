package it.polimi.ingsw.galaxytrucker.Model.Cards;

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
