package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.JsonParsing;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ActivateEnginesEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.NoChoiceEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.OpenSpaceState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class OpenSpaceCardTest {
    OpenSpaceCard openSpaceCard = new OpenSpaceCard(true, true);
    Player p = new Player("IP", "nick", Color.RED);
    Ship ship = p.getShip();
    Game game = new Game();
    OpenSpaceState openSpaceState = new OpenSpaceState(game,new OpenSpaceCard(true, true));
    @Test
    void openSpaceTest(){
        game.addPlayer(p);
        Player a = new Player("IP", "nick", Color.BLUE);
        a.setOnlineStatus(true);
        p.setOnlineStatus(true);
        game.addPlayer(a);
        ship.setTravelDays(1);
        a.getShip().setTravelDays(0);
        game.updateListOfActivePlayers();
        openSpaceState.init();


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

        List<List<Integer>> tiles = new ArrayList<>();
        List<List<Integer>> batteries = new ArrayList<>();
        List<Integer> number = new ArrayList<>();
        number.add(3);
        number.add(3);
        tiles.add(number);
        number=new ArrayList<>();
        number.add(1);
        number.add(5);
        number.add(1);
        batteries.add(number);


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


        openSpaceState.handleEvent(new ActivateEnginesEvent(p,tiles,batteries));
        openSpaceState.handleEvent(new NoChoiceEvent(a));

        ArrayList<Player> list = new ArrayList<>();

        list.add(p);
        list.add(a);

        assertEquals(list,game.getListOfActivePlayers());

        assertEquals(null,a.getShip().getTravelDays());
        assertEquals(4,ship.getTravelDays());


    }

    @Test
    void process1TestEnginePowerZero() {
        Ship s = p.getShip();

        // s.setEnginePower(0); TODO when engine setter works

        // openSpaceCard.process1(p); TODO fix iterator in ship

        assertNull(s.getTravelDays());
    }

    @Test
    void process1TestEnginePowerNotZero() {
        Ship s = p.getShip();
        // p.setInputEngine(4);

        // s.setEnginePower(2); TODO when engine setter works

        // openSpaceCard.process1(p); // TODO fix iterator in ship

        // assertEquals(s.getEnginePower(), 0); // will be 6 when process works, TODO when engine getter works
    }

    @Test
    void process2Test() {
        Ship s = p.getShip();
        s.setTravelDays(5);
        // s.setEnginePower(2); TODO when engine setter works

        // openSpaceCard.process2(p); TODO fix iterator in ship

        assertEquals(s.getTravelDays(), 5); // will be 7 when process works
    }
}