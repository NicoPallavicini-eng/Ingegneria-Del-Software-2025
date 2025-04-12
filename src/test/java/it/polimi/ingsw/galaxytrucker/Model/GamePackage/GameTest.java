package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Player p1 = new Player("a", "a", Color.RED);
    Player p2 = new Player("b","b", Color.BLUE);
    Player p3 = new Player( "c", "c", Color.GREEN);
    Game game = new Game(p1);


    @Test
    void getListOfPlayers() {

    }

    @Test
    void test1() {

        ArrayList<Player> expected = new ArrayList<>();
        expected.add(p1);

        assertEquals(expected, game.getListOfPlayers());
    }

    @Test
    void removePlayer() {
    }

    @Test
    void getNumberOfPlayers() {
    }

    @Test
    void setNumberOfPlayers() {
    }

    @Test
    void getGameState() {
    }

    @Test
    void nextGameState() {
    }

    @Test
    void getHourglass() {
    }

    @Test
    void getTilePile() {
    }

    @Test
    void getDeck() {
    }

    @Test
    void setDeck() {
    }
}