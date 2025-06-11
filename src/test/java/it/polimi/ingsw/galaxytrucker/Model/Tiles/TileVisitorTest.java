package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileVisitorTest {
    @Test
    void testBatteryTileVisitor() {
        BatteryTileVisitor visitor = new BatteryTileVisitor();
        BatteryTile batteryTile = new BatteryTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, 5, 3);
        batteryTile.accept(visitor);

        List<BatteryTile> collectedTiles = visitor.getList();
        assertEquals(1, collectedTiles.size());
        assertEquals(batteryTile, collectedTiles.get(0));
    }

    @Test
    void testBioadaptorTileVisitor() {
        BioadaptorTileVisitor visitor = new BioadaptorTileVisitor();
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, AlienColor.ORANGE);
        bioadaptorTile.accept(visitor);

        List<BioadaptorTile> collectedTiles = visitor.getList();
        assertEquals(1, collectedTiles.size());
        assertEquals(bioadaptorTile, collectedTiles.get(0));
    }

    @Test
    void testCabinTileVisitor() {
        CabinTileVisitor visitor = new CabinTileVisitor();
        CabinTile cabinTile = new CabinTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, CabinInhabitants.NONE, true, Color.RED,2, 1);
        cabinTile.accept(visitor);

        List<CabinTile> collectedTiles = visitor.getList();
        assertEquals(1, collectedTiles.size());
        assertEquals(cabinTile, collectedTiles.get(0));
    }
    @Test
    void testCannonTileVisitor() {
        CannonTileVisitor visitor = new CannonTileVisitor();
        CannonTile cannonTile = new CannonTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, true, false);
        cannonTile.accept(visitor);

        List<CannonTile> collectedTiles = visitor.getList();
        assertEquals(1, collectedTiles.size());
        assertEquals(cannonTile, collectedTiles.get(0));
    }

    @Test
    void testCargoTileVisitor() {
        CargoTileVisitor visitor = new CargoTileVisitor();
        CargoTile cargoTile = new CargoTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, 4, true, List.of(1, 2));
        cargoTile.accept(visitor);

        List<CargoTile> collectedTiles = visitor.getList();
        assertEquals(1, collectedTiles.size());
        assertEquals(cargoTile, collectedTiles.get(0));
    }

    @Test
    void testEngineTileVisitor() {
        EngineTileVisitor visitor = new EngineTileVisitor();
        EngineTile engineTile = new EngineTile(true, false, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE);
        engineTile.accept(visitor);

        List<EngineTile> collectedTiles = visitor.getList();
        assertEquals(1, collectedTiles.size());
        assertEquals(engineTile, collectedTiles.get(0));
    }
    @Test
    void testShieldTileVisitor() {
        ShieldTileVisitor visitor = new ShieldTileVisitor();
        ShieldTile shieldTile = new ShieldTile(ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.NONE, ShieldOrientation.NORTHWEST, false);
        shieldTile.accept(visitor);

        List<ShieldTile> collectedTiles = visitor.getList();
        assertEquals(1, collectedTiles.size());
        assertEquals(shieldTile, collectedTiles.get(0));
    }

}