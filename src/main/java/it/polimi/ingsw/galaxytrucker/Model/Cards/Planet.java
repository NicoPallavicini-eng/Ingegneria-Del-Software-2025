package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.io.Serializable;
import java.util.List;

/**
 * Planet is the collection of Cargos
 */
public class Planet implements Serializable {
    private final List <Integer> blocks;
    public Planet(List <Integer> blocks) {
        this.blocks = blocks;
    }

    /**
     * This function returns a List of Cargo,that planet has
     * @return List <Integer>
     */
    public List <Integer> getBlocks() {
        return blocks;
    }

}
