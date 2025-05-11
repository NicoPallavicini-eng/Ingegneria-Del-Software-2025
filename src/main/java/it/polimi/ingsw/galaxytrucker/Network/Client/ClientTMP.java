package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServer;
import it.polimi.ingsw.galaxytrucker.View.TUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientTMP {
    private static RMIClient rmi;
    private static TUI tui;


    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Select the method for the UI: (1) TUI, (2) GUI");
        Scanner scanner = new Scanner(System.in);
        int uiChoice = scanner.nextInt();
        System.out.println("Select the method of connection: (1) RMI, (2) Socket");
        int connectionChoice = scanner.nextInt();
        switch (connectionChoice) {
            case 1 -> {
                final String serverName = "AdderServer";
                Registry registry = LocateRegistry.getRegistry("localhost", 1090);
                VirtualServer server = (VirtualServer) registry.lookup(serverName);
                rmi = new RMIClient(server, uiChoice);
                rmi.run();
            }
            case 2 -> {
                //Socket connection
            }
            default -> {
                System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }

}