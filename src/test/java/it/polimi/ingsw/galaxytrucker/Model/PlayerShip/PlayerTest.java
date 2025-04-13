package it.polimi.ingsw.galaxytrucker.Model.PlayerShip;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player = new Player("UYUBUBUHU","JONNY", Color.RED);
    @Test
    void getEngages() {
        assertEquals(false,player.getEngages());
    }

    @Test
    void setEngages() {
        player.setEngages(true);
        assertEquals(true,player.getEngages());
    }

    @Test
    void getInput() {
        assertEquals(0,player.getInput());
    }

    @Test
    void setInputEngine() {
        player.setInputEngine(2);
        assertEquals(2,player.getInput());
    }

    @Test
    void getNickname() {
        assertEquals("JONNY",player.getNickname());
    }

    @Test
    void setNickname() {
        player.setNickname("JONNY1");
        assertEquals("JONNY1",player.getNickname());
    }

    @Test
    void getShip() {
        assertNotNull(player.getShip());
    }

    @Test
    void setShip() {
        Ship ship = new Ship(Color.BLUE);
        player.setShip(ship);
        assertEquals(ship,player.getShip());
    }

    @Test
    void getPlayerIp() {
        assertEquals(player.getPlayerIp(),"UYUBUBUHU");
    }

    @Test
    void getOnlineStatus() {
        assertEquals(true,player.getOnlineStatus());
    }

    @Test
    void setOnlineStatus() {
        player.setOnlineStatus(false);
        assertEquals(false,player.getOnlineStatus());
    }
}