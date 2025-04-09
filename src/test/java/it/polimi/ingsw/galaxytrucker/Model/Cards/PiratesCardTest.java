package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.MeteorsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PiratesCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PiratesCardTest {
    List<Cannonball> cannonballList = new ArrayList<>();
    PiratesCardVisitor piratesCardVisitor = new PiratesCardVisitor();
    PiratesCard piratesCard = new PiratesCard(true, true, piratesCardVisitor, 5, 3, 2, cannonballList);

    Player fp = new Player("IPfp", "name", Color.BLUE);
    Game game = new Game(fp);
    SequentialTravellingState state = new SequentialTravellingState(game, piratesCard);

    @Test
    void processTestAccomplishedTrue() {
        Player p = new Player("IP", "nick", Color.RED);
        Ship s = p.getShip();
        // s.setFirepower(10); TODO fix ship method

        // piratesCard.process(p, state);

        // assertTrue(state.getAccomplished()); nothing works and i wanna die
    }
}