package it.polimi.ingsw.galaxytrucker.Network.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    //public RMIServer rmiServer;
    public VirtualServer rmiServer;
    public SocketServer socketServer;

    public Server() throws RemoteException , IOException {
        
        final String serverName = "AdderServer";

        VirtualServer server = new RMIServer();
        rmiServer = server;
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server, 1090);

        Registry registry = LocateRegistry.createRegistry(1090);
        registry.rebind(serverName, stub);
        System.out.println("server bound.");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try{
                    rmiServer.ping();
                }catch(RemoteException e){
                   // System.out.println("Client RMI Disconnesso");
                }
            }
        },0,5000);

//        try {
//            // Mantiene il main thread in esecuzione
//            synchronized (RMIServer.class) {
//                RMIServer.class.wait();
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
        String host = "localhost";
        int port = 12343;
        try{
            ServerSocket listenSocket = new ServerSocket(port);
            socketServer = new SocketServer(listenSocket);
            socketServer.run();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RemoteException {
        try{
            Server server = new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
