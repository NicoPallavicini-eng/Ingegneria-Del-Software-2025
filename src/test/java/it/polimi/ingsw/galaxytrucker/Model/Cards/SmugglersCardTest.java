package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ClaimRewardEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.NoChoiceEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.RemoveBatteriesEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.SmugglersState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.StardustState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmugglersCardTest {

    @Test
    void processTestAccomplishedTrue() {
        Player p1 = new Player("IP", "nick", Color.RED);
        Ship s = p1.getShip();

        // smugglersCard.process(p, state);

        // assertTrue(state.getAccomplished());
    }

    @Test
    void testSmugglersCard() {
        List<Integer> blocks = new ArrayList<>();
        blocks.add(3);
        blocks.add(4);
        blocks.add(1);
        SmugglersCard smugglersCard = new SmugglersCard(true, true, 5, blocks, 2, 4);

        Player p = new Player("IP", "nick", Color.RED);
        Player a = new Player("IP2", "nick2", Color.BLUE);

        Game game = new Game();

        Ship ship = p.getShip();
        Ship ship2 = a.getShip();

        game.addPlayer(p);
        game.addPlayer(a);

        a.setOnlineStatus(true);
        p.setOnlineStatus(true);

        ship.setTravelDays(1);
        a.getShip().setTravelDays(0);
        game.updateListOfActivePlayers();
        game.sortListOfActivePlayers();

        SmugglersState state = new SmugglersState(game, smugglersCard);

        CabinTile centralCabin = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL, CabinInhabitants.NONE,true,Color.RED,0,0);

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

        CabinTile centralCabin1 = new CabinTile(ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL,ConnectorType.UNIVERSAL, CabinInhabitants.NONE,true,Color.RED,0,0);

        ship2.setTileOnFloorPlan(2,3,centralCabin1);

        state.init();

        state.handleEvent(new NoChoiceEvent(p));
        state.handleEvent(new NoChoiceEvent(a));
        //state.handleEvent(new ClaimRewardEvent(a,true));



        assertEquals(1,ship.getTravelDays());
        assertEquals(0,ship2.getTravelDays());

        assertEquals(game.getListOfActivePlayers(),state.getHandledPlayers());



        List<Integer> battery = new ArrayList<>();
        battery.add(1);
        battery.add(5);
        battery.add(2);

        //state.handleEvent(new RemoveBatteriesEvent(a,battery));
        assertTrue(state.getHandledPlayers().containsAll(game.getListOfActivePlayers()));
        assertTrue(state.isReckoningPhase());
        assertEquals(0,ship.getBatteries());

        assertFalse(state instanceof SmugglersState);



    }
}