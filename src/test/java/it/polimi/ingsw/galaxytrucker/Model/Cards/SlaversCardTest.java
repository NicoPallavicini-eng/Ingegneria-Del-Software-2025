package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.ShipCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.SlaversCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SlaversCardTest {
    SlaversCardVisitor slaversCardVisitor = new SlaversCardVisitor();
    SlaversCard slaversCard = new SlaversCard(true, true, slaversCardVisitor, 5, 3, 2, 4);

    Player fp = new Player("IPfp", "name", Color.BLUE);
    Game game = new Game(fp);
    SequentialTravellingState state = new SequentialTravellingState(game, slaversCard);

    @Test
    void processTestAccomplishedTrue() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();

        // slaversCard.process(p, state);

        // assertTrue(state.getAccomplished());
    }
}