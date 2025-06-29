package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServerSocket;
import it.polimi.ingsw.galaxytrucker.View.GUI;
import it.polimi.ingsw.galaxytrucker.View.TUI;
import it.polimi.ingsw.galaxytrucker.View.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static it.polimi.ingsw.galaxytrucker.View.GUI.launchGUI;

/**
 * SocketClient is the client-side implementation that connects to the server using sockets.
 * It handles user input and game updates, and can operate in both TUI and GUI modes.
 */
public class SocketClient {
    final VirtualServerSocket server;
    private final ObjectInputStream objIn;
    private Game game;
    private UI ui;
    private String nickname = null;
    private boolean flag = true;

    /**
     * Constructor for SocketClient.
     * Initializes the client with the given ObjectInputStream and ObjectOutputStream.
     * If the choice is 2, it sends a message to the server to start a game and waits for a response.
     *
     * @param objIn  ObjectInputStream for reading messages from the server
     * @param objOut ObjectOutputStream for sending messages to the server
     * @param choice UI choice (1 for TUI, 2 for GUI)
     * @throws UnknownHostException if the host is unknown
     * @throws IOException          if an I/O error occurs
     */
    protected SocketClient(ObjectInputStream objIn, ObjectOutputStream objOut, int choice) throws UnknownHostException, IOException {
        this.objIn = objIn;
        this.server = new VirtualServerSocket(objOut);
        if (choice == 2) {
            server.sendMessageToServer("GAME", "");
            Message msg;
            try{
                msg = (Message) objIn.readObject();
                referMethod(msg);
                launchGUI(game, null,this); // TODO update - is now ok?ui = GUI.getInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ui = new TUI();
            ui.printTitle();
            ui.printGuide();
        }
    }

    /**
     * Sets the GUI instance for the client.
     * This method is used to update the GUI reference when it is created.
     *
     * @param gui the GUI instance to set
     */
    public void setGUI(GUI gui) {
        ui = gui;
    }

    /**
     * Runs the client, starting the virtual server and the command line interface.
     * It listens for messages from the server and processes them accordingly.
     *
     * @throws RemoteException if there is an error in remote communication
     */
    public void run() throws RemoteException {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        try{
            runCli();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Runs the virtual server, continuously reading messages from the server.
     * It processes each message by invoking the referMethod method.
     *
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if a class cannot be found during deserialization
     */
    public void runVirtualServer() throws IOException,ClassNotFoundException {
        String line;
        Object obj;
        Message msg;
        while (true) {
            msg = (Message) objIn.readObject();
            //showMessageFromServer(msg.getMessage());
            referMethod(msg);
            //obj= objIn.readObject();
        }
    }

    /**
     * Runs the command line interface, allowing the user to input commands.
     * It continuously prompts the user for input and sends messages to the server.
     *
     * @throws RemoteException if there is an error in remote communication
     * @throws IOException     if an I/O error occurs
     * @throws ClassNotFoundException if a class cannot be found during deserialization
     */
    private void runCli() throws RemoteException,IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        //esiste flag implementato
        while (true) {
            try{
                //serve per ritardare la stampa
                //in caso non dovesse stampare bene cambiare questo qua
                Thread.sleep(300);
                System.out.print("> ");
                String message = scan.nextLine();
                //int command = scan.nextInt();
                server.sendMessageToServer(message,nickname);
            }catch(InterruptedException e){
                e.printStackTrace();
            }


        }
    }

    /**
     * Processes the received message and calls the appropriate method based on its content.
     * It handles both string messages and game-related messages.
     *
     * @param msg the message received from the server
     * @throws RemoteException if there is an error in remote communication
     */
    public void referMethod(Message msg) throws RemoteException {
    if(msg.isStringMessage()){
        String line = msg.getMessage();
        switch(line){
            case "helpMessage" ->{
                helpMessage();
            }case "setNickname" ->{
                setNickname(msg);
                //System.out.println("Nickname set to: " + nickname);
                pongNickname();

            }case "PING"->{

            }case "disconnect" ->{
                flag = false;
                System.exit(0); //TODO: Capire se va bene...
            }
            case "NewGame" -> {
                this.game = msg.getGame();
            }
            default -> {
                ui.printMessage(line);
            }
        }
    }else{
        String line = msg.getMessage();
        //metodi di Game
        switch(line){
            case "defaultView" -> {
                game = msg.getGame();
                defaultView(game, msg.getNickname());
            }case "viewLeaderboard" ->{
                game = msg.getGame();
                viewLeaderboard(game, msg.getNickname());
            }case "viewMyShip" ->{
                game = msg.getGame();
                viewMyShip(game,msg.getNickname());
            }case "viewTilepile" ->{
                game = msg.getGame();
                viewTilePile(game);
            }case "viewShips"->{
                if(game == msg.getGame()){
                    System.out.println("I game coincidono");
                }
                game = msg.getGame();
                viewShips(game);
            }case "viewTile"->{
                viewTile(msg.getTile());
            }case "viewCard" ->{
                game = msg.getGame();
                viewCard(game);
            }
            default -> {
                System.out.println("Il messaggio con attributo Game non ha il commando specificato");
            }

        }
    }
}

    /**
     * Displays a message from the server.
     * This method is used to print messages received from the server to the console.
     *
     * @param message the message to display
     */
    public void viewCard(Game game) throws RemoteException{
        ui.viewCard(game);
    }

    /**
     * Displays the ships in the game.
     * This method is used to print the ships in the game to the console.
     *
     * @param game the game for which to view the ships
     * @throws RemoteException if there is an error in remote communication
     */
    public void viewShips(Game game) throws RemoteException {
        ui.printShips(game);
    }

    /**
     * Displays the current tile in the game.
     * This method is used to print the current tile to the console.
     *
     * @param currentTile the tile to view
     * @throws RemoteException if there is an error in remote communication
     */
    public void viewTile(Tile currentTile) throws RemoteException {
        ui.printTile(currentTile);
    }

    /**
     * Connects the view to the game.
     * This method is called when the client connects to the server and needs to view the game state.
     *
     * @param game the game to connect to
     * @throws RemoteException if there is an error in remote communication
     */
    public void viewTilePile(Game game) throws RemoteException{
        ui.viewTilePile(game);
    }

    /**
     * Displays the default view of the game.
     * This method updates the game state and prints the tile pile and the player's ship.
     *
     * @param game     the game to view
     * @param nickname the nickname of the player
     * @throws RemoteException if there is an error in remote communication
     */
    public void defaultView(Game game, String nickname) throws RemoteException {
        ui.updateGame(game);
        ui.viewTilePile(game);
        ui.printMyShip(game, nickname);
    }

    /**
     * Displays the leaderboard of the game.
     * This method prints the leaderboard to the console.
     *
     * @param game     the game for which to view the leaderboard
     * @param nickname the nickname of the player
     * @throws RemoteException if there is an error in remote communication
     */
    public void viewLeaderboard(Game game, String nickname) throws RemoteException {
        ui.viewLeaderboard(game);
    }

    /**
     * Displays the player's ship.
     * This method prints the player's ship to the console.
     *
     * @param game     the game for which to view the player's ship
     * @param nickname the nickname of the player
     * @throws RemoteException if there is an error in remote communication
     */
    public void viewMyShip(Game game, String nickname) throws RemoteException {
        ui.printMyShip(game, nickname);
    }

    /**
     * Prints a help message to the console.
     * This method is used to display a guide or help message to the user.
     */
    public void helpMessage(){
        ui.printHelpMessage();
    }

    /**
     * Sets the nickname for the player.
     * This method is called when the server sends a message to set the player's nickname.
     *
     * @param msg the message containing the nickname
     * @throws RemoteException if there is an error in remote communication
     */
    public void setNickname(Message msg) throws RemoteException{
        nickname = msg.getNickname();
    }

    /**
     * Sends a PONG message with the player's nickname to the server.
     * This method is called to confirm that the client is still connected and active.
     */
    public void pongNickname() {
        try{
            server.sendMessageToServer("/NICKNAME_PONG",nickname);
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Returns the server socket associated with this client.
     * This method is used to retrieve the server socket for further communication.
     *
     * @return the VirtualServerSocket instance
     * @throws IOException if an I/O error occurs
     */
    public VirtualServerSocket getServerSocket() throws IOException{
        return server;
    }

    /**
     * Returns the nickname of the player.
     * This method is used to retrieve the player's nickname.
     *
     * @return the nickname of the player
     */
    public static void main(String[] args) throws IOException, UnknownHostException {
        String host = "192.168.224.181";
        int port = 1091;
        try {
            Socket serverSocket = new Socket(host, port);

            ObjectOutputStream objOut = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(serverSocket.getInputStream());

            new SocketClient(objIn, objOut, 1).run();
        } catch (IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        }

}
