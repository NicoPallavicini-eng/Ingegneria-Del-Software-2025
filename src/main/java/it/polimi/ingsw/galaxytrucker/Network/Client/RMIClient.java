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

/**
 * RMIClient is the client-side implementation of the VirtualClient interface.
 * It connects to the VirtualServer and handles user input and game updates.
 */

public class RMIClient extends UnicastRemoteObject implements VirtualClient, Runnable {
    final VirtualServer server;
    private String nickname;
    private Game game;
    private final int uiChoice;
    private static UI ui;

    /**
     * Constructor for RMIClient.
     * @param server
     * @param choiceUI
     * @throws RemoteException
     */
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

    /**
     * Runs the client, connecting to the server and starting the user interface.
     * If the UI choice is not GUI, it runs the command line interface.
     */
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

    /**
     * Runs the command line interface for user input.
     * It continuously prompts the user for commands until "/disconnect" is entered.
     * @throws RemoteException if there is an error in RMI communication
     */
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

    /**
     * Closes the client application.
     * This method is called when the user wants to exit the application.
     */
    public void close(){
        System.exit(0);
    }

    /**
     * Returns the nickname of the client.
     * @return the nickname of the client
     */
    @Override
    public void setNickname(String nickname) throws RemoteException{
        this.nickname = nickname;
        server.mapNicknameClient(this, nickname);
        ui.setNickname(nickname);
    }

    /**
     * Returns the nickname of the client.
     * @return the nickname of the client
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public String getNickname() throws RemoteException {
        return this.nickname;
    }

    /**
     * Returns the game associated with the client.
     * @return the game associated with the client
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void invalidCommand(String error) throws RemoteException {
        ui.invalidCommand(error);
    }

    /**
     * Displays the leaderboard of the game.
     * @param game the game for which to view the leaderboard
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void viewLeaderboard(Game game) throws RemoteException {
        ui.viewLeaderboard(game);
    }

    /**
     * Displays the tile pile of the game.
     * @param game the game for which to view the tile pile
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void viewTilepile(Game game) throws RemoteException{
        ui.viewTilePile(game);
    }

    /**
     * Sets the main cabin of the ship in the game.
     * This method is called when the player places the main cabin tile on their ship.
     * @param game the game in which to set the main cabin
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void setMainCabin(Game game) throws RemoteException {
        // TODO add main cabin to center of ship (check color of mainCabin == color of ship)
    }

    /**
     * Displays the help message for the game.
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void helpMessage() throws RemoteException {
        ui.printHelpMessage();
    }

    /**
     * Displays the current game state.
     * @param game the game to view
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void viewMyShip(Game game, String nickname) throws RemoteException {
        ui.printVoid();
        ui.printMyShip(game, nickname);
    }

    /**
     * Displays the ships in the game.
     * @param game the game to view
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void viewShips(Game game) throws RemoteException {
        ui.printShips(game);
    }

    /**
     * Displays the current tile in the game.
     * @param currentTile the tile to view
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void viewTile(Tile currentTile) throws RemoteException {
        ui.printTile(currentTile);
    }

    /**
     * Connects the view to the game.
     * This method is called when the client connects to the server and needs to view the game state.
     * @param game the game to connect to
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void connectView(Game game) throws RemoteException {
         ui.viewTilePile(game);
         ui.printMyShip(game, nickname);
    }

    /**
     * Prints a message to the user interface.
     * @param message the message to print
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void printMessage(String message) throws RemoteException {
        ui.printMessage(message);
    }

    /**
     * Displays the current game state.
     * @param game the game to view
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void viewCard(Game game) throws RemoteException{
        ui.viewCard(game);
    }

    /**
     * Handles the pong event in the game.
     * This method is called when the server sends a pong event to the client.
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void pong() throws RemoteException {

    }

    /**
     * Displays the deck of cards in the game.
     * @param game the game for which to view the deck
     * @param index the index of the deck to view
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void viewDeck(Game game, int index) throws RemoteException {
        ui.viewDeck(game, index);
    }

    /**
     * Returns the server associated with this client.
     * @return the server associated with this client
     * @throws RemoteException if there is an error in RMI communication
     */
    public VirtualServer getServer() throws RemoteException {
        return server;
    }

    /**
     * Updates the game state in the user interface.
     * This method is called when the game state changes and needs to be reflected in the UI.
     * @param game the updated game state
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void updateGame(Game game) throws RemoteException {
        ui.updateGame(game);
    }

    /**
     * Handles the next scene in the game.
     * This method is called when the game progresses to the next scene and needs to update the UI accordingly.
     * @param game the current game state
     * @param message a message to display in the UI
     * @throws RemoteException if there is an error in RMI communication
     */
    @Override
    public void next(Game game, String message) throws RemoteException {
        ui.updateGame(game);
        ui.nextScene(game,message);
    }
}
