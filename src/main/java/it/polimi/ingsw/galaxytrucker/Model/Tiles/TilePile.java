package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import java.util.List;

public class TilePile {
    private List<Tile> tilePile;

    public TilePile(List<Tile> tilePile) {
        this.tilePile = tilePile;
    }

    public TilePile getTilePile() {
        return this;
    }

    public Tile pickUpTile(int row, int column){
        //TODO logic
        return null;
    }

    public void putDownTile(Tile tile){
        //TODO logic
    }
}
