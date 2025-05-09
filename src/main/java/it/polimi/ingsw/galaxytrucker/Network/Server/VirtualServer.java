package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote{
    String sayHello() throws RemoteException;
    void connect(VirtualClient virtualClient) throws RemoteException;
    void showMessage(String input) throws RemoteException;
    void handleUserInput(VirtualClient virtualClient, String input) throws RemoteException;
}
