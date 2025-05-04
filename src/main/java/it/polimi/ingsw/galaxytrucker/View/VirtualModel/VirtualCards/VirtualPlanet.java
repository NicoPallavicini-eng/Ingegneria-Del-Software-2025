package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualShip;

import java.util.List;

public class VirtualPlanet {
    private final List <Integer> blocks;
    private VirtualShip shipLanded = null;

    public VirtualPlanet(List <Integer> blocks) {
        this.blocks = blocks;
    }

    public List <Integer> getBlocks() {
        return blocks;
    }

    public VirtualShip getShipLanded() {
        return shipLanded;
    }
}
