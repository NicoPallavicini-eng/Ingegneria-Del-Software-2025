package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EngineTileTest {

    @Test
    void testGetDoublePower() {
        EngineTile engineTile = new EngineTile(true, false, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        assertTrue(engineTile.getDoublePower());
    }

    @Test
    void testSetAndGetActiveState() {
        EngineTile engineTile = new EngineTile(true, false, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        engineTile.setActiveState(true);
        assertTrue(engineTile.getActiveState());
    }


}