package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.SmugglersState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmugglersCardTest {
    List<Integer> blocks = new ArrayList<>();
    SmugglersCard smugglersCard = new SmugglersCard(true, true, 5, blocks, 2, 4);

    Player fp = new Player("IPfp", "name", Color.BLUE);
    Game game = new Game();
    SmugglersState state = new SmugglersState(game, smugglersCard);

    @Test
    void processTestAccomplishedTrue() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();

        // smugglersCard.process(p, state);

        // assertTrue(state.getAccomplished());
    }
}