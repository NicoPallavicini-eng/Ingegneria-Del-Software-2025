package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.BuildingState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.WaitingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Player p1 = new Player("a", "a", Color.RED);
    Player p2 = new Player("b","b", Color.BLUE);
    Player p3 = new Player( "c", "c", Color.GREEN);
    Game game = new Game();
    ArrayList<Player> expected = new ArrayList<>();
    GameState gameState;


    @BeforeEach void init(){
        game.addPlayer(p2);
        game.addPlayer(p3);

        expected.add(p1);
        expected.add(p2);
        expected.add(p3);
    }


    @Test
    void test1() {


        assertEquals(expected, game.getListOfPlayers());
    }

    @Test
    void removePlayer() {
        expected.remove(p1);
        expected.remove(p2);
        expected.remove(p3);
        game.removePlayer(p1);
        game.removePlayer(p2);
        game.removePlayer(p3);
        assertEquals(expected, game.getListOfPlayers());
    }


    @Test
    void setNumberOfPlayers() {
        game.setNumberOfPlayers(3);
        assertEquals(3, game.getNumberOfPlayers());
    }

    @Test
    void getGameState() {
        gameState = game.getGameState();
        assertNotNull(gameState);
        assert gameState instanceof WaitingState;
    }

    @Test
    void nextGameState() {
        //game.nextGameState();
        assert gameState instanceof BuildingState;
        // game.nextGameState();
        //assert gameState instanceof;

        // TODO test I assume
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