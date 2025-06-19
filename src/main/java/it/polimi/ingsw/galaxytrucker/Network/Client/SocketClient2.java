package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServerSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient2 {
    //final BufferedReader input;
    final VirtualServerSocket server;
    private final ObjectInputStream objIn;
    private String nickname=null;
    //private final ObjectOutputStream objOut;

    protected SocketClient2(ObjectInputStream objIn, ObjectOutputStream objOut) throws UnknownHostException, IOException {
        this.objIn = objIn;
        this.server = new VirtualServerSocket(objOut);
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
            showMessageFromServer(msg.getMessage());
            //obj= objIn.readObject();
        }
    }

    private void runCli() throws RemoteException,IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        while (true) {

            System.out.print("> ");
            String message = scan.nextLine();
            //int command = scan.nextInt();
            server.sendMessageToServer(message,nickname);

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
    public void showMessageFromServer(String message){
        System.out.println("You recieved this :" + message);
    }

    public static void main(String[] args) throws IOException, UnknownHostException {
        String host = "localhost";
        int port = 12343;

        Socket serverSocket = new Socket(host, port);

//        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
//        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());


        ObjectOutputStream objOut = new ObjectOutputStream(serverSocket.getOutputStream());
        //objOut.flush();

        ObjectInputStream objIn = new ObjectInputStream(serverSocket.getInputStream());

//        ObjectOutputStream objOut = new ObjectOutputStream(serverSocket.getOutputStream());
////        objOut.flush();
//        ObjectInputStream objIn = new ObjectInputStream(serverSocket.getInputStream());

        //new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx)).run();
        new SocketClient2(objIn,objOut).run();
    }
}
