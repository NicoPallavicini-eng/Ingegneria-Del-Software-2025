package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.PiratesState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingStateFactory;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PiratesCardTest {
    List<Cannonball> cannonballList = new ArrayList<>();
    PiratesCard piratesCard = new PiratesCard(true, true, 5, 3, 2, cannonballList);

    Player fp = new Player("IPfp", "name", Color.BLUE);
    Game game = new Game();
    PiratesState state = new PiratesState(game, piratesCard);

    @Test
    void processTestAccomplishedTrue() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        // s.setFirepower(10); TODO fix ship method

        // piratesCard.process(p, state);

        // assertTrue(state.getAccomplished());
    }
}