package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualClient extends Remote {
    String sayHello() throws RemoteException;
    void setNickname(String nickname) throws RemoteException;
    void setMainCabin(Game game) throws RemoteException;
    String getNickname() throws RemoteException;
    void invalidCommand(String error) throws RemoteException;
    void helpMessage() throws RemoteException;
    void viewLeaderboard(Game game) throws RemoteException;
    void viewTilepile(Game game) throws RemoteException;
    void viewMyShip(Game game, String nickname) throws RemoteException;
    void viewShips(Game game) throws RemoteException;
    void viewTile(Tile currentTile) throws RemoteException;
    void defaultView(Game game) throws RemoteException;
    void pong()throws RemoteException;
}