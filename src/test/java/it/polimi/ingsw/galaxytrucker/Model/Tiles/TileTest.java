package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    @Test
    void testRotateRight() {
        Tile tile = new Tile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        tile.rotate(Side.RIGHT);
        List<ConnectorType> connectors = tile.getConnectors();
        assertEquals(ConnectorType.DOUBLE, connectors.get(0));
        assertEquals(ConnectorType.UNIVERSAL, connectors.get(1));
        assertEquals(ConnectorType.NONE, connectors.get(2));
        assertEquals(ConnectorType.SINGLE, connectors.get(3));
    }

    @Test
    void testRotateLeft() {
        Tile tile = new Tile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        tile.rotate(Side.LEFT);
        List<ConnectorType> connectors = tile.getConnectors();
        assertEquals(ConnectorType.NONE, connectors.get(0));
        assertEquals(ConnectorType.SINGLE, connectors.get(1));
        assertEquals(ConnectorType.DOUBLE, connectors.get(2));
        assertEquals(ConnectorType.UNIVERSAL, connectors.get(3));
    }

    @Test
    void testSetAndGetAttached() {
        Tile tile = new Tile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        assertFalse(tile.isAttached());
        tile.setAttached(true);
        assertTrue(tile.isAttached());
    }
    @Test
    void testFlip() {
        Tile tile = new Tile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        tile.flip();
        assertTrue(tile.getFacingUp());
    }

    @Test
    void testSetAndGetChoosable() {
        Tile tile = new Tile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        assertTrue(tile.isChoosable());
        tile.setChoosable(false);
        assertFalse(tile.isChoosable());
    }

    @Test
    void testGetConnectors() {
        Tile tile = new Tile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        List<ConnectorType> connectors = tile.getConnectors();
        assertEquals(4, connectors.size());
        assertEquals(ConnectorType.SINGLE, connectors.get(0));
        assertEquals(ConnectorType.DOUBLE, connectors.get(1));
        assertEquals(ConnectorType.UNIVERSAL, connectors.get(2));
        assertEquals(ConnectorType.NONE, connectors.get(3));
    }


}