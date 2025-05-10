package it.polimi.ingsw.galaxytrucker.Controller.Server;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.View.TUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServerControllerTest {

    private ServerController serverController;
    private TestVirtualClient testClient;
    private TestGame testGame;
    private TestGameState testGameState;

    @BeforeEach
    void setUp() {
        testGameState = new TestGameState();
        testGame = new TestGame(testGameState);
        testClient = new TestVirtualClient();
        serverController = new ServerController();
    }

    @Test
    void testHandleUserInput_InvalidCommand() throws RemoteException {
        serverController.handleUserInput(testClient, null);
        assertEquals("Invalid command", testClient.getLastInvalidCommand());

        serverController.handleUserInput(testClient, "invalid");
        assertEquals("Invalid command", testClient.getLastInvalidCommand());
    }

    @Test
    void testHandleUserInput_HelpCommand() throws RemoteException {
        serverController.handleUserInput(testClient, "/help");
        assertTrue(testClient.isHelpMessageSent());
    }

    @Test
    void testHandleUserInput_ConnectCommand_Success() throws RemoteException {
        testClient.setNickname(null);
        testGame.setPlayers(new ArrayList<>());

        serverController.handleUserInput(testClient, "/connect player1");
        assertEquals("player1", testClient.getNickname());
        assertTrue(testGameState.isEventHandled());
    }

    @Test
    void testHandleUserInput_ConnectCommand_NicknameTaken() throws RemoteException {
        Player player = new Player("localhost", "player1", Color.BLUE);
        testGame.setPlayers(List.of(player));

        serverController.handleUserInput(testClient, "/connect player1");
        assertEquals("Nickname already taken, please choose another one!", testClient.getLastInvalidCommand());
    }

    @Test
    void testCheckPlayer_PlayerExists() {
        Player player = new Player("localhost", "player1", Color.BLUE);
        testGame.setPlayers(List.of(player));

        Player result = serverController.checkPlayer("player1");
        assertNotNull(result);
        assertEquals("player1", result.getNickname());
    }

    @Test
    void testCheckPlayer_PlayerDoesNotExist() {
        testGame.setPlayers(new ArrayList<>());

        Player result = serverController.checkPlayer("player1");
        assertNull(result);
    }

    @Test
    void testValidTilePosition_Valid() {
        assertTrue(serverController.validTilePosition(7, 6));
    }

    @Test
    void testValidTilePosition_Invalid() {
        assertFalse(serverController.validTilePosition(5, 4));
    }

    // Additional tests can be added similarly
}

// Stub classes for testing
class TestGame extends Game {
    private List<Player> players = new ArrayList<>();
    private final GameState gameState;

    public TestGame(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public List<Player> getListOfPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }
}

class TestGameState extends GameState {
    private boolean eventHandled = false;

    public boolean isEventHandled() {
        return eventHandled;
    }
}

class TestVirtualClient implements VirtualClient {
    private String nickname;
    private String lastInvalidCommand;
    private boolean helpMessageSent = false;

    @Override
    public String sayHello() throws RemoteException {
        return "";
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void invalidCommand(String message) {
        lastInvalidCommand = message;
    }

    @Override
    public void getView(boolean myShip, boolean everyoneShip, boolean colorOfShip, boolean board, boolean currentCard) throws RemoteException {

    }

    @Override
    public void helpMessage() {
        helpMessageSent = true;
    }

    @Override
    public void viewLeaderboard(Game game, String nickname) throws RemoteException {

    }

    @Override
    public TUI getTui() throws RemoteException {
        return null;
    }

    public String getLastInvalidCommand() {
        return lastInvalidCommand;
    }

    public boolean isHelpMessageSent() {
        return helpMessageSent;
    }
}