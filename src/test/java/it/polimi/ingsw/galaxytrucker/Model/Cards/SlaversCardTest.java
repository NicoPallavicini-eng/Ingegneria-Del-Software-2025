package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.SlaversState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlaversCardTest {
    SlaversCard slaversCard = new SlaversCard(true, true, 5, 3, 2, 4);

    Player fp = new Player("IPfp", "name", Color.BLUE);
    Game game = new Game();
    SlaversState state = new SlaversState(game, slaversCard);

    @Test
    void processTestAccomplishedTrue() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();

        // slaversCard.process(p, state);

        // assertTrue(state.getAccomplished());
    }
}