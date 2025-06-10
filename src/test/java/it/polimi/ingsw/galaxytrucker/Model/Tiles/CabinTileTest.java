package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CabinTileTest {
    @Test
    void testUpdateInhabitants() {
        CabinTile cabinTile = new CabinTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, CabinInhabitants.NONE, true, Color.RED,2, 1);
        cabinTile.updateInhabitants(CabinInhabitants.TWO);
        assertEquals(CabinInhabitants.TWO, cabinTile.getInhabitants());
    }

    @Test
    void testRemoveAdaptors() {
        CabinTile cabinTile = new CabinTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, CabinInhabitants.NONE, true, Color.RED,2, 1);
        cabinTile.removePinkAdaptors();
        assertEquals(1, cabinTile.getPurple());
        cabinTile.removeOrangeAdaptors();
        assertEquals(0, cabinTile.getOrange());
    }

    @Test
    void testIsMainCapsule() {
        CabinTile cabinTile = new CabinTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, CabinInhabitants.NONE, true, Color.RED,2, 1);
        assertTrue(cabinTile.isMainCapsule());
    }
}