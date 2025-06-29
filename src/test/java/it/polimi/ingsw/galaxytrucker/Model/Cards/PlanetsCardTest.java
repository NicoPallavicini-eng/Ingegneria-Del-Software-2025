package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.EpidemicState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.PlanetsState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlanetsCardTest {
    List <Planet> planets = new ArrayList<>();

    List<Integer> earthBlocks = new ArrayList<>();
    List <Integer> marsBlocks = new ArrayList<>();
    List <Integer> venusBlocks = new ArrayList<>();
    List <Integer> mercuryBlocks = new ArrayList<>();

    Integer b1earth = 4;
    Integer b2earth = 3;
    Integer b3earth = 3;

    Integer b1mars = 4;
    Integer b2mars = 2;
    Integer b3mars = 1;

    Integer b1venus = 3;
    Integer b2venus = 2;
    Integer b3venus = 1;

    Integer b1mercury = 2;
    Integer b2mercury = 1;
    Integer b3mercury = 1;


    PlanetsCard planetCard = new PlanetsCard(true, true, planets, 4);

    @Test
    void PlanetsProcessTest() {
        //Player p = new Player("IP", "nick", Color.RED);
        //Ship s = p.getShip();
        //s.setTravelDays(20);

        earthBlocks.add(b1earth);
        earthBlocks.add(b2earth);
        earthBlocks.add(b3earth);

        marsBlocks.add(b1mars);
        marsBlocks.add(b2mars);
        marsBlocks.add(b3mars);

        venusBlocks.add(b1venus);
        venusBlocks.add(b2venus);
        venusBlocks.add(b3venus);

        mercuryBlocks.add(b1mercury);
        mercuryBlocks.add(b2mercury);
        mercuryBlocks.add(b3mercury);

        Planet earth = new Planet(earthBlocks);
        Planet mars = new Planet(marsBlocks);
        Planet venus = new Planet(venusBlocks);
        Planet mercury = new Planet(mercuryBlocks);

        planets.add(earth);
        planets.add(mars);
        planets.add(venus);
        planets.add(mercury);

        //p.setEngages(true);
        // input is Mars
        // p.setInputEngine(1); // will have to rename "setInput" (chooses planet here)

        // planetCard.process(p);

        //assertEquals(mars.getShipLanded(), s);
        //assertEquals(, marsBlocks);
        //assertEquals(s.getTravelDays(), 20 - planetCard.getDaysToLose());
    }

    @Test
    void planetCardTest(){
        List <Planet> planets = new ArrayList<>();

        List<Integer> earthBlocks = new ArrayList<>();
        List <Integer> marsBlocks = new ArrayList<>();
        List <Integer> venusBlocks = new ArrayList<>();
        List <Integer> mercuryBlocks = new ArrayList<>();

        earthBlocks.add(b1earth);
        earthBlocks.add(b2earth);
        earthBlocks.add(b3earth);

        marsBlocks.add(b1mars);
        marsBlocks.add(b2mars);
        marsBlocks.add(b3mars);

        venusBlocks.add(b1venus);
        venusBlocks.add(b2venus);
        venusBlocks.add(b3venus);

        mercuryBlocks.add(b1mercury);
        mercuryBlocks.add(b2mercury);
        mercuryBlocks.add(b3mercury);

        Planet earth = new Planet(earthBlocks);
        Planet mars = new Planet(marsBlocks);
        Planet venus = new Planet(venusBlocks);
        Planet mercury = new Planet(mercuryBlocks);

        planets.add(earth);
        planets.add(mars);
        planets.add(venus);
        planets.add(mercury);

        PlanetsCard planetCard = new PlanetsCard(true, true, planets, 4);

        Player p = new Player("IP", "nick", Color.RED);
        Player a = new Player("IP2", "nick2", Color.BLUE);
        Ship ship = p.getShip();
        Ship ship2 = a.getShip();

        Game game = new Game();
        PlanetsState state = new PlanetsState(game, planetCard);

        game.addPlayer(p);
        game.addPlayer(a);

        ship.setTravelDays(1);
        ship2.setTravelDays(0);

        a.setOnlineStatus(true);
        p.setOnlineStatus(true);

        game.updateListOfActivePlayers();
        game.sortListOfActivePlayers();

        //stardustState.init();
        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL, CabinInhabitants.TWO,true,Color.RED,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.TWO,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.TWO,false,Color.NONE,0,0);
        CannonTile cannon1 = new CannonTile(ConnectorType.CANNON_SINGLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.NONE,false,true);
        CannonTile cannon2 = new CannonTile(ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.CANNON_SINGLE,ConnectorType.NONE,false,true);
        EngineTile engine1 = new EngineTile(true,false,ConnectorType.DOUBLE,ConnectorType.ENGINE_DOUBLE,ConnectorType.NONE,ConnectorType.UNIVERSAL);
        EngineTile engine2 = new EngineTile(false,true,ConnectorType.DOUBLE,ConnectorType.ENGINE_SINGLE,ConnectorType.NONE,ConnectorType.NONE);
        BioadaptorTile bioadaptorTile = new BioadaptorTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,AlienColor.PURPLE);
        CargoTile cargoTile = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.NONE,2,false,null);
        CargoTile cargoTile1 = new CargoTile(ConnectorType.NONE,ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.DOUBLE,2,false,null);
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
        ship.setTileOnFloorPlan(3,5,cargoTile1);

        assertEquals(true,ship.checkFloorPlanConnection());

        CabinTile centralCabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL, CabinInhabitants.TWO,true,Color.BLUE,0,0);

        ship2.setTileOnFloorPlan(2,3,centralCabin1);

        state.init();

        state.handleEvent(new ChoosePlanetEvent(p,3));
        state.handleEvent(new ChoosePlanetEvent(a,2));

        state.handleEvent(new AddCargoEvent(p,1,2,2));
        state.handleEvent(new AddCargoEvent(p,1,2,1));
        state.handleEvent(new SwitchCargoEvent(p,1,2,3,5,2));

        List<Integer> blocks2 = new ArrayList<>();
        blocks2.add(2);
        assertEquals(blocks2,cargoTile1.getTileContent());

        state.handleEvent(new SwitchCargoEvent(p,1,2,3,5,1));

        state.handleEvent(new RemoveCargoEvent(p,3,5,2));

        state.handleEvent(new AddCargoEvent(p,3,5,1));

        List<Integer> blocks1 = new ArrayList<>();
        blocks1.add(1);
        blocks1.add(1);
        assertEquals(blocks1,cargoTile1.getTileContent());

        state.handleEvent(new DoneEvent(p));
        assertEquals(0, ship.getCargoFromCards().size());

        //state.handleEvent(new DoneEvent(a));
        assertEquals(-3,ship.getTravelDays());
    }
}