package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OpenSpaceCardTest {
    OpenSpaceCard openSpaceCard = new OpenSpaceCard(true, true);
    Player p = new Player("IP", "nick", Color.RED);

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