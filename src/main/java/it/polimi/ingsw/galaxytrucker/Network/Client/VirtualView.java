package it.polimi.ingsw.galaxytrucker.Network.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote{
    String sayHello() throws RemoteException;
    void setNickname(String nickname) throws RemoteException;
    String getNickname() throws RemoteException;
    void invalidCommand(String error) throws RemoteException;
    void getView(boolean myShip, boolean everyoneShip, boolean colorOfShip, boolean board, boolean currentCard) throws RemoteException;
    void helpMessage() throws RemoteException;
}