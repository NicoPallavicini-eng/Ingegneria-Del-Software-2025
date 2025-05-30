package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TilePileTest {

    private TilePile tilePile;
    private Tile tile1;
    private Tile tile2;

    @BeforeEach
    void setUp() {
        tile1 = new Tile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        tile2 = new Tile(ConnectorType.NONE, ConnectorType.UNIVERSAL, ConnectorType.DOUBLE, ConnectorType.SINGLE);
        List<Tile> tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        tilePile = new TilePile(tiles);
    }

    @Test
    void testGetTilePile() {
        assertNotNull(tilePile.getTilePile());
    }

    @Test
    void testPickUpTile() {
        assertTrue(tile1.isChoosable());
        tilePile.pickUpTile(tile1);
        assertFalse(tile1.isChoosable());
    }

    @Test
    void testPutDownTile() {
        tile1.setChoosable(false);
        tilePile.putDownTile(tile1);
        assertTrue(tile1.isChoosable());
        assertFalse(tile1.getUpsideDown());
    }
}