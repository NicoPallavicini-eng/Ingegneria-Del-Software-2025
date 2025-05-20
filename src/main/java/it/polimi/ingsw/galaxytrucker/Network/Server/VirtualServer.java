package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualServer extends Remote{
    String sayHello() throws RemoteException;
    void connect(VirtualClient virtualClient) throws RemoteException;
    void showMessage(String input) throws RemoteException;
    void handleUserInput(VirtualClient virtualClient, String input) throws RemoteException;
    List<VirtualClient> getClients() throws RemoteException;
    void ping()throws RemoteException;
    void addNickname(String nickname) throws RemoteException;
    void mapNicknameClient(VirtualClient virtualClient, String nickname) throws RemoteException;
}
