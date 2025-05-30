package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatteryTileTest {

    @Test
    void testRemoveBattery() {
        BatteryTile batteryTile = new BatteryTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, 5, 3);
        int remaining = batteryTile.removeBattery(2);
        assertEquals(1, remaining);
    }

    @Test
    void testGetSlotsNumber() {
        BatteryTile batteryTile = new BatteryTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, 5, 3);
        assertEquals(5, batteryTile.getSlotsNumber());
    }

    @Test
    void testSetAndGetSlotsFilled() {
        BatteryTile batteryTile = new BatteryTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, 5, 3);
        batteryTile.setSlotsFilled(4);
        assertEquals(4, batteryTile.getSlotsFilled());
    }
}