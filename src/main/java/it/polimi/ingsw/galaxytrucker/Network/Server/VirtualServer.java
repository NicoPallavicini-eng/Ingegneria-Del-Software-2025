package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote{
    String sayHello() throws RemoteException;
    void connect(VirtualView virtualView) throws RemoteException;
    void showMessage(String input) throws RemoteException;
    void handleUserInput(VirtualView virtualView, String input) throws RemoteException;
}
