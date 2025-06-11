package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BioadaptorTileTest {
    @Test
    void testSetAndGetColor() {
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, AlienColor.ORANGE);
        assertEquals(AlienColor.ORANGE, bioadaptorTile.getAlienColor());
    }

    @Test
    void testGetColor() {
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, AlienColor.ORANGE);
        assertEquals(AlienColor.ORANGE, bioadaptorTile.getColor());
    }

}