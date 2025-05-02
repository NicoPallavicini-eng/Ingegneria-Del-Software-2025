package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import java.util.List;

public class TilePile {
    private List<Tile> tilePile;

    public TilePile(List<Tile> tilePile) {
        this.tilePile = tilePile;
    }

    public TilePile getTilePile() {
        return this;
    }

    public Tile pickUpTile(Tile tile){
        tile.setChoosable(false);
        return tile;
    }

    public void putDownTile(Tile tile){
        tile.setChoosable(true);
        tile.flip();
    }
}
