package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    final ServerSocket listenSocket;
    //final CounterController controller;
    private ServerController serverController;

    final List<SocketClientHandler> clients = new ArrayList<>();

    public SocketServer(ServerSocket listenSocket) {
        this.listenSocket = listenSocket;
        this.serverController = new ServerController(this);
    }

    public void run() throws IOException {
        Socket clientSocket = null;
        while ((clientSocket = this.listenSocket.accept()) != null) {
//            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
//            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());
            //BufferedOutputStream bufferedOut = new BufferedOutputStream(clientSocket.getOutputStream(), 64 * 1024);
            ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
            objOut.flush();
            //BufferedInputStream bufferedIn = new BufferedInputStream(, 64 * 1024);
            ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());



            //SocketClientHandler handler = new SocketClientHandler( clientSocket,this.serverController,this, new BufferedReader(socketRx), new PrintWriter(socketTx,true));
            SocketClientHandler handler = new SocketClientHandler(clientSocket,this.serverController,this,objIn,objOut);
            synchronized (this.clients) {
                clients.add(handler);
                serverController.setSocketPlayers(this.clients);
            }

            new Thread(() -> {
                try {
                    handler. runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void broadcastUpdate(String value) {
        // Potrei usare un canale
        synchronized (this.clients) {
            for (var client : this.clients) {

                //client.showMessage(value);
            }
        }
    }

    public Game getGame(){
        return serverController.getGame();
    }

    public void deleteClientFromList(SocketClientHandler handler){
        synchronized (this.clients) {
            clients.remove(handler);
        }
    }
    public List<SocketClientHandler> getClientsList(){
        return clients;
    }
    public static void main(String[] args) throws IOException {
        int port = 1091;
        try {
            ServerSocket listenSocket = new ServerSocket(port);
            System.out.println("[SERVER] In ascolto sulla porta " + port);
            new SocketServer(listenSocket).run();
        } catch (IOException e) {
            System.err.println("Error initializing server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
