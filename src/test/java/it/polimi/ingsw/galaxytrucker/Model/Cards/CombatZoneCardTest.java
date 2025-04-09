package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CombatZoneCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PlanetsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CombatZoneCardTest {
    List <Cannonball> cannonballList = new ArrayList<>();

    CombatZoneCardVisitor combatZoneCardVisitor = new CombatZoneCardVisitor();
    CombatZoneCard combatZoneCard = new CombatZoneCard(true, true, combatZoneCardVisitor, 4, 2, cannonballList);

    @Test
    void lessCrewProcessTest() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        s.setTravelDays(20);

        combatZoneCard.lessCrewProcess(s);

        assertEquals(s.getTravelDays(), 20 - combatZoneCard.getDaysLostLessCrew());
    }

    @Test
    void lessEngineProcessTest() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        s.setCrewMembers(4);

        combatZoneCard.lessEngineProcess(s);

        assertEquals(s.getNumberOfCrewMembers(), 4 - combatZoneCard.getCrewLostLessEngine());
    }

    @Test
    void lessFirepowerProcessTest() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();

        combatZoneCard.lessFirepowerProcess(s);

        // assertEquals(); TODO move in ship
    }
}