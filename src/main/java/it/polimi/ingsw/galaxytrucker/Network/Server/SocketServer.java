package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The SocketServer class listens for incoming client connections on a specified port.
 * It creates a new SocketClientHandler for each connected client and starts a new thread to handle communication.
 * The server maintains a list of connected clients and can broadcast updates to them.
 */
public class SocketServer {
    final ServerSocket listenSocket;
    //final CounterController controller;
    private ServerController serverController;

    final List<SocketClientHandler> clients = new ArrayList<>();

    /**
     * Constructor for the SocketServer class.
     * Initializes the server with a ServerSocket and a ServerController.
     *
     * @param listenSocket the ServerSocket to listen for incoming connections
     */
    public SocketServer(ServerSocket listenSocket) {
        this.listenSocket = listenSocket;
        this.serverController = new ServerController(this);
    }

    /**
     * Runs the server, accepting incoming client connections.
     * For each connection, it creates a SocketClientHandler and starts a new thread to handle communication.
     *
     * @throws IOException if an I/O error occurs while accepting connections
     */
    public void run() throws IOException {
        System.out.println("Socket port: " + listenSocket.getLocalPort());
        Socket clientSocket = null;
        while ((clientSocket = this.listenSocket.accept()) != null) {
            ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
            objOut.flush();
            ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());
            SocketClientHandler handler = new SocketClientHandler(clientSocket,this.serverController,this,objIn,objOut);
            synchronized (this.clients) {
                clients.add(handler);
                serverController.setSocketPlayers(this.clients);
            }
            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Broadcasts an update to all connected clients.
     * This method can be used to send messages or updates to all clients simultaneously.
     *
     * @param value the message or update to broadcast
     */
    public void broadcastUpdate(String value) {
        // Potrei usare un canale
        synchronized (this.clients) {
            for (var client : this.clients) {

                //client.showMessage(value);
            }
        }
    }

    /**
     * Returns the ServerController instance associated with this server.
     *
     * @return the ServerController instance
     */
    public Game getGame(){
        return serverController.getGame();
    }

    /**
     * Deletes a client from the list of connected clients.
     * This method is called when a client disconnects or closes the connection.
     *
     * @param handler the SocketClientHandler representing the client to be removed
     */
    public void deleteClientFromList(SocketClientHandler handler){
        synchronized (this.clients) {
            clients.remove(handler);
        }
    }

    /**
     * Returns the list of connected clients.
     * This method can be used to retrieve the current list of clients connected to the server.
     *
     * @return a list of SocketClientHandler instances representing connected clients
     */
    public List<SocketClientHandler> getClientsList(){
        return clients;
    }

    /**
     * Main method to start the SocketServer.
     * It initializes the server on a specified port and starts listening for incoming connections.
     *
     * @param args command line arguments (not used)
     * @throws IOException if there is an error during server initialization
     */
    public static void main(String[] args) throws IOException {
        int port = 1091;
        try {
            ServerSocket listenSocket = new ServerSocket(port);
            System.out.println("[SERVER] In ascolto sulla porta " + port);
            new SocketServer(listenSocket).run();
        } catch (IOException e) {
            System.err.println("Error initializing server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
