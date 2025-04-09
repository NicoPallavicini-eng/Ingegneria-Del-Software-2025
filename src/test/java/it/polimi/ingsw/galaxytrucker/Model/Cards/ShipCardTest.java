package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PiratesCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.ShipCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipCardTest {
    ShipCardVisitor shipCardVisitor = new ShipCardVisitor();
    ShipCard shipCard = new ShipCard(true, true, shipCardVisitor, 5, 3, 2);

    Player fp = new Player("IPfp", "name", Color.BLUE);
    Game game = new Game(fp);
    SequentialTravellingState state = new SequentialTravellingState(game, shipCard);

    @Test
    void processTestAccomplishedTrue() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();

        // shipCard.process(p, state);

        // assertTrue(state.getAccomplished());
    }
}