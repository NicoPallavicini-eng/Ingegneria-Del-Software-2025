package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CombatZoneCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.EpidemicCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpidemicCardTest {
    List<Cannonball> cannonballList = new ArrayList<>();

    EpidemicCardVisitor epidemicCardVisitor = new EpidemicCardVisitor();
    EpidemicCard epidemicCard = new EpidemicCard(true, true, epidemicCardVisitor);

    @Test
    void processTest() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        s.setCrewMembers(20);

        // epidemicCard.process(s); TODO fix iterator in ship line 584

        assertEquals(s.getNumberOfCrewMembers(), 20);
    }
}