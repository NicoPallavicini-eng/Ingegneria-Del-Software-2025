package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CargoTileTest {

    @Test
    void testSetAndGetTileContent() {
        List<Integer> content = new ArrayList<>();
        CargoTile cargoTile = new CargoTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, 3, true, content);
        cargoTile.setTileContent(1);
        assertEquals(1, cargoTile.getTileContent().size());
    }

    @Test
    void testRemoveBlock() {
        List<Integer> content = new ArrayList<>();
        content.add(1);
        CargoTile cargoTile = new CargoTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, 3, true, content);
        cargoTile.removeBlock(1);
        assertTrue(cargoTile.getTileContent().isEmpty());
    }

    @Test
    void testFitsRed() {
        CargoTile cargoTile = new CargoTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, 3, true, new ArrayList<>());
        assertTrue(cargoTile.fitsRed());
    }
}