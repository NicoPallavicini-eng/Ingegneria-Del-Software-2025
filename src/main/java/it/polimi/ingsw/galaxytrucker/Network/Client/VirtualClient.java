package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.View.TUI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualClient extends Remote {
    String sayHello() throws RemoteException;
    void setNickname(String nickname) throws RemoteException;
    String getNickname() throws RemoteException;
    void invalidCommand(String error) throws RemoteException;
    void helpMessage() throws RemoteException;
    void viewLeaderboard(Game game, String nickname) throws RemoteException;
}