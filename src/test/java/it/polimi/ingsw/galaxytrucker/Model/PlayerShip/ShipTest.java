package it.polimi.ingsw.galaxytrucker.Model.PlayerShip;

import it.polimi.ingsw.galaxytrucker.JsonCardParsing;
import it.polimi.ingsw.galaxytrucker.JsonParsing;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class ShipTest {
    JsonParsing jsonParser = new JsonParsing();
    List<Tile> tiles = jsonParser.getCompleteList();
    Ship ship = new Ship(Color.RED);
    Random random = new Random();

    @Test
    void getLastPlacedTile() {
        ship.setLastPlacedTile(tiles.get(0));
        assertEquals(tiles.get(0), ship.getLastPlacedTile());
//        Tile tile;
//        for(int i=0;i<jsonParser.getCompleteList().size();i++){
//            tile = jsonParser.getBatteries().get(i);
//            ship.setTileOnFloorPlan(2,2,tile);
//            assertEquals(tile,ship.getLastPlacedTile());
//        }
    }

    @Test
    void setLastPlacedTile() {
        for(int i=0;i<jsonParser.getCompleteList().size();i++) {
            ship.setLastPlacedTile(tiles.get(0));
            assertEquals(tiles.get(0), ship.getLastPlacedTile());
        }
    }

    @Test
    void getTileInHand() {
        for(int i=0;i<jsonParser.getCompleteList().size();i++) {
            ship.setTileInHand(tiles.get(0));
            assertEquals(tiles.get(0), ship.getTileInHand());
        }
    }

    @Test
    void setTileInHand() {
        for(int i=0;i<jsonParser.getCompleteList().size();i++) {
            ship.setTileInHand(tiles.get(0));
            assertEquals(tiles.get(0), ship.getTileInHand());
        }
    }

    @Test
    void getRowMax() {
        assertEquals(5,ship.getRowMax());
    }

    @Test
    void getColMax() {
        assertEquals(7,ship.getColMax());
    }

    @Test
    void getColor() {
        assertEquals(Color.RED,ship.getColor());
    }

    @Test
    void getFloorplanArrayList() {
        assertNotNull(ship.getFloorplanArrayList());
    }

    @Test
    void findTileOnFloorplanRow() {
        Random random = new Random();
        for(int i=0;i<jsonParser.getCompleteList().size();i++) {
            int row = random.nextInt(ship.getRowMax());
            int col = random.nextInt(ship.getColMax());
            ship.setTileOnFloorPlan(row,col,tiles.get(i));
            assertEquals(row,ship.findTileOnFloorplanRow(tiles.get(i)));
        }
    }

    @Test
    void findTileOnFloorPlanColumn() {
        Random random = new Random();
        for(int i=0;i<jsonParser.getCompleteList().size();i++) {
            int row = random.nextInt(ship.getRowMax());
            int col = random.nextInt(ship.getColMax());
            ship.setTileOnFloorPlan(row,col,tiles.get(i));
            assertEquals(col,ship.findTileOnFloorPlanColumn(tiles.get(i)));
        }
    }

    @Test
    void getAdiacentTiles() {
        Random random = new Random();
        for(int i=0;i<jsonParser.getCompleteList().size();i++) {
            int row = random.nextInt(ship.getRowMax());
            int col = random.nextInt(ship.getColMax());
            ship.setTileOnFloorPlan(row,col,tiles.get(i));
            Optional<Tile> optional = ship.getTileOnFloorPlan(row,col);
            if(optional.isPresent()) {
                Tile tile = optional.get();
                assertNotNull(ship.getAdiacentTiles(tile));
                assertEquals(4,ship.getAdiacentTiles(tile).size());
            }

        }
        ArrayList<Tile> example = new ArrayList<>();
        example.add(null);
        example.add(null);
        if(ship.getTileOnFloorPlan(1,0).isPresent()) {
            example.add(ship.getTileOnFloorPlan(1,0).get());
        }
        if(ship.getTileOnFloorPlan(0,1).isPresent()) {
            example.add(ship.getTileOnFloorPlan(0,1).get());
        }
        if(ship.getTileOnFloorPlan(0,0).isPresent()) {
            assertEquals(ship.getAdiacentTiles(ship.getTileOnFloorPlan(0,0).get()),example);
        }

        for(int i=0;i<jsonParser.getCompleteList().size();i++) {
            int row = 1 + random.nextInt(ship.getRowMax()-2);
            int col = 1 + random.nextInt(ship.getColMax()-2);
            Optional<Tile> optional = ship.getTileOnFloorPlan(row,col);
            if(optional.isPresent()){
                example = new ArrayList<>();
                if(ship.getTileOnFloorPlan(row-1,col).isPresent()) {
                    example.add(ship.getTileOnFloorPlan(row-1,col).get());
                }else{
                    example.add(null);
                }
                if(ship.getTileOnFloorPlan(row,col-1).isPresent()) {
                    example.add(ship.getTileOnFloorPlan(row,col-1).get());
                }else{
                    example.add(null);
                }
                if(ship.getTileOnFloorPlan(row+1,col).isPresent()) {
                    example.add(ship.getTileOnFloorPlan(row+1,col).get());
                }else{
                    example.add(null);
                }
                if(ship.getTileOnFloorPlan(row,col+1).isPresent()) {
                    example.add(ship.getTileOnFloorPlan(row,col+1).get());
                }else{
                    example.add(null);
                }
                assertEquals(ship.getAdiacentTiles(ship.getTileOnFloorPlan(row,col).get()),example);
            }

        }

    }

    @Test
    void checkFloorPlanConnection() {
//        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.RED,0,0);
//        CannonTile cannon = new CannonTile(ConnectorType.NONE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.SINGLE,false,true);
//        EngineTile engine = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
//
//        ship.setTileOnFloorPlan(2,3,centralCabin);
//        ship.setTileOnFloorPlan(1,3,cannon);
//        ship.setTileOnFloorPlan(2,2,engine);
//
//        assertEquals(false,ship.checkFloorPlanConnection());

        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.BLUE,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        //assertEquals(true,ship.checkFloorPlanConnection());

        ship.setTileOnFloorPlan(3,2,null);

        assertEquals(true,ship.checkFloorPlanConnection());

        ship.setTileOnFloorPlan(3,4,null);

        assertEquals(false,ship.checkFloorPlanConnection());

        ship.setTileOnFloorPlan(2,3,null);

        assertEquals(false,ship.checkFloorPlanConnection());
    }
    //@Test
//    void checkEngineOrCanon(){
//        assertEquals(false,ship.checkEngineOrCannon(ConnectorType.DOUBLE,ConnectorType.DOUBLE));
//        assertEquals(true,ship.checkEngineOrCannon(ConnectorType.SINGLE,ConnectorType.DOUBLE));
//        assertEquals(true,ship.checkEngineOrCannon(ConnectorType.UNIVERSAL,ConnectorType.ENGINE_SINGLE));
//        assertEquals(true,ship.checkEngineOrCannon(ConnectorType.NONE,ConnectorType.ENGINE_DOUBLE));
//
//    }
    @Test
    void checkFloorplanConnection() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.BLUE,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        assertEquals(true,ship.checkFloorPlanConnection());

//        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.RED,0,0);
//        CannonTile cannon = new CannonTile(ConnectorType.NONE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.SINGLE,false,true);
//        EngineTile engine = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
//
//        ship.setTileOnFloorPlan(2,3,centralCabin);
//        ship.setTileOnFloorPlan(1,3,cannon);
//        ship.setTileOnFloorPlan(2,2,engine);
//
//        ArrayList<Tile> adiacentTiles = new ArrayList<>();
//        adiacentTiles.add(centralCabin);
//        adiacentTiles.add(cannon);
//        //adiacentTiles.add(engine);
//
//
//        assertEquals(adiacentTiles,ship.checkFloorplanConnection());
    }

    @Test
    void getNonValidTileList() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.BLUE,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        assertEquals(0,ship.getNonValidTileList().size());

        ship.setTileOnFloorPlan(3,2,null);

        assertEquals(0,ship.getNonValidTileList().size());

        ship.setTileOnFloorPlan(3,4,null);

        assertEquals(1,ship.getNonValidTileList().size());

        ship.setTileOnFloorPlan(2,3,null);

        assertEquals(7,ship.getNonValidTileList().size());
    }

    @Test
    void isShipBroken2(){
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.BLUE,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        assertEquals(false,ship.isShipBroken());

        ship.setTileOnFloorPlan(3,2,null);

        assertEquals(false,ship.isShipBroken());

        ship.setTileOnFloorPlan(3,4,null);

        assertEquals(true,ship.isShipBroken());

        ship.setTileOnFloorPlan(2,3,null);

        assertEquals(true,ship.isShipBroken());
    }

    @Test
    void isShipBroken() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.RED,0,0);
        CannonTile cannon = new CannonTile(ConnectorType.NONE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.SINGLE,false,true);
        EngineTile engine = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.ENGINE_SINGLE, ConnectorType.SINGLE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon);
        ship.setTileOnFloorPlan(2,2,engine);

        assertEquals(true,ship.isShipBroken());

        Random random = new Random();
        for(int i=0;i<jsonParser.getCompleteList().size();i++) {
            int row = random.nextInt(ship.getRowMax());
            int col = random.nextInt(ship.getColMax());
            ship.setTileOnFloorPlan(row,col,tiles.get(i));
            assertEquals(true,ship.isShipBroken());
        }
    }

    @Test
    void getShipPiecesList() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.RED,0,0);
        CannonTile cannon = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.SINGLE,false,true);
        EngineTile engine = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon);
        ship.setTileOnFloorPlan(2,2,engine);

        ArrayList<ArrayList<Tile>> setTiles = new ArrayList<>();
        ArrayList<Tile> adiacentTiles = new ArrayList<>();

        adiacentTiles.add(centralCabin);
        adiacentTiles.add(cannon);
        setTiles.add(adiacentTiles);

        ArrayList<Tile> adiacentTiles2 = new ArrayList<>();

        adiacentTiles2.add(engine);
        setTiles.add(adiacentTiles2);

        assertEquals(setTiles,ship.getShipPiecesList());
    }
    @Test
    void testShipPieceList(){
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.BLUE,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        assertEquals(1,ship.getShipPiecesList().size());

        ship.setTileOnFloorPlan(3,2,null);

        assertEquals(1,ship.getShipPiecesList().size());

        ship.setTileOnFloorPlan(3,4,null);

        assertEquals(2,ship.getShipPiecesList().size());

        ship.setTileOnFloorPlan(2,3,null);

        assertEquals(5,ship.getShipPiecesList().size());
    }
    @Test
    void testCheckFloorplanConnection() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.RED,0,0);
        CannonTile cannon = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.SINGLE,false,true);
        EngineTile engine = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon);
        ship.setTileOnFloorPlan(2,2,engine);

        ArrayList<Tile> adiacentTiles = new ArrayList<>();

        adiacentTiles.add(centralCabin);
        adiacentTiles.add(cannon);
        //adiacentTiles.add(engine);


        assertEquals(adiacentTiles,ship.checkFloorplanConnection(centralCabin));
    }
    @Test
    void testCheckFloorplanConnection2() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.BLUE,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        assertEquals(12,ship.checkFloorplanConnection(centralCabin).size());

        ship.setTileOnFloorPlan(3,2,null);

        assertEquals(11,ship.checkFloorplanConnection(centralCabin).size());

        ship.setTileOnFloorPlan(3,4,null);

        assertEquals(9,ship.checkFloorplanConnection(centralCabin).size());

        ship.setTileOnFloorPlan(2,3,null);

        assertEquals(2,ship.checkFloorplanConnection(cargoTile).size());
    }
    @Test
    void getCargoFromCards() {
        assertNotNull(ship.getCargoFromCards());
    }

    @Test
    void addBlocks() {
        ArrayList<Integer> blocks = new ArrayList<>();
        blocks.add(1);
        ship.addBlocks(blocks);
        assertEquals(blocks,ship.getCargoFromCards());
    }

    @Test
    void setCredits() {
        ship.setCredits(5);
        assertEquals(5, ship.getCredits());
    }

    @Test
    void getCredits() {
        assertEquals(0, ship.getCredits());
    }

    @Test
    void getTravelDays() {
        assertEquals(null, ship.getTravelDays());
        ship.setTravelDays(10);
        assertEquals(10, ship.getTravelDays());
    }

    @Test
    void setTravelDays() {
        ship.setTravelDays(5);
        assertEquals(5, ship.getTravelDays());
        ship.setTravelDays(10);
        assertEquals(10, ship.getTravelDays());
        ship.setTravelDays(null);
        assertNull(ship.getTravelDays());
        ship.setTravelDays(-1);
        assertEquals(-1, ship.getTravelDays());
    }

    @Test
    void getTileOnFloorPlan() {
        Tile tile = new EngineTile(true,false,ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.DOUBLE,ConnectorType.SINGLE);
        ArrayList<ArrayList<Tile>> floorplanArrayList= new ArrayList<>();
        int col_max = ship.getColMax();
        int row_max = ship.getRowMax();
        for(int i=0;i<row_max;i++){
            floorplanArrayList.add(new ArrayList<>());
            for(int j=0;j<col_max;j++){
                if((i==2&&j==3)||(i==1&&j==4)){
                    floorplanArrayList.get(i).add(tile);
                }else{
                    floorplanArrayList.get(i).add(null);
                }

            }
        }
        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(1,4,tile);
        assertEquals(tile,ship.getTileOnFloorPlan(2,3).get());
        assertEquals(false,ship.getTileOnFloorPlan(2,4).isPresent());
        assertEquals(tile,ship.getTileOnFloorPlan(1,4).get());
    }

    @Test
    void setTileOnFloorPlan() {
        Tile tile = new EngineTile(true,false,ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.DOUBLE,ConnectorType.SINGLE);
        ArrayList<ArrayList<Tile>> floorplanArrayList= new ArrayList<>();
        int col_max = ship.getColMax();
        int row_max = ship.getRowMax();
        for(int i=0;i<row_max;i++){
            floorplanArrayList.add(new ArrayList<>());
            for(int j=0;j<col_max;j++){
                if(i==2&&j==3){
                    floorplanArrayList.get(i).add(tile);
                }else{
                    floorplanArrayList.get(i).add(null);
                }

            }
        }
        ship.setTileOnFloorPlan(2,3,tile);

        assertEquals(floorplanArrayList,ship.getFloorplanArrayList());
        ship.setTileOnFloorPlan(2,3,null);

    }

    @Test
    void removeTileOnFloorPlan() {
        Ship ship1 = new Ship(Color.BLUE);
        Tile tile = new EngineTile(true,false,ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.DOUBLE,ConnectorType.SINGLE);
        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(1,4,tile);
        ship1.setTileOnFloorPlan(1,4,tile);
        ship.removeTileOnFloorPlan(2,3);
        assertEquals(ship.getFloorplanArrayList(),ship1.getFloorplanArrayList());
    }

    @Test
    void addLostTiles() {
        ship.addLostTiles(5);
        assertEquals(5,ship.getLostTiles());
    }

    @Test
    void getLostTiles() {
        assertEquals(0,ship.getLostTiles());
    }

    @Test
    void getPurpleAlien() {
        assertEquals(false,ship.getPurpleAlien());
    }

    @Test
    void getOrangeAlien() {
        assertEquals(false,ship.getOrangeAlien());
    }

    @Test
    void setPurpleAlien() {
        ship.setPurpleAlien(true);
        assertEquals(true,ship.getPurpleAlien());
    }

    @Test
    void setOrangeAlien() {
        ship.setOrangeAlien(true);
        assertEquals(true,ship.getOrangeAlien());
    }

    @Test
    void getReservedTiles() {
        assertNotNull(ship.getReservedTiles());
    }

    @Test
    void addReservedTile() {
        ArrayList<Tile> reservedTiles= new ArrayList<>();
        CannonTile cannon = new CannonTile(ConnectorType.NONE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.CANNON_SINGLE, true,true);
        CannonTile cannon1 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,false,true);
        reservedTiles.add(cannon);
        reservedTiles.add(cannon1);
        ship.addReservedTile(cannon);
        ship.addReservedTile(cannon1);
        assertEquals(reservedTiles,ship.getReservedTiles());
    }

    @Test
    void moveReservedToDiscard() {
        ArrayList<Tile> reservedTiles= new ArrayList<>();
        CannonTile cannon = new CannonTile(ConnectorType.NONE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.CANNON_SINGLE, true,true);
        CannonTile cannon1 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.NONE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.CANNON_SINGLE, true,true);
        CannonTile cannon3 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, false,true);

        ship.addReservedTile(cannon);
        ship.addReservedTile(cannon1);
        ship.addReservedTile(cannon2);
        ship.addReservedTile(cannon3);

        ship.moveReservedToDiscard();

        assertEquals(4,ship.getLostTiles());

        assertEquals(new ArrayList<>(),ship.getReservedTiles());

    }

    @Test
    void moveReservedToBoard() {
    }

    @Test
    void getExposedConnectors() {
        assertEquals(0,ship.getExposedConnectors());
    }

    @Test
    void getFirepower() {
        CannonTile cannon = new CannonTile(ConnectorType.NONE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.CANNON_SINGLE, true,true);
        CannonTile cannon1 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.UNIVERSAL, true,true);

        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,1,0);
        CabinTile cabin1 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ONE,true,Color.RED,0,0);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,cabin);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);

        ship.setTileOnFloorPlan(3,2,cannon1);
        ship.setTileOnFloorPlan(3,5,cannon2);
        ship.setTileOnFloorPlan(3,1,cannon);

        assertEquals(2.5,ship.getFirepower());

        ship.setFirepower(cannon,false);
        assertEquals(1.5,ship.getFirepower());
    }

    @Test
    void setFirepower() {
        CannonTile cannon = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, true,false);
        ship.setFirepower(cannon,true);
        assertEquals(true,cannon.getActiveState());
    }

    @Test
    void getListOfFirepower() {
        CannonTile cannon = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, true,false);
        CannonTile cannon1 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, true,true);

        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,1,0);
        CabinTile cabin1 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ONE,true,Color.NONE,0,0);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,cabin);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);

        ship.setTileOnFloorPlan(3,2,cannon1);
        ship.setTileOnFloorPlan(3,5,cannon2);
        ship.setTileOnFloorPlan(3,1,cannon);

        ArrayList<CannonTile> floorplanArrayList= new ArrayList<>();
        floorplanArrayList.add(cannon);
        floorplanArrayList.add(cannon1);
        floorplanArrayList.add(cannon2);

        assertEquals(floorplanArrayList,ship.getListOfFirepower());
    }

    @Test
    void getListOfDoubleFirepower() {
        CannonTile cannon = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, true,false);
        CannonTile cannon1 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL, true,true);

        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,1,0);
        CabinTile cabin1 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ONE,true,Color.RED,0,0);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,cabin);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);

        ship.setTileOnFloorPlan(3,2,cannon1);
        ship.setTileOnFloorPlan(3,5,cannon2);
        ship.setTileOnFloorPlan(3,1,cannon);

        ArrayList<CannonTile> floorplanArrayList= new ArrayList<>();
        floorplanArrayList.add(cannon);
        floorplanArrayList.add(cannon2);
        //floorplanArrayList.add(cannon1);

        assertEquals(floorplanArrayList,ship.getListOfDoubleFirepower());
    }

    @Test
    void getEnginePower() {
        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,1,0);
        CabinTile cabin1 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ONE,true,Color.RED,0,0);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,cabin);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);


        EngineTile u2 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        EngineTile u1 = new EngineTile(true,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        EngineTile u = new EngineTile(true,false,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);

        ship.setTileOnFloorPlan(3,2,u1);
        ship.setTileOnFloorPlan(3,5,u2);
        ship.setTileOnFloorPlan(3,1,u);

        ship.setEnginePower(u,true);

        assertEquals(5,ship.getEnginePower());
    }

    @Test
    void setEnginePower() {
        EngineTile tile2 = new EngineTile(true,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        ship.setEnginePower(tile2,false);
        assertEquals(false,tile2.getActiveState());
        ship.setEnginePower(tile2,true);
        assertEquals(true,tile2.getActiveState());

    }

    @Test
    void getListOfEngine() {
        EngineTile tile2 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        EngineTile tile1 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        EngineTile tile = new EngineTile(true,false,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        ship.setTileOnFloorPlan(2,4,tile1);
        ship.setTileOnFloorPlan(2,3,tile2);
        ship.setTileOnFloorPlan(2,2,tile);
        ArrayList<EngineTile> floorplanArrayList= new ArrayList<>();
        floorplanArrayList.add(tile);
        floorplanArrayList.add(tile2);
        floorplanArrayList.add(tile1);
        assertEquals(floorplanArrayList,ship.getListOfEngine());
    }

    @Test
    void getListOfDoubleEnginePower() {
        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,1,0);
        CabinTile cabin1 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ONE,true,Color.RED,0,0);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,cabin);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);


        EngineTile u2 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        EngineTile u1 = new EngineTile(true,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        EngineTile u = new EngineTile(true,false,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);

        ship.setEnginePower(u,true);

        ship.setTileOnFloorPlan(3,2,u1);
        ship.setTileOnFloorPlan(3,5,u2);
        ship.setTileOnFloorPlan(3,1,u);

        ArrayList<EngineTile> floorplanArrayList= new ArrayList<>();

        floorplanArrayList.add(u);
        floorplanArrayList.add(u1);

        assertEquals(floorplanArrayList,ship.getListOfDoubleEnginePower());


    }

    @Test
    void setShield() {
        ShieldTile shield = new ShieldTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,ShieldOrientation.NORTHEAST,false);
        ship.setShield(shield,true);
        assertEquals(true,shield.getActiveState());

    }

    @Test
    void getListOfShield() {
        ShieldTile shield = new ShieldTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,ShieldOrientation.NORTHEAST,true);
        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,0,0);
        CabinTile cabin1 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ONE,true,Color.RED,0,0);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        ShieldTile shield1 = new ShieldTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,ShieldOrientation.NORTHEAST,false);

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,cabin);

        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(3,5,shield);
        ship.setTileOnFloorPlan(3,1,shield1);

        ArrayList<ShieldTile> floorplanArrayList = new ArrayList<>();
        floorplanArrayList.add(shield1);
        floorplanArrayList.add(shield);

        assertEquals(floorplanArrayList,ship.getListOfShield());
    }

    @Test
    void getListOfShieldOrientation() {

    }

    @Test
    void getListOfActiveShieldOrientation() {
        ShieldTile shield = new ShieldTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,ShieldOrientation.NORTHEAST,true);
        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,0,0);
        CabinTile cabin1 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ONE,true,Color.RED,0,0);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        ShieldTile shield1 = new ShieldTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,ShieldOrientation.NORTHEAST,false);

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,cabin);

        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(3,5,shield);
        ship.setTileOnFloorPlan(3,1,shield1);

        ship.setShield(shield1,true);

        ArrayList<ShieldOrientation> floorplanArrayList = new ArrayList<>();
        floorplanArrayList.add(shield1.getOrientation());
        floorplanArrayList.add(shield.getOrientation());

        assertEquals(floorplanArrayList,ship.getListOfActiveShieldOrientation());
    }

    @Test
    void getListOfCabin() {
        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,0,0);
        CabinTile cabin1 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ONE,true,Color.RED,0,0);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,cabin);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(3,5,cargo2);
        ship.setTileOnFloorPlan(3,1,cargo3);

        ArrayList<CabinTile> floorplanArrayList= new ArrayList<>();

        floorplanArrayList.add(cabin1);
        floorplanArrayList.add(cabin);

        assertEquals(floorplanArrayList,ship.getListOfCabin());
    }

    @Test
    void getListOfBattery() {
        BatteryTile battery = new BatteryTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,3);
        BatteryTile battery2 = new BatteryTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,3);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,battery);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);
        ship.setTileOnFloorPlan(3,2,battery2);
        ship.setTileOnFloorPlan(3,5,cargo2);
        ship.setTileOnFloorPlan(3,1,cargo3);

        ArrayList<BatteryTile> floorplanArrayList= new ArrayList<>();

        floorplanArrayList.add(battery2);
        floorplanArrayList.add(battery);

        assertEquals(floorplanArrayList,ship.getListOfBattery());
    }

    @Test
    void getBatteries() {
        BatteryTile battery = new BatteryTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,3);
        BatteryTile battery2 = new BatteryTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,1);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,battery);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);
        ship.setTileOnFloorPlan(3,2,battery2);
        ship.setTileOnFloorPlan(3,5,cargo2);
        ship.setTileOnFloorPlan(3,1,cargo3);

        assertEquals(4,ship.getBatteries());
    }

    @Test
    void getNumberOfInhabitants() {
        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,0,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.TWO,false,Color.NONE,0,0);
        CabinTile cabin3 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,false,Color.NONE,0,0);

        ship.setTileOnFloorPlan(2,5,cabin);
        ship.setTileOnFloorPlan(0,2,cabin2);
        ship.setTileOnFloorPlan(1,2,cabin3);

        assertEquals(3,ship.getNumberOfInhabitants());
    }

    @Test
    void getListOfCargo() {
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,tile3);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);
        ship.setTileOnFloorPlan(3,2,cargo1);
        ship.setTileOnFloorPlan(3,5,cargo2);
        ship.setTileOnFloorPlan(3,1,cargo3);

        ArrayList<CargoTile> floorplanArrayList= new ArrayList<>();

        floorplanArrayList.add(cargo4);
        floorplanArrayList.add(cargo);
        floorplanArrayList.add(cargo3);
        floorplanArrayList.add(cargo1);
        floorplanArrayList.add(cargo2);

        assertEquals(floorplanArrayList,ship.getListOfCargo());
    }

    @Test
    void getListOfPurpleAdaptors() {
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,tile3);
        ship.setTileOnFloorPlan(2,2,tile4);

        ArrayList<BioadaptorTile> floorplanArrayList= new ArrayList<>();

        floorplanArrayList.add(tile2);
        floorplanArrayList.add(tile);
        floorplanArrayList.add(tile3);

        assertEquals(floorplanArrayList,ship.getListOfPurpleAdaptors());
    }

    @Test
    void getListOfOrangeAdaptors() {
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,tile3);
        ship.setTileOnFloorPlan(2,2,tile4);

        ArrayList<BioadaptorTile> floorplanArrayList= new ArrayList<>();

        floorplanArrayList.add(tile1);


        assertEquals(floorplanArrayList,ship.getListOfOrangeAdaptors());
    }

    @Test
    void getListOfAdaptors() {
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,tile3);
        ship.setTileOnFloorPlan(2,2,tile4);

        ArrayList<BioadaptorTile> floorplanArrayList= new ArrayList<>();

        floorplanArrayList.add(tile1);
        floorplanArrayList.add(tile2);
        floorplanArrayList.add(tile);
        floorplanArrayList.add(tile3);

        assertEquals(floorplanArrayList,ship.getListOfAdaptors());
    }

    @Test
    void getRowListTiles() {
        Tile tile2 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        Tile tile1 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        Tile tile = new EngineTile(true,false,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        ship.setTileOnFloorPlan(2,4,tile1);
        ship.setTileOnFloorPlan(2,3,tile2);
        ship.setTileOnFloorPlan(2,2,tile);
        ArrayList<Tile> floorplanArrayList= new ArrayList<>();
        for(int i=0;i<ship.getColMax();i++){
            if(i==2){
                floorplanArrayList.add(tile);
            } else if (i==3) {
                floorplanArrayList.add(tile2);
            } else if (i==4) {
                floorplanArrayList.add(tile1);
            }else{
                //floorplanArrayList.add(null);
            }
        }
        assertEquals(floorplanArrayList,ship.getRowListTiles(2));
    }

    @Test
    void getColumnListTiles() {
        Tile tile2 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        Tile tile1 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        Tile tile = new EngineTile(true,false,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        ship.setTileOnFloorPlan(0,4,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(2,4,tile);
        ArrayList<Tile> floorplanArrayList= new ArrayList<>();
        for(int i=0;i<ship.getRowMax();i++){
            if(i==0){
                floorplanArrayList.add(tile1);
            } else if (i==1) {
                floorplanArrayList.add(tile2);
            } else if (i==2) {
                floorplanArrayList.add(tile);
            }else{
                //floorplanArrayList.add(null);
            }
        }
        assertEquals(floorplanArrayList,ship.getColumnListTiles(4));
    }

    @Test
    void updateCabinTiles() {

    }

    @Test
    void disactivateEverything() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,true,Color.BLUE,0,0);
        List<Integer> listBlock = new ArrayList<>(Arrays.asList(1, 2, 3));
        centralCabin.setAlienColor(AlienColor.ORANGE);
        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,listBlock);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        assertEquals(1.5,ship.getFirepower());
        assertEquals(5,ship.getEnginePower());
        ship.disactivateEverything();
        assertEquals(1.5,ship.getFirepower());
        assertEquals(3,ship.getEnginePower());

    }

    @Test
    void ejectAll() {
        CabinTile cabin = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,false,Color.NONE,0,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.TWO,false,Color.NONE,0,0);
        CabinTile cabin3 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,false,Color.NONE,0,0);

        ship.setTileOnFloorPlan(2,5,cabin);
        ship.setTileOnFloorPlan(0,2,cabin2);
        ship.setTileOnFloorPlan(1,2,cabin3);
        ship.ejectAll();
        assertEquals(0,ship.getNumberOfInhabitants());
    }

    @Test
    void removeAllCargo() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.BLUE,0,0);
        List<Integer> listBlock = new ArrayList<>(Arrays.asList(1, 2, 3));
        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,listBlock);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        assertEquals(listBlock,cargoTile.getTileContent());

        ship.removeAllCargo();

        assertEquals(0,cargoTile.getTileContent().size());

    }

    @Test
    void removeAllBatteries() {
        BatteryTile battery = new BatteryTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,3);
        BatteryTile battery2 = new BatteryTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,1);
        BioadaptorTile tile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile1 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.ORANGE);
        BioadaptorTile tile2 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        BioadaptorTile tile3 = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,AlienColor.PURPLE);
        Tile tile4 = new EngineTile(false,true,ConnectorType.SINGLE,ConnectorType.SINGLE,ConnectorType.ENGINE_SINGLE,ConnectorType.SINGLE);
        CargoTile cargo = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo1 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo2 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,2,false,new ArrayList<>());
        CargoTile cargo3 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,3,true,new ArrayList<>());
        CargoTile cargo4 = new CargoTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.UNIVERSAL,4,false,new ArrayList<>());

        ship.setTileOnFloorPlan(2,3,tile);
        ship.setTileOnFloorPlan(0,5,tile1);
        ship.setTileOnFloorPlan(1,4,tile2);
        ship.setTileOnFloorPlan(4,6,battery);
        ship.setTileOnFloorPlan(0,2,cargo4);
        ship.setTileOnFloorPlan(2,2,cargo);
        ship.setTileOnFloorPlan(3,2,battery2);
        ship.setTileOnFloorPlan(3,5,cargo2);
        ship.setTileOnFloorPlan(3,1,cargo3);

        ship.removeAllBatteries();

        assertEquals(0,ship.getBatteries());

    }

    @Test
    void removeFirstTile() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.NONE,true,Color.BLUE,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        ship.removeFirstTile(3, Direction.NORTH);
        assertEquals(0.5,ship.getFirepower());
        ship.removeFirstTile(4, Direction.SOUTH);
        assertEquals(0,ship.getEnginePower());

    }

    @Test
    void fill() {
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,CabinInhabitants.ALIEN,true,Color.BLUE,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.NONE,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.NONE,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        BatteryTile batteryTile = new BatteryTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,2);
        ShieldTile shieldTile = new ShieldTile(ConnectorType.NONE,ConnectorType.DOUBLE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ShieldOrientation.NORTHWEST,false);
        Tile tile = new Tile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.DOUBLE,ConnectorType.NONE);

        ship.setTileOnFloorPlan(2,3,centralCabin);
        ship.setTileOnFloorPlan(1,3,cannon1);
        ship.setTileOnFloorPlan(3,3,engine1);
        ship.setTileOnFloorPlan(2,2,bioadaptorTile);
        ship.setTileOnFloorPlan(1,2,cargoTile);
        ship.setTileOnFloorPlan(3,2,cabin1);
        ship.setTileOnFloorPlan(2,4,cabin2);
        ship.setTileOnFloorPlan(1,4,shieldTile);
        ship.setTileOnFloorPlan(3,4,tile);
        ship.setTileOnFloorPlan(4,4,engine2);
        ship.setTileOnFloorPlan(2,5,cannon2);
        ship.setTileOnFloorPlan(1,5,batteryTile);

        ship.fill();
        assertEquals(5,ship.getNumberOfInhabitants());
    }
}