package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for the VirtualServer, which extends Remote to allow RMI communication.
 * It provides methods for connecting clients, handling user input, and managing game state.
 */
public interface VirtualServer extends Remote {
    void connect(VirtualClient virtualClient) throws RemoteException;
    void showMessage(String input) throws RemoteException;
    void handleUserInput(VirtualClient virtualClient, String input) throws RemoteException;
    List<VirtualClient> getClients() throws RemoteException;
    void ping()throws RemoteException;
    void addNickname(String nickname) throws RemoteException;
    void mapNicknameClient(VirtualClient virtualClient, String nickname) throws RemoteException;
    Game getGame() throws RemoteException;
    void sendMessageToServer(String message,String nickname)throws IOException;
}
