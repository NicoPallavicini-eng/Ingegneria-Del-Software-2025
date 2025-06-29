package it.polimi.ingsw.galaxytrucker.Network.Server;

import java.io.IOException;

/**
 * Interface for the VirtualServerSocket, which defines methods for sending messages to the server.
 * This interface is used to facilitate communication between clients and the server.
 */
public interface VirtualServerSocketInterface {
    void sendMessageToServer(String message,String nickname)throws IOException;
}
