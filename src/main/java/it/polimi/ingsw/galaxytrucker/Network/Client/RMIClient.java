package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServer;
import it.polimi.ingsw.galaxytrucker.View.TUI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class RMIClient extends UnicastRemoteObject implements VirtualClient, Runnable {
    final VirtualServer server;
    private String nickname;
    private static TUI tui; // Later maybe UI???

    public RMIClient(VirtualServer server, int choiceUI) throws RemoteException {
        this.server = server;
        nickname = null;
        if (choiceUI == 1) {
            tui = new TUI();
            tui.printTitle();
            tui.printGuide();
        }
        else{
            //GUI
        }
    }
    @Override
    public void run(){
        try {
            this.server.connect(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            this.runCl();
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
            server.showMessage(input);

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
/*
    public static void main(String[] args) throws RemoteException, NotBoundException {
        final String serverName = "AdderServer";

        Registry registry = LocateRegistry.getRegistry("localhost", 1090);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RMIClient(server, ).run();
    }*/

    @Override
    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }
    @Override
    public void setNickname(String nickname) throws RemoteException{
        this.nickname = nickname;
        //server.addNickname(nickname);
        server.mapNicknameClient(this, nickname);
        tui.setNickname(nickname);
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
        tui.viewLeaderboard(game);
    }

    @Override
    public void viewTilepile(Game game) throws RemoteException{
        tui.viewTilePile(game);
    }

    @Override
    public void setMainCabin(Game game) throws RemoteException {
        // TODO add main cabin to center of ship (check color of mainCabin == color of ship)
    }

    @Override
    public void helpMessage() throws RemoteException {
        tui.printHelpMessage();
    }

    @Override
    public void viewMyShip(Game game, String nickname) throws RemoteException {
        tui.printVoid();
        tui.printMyShip(game, nickname);
    }

    @Override
    public void viewShips(Game game) throws RemoteException {
        tui.printShips(game);
    }

    @Override
    public void viewTile(Tile currentTile) throws RemoteException {
        tui.printTile(currentTile);
    }

    @Override
    public void connectView(Game game) throws RemoteException {
         tui.viewTilePile(game);
         tui.printMyShip(game, nickname);
    }

    @Override
    public void printMessage(String message) throws RemoteException {
        tui.printMessage(message);
    }

    @Override
    public void viewCard(Game game) throws RemoteException{
        tui.viewCard(game);
    }

    @Override
    public void pong() throws RemoteException {

    }

    @Override
    public void printIndex(int index) throws RemoteException {
        System.out.println("Index: " + index);
    }
}
