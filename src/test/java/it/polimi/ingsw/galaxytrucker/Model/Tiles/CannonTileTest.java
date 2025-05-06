package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CannonTileTest {

    @Test
    void testGetDoublePower() {
        CannonTile cannonTile = new CannonTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, true, false);
        assertTrue(cannonTile.getDoublePower());
    }

    @Test
    void testSetAndGetActiveState() {
        CannonTile cannonTile = new CannonTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, true, false);
        cannonTile.setActiveState(true);
        assertTrue(cannonTile.getActiveState());
    }
}