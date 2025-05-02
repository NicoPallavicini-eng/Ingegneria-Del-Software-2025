package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class RMIClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;
    private String nickname;

    public RMIClient(VirtualServer server) throws RemoteException {
        this.server = server;
        nickname = null;
    }
    public void run() throws RemoteException {
        this.server.connect(this);
        this.runCl();
    }
    private void runCl() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String input = new String("0");
        while(!input.equals("/disconnect")){
            System.out.print("Enter command: ");
            input = scanner.nextLine();
            System.out.println("Server command: " + input);
            server.handleUserInput(this,input);
            try{
                server.showMessage(input);
            }catch(RemoteException e){
                e.printStackTrace();
            }
        }
        System.out.println("Exited Loop");
        System.exit(0);
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        final String serverName = "AdderServer";

        Registry registry = LocateRegistry.getRegistry("localhost", 1090);
        VirtualServer server = (VirtualServer) registry.lookup(serverName);

        new RMIClient(server).run();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }
    @Override
    public void setNickname(String nickname) throws RemoteException{
        this.nickname = nickname;
    }
    @Override
    public String getNickname() throws RemoteException {
        return this.nickname;
    }
    @Override
    public void invalidCommand(String error) throws RemoteException {
        System.out.println(error);
    }

    @Override
    public void getView(boolean myShip, boolean everyoneShip, boolean colorOfShip, boolean board, boolean currentCard) throws RemoteException {
        // Implementation for getting the view
        // Add your logic here to display the view based on the parameters
    }

    @Override
    public void helpMessage() throws RemoteException {
        System.out.println("Help message: \n" +
                "Every command must be preceded by a slash (/) and could require parameters divided by a comma (,)\n" +
                "Some commands require two set of parameters that must be divided by a semicolon (;)\n" +
                "Example: /command set1param1, set1param2; set2param1, set2param2\n" +
                "Available commands: \n"+
                "\nALWAYS AVAILABLE\n" +
                "/help - Show this help message\n" +
                "/disconnect - If you are connected to the game, disconnect from it. No parameters needed.\n" +
                "\nPRE BUILDING PHASE\n" +
                "/connect - Connect to the game. Require the nickname for the game.\n" +
                "/setnumberofplyers - Set the max number of player only if you are the host. Require the number of players.\n" +
                "\nBUILDING PHASE\n");
    }

}