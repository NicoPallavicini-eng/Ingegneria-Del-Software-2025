package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CabinInhabitants;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CabinTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ConnectorType;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombatZoneCardTest { // passed
    List <Cannonball> cannonballList = new ArrayList<>();

    CombatZoneCard combatZoneCard = new CombatZoneCard(true, true, 4, 2, cannonballList);

    @Test
    void lessCrewProcessTest() { // passed
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        s.setTravelDays(20);

        // combatZoneCard.lessCrewProcess(s);

        assertEquals(s.getTravelDays(), 20 - combatZoneCard.getDaysLostLessCrew());
    }

    @Test
    void lessEngineProcessTest() { // passed
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        // s.setCrewMembers(4);
        Tile tile = new CabinTile(ConnectorType.CANNON_SINGLE,ConnectorType.SINGLE,ConnectorType.DOUBLE,ConnectorType.SINGLE, CabinInhabitants.TWO, false, 0, 0);
        s.setTileOnFloorPlan(2,3,tile);

        // combatZoneCard.lessEngineProcess(s);

        assertEquals(s.getNumberOfInhabitants(), 4 - combatZoneCard.getCrewLostLessEngine());
    }

    @Test
    void lessFirepowerProcessTest() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();

        // combatZoneCard.lessFirepowerProcess(s);

        // view getHit() tests
    }
}