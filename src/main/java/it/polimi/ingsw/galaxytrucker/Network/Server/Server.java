package it.polimi.ingsw.galaxytrucker.Network.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    public VirtualServer rmiServer;
    public SocketServer socketServer;

    public Server() throws RemoteException , IOException {
        final String serverName = "AdderServer";
        int port = 1090;
        InetAddress localHost = InetAddress.getLocalHost();
        String ipAddress = localHost.getHostAddress();

        VirtualServer server = new RMIServer();
        rmiServer = server;
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server, port);

        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(serverName, stub);
        System.out.println("server bound, waiting for connection");
        System.out.println("IP: " + ipAddress + "\n" + "RMI port: " + port);

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

        String host = "localhost";
        int portSocket = 12343;
        try{
            ServerSocket listenSocket = new ServerSocket(portSocket);
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
