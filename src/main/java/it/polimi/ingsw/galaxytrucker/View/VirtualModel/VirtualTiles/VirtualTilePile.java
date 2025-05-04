package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import java.util.List;

public class VirtualTilePile {
    private List<VirtualTile> tilePile;

    public VirtualTilePile(List<VirtualTile> tilePile) {
        this.tilePile = tilePile;
    }

    public VirtualTilePile getTilePile() {
        return this;
    }
}
