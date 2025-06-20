package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServer;
import it.polimi.ingsw.galaxytrucker.View.TUI;
import it.polimi.ingsw.galaxytrucker.View.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    private static RMIClient rmi;
    private static SocketClient socketClient;
    private static UI ui;
    private static boolean okConnection = false;

    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        while (!okConnection) {
            System.out.println("Select the method for the UI: (1) TUI, (2) GUI (Default is TUI)");
            Scanner scanner = new Scanner(System.in);
            int uiChoice = scanner.nextInt();
            System.out.println("Select the method of connection: (1) RMI, (2) Socket");
            int connectionChoice = scanner.nextInt();
            switch (connectionChoice) {
                case 1 -> {
                    System.out.println("Connecting via RMI, please choose IP and port of the server. For default connection press '-1'.");
                    int input = scanner.nextInt();
                    if (input == -1) {
                        String serverIP = "localhost";
                        int serverPort = 1090;
                        final String serverName = "AdderServer";
                        Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
                        VirtualServer server = (VirtualServer) registry.lookup(serverName);
                        rmi = new RMIClient(server, uiChoice);
                        rmi.run();
                    }
                    else {
                        System.out.print("Enter server IP: ");
                        String serverIP = scanner.next();
                        System.out.print("Enter server port: ");
                        int serverPort = scanner.nextInt();
                        final String serverName = "AdderServer";
                        Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
                        VirtualServer server = (VirtualServer) registry.lookup(serverName);
                        rmi = new RMIClient(server, uiChoice);
                        rmi.run();
                    }
                    okConnection = true;

                }
                case 2 -> {
                    //Socket connection
                    System.out.println("Connecting via SOCKET, please choose IP and port of the server. For default connection press '-1'.");
                    int input = scanner.nextInt();
                    if (input == -1){
                        String serverIP = "localhost";
                        int serverPort = 12343;
                        String host = serverIP;
                        int port = serverPort;
                        try {
                            Socket serverSocket = new Socket(host, port);
                            ObjectOutputStream objOut = new ObjectOutputStream(serverSocket.getOutputStream());
                            ObjectInputStream objIn = new ObjectInputStream(serverSocket.getInputStream());
                            try {
                                socketClient = new SocketClient(objIn, objOut, uiChoice);
                                socketClient.run();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        System.out.print("Enter server IP: ");
                        String serverIP = scanner.next();
                        System.out.print("Enter server port: ");
                        int serverPort = scanner.nextInt();
                        String host = serverIP;
                        int port = serverPort;
                        try {
                            Socket serverSocket = new Socket(host, port);
                            ObjectOutputStream objOut = new ObjectOutputStream(serverSocket.getOutputStream());
                            ObjectInputStream objIn = new ObjectInputStream(serverSocket.getInputStream());
                            try {
                                socketClient = new SocketClient(objIn, objOut, uiChoice);
                                socketClient.run();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    okConnection = true;
                }
                default -> {
                    System.out.println("Invalid choice. Please select 1 or 2.");
                }
            }
        }
    }
}
