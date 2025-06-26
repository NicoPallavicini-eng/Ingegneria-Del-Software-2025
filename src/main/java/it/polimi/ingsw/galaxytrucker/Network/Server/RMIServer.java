package it.polimi.ingsw.galaxytrucker.Network.Server;

import it.polimi.ingsw.galaxytrucker.Controller.Server.ServerController;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RMIServer implements VirtualServer {
    final List<VirtualClient> clients = new ArrayList<>();
    final List<String> nicknameList = new ArrayList<>();
    private Map<VirtualClient,String> mapper = new HashMap<>();
    private ServerController serverController = new ServerController(this);

    public RMIServer() throws RemoteException {
        super();
    }

    @Override
    public synchronized void connect(VirtualClient virtualClient) throws RemoteException {
        this.clients.add(virtualClient);
        serverController.setRmiPlayers(this.clients);
    }

    @Override
    public synchronized List<VirtualClient> getClients() throws RemoteException{
        return clients;
    }
    @Override
    public void showMessage(String input) throws RemoteException {
        System.out.println(input);
    }

    public static void main(String[] args) throws RemoteException {
        final String serverName = "AdderServer";
        System.setProperty("java.rmi.server.hostname", "192.168.224.181");

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
    public void handleUserInput(VirtualClient virtualClient, String input) throws RemoteException {
        serverController.handleUserInput(virtualClient,input);
    }
    public void addNickname(String nickname) {
        nicknameList.add(nickname);
    }
    @Override
    public void ping() throws RemoteException {
        ArrayList<VirtualClient> disconnectedClients = new ArrayList<>();
        for (VirtualClient virtualClient : clients) {
            try {
                virtualClient.pong();
            } catch (RemoteException e) {
                System.out.println("Client disconnesso o non raggiungibile");
                disconnectedClients.add(virtualClient);
                String nickname = mapper.get(virtualClient);
                serverController.disconnect(nickname);
            }
        }
        for (VirtualClient virtualClient : disconnectedClients) {
            clients.remove(virtualClient);
        }
    }
    @Override
    public void mapNicknameClient(VirtualClient virtualClient, String nickname) {
        mapper.put(virtualClient,nickname);
    }

    @Override
    public Game getGame() {
        return ServerController.getGame();
    }

    public void sendMessageToServer(String message, String nickname) {}
}