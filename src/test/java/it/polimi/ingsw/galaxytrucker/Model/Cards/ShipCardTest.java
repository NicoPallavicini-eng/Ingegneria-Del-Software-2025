package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.AddCargoEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EjectPeopleEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.NoChoiceEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.FinalState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.ShipState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.StardustState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipCardTest {
    ShipCard shipCard = new ShipCard(true, true, 5, 3, 2);

    //Player fp = new Player("IPfp", "name", Color.BLUE);


    Player p = new Player("IP", "nick", Color.RED);
    Player a = new Player("IP2", "nick2", Color.BLUE);
    Ship ship = p.getShip();
    Ship ship2 = a.getShip();
    Game game = new Game();
    ShipState state = new ShipState(game, shipCard);

    @Test
    void processTestAccomplishedTrue() {
//        Player p = new Player("IP", "nick", Color.RED);
//        Ship s = p.getShip();

        // shipCard.process(p, state);

        // assertTrue(state.getAccomplished());
    }

    @Test
    void testShipCard(){

        game.addPlayer(a);
        game.addPlayer(p);

        ship.setTravelDays(0);
        ship2.setTravelDays(1);

        a.setOnlineStatus(true);
        p.setOnlineStatus(true);

        game.updateListOfActivePlayers();

        //stardustState.init();
        state.init();


        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL, CabinInhabitants.TWO,true,Color.RED,0,0);

        CabinTile cabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.NONE,ConnectorType.SINGLE,ConnectorType.NONE,CabinInhabitants.TWO,false,Color.NONE,1,0);
        CabinTile cabin2 = new CabinTile(ConnectorType.DOUBLE,ConnectorType.DOUBLE,ConnectorType.NONE,ConnectorType.SINGLE,CabinInhabitants.TWO,false,Color.NONE,0,0);
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

        CabinTile centralCabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL, CabinInhabitants.NONE,true,Color.BLUE,0,0);

        ship2.setTileOnFloorPlan(2,3,centralCabin1);

        List<List<Integer>> tiles = new ArrayList<>();
        List<Integer> number = new ArrayList<>();
        number.add(2);
        number.add(3);
        number.add(2);
        tiles.add(number);
        number = new ArrayList<>();
        number.add(3);
        number.add(2);
        number.add(2);
        tiles.add(number);
        number = new ArrayList<>();
        number.add(2);
        number.add(4);
        number.add(1);
        tiles.add(number);



        assertEquals(6,ship.getNumberOfInhabitants());

        state.handleEvent(new NoChoiceEvent(a));
        assertEquals(0,ship.getTravelDays());
//        state.handleEvent(new EjectPeopleEvent(p,tiles));
//
//        assertEquals(1,ship.getNumberOfInhabitants());
//
//        assertEquals(-2,ship.getTravelDays());
//        assertEquals(3,ship.getCredits());
        state.handleEvent(new NoChoiceEvent(p));
        assertEquals(0,ship.getTravelDays());




    }
}