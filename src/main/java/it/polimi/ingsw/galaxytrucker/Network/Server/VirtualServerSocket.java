package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.Network.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Implementation of the VirtualServer interface that uses an ObjectOutputStream
 * to send messages to the server. This class is used for RMI communication
 * between the client and server.
 */

public class VirtualServerSocket implements VirtualServer {
    private final ObjectOutputStream objOut;

    public VirtualServerSocket(ObjectOutputStream output) throws IOException {
        this.objOut = output;
    }

    /**
     * Connects a VirtualClient to the server.
     * This method is a placeholder and does not perform any action in this implementation.
     * @param virtualClient the client to connect
     */
    @Override
    public void sendMessageToServer(String message,String nickname) throws IOException{
        Message obj = new Message("String",null,message);
        obj.setNickname(nickname);
        objOut.writeObject(obj);
        objOut.flush();
    }

    /**
     * Connects a VirtualClient to the server.
     * This method is a placeholder and does not perform any action in this implementation.
     * @param virtualClient the client to connect
     */
    public void connect(VirtualClient virtualClient) {}

    /**
     * Displays a message to the server console.
     * This method is a placeholder and does not perform any action in this implementation.
     * @param input the message to display
     */
    public void showMessage(String input) {}

    /**
     * Handles user input from a VirtualClient.
     * This method is a placeholder and does not perform any action in this implementation.
     * @param virtualClient the client sending the input
     * @param input the input from the client
     */
    public void handleUserInput(VirtualClient virtualClient, String input){}

    /**
     * Returns the list of connected VirtualClients.
     * This method is a placeholder and does not return any clients in this implementation.
     * @return a list of VirtualClients
     */
    public List<VirtualClient> getClients() {
        return null;
    }

    /**
     * Sends a ping to the server.
     * This method is a placeholder and does not perform any action in this implementation.
     */
    public void ping() {}

    /**
     * Adds a nickname to the server.
     * This method is a placeholder and does not perform any action in this implementation.
     * @param nickname the nickname to add
     */
    public void addNickname(String nickname) {}

    /**
     * Maps a VirtualClient to its nickname.
     * This method is a placeholder and does not perform any action in this implementation.
     * @param virtualClient the client to map
     * @param nickname the nickname to associate with the client
     */
    public void mapNicknameClient(VirtualClient virtualClient, String nickname){}

    /**
     * Returns the Game instance associated with the server.
     * This method is a placeholder and does not return any game in this implementation.
     * @return the Game instance
     */
    public Game getGame() {
        return null;
    }
}
