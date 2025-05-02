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
        System.out.println(
                "Help message: \n" +
                "Every command must be preceded by a slash (/) and could require parameters divided by a comma (,)\n" +
                "Some commands require two set of parameters that must be divided by a semicolon (;)\n" +
                "Example: /command set1param1, set1param2; set2param1, set2param2\n" +
                "Available commands: \n"+
                "\nALWAYS AVAILABLE\n" +
                "/help - Show this help message\n" +
                "/disconnect - If you are connected to the game, disconnect from it. No parameters needed.\n" +
                "\nPRE BUILDING PHASE\n" +
                "/connect - Connect to the game. Require the nickname for the game.\n" +
                "/setnumberofplayers - Set the max number of player only if you are the host. Require the number of players.\n" +
                "\nBUILDING PHASE\n"+
                "/pickuptile - Pick up a tile from the tile pile. Require the row and column of the tile in the pile.\n" +
                "/putdowntile - Put down the tile that you have last picked up. No parameters needed.\n" +
                "/placetile - Place the tile that you have last picked up on your ship. Require the row and column where you want to to place your tile in the ship.\n" +
                "/reservetile - Reserve the tile that you have last picked up. Requires the position (index) where tu put the tile (can only be 1 or 2).\n" +
                "/rotate - Rotate the tile that you last placed on your ship. Requires the side of the rotation, either LEFT or RIGHT.\n" +
                "/pickupfromship - Pick up the last tile you placed on your ship. No parameters needed.\n" +
                "/pikcupreservedtile - Pick up one of the tiles in the reserved tiles. Requires the position (index) of the tile you want to pick up.\n" +
                "/fliphourglass - Flip the hourglass. No parameters needed.\n" +
                "/setposition - Set your ship into the board. Requires the place where you want to put your ship.\n" +
                "/placeorangealien - Place the orange alien on your ship. Requires the row and column where you want to place the alien.\n" +
                "/placepurplealien - Place the purple alien on your ship. Requires the row and column where you want to place the alien.\n" +
                "/removetile - Remove a tile from your ship. Requires the row and column of the tile you want to remove.\n" +
                "/viewships - View the ships of all players. No parameters needed.\n" +
                "\nTRAVELLING PHASE\n" +
                "/viewleaerboard - View the leaderboard. No parameters needed.\n" +
                "/activateengines - Activate double engines. Requires two sets of parameters. The first one with the position (row and column) of the engines and the second one with the position of the battery tile and how many tile to take from it.\n" +
                "/activatecannons - Activate double cannons. Requires two sets of parameters. The first one with the position (row and column) of the cannons and the second one with the position of the battery tile and how many tile to take from it.\n" +
                "/activateshield - Activate one shield. Requires four parameters, the positions of the shield and the battery." +
                "/removecargo - Remove a cargo from your ship. Requires the position of the cargo and the value of the cargo to be removed.\n" +
                "/addcargo - Add a cargo to your ship. Requires the position of the cargo and the value of the cargo to be added.\n" +
                "/switchcargo - Switch a cargo with another one. Requires two set of parameters. The first one with the position and the value of the first cargo and the second one with the position of the second cargo.\n" +
                "/ejectpeople - Eject a number of people from your ship. Requires the position of the cabin from the people will be ejected and the number of people to be ejected.\n" +
                "/giveup - Give up the game. No parameters needed.\n" +
                "/viewinvetory - View your inventory. No parameters needed.\n" +
                "/choosesubship - Choose a subship in case of a destruction of part of your ship. Requires the position of a random tile in the subship that you want to keep.\n" +
                "/nochoice - Declares that you won't do any choice. No parameters needed.\n" +
                "/done - Declare that you are done with your action. No parameters needed.\n" +
                "\nEND GAME PHASE\n" +
                "/claimreward - Claim the end of the game reward. No parameters needed.\n"
        );
    }

}