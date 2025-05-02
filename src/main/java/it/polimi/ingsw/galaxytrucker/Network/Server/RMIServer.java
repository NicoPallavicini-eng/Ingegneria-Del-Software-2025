package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Network.Client.*;
import it.polimi.ingsw.galaxytrucker.Network.Server.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer implements VirtualServer {
    final List<VirtualView> clients = new ArrayList<>();
    private ServerController serverController = new ServerController();

    public RMIServer() throws RemoteException {
        super();
    }

    @Override
    public void connect(VirtualView virtualView) throws RemoteException {
        this.clients.add(virtualView);
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
    }
    @Override
    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }
    @Override
    public void handleUserInput(VirtualView virtualView, String input) throws RemoteException {
        serverController.handleUserInput(virtualView,input);
    }
}