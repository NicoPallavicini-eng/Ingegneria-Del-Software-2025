package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import java.util.List;

public class TilePile {
    private List<Tile> tilePile;

    public TilePile(List<Tile> tilePile) {
        this.tilePile = tilePile;
    }

    public List<Tile> getTilePile() {
        return tilePile;
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
