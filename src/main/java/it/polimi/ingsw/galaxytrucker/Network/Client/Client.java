package it.polimi.ingsw.galaxytrucker.Network.Client;

import it.polimi.ingsw.galaxytrucker.Network.Server.VirtualServer;
import it.polimi.ingsw.galaxytrucker.View.TUI;
import it.polimi.ingsw.galaxytrucker.View.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
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
            int uiChoice;
            int connectionChoice;
            try{
                uiChoice = scanner.nextInt();
            }catch(InputMismatchException e){
                uiChoice = 1;
                scanner.nextLine();
            }
            System.out.println("Select the method of connection: (1) RMI, (2) Socket");
            try{
                connectionChoice = scanner.nextInt();
            }catch(InputMismatchException e){
                connectionChoice = 1;
                scanner.nextLine();
            }
            switch (connectionChoice) {
                case 1 -> {
                    System.out.println("Connecting via RMI, please choose IP and port of the server. For default connection press '-1'.");
                    int input = -1;
                    try{
                        input = scanner.nextInt();
                    }catch(InputMismatchException e){
                        input = -1;
                        scanner.nextLine();
                    }
                    if (input == -1) {
                        try{
                            String serverIP = "localhost";
                            int serverPort = 1090;
                            final String serverName = "AdderServer";
                            Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
                            VirtualServer server = (VirtualServer) registry.lookup(serverName);
                            rmi = new RMIClient(server, uiChoice);
                            rmi.run();
                        }catch(ConnectException e){
                            System.out.println("Server is not available");
                            System.exit(0);
                        }

                    }
                    else {
                        System.out.print("Enter server IP: ");
                        String serverIP = scanner.next();
                        System.out.print("Enter server port: ");
                        int serverPort = 1090;
                        boolean finish = false;
                        while(!finish){
                            try{
                                serverPort = scanner.nextInt();
                                finish = true;
                            }catch(InputMismatchException e){
                                System.out.print("Enter server port: ");
                                scanner.nextLine();
                            }
                        }
                        final String serverName = "AdderServer";
                        try{
                            Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
                            VirtualServer server = (VirtualServer) registry.lookup(serverName);
                            rmi = new RMIClient(server, uiChoice);
                            rmi.run();
                        }catch (UnknownHostException e){
                            System.out.println("You wrote non valid serverIp or serverPort relaunch application!");
                            System.exit(0);
                        }
                    }
                    okConnection = true;

                }
                case 2 -> {
                    //Socket connection
                    System.out.println("Connecting via SOCKET, please choose IP and port of the server. For default connection press '-1'.");
                    int input = -1;
                    try{
                        input = scanner.nextInt();
                    }catch(InputMismatchException e){
                        input = -1;
                        scanner.nextLine();
                    }
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
                                if (uiChoice != 2) {
                                    socketClient.run();
                                }
                            } catch (IOException e) {
                                System.out.println("Server is not available");
                                System.exit(0);
                                //e.printStackTrace();
                            }
                        } catch (IOException e) {
                            System.out.println("Server is not available");
                            System.exit(0);
                            //e.printStackTrace();
                        }
                    }
                    else {
                        System.out.print("Enter server IP: ");
                        String serverIP = scanner.next();
                        System.out.print("Enter server port: ");
                        boolean finish = false;
                        int serverPort = 12343;
                        while(!finish){
                            try{
                                serverPort = scanner.nextInt();
                            }catch(InputMismatchException e){
                                System.out.print("Enter server port: ");
                                scanner.nextLine();
                            }
                        }

                        String host = serverIP;
                        int port = serverPort;
                        try {
                            Socket serverSocket = new Socket(host, port);
                            ObjectOutputStream objOut = new ObjectOutputStream(serverSocket.getOutputStream());
                            ObjectInputStream objIn = new ObjectInputStream(serverSocket.getInputStream());
                            try {
                                socketClient = new SocketClient(objIn, objOut, uiChoice);
                                if (uiChoice != 2) {
                                    socketClient.run();
                                }
                            } catch (IOException e) {
                                System.out.println("You wrote non valid serverIp or serverPort relaunch application!");
                                System.exit(0);
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            System.out.println("You wrote non valid serverIp or serverPort relaunch application!");
                            System.exit(0);
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
