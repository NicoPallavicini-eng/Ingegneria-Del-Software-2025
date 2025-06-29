package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RMIServer is the server-side implementation of the VirtualServer interface.
 * It manages client connections, handles user input, and maintains the game state.
 */
public class RMIServer implements VirtualServer {
    final List<VirtualClient> clients = new ArrayList<>();
    final List<String> nicknameList = new ArrayList<>();
    private Map<VirtualClient,String> mapper = new HashMap<>();
    private ServerController serverController = new ServerController(this);

    public RMIServer() throws RemoteException {
        super();
    }

    /**
     * Connects a VirtualClient to the server.
     * @param virtualClient the client to connect
     * @throws RemoteException if there is an error during the connection
     */
    @Override
    public synchronized void connect(VirtualClient virtualClient) throws RemoteException {
        this.clients.add(virtualClient);
        serverController.setRmiPlayers(this.clients);
    }

    /**
     * Disconnects a VirtualClient from the server.
     * @param virtualClient the client to disconnect
     * @throws RemoteException if there is an error during the disconnection
     */
    @Override
    public synchronized List<VirtualClient> getClients() throws RemoteException{
        return clients;
    }

    /**
     * Displays a message to the server console.
     * @param input the message to display
     * @throws RemoteException if there is an error during the remote method call
     */
    @Override
    public void showMessage(String input) throws RemoteException {
        System.out.println(input);
    }

    /**
     * Main method to start the RMI server.
     * It binds the RMIServer instance to the RMI registry and keeps the server running.
     *
     * @param args command line arguments (not used)
     * @throws RemoteException if there is an error during the remote method call
     */
    public static void main(String[] args) throws RemoteException {
        final String serverName = "AdderServer";
        System.setProperty("java.rmi.server.hostname", "192.168.224.181");

        VirtualServer server = new RMIServer();
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server, 1090);

        Registry registry = LocateRegistry.createRegistry(1090);
        registry.rebind(serverName, stub);
        System.out.println("server bound.");

        try {
            // Mantiene il main thread in esecuzione
            synchronized (RMIServer.class) {
                RMIServer.class.wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    /**
     * Handles user input from the VirtualClient.
     * It delegates the input handling to the ServerController.
     *
     * @param virtualClient the client that sent the input
     * @param input the user input
     * @throws RemoteException if there is an error during the remote method call
     */
    @Override
    public void handleUserInput(VirtualClient virtualClient, String input) throws RemoteException {
        serverController.handleUserInput(virtualClient,input);
    }

    /**
     * Adds a nickname to the server's nickname list.
     * @param nickname the nickname to add
     */
    public void addNickname(String nickname) {
        nicknameList.add(nickname);
    }
    @Override

    /**
     * Returns the list of nicknames connected to the server.
     * @return a list of nicknames
     */
    public void ping() throws RemoteException {
        ArrayList<VirtualClient> disconnectedClients = new ArrayList<>();
        for (VirtualClient virtualClient : clients) {
            try {
                virtualClient.pong();
            } catch (RemoteException e) {
                System.out.println("Client disconnesso o non raggiungibile");
                disconnectedClients.add(virtualClient);
                String nickname = mapper.get(virtualClient);
                serverController.disconnect(nickname);
            }
        }
        for (VirtualClient virtualClient : disconnectedClients) {
            clients.remove(virtualClient);
        }
    }
    @Override

    /**
     * Maps a VirtualClient to its nickname.
     * This method is used to associate a client with its nickname for easy reference.
     *
     * @param virtualClient the client to map
     * @param nickname the nickname to associate with the client
     */
    public void mapNicknameClient(VirtualClient virtualClient, String nickname) {
        mapper.put(virtualClient,nickname);
    }

    /**
     * Returns the nickname associated with a VirtualClient.
     * This method retrieves the nickname for a given client.
     *
     * @param virtualClient the client whose nickname is to be retrieved
     * @return the nickname of the client
     */
    @Override
    public Game getGame() {
        return ServerController.getGame();
    }

    /**
     * Returns the ServerController instance associated with this server.
     * This method provides access to the controller for managing game logic and state.
     *
     * @return the ServerController instance
     */
    @Override
    public void sendMessageToServer(String message, String nickname) {}
}