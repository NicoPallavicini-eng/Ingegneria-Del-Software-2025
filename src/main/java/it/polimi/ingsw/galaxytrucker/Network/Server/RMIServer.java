package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer implements VirtualServer {
    final List<VirtualClient> clients = new ArrayList<>();
    private ServerController serverController = new ServerController(this);

    public RMIServer() throws RemoteException {
        super();
    }


    @Override
    public void connect(VirtualClient virtualClient) throws RemoteException {
        this.clients.add(virtualClient);
    }

    @Override
    public List<VirtualClient> getClients() throws RemoteException{
        return clients;
    }
    @Override
    public void showMessage(String input) throws RemoteException {
        System.out.println(input);
    }

    public static void main(String[] args) throws RemoteException {
        final String serverName = "AdderServer";

        VirtualServer server = new RMIServer();
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server, 1090);

        Registry registry = LocateRegistry.createRegistry(1090);
        registry.rebind(serverName, stub);
        System.out.println("server bound.");

        try {
            // Mantiene il main thread in esecuzione
            synchronized (RMIServer.class) {
                RMIServer.class.wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
    @Override
    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }
    @Override
    public void handleUserInput(VirtualClient virtualClient, String input) throws RemoteException {
        serverController.handleUserInput(virtualClient,input);
    }
}