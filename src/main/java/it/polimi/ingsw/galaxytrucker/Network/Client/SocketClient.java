package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServerSocket;
import it.polimi.ingsw.galaxytrucker.View.TUI;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient implements VirtualClientSocket {
    //final BufferedReader input;
    final VirtualServerSocket server;
    private final ObjectInputStream objIn;
    private Game game;
    private TUI tui;

    //private final ObjectOutputStream objOut;

    protected SocketClient(ObjectInputStream objIn, ObjectOutputStream objOut, int choise) throws UnknownHostException, IOException {
        this.objIn = objIn;
        this.server = new VirtualServerSocket(objOut);
        if(choise == 1) {
            tui = new TUI();
        }
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

    private void runVirtualServer() throws IOException,ClassNotFoundException {
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
        while (true) {

            System.out.print("> ");
            String message = scan.nextLine();
            //int command = scan.nextInt();
            server.sendMessageToServer(message);

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

public void referMethod(Message msg){
    if(msg.isStringMessage()){
        String line = msg.getMessage();
        switch(line){
            case "helpMessage" ->{
                helpMessage();
            }default -> {
                System.out.println(line);
            }
        }
    }else{
        //metodi di Game
    }
}

public void showMessageFromServer(String message){
    System.out.println("You recieved this : " + message);
}

    public void helpMessage(){
        tui.printHelpMessage();
    }

    public static void main(String[] args) throws IOException, UnknownHostException {
        String host = "localhost";
        int port = 12343;

        Socket serverSocket = new Socket(host, port);

        ObjectOutputStream objOut = new ObjectOutputStream(serverSocket.getOutputStream());
        ObjectInputStream objIn = new ObjectInputStream(serverSocket.getInputStream());

        new SocketClient(objIn,objOut,1).run();
    }
}
