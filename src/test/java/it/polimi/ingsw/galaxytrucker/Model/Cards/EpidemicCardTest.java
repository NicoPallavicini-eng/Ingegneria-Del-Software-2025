package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EpidemicCardTest { // needs ship changes
    List<Cannonball> cannonballList = new ArrayList<>();

    // EpidemicCardVisitor epidemicCardVisitor = new EpidemicCardVisitor();
    EpidemicCard epidemicCard = new EpidemicCard(true, true);

    @Test
    void processTest() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        // s.setCrewMembers(20);

        // epidemicCard.process(s);

        // assertEquals(s.getNumberOfCrewMembers(), 20);
    }
}