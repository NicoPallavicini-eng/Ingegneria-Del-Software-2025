package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShieldTileTest {
    @Test
    void testSetAndGetActiveState() {
        ShieldTile shieldTile = new ShieldTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, ShieldOrientation.NORTHWEST, false);
        shieldTile.setActiveState(true);
        assertTrue(shieldTile.getActiveState());
    }

    @Test
    void testGetOrientation() {
        ShieldTile shieldTile = new ShieldTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, ShieldOrientation.NORTHWEST, false);
        assertEquals(ShieldOrientation.NORTHWEST, shieldTile.getOrientation());
    }

    @Test
    void testRotateRight() {
        ShieldTile shieldTile = new ShieldTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, ShieldOrientation.NORTHWEST, false);
        shieldTile.rotate(Side.RIGHT);
        assertEquals(ShieldOrientation.NORTHEAST, shieldTile.getOrientation());
    }

    @Test
    void testRotateLeft() {
        ShieldTile shieldTile = new ShieldTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, ShieldOrientation.NORTHWEST, false);
        shieldTile.rotate(Side.LEFT);
        assertEquals(ShieldOrientation.SOUTHWEST, shieldTile.getOrientation());
    }

}