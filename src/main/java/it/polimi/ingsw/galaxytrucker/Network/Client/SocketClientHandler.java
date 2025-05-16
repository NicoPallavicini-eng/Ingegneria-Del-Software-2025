package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClientSocket;
import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.Network.Server.SocketServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClientHandler implements VirtualClientSocket {
    //final CounterController controller;
    final ServerController serverController;
    final SocketServer server;
    //final BufferedReader input;
    //final PrintWriter output;
    final Socket socket;
    private Game game;
    private final ObjectInputStream objIn;
    private final ObjectOutputStream objOut;

    public SocketClientHandler( Socket socket,ServerController serverController,SocketServer server, ObjectInputStream input, ObjectOutputStream output) {
        //this.controller = controller;
        this.socket = socket;
        this.server = server;
        this.objIn = input;
        this.objOut = output;
        this.serverController = serverController;
    }

    public void runVirtualView() throws IOException,ClassNotFoundException {
        String line;
        Object obj;
        Message msg;
        System.out.println("Server in attesa di messaggi...");
        while (true) {
            try{
                msg = (Message) objIn.readObject();
                //msg = (Message) objIn.readObject();
                serverController.handleUserInput(msg,objOut);
                showMessage(msg);
                //callSpecificMethod(msg);
                //sendMessageToClient(msg.getMessage());
            }catch(EOFException e){
                System.out.println("End of stream reached.");
            }catch (OptionalDataException e){
                if (e.eof) {
                    System.out.println("Unexpected end of data encountered.");
                } else {
                    System.out.println("Found primitive data instead of an object. Skipping " + e.length + " bytes.");
                    objIn.skip(e.length);  // Skip the primitive data
                }
            }

            //System.out.println(msg.getMessage());
            //System.out.println(objIn.available());
            //showMessage(obj);
            //callSpecificMethod(line);
            //sendMessageToClient(msg.getMessage());
        }
    }
    public void showMessage(Message obj) {
        //Message message = (Message) obj;
        if(obj.isStringMessage()){
            System.out.println(obj.getMessage());
            System.out.println(obj.getType()+"\n");
        }else{
            System.out.println("Non è una stringa");
        }
    }
    /*
    Metodi invocati lato server per
comunicare con il client.
Nell'implementazione, viene scritto il
messaggio che porta all'invocazione del
lato client del metodo corrispondente
     */
    public void sendMessageToClient(String message) throws IOException {
        //output.println(message);
        //output.flush();
        Message obj = new Message("String",null,message);
        objOut.writeObject(obj);
        objOut.flush();
        System.out.println("Oggetto inviato:" + message);
    }

    public void callSpecificMethod(Message msg) throws ClassNotFoundException {
        if(msg.isStringMessage()){
            System.out.println("Message is String Message");
        }else{
            System.out.println("Message is Game Message");
        }

}}
