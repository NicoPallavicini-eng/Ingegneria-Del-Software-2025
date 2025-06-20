package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServer;
import it.polimi.ingsw.galaxytrucker.View.GUI;
import it.polimi.ingsw.galaxytrucker.View.TUI;
import it.polimi.ingsw.galaxytrucker.View.UI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import static it.polimi.ingsw.galaxytrucker.View.GUI.launchGUI;


public class RMIClient extends UnicastRemoteObject implements VirtualClient, Runnable {
    final VirtualServer server;
    private String nickname;
    private Game game;
    private final int uiChoice;
    private static UI ui;

    public RMIClient(VirtualServer server, int choiceUI) throws RemoteException {
        this.server = server;
        this.uiChoice = choiceUI;
        synchronized (this.server) {
            this.game = server.getGame();
        }
        if (choiceUI == 2) {
            synchronized(this.server){
                this.server.connect(this);
            }
            launchGUI(game, this, null);
        } else {
            ui = new TUI();
            ui.printTitle();
            ui.printGuide();
        }
    }

    public void setGUI(GUI gui) {
        ui = gui;
    }

    @Override
    public void run(){
        try {
            synchronized (this.server) {
                this.server.connect(this);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            if(uiChoice != 2) {
                this.runCl();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    private void runCl() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String input = new String("0");
        while(!input.equals("/disconnect")){
            try{
            System.out.print("Enter command: ");
            input = scanner.nextLine();
            //System.out.println("Server command: " + input);
            server.showMessage(this + input);
            server.handleUserInput(this,input);
            }catch(RemoteException e){
                e.printStackTrace();
            }
            catch(IllegalEventException e){
                System.out.println("Error: "+ e.getMessage());
            }
        }
        System.out.println("Exited Loop");
        System.exit(0);
    }

    @Override
    public void setNickname(String nickname) throws RemoteException{
        this.nickname = nickname;
        server.mapNicknameClient(this, nickname);
        ui.setNickname(nickname);
    }
    @Override
    public String getNickname() throws RemoteException {
        return this.nickname;
    }
    @Override
    public void invalidCommand(String error) throws RemoteException {
        System.out.println(error);
    }

    @Override
    public void viewLeaderboard(Game game) throws RemoteException {
        ui.viewLeaderboard(game);
    }

    @Override
    public void viewTilepile(Game game) throws RemoteException{
        ui.viewTilePile(game);
    }

    @Override
    public void setMainCabin(Game game) throws RemoteException {
        // TODO add main cabin to center of ship (check color of mainCabin == color of ship)
    }

    @Override
    public void helpMessage() throws RemoteException {
        ui.printHelpMessage();
    }

    @Override
    public void viewMyShip(Game game, String nickname) throws RemoteException {
        ui.printVoid();
        ui.printMyShip(game, nickname);
    }

    @Override
    public void viewShips(Game game) throws RemoteException {
        ui.printShips(game);
    }

    @Override
    public void viewTile(Tile currentTile) throws RemoteException {
        ui.printTile(currentTile);
    }

    @Override
    public void connectView(Game game) throws RemoteException {
         ui.viewTilePile(game);
         ui.printMyShip(game, nickname);
    }

    @Override
    public void printMessage(String message) throws RemoteException {
        ui.printMessage(message);
    }

    @Override
    public void viewCard(Game game) throws RemoteException{
        ui.viewCard(game);
    }

    @Override
    public void pong() throws RemoteException {

    }

    @Override
    public void viewDeck(Game game, int index) throws RemoteException {
        //ui.viewDeck(game, index);
        //TODO implements viewDeck in TUI
    }

    public VirtualServer getServer() throws RemoteException {
        return server;
    }

    @Override
    public void updateGame(Game game) throws RemoteException {
        ui.updateGame(game);
    }
}
