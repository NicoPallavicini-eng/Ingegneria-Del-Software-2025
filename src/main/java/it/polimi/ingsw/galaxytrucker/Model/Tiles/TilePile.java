package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * TilePile Class contains all Tiles present in the Game
 */
public class TilePile implements Serializable {
    private List<Tile> tilePile;

    /**Constructor of TilePile shuffles all Tiles
     * @param tilePile
     */
    public TilePile(List<Tile> tilePile) {
        this.tilePile = tilePile;
        shuffle(this.tilePile);
    }

    /**
     * This function returns entire Tile Pile
     * @return List<Tile>
     */
    public List<Tile> getTilePile() {
        return tilePile;
    }

    /**
     * This function is called when Player is picking up Tile
     * @param tile
     * @return Tile
     */
    public Tile pickUpTile(Tile tile){
        tile.setChoosable(false);
        return tile;
    }

    /**
     * This function is used when Player put down Tile in Tile Pile
     * @param tile
     */
    public void putDownTile(Tile tile){
        tile.setChoosable(true);
        tile.flip();
    }

    /**This function shuffles Tiles
     * @param tiles
     */
    public void shuffle(List <Tile> tiles) {
        // Shuffle the deck using Collections.shuffle() with a randomizer
        Collections.shuffle(tiles);
    }
}
