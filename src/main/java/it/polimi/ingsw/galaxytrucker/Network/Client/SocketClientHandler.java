package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.Network.Server.SocketServer;

import java.io.*;
import java.net.Socket;

/**
 * This class handles the communication between the server and a client.
 * It reads messages from the client, processes them, and sends responses back.
 */
public class SocketClientHandler {
    private boolean connect = true;
    //final CounterController controller;
    final ServerController serverController;
    final SocketServer server;
    //final BufferedReader input;
    //final PrintWriter output;
    final Socket socket;
    private Game game;
    private final ObjectInputStream objIn;
    private final ObjectOutputStream objOut;
    private String nickname = null;

    /**
     * Constructor for SocketClientHandler.
     * Initializes the socket, server, input and output streams.
     *
     * @param socket the socket connected to the client
     * @param serverController the controller managing the server logic
     * @param server the server instance
     * @param input the input stream for reading messages from the client
     * @param output the output stream for sending messages to the client
     */
    public SocketClientHandler( Socket socket,ServerController serverController,SocketServer server, ObjectInputStream input, ObjectOutputStream output) {
        //this.controller = controller;
        this.socket = socket;
        this.server = server;
        this.objIn = input;
        this.objOut = output;
        this.serverController = serverController;
    }

    /**
     * This method runs the virtual view, continuously listening for messages from the client.
     * It processes each message and calls the appropriate methods in the server controller.
     *
     * @throws IOException if an I/O error occurs while reading from or writing to the socket
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public void runVirtualView() throws IOException,ClassNotFoundException {
        String line;
        Object obj;
        Message msg;
        System.out.println("Server in attesa di messaggi...");
        while (connect) {
            try{
                msg = (Message) objIn.readObject();
                showMessage(msg);
                this.nickname = msg.getNickname();
                serverController.handleUserInput(msg,objOut);
            }catch(EOFException e){
                System.out.println("End of stream reached.");
                closeConnection();
                serverController.disconnect(this.nickname);
            }catch (OptionalDataException e){
                if (e.eof) {
                    System.out.println("Unexpected end of data encountered.");
                    closeConnection();
                    serverController.disconnect(this.nickname);
                } else {
                    System.out.println("Found primitive data instead of an object. Skipping " + e.length + " bytes.");
                    objIn.skip(e.length);  // Skip the primitive data
                }
            }
        }
    }

    /**
     * This method displays the message received from the client.
     * It checks if the message is a string message and prints it to the console.
     *
     * @param obj the message object received from the client
     */
    public void showMessage(Message obj) {
        //Message message = (Message) obj;
        if(obj.isStringMessage()){
            System.out.println(obj.getMessage());
            System.out.println(obj.getType()+"\n");
        }else{
            System.out.println("Non è una stringa");
        }
    }

    /**
     * This method sends a message to the client.
     * It creates a Message object and writes it to the output stream.
     *
     * @param message the message to be sent to the client
     * @throws IOException if an I/O error occurs while writing to the output stream
     */
    public void sendMessageToClient(String message) throws IOException {
        //output.println(message);
        //output.flush();
        Message obj = new Message("String",null,message);
        objOut.writeObject(obj);
        objOut.flush();
        System.out.println("Oggetto inviato:" + message);
    }

    /**
     * This method closes the connection to the client.
     * It closes the input and output streams, and the socket.
     * It also updates the server to remove this client from its list.
     */
    public void closeConnection() {
        try{
            objIn.close();
        }catch(IOException e){

        }
        try {
            objOut.close();
        } catch (IOException e) {

        }
        try {
            socket.close();
        }catch (IOException e) {
        }
        connect = false;
        server.deleteClientFromList(this);
        System.out.println("Connessione chiusa correttamente.");
        //notify serverController
    }

    /**
     * This method sets the game associated with this client handler.
     *
     * @param game the game to be associated with this client handler
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method sets the game associated with this client handler.
     *
     * @param game the game to be associated with this client handler
     */
    public ObjectOutputStream getObjOut() {
        return objOut;
    }

    /**
     * This method sets the game associated with this client handler.
     *
     * @param game the game to be associated with this client handler
     */
    public void callSpecificMethod(Message msg) throws ClassNotFoundException {
        if(msg.isStringMessage()){
            System.out.println("Message is String Message");
        }else{
            System.out.println("Message is Game Message");
        }


}}
