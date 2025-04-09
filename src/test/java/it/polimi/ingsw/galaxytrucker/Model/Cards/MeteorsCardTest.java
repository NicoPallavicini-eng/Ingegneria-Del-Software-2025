package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.EpidemicCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.MeteorsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeteorsCardTest {
    List<Meteor> meteorList = new ArrayList<>();

    MeteorsCardVisitor meteorsCardVisitor = new MeteorsCardVisitor();
    MeteorsCard meteorsCard = new MeteorsCard(true, true, meteorList, meteorsCardVisitor);

    @Test
    void processTest() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();

        meteorsCard.process(s);

        // assertEquals(); TODO move in ship
    }
}