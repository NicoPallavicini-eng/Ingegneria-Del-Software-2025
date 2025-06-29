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

public class SocketClient {
    //final BufferedReader input;
    final VirtualServerSocket server;
    private final ObjectInputStream objIn;
    private Game game;
    private UI ui;
    private String nickname = null;
    private boolean flag = true;

    //private final ObjectOutputStream objOut;

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

    public void setGUI(GUI gui) {
        ui = gui;
    }

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

    /*
    Metodi invocati lato client quando
arriva un messaggio.
Nell'implementazione c'è la logica
per mostrare il messaggio ricevuto
all'utente
     */
public void metodoClientClient(String message){
        System.out.println(message);
}

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

    public void viewCard(Game game) throws RemoteException{
        ui.viewCard(game);
    }
    public void viewShips(Game game) throws RemoteException {
        ui.printShips(game);
    }
    public void viewTile(Tile currentTile) throws RemoteException {
        ui.printTile(currentTile);
    }
    public void viewTilePile(Game game) throws RemoteException{
        ui.viewTilePile(game);
    }

    public void defaultView(Game game, String nickname) throws RemoteException {
        ui.updateGame(game);
        ui.viewTilePile(game);
        ui.printMyShip(game, nickname);
    }
    public void viewLeaderboard(Game game, String nickname) throws RemoteException {
        ui.viewLeaderboard(game);
    }
    public void viewMyShip(Game game, String nickname) throws RemoteException {
        ui.printMyShip(game, nickname);
    }
    public void helpMessage(){
        ui.printHelpMessage();
    }
    public void setNickname(Message msg) throws RemoteException{
        nickname = msg.getNickname();
    }
    public void pongNickname() {
        try{
            server.sendMessageToServer("/NICKNAME_PONG",nickname);
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public VirtualServerSocket getServerSocket() throws IOException{
        return server;
    }

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
