package it.polimi.ingsw.galaxytrucker.Controller.Server;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.RMIClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerControllerTest {

    private ServerController serverController;
    private VirtualServer server;

    @BeforeEach
    void setUp() {
        serverController = new ServerController();
        server = new VirtualServer() {
            @Override
            public String sayHello() throws RemoteException {
                return "";
            }

            @Override
            public void connect(VirtualClient virtualClient) throws RemoteException {

            }

            @Override
            public void showMessage(String input) throws RemoteException {

            }

            @Override
            public void handleUserInput(VirtualClient virtualClient, String input) throws RemoteException {

            }
        };
    }

    @Test
    void testMultipleClientConnectionsAndOperations() throws RemoteException {
        // Create real clients
        VirtualClient client1 = new RMIClient(server, 1);
        VirtualClient client2 = new RMIClient(server, 1);
        VirtualClient client3 = new RMIClient(server, 1);
        VirtualClient client4 = new RMIClient(server, 1);

        // Simulate client connections
        serverController.handleUserInput(client1, "/connect Client1");
        serverController.handleUserInput(client1, "/setnumberofplayers 4");
        serverController.handleUserInput(client2, "/connect Client2");
//        serverController.handleUserInput(client3, "/connect Client3");
//        serverController.handleUserInput(client4, "/connect Client4");

        // Verify connections
        Game game = serverController.getGame();
        assertEquals(2, game.getListOfActivePlayers().size());

        // Simulate operations from each client
        serverController.handleUserInput(client1, "/help");
        serverController.handleUserInput(client2, "/viewleaderboard");
        serverController.handleUserInput(client3, "/setnumberofplayers 3");
        serverController.handleUserInput(client4, "/giveup");

        // Add assertions to verify expected behavior
        //assertEquals(3, game.getNumberOfPlayers());
    }

    // Real implementation of VirtualClient
    static abstract class RealVirtualClient implements VirtualClient {
        private String nickname;

        public RealVirtualClient(String nickname) {
            this.nickname = nickname;
        }

        @Override
        public void invalidCommand(String message) throws RemoteException {
            System.out.println(nickname + " received invalid command: " + message);
        }

        @Override
        public void helpMessage() throws RemoteException {
            System.out.println(nickname + " received help message.");
        }


        @Override
        public void setNickname(String nickname) throws RemoteException {
            this.nickname = nickname;
        }

        @Override
        public String getNickname() throws RemoteException {
            return nickname;
        }
    }
}