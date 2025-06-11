package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.StationState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationCardTest {
    List<Integer> blocks = new ArrayList<>();
    StationCard stationCard = new StationCard(true, true, 5, blocks, 2);

    Player fp = new Player("IPfp", "name", Color.BLUE);
    Game game = new Game();
    StationState state = new StationState(game, stationCard);

    @Test
    void processTestAccomplishedTrue() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();

        // stationCard.process(p, state);

        // assertTrue(state.getAccomplished());
    }
}