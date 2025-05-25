package it.polimi.ingsw.galaxytrucker.Controller.Server;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.BuildingState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClientHandler;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.Network.Server.RMIServer;
import it.polimi.ingsw.galaxytrucker.Network.Server.SocketServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.*;

/**
 * The ServerController class handles user input and manages the game state on the server side.
 * It also create the game when instantiated.
 */

public class ServerController {
    private static final Game game = new Game();
    private static RMIServer rmiServer = null;
    private static SocketServer socketServer = null;
    private RemoteObserver remoteObserver = null;
    private final List<String> finalCommands = List.of("help", "viewcard", "viewleaderboard", "viewmyship", "viewtilepile", "viewships",
            "connect", "disconnect", "setnumberofplayers", "pickuptile", "rotate", "putdowntile",
            "placetile", "reservetile", "fliphourglass", "setposition", "pickupfromship", "pickupreservedtile", "activateengines", "activatecannons", "activateshields",
            "removecargo", "addcargo", "switchcargo", "ejectpeople", "giveup", "viewinventory", "claimreward", "choosesubship", "nochoice",
            "done", "placeorangealien", "placepurplealien", "removetile", "chooseplanet","riconnect","flipall");
    private Map<String,Game> gameMapper;
    private int hourglassCounter = 1;

    public ServerController(RMIServer rmiServer) {
        this.rmiServer = rmiServer;
        remoteObserver = new RemoteObserver(this, game);
    }

    public ServerController(SocketServer socketServer) {
        this.socketServer = socketServer;
        remoteObserver = new RemoteObserver(this, game);
    }

    public static Game getGame() {
        return game;
    }

    public void updateView(Game game, String message){
        GameState gameState = game.getGameState();
        try {
            List<VirtualClient> rmiClients = this.rmiServer.getClients();
            if (gameState instanceof BuildingState) {
                if (Objects.equals(message, "gamestate")) {
                    if (rmiClients == null || rmiClients.isEmpty()) {
                        return;
                    }
                    for (VirtualClient rmiClient : rmiClients) {
                        if (rmiClient != null) {
                            try {
                                rmiClient.printMessage("Started Building State");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else if (Objects.equals(message, "time")) {
                    if (rmiClients == null || rmiClients.isEmpty()) {
                        return;
                    }
                    for (VirtualClient rmiClient : rmiClients) {
                        if (rmiClient != null) {
                            try {
                                rmiClient.printMessage("Time is up, building phase is now over. You can place your alien in the ship and when you are done, type /done");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else if (Objects.equals(message, "finish")) {
                    if (rmiClients == null || rmiClients.isEmpty()) {
                        return;
                    }
                    for (VirtualClient rmiClient : rmiClients) {
                        if (rmiClient != null) {
                            try {
                                rmiClient.viewShips(game);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles user input from the client and executes the corresponding command.
     */

    /*

    SOCKET CONTROLLER

     */

    public void handleUserInput(Message msg, ObjectOutputStream objOut) throws IOException {
        String input = msg.getMessage();
        if (input == null || !input.startsWith("/")) {
            Message newMessage = new Message("String", null, "Invalid command");
            objOut.writeObject(newMessage);
            objOut.flush();
            return;
        }
        // Input to lowercase
        input = input.toLowerCase();
        // Remove / from the command
        String cleanInput = input.substring(1).trim();

        // Split input into command and parameters
        String[] parts = cleanInput.split(" ", 2);
        String command = parts[0];
        String par = parts.length > 1 ? parts[1].trim() : "";

        String[] subParameters = par.split(";", 2);

        List<String> firstParameters = new ArrayList<>();
        List<String> secondParameters = new ArrayList<>();

        if (subParameters.length >= 1 && !subParameters[0].isBlank()) {
            firstParameters = Arrays.stream(subParameters[0].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }
        if (subParameters.length == 2 && !subParameters[1].isBlank()) {
            secondParameters = Arrays.stream(subParameters[1].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }
        executeCommand(command, firstParameters, secondParameters, objOut, msg);
    }

    public void executeCommand(String command, List<String> firstParameters, List<String> secondParameters, ObjectOutputStream objOut, Message msg) throws IOException {
        switch (command) {
            case "help" -> {
                Message newMessage;
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                    newMessage = new Message("String", null, "/help doesn't support parameters, but here is the help command anyway!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                }
                String help = "helpMessage";
                newMessage = new Message("String", null, help);
                objOut.writeObject(newMessage);
                objOut.flush();

            }
            case "viewleaderboard" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                        newMessage = new Message("String",null,"/viewleaderboard doesn't support parameters!");
                        newMessage.setNickname(msg.getNickname());
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/viewleaderboard doesn't support parameters!");
                    }
                    newMessage = new Message("Game",game,"viewLeaderboard");
                    newMessage.setNickname(msg.getNickname());
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    objOut.reset();
                    //client.viewLeaderboard(game);
                }else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                }

            } //ok
            case "riconnect" ->{
                Message newMessage;
                if(secondParameters.isEmpty()){
                    if(firstParameters.size()==1){
                        String clientNickname = msg.getNickname();
                        String nickname = firstParameters.get(0);
                        if(clientNickname==null){
                            //trovare il Player di quel nickname
                            //trovare il Player di quel nickname
                            Optional<Player> playerOptional = game.getListOfPlayers().stream()
                                    .filter(player1 -> player1.getNickname().equals(nickname))
                                    .findAny();
                            if (playerOptional.isPresent()) {
                                //gestire lo status se offline riconetti,se no manda errore
                                Player player = playerOptional.get();
                                if(player.getOnlineStatus()){
                                    newMessage = new Message("String",null,"Il giocatore è online");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Il giocatore è online");
                                }else{
                                    player.setOnlineStatus(true);
                                    newMessage = new Message("String",null,"setNickname");
                                    newMessage.setNickname(nickname);
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    newMessage = new Message("Game",game,"defaultView");
                                    newMessage.setNickname(nickname);
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.setNickname(nickname);
                                }


                            } else {
                                newMessage = new Message("String",null,"Il nickname di Player is not present");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Il nickname di Player is not present");
                            }
                        }else{
                            newMessage = new Message("String",null,"/You already riconnected to the game");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/You already riconnected to the game");
                        }
                    }else{
                        newMessage = new Message("String",null,"/riconnect request one parameter.");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/riconnect request one parameter.");
                    }
                }else{
                    newMessage = new Message("String",null,"/riconnect request one parameter.");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("/riconnect request one parameter.");
                }
            }
            case "connect" -> {
                //GameState gameState = game.getGameState();
                Message newMessage;
                if (secondParameters.isEmpty()) {
                    if (firstParameters.size() == 1) {
                        String clientNickname = msg.getNickname();
                        String nickname = firstParameters.get(0);
                        if (clientNickname != null) {
                            newMessage = new Message("String", null, "It's forbidden for one client to connect to the game more than once!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("It's forbidden for one client to connect to the game more than once!");
                        } else {
                            Optional<Player> playerOptional = game.getListOfPlayers().stream()
                                    .filter(player1 -> player1.getNickname().equals(nickname))
                                    .findAny();

                            if (!playerOptional.isPresent()) {
                                try {
                                    ConnectEvent event = new ConnectEvent(nickname, "localhost");
                                    game.getGameState().handleEvent(event, game);
                                    newMessage = new Message("String", null, "setNickname");
                                    newMessage.setNickname(nickname);
                                    objOut.writeObject(newMessage);
                                    objOut.flush();

                                    //client.setNickname(nickname);
                                    newMessage = new Message("Game", game, "defaultView");
                                    newMessage.setNickname(nickname);
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.defaultView(game, nickname);
                                    // TODO update view
                                } catch (IllegalArgumentException e) {
                                    newMessage = new Message("String", null, e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Error: " + e.getMessage());
                                }catch(IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }

                            } else {
                                newMessage = new Message("String", null, "Nickname already taken, please choose another one!");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Nickname already taken, please choose another one!");
                            }
                        }
                    } else {
                        newMessage = new Message("String", null, "/connect request one parameter.");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/connect request one parameter.");
                    }
                } else {
                    newMessage = new Message("String", null, "/connect request one parameter.");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("/connect request one parameter.");
                }
            } //ok
            case "disconnect" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        try{
                            DisconnectEvent event = new DisconnectEvent(player);
                            game.getGameState().handleEvent(event,game);
                            newMessage = new Message("String", null, "setNickname");
                            newMessage.setNickname(null);
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            newMessage = new Message("String",null,"disconnect");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }catch(IllegalEventException e){
                            newMessage = new Message("String",null,e.getMessage());
                            newMessage.setNickname(msg.getNickname());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    }
                    else {
                        newMessage = new Message("String",null,"/disconnect doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/disconnect doesn't support parameters!");
                    }
                } else {
                    newMessage = new Message("String", null, "You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "setnumberofplayers" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 1) {
                            String numberOfPlayersStr = firstParameters.get(0);
                            int numberOfPlayers = Integer.parseInt(numberOfPlayersStr);
                            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                                newMessage = new Message("String", null, "Number of players not valid. It must be between 2 and 4");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Number of players not valid. It must be between 2 and 4");
                            } else {
                                SetNumberOfPlayersEvent event = new SetNumberOfPlayersEvent(numberOfPlayers);
                                try {
                                    game.getGameState().handleEvent(event);
                                    newMessage = new Message("String", null, "Hai settato il numero dei giocatori");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                } catch (IllegalArgumentException e) {
                                    newMessage = new Message("String", null, e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }catch(IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        } else {
                            newMessage = new Message("String", null, "/setnumberofplayers supports only one parameter!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/setnumberofplayers supports only one parameter!");
                        }
                    } else {
                        newMessage = new Message("String", null, "/setnumberofplayers supports only one parameter!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/setnumberofplayers supports only one parameter!");
                    }
                } else {
                    newMessage = new Message("String", null, "You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "viewmyship" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                        newMessage = new Message("String",null,"/viewmyship doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/viewship doesn't support parameters!");
                    }

                    newMessage = new Message("Game",game,"viewMyShip");
                    newMessage.setNickname(msg.getNickname());
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    objOut.reset();
                    //client.viewMyShip(game, client.getNickname());
                }else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }

            }
            case "viewtilepile" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                        newMessage = new Message("String",null,"/viewtilepile doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/viewtilepile doesn't support parameters!");
                    }
                    newMessage = new Message("Game",game,"viewTilepile");
                    newMessage.setNickname(msg.getNickname());
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    objOut.reset();
                    //client.viewTilepile(game);
                }else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            }
            case "viewships" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                        newMessage = new Message("String",null,"/viewships doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        objOut.reset();
                        //client.invalidCommand("/viewships doesn't support parameters!");
                    }
                    newMessage = new Message("Game",game,"viewShips");
                    newMessage.setNickname(msg.getNickname());
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    objOut.reset();
                    //client.viewShips(game);
                }else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "pickuptile" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if(firstParameters.size() == 2) {
                            String tileRow = firstParameters.get(0);
                            String tileColumn = firstParameters.get(1);
                            int tileRowInt = Integer.parseInt(tileRow);
                            int tileColumnInt = Integer.parseInt(tileColumn);
                            int tilePositionInt = (tileRowInt * 16) + tileColumnInt;
                            if (tilePositionInt > 0 && tilePositionInt < 152) {
                                try{
                                    PickUpTileEvent event = new PickUpTileEvent(player, tilePositionInt);
                                    game.getGameState().handleEvent(event);
                                    Tile currentTile = player.getShip().getTileInHand();
                                    newMessage = new Message("Game",game,"viewMyShip");
                                    newMessage.setNickname(msg.getNickname());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.viewMyShip(game, client.getNickname());
                                    newMessage = new Message("Game",game,"viewTilepile");
                                    newMessage.setNickname(msg.getNickname());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.viewTilepile(game);
                                }catch(IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }


                            } else {
                                newMessage = new Message("String",null,"Tile position not valid. It must be between 1 and 156");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Tile position not valid. It must be between 1 and 156");
                            }
                        }
                        else{
                            newMessage = new Message("String",null,"/pickuptile supports only one parameter!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/pickuptile supports only one parameter!");
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/pickuptile supports only one parameter!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/pickuptile supports only one parameter!");
                    }
                }
                else {
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } // ok
            case "rotatetile" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 1) {
                            try{
                                String side = firstParameters.get(0);
                                RotateTileEvent event = new RotateTileEvent(player, side);
                                game.getGameState().handleEvent(event);
                                newMessage = new Message("Game",game,"defaultView");
                                newMessage.setNickname(msg.getNickname());
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                objOut.reset();
                                //client.defaultView(game);
                            }catch (IllegalEventException e){
                                newMessage = new Message("String",null,e.getMessage());
                                objOut.writeObject(newMessage);
                                objOut.flush();
                            }

                        }
                        else{
                            newMessage = new Message("String",null,"/rotatetile supports only one parameter!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/rotatetile supports only one parameter!");
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/rotatetile supports only one parameter.");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/rotatetile supports only one parameter.");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "putdowntile" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        try{
                            PutDownTileEvent event = new PutDownTileEvent(player);
                            game.getGameState().handleEvent(event);
                            newMessage = new Message("Game",game,"defaultView");
                            newMessage.setNickname(msg.getNickname());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            objOut.reset();
                            //client.defaultView(game);
                        }catch (IllegalEventException e){
                            newMessage = new Message("String",null,e.getMessage());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/putdowntile doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/putdowntile doesn't support parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "placetile" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 2){
                            String row = firstParameters.get(0);
                            String column = firstParameters.get(1);
                            int rowInt = Integer.parseInt(row);
                            int columnInt = Integer.parseInt(column);
                            boolean checkPosition = validTilePosition(rowInt, columnInt);
                            if ((rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) || !checkPosition) {
                                newMessage = new Message("String",null,"Row or column not valid. It must be between 5 and 9 for rows and between 4 and 10 for columns");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Row or column not valid. It must be between 5 and 9 for rows and between 4 and 10 for columns");
                            }
                            else{
                                try{
                                    PlaceTileEvent event = new PlaceTileEvent(player, rowInt-5, columnInt-4);
                                    game.getGameState().handleEvent(event);
                                    newMessage = new Message("Game",game,"defaultView");
                                    newMessage.setNickname(msg.getNickname());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.defaultView(game);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        }
                        else{
                            newMessage = new Message("String",null,"/placetile supports only two parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/placetile supports only two parameters!");
                        }
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "reservetile" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 1){
                            String indexStr = firstParameters.get(0);
                            int index = Integer.parseInt(indexStr);
                            if (index < 1 || index > 2) {
                                newMessage = new Message("String",null,"Index not valid. It must be either 1 or 2");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Index not valid. It must be either 1 or 2");
                            }
                            else{
                                try{
                                    ReserveTileEvent event = new ReserveTileEvent(player, index-1);
                                    game.getGameState().handleEvent(event);
                                    newMessage = new Message("Game",game,"defaultView");
                                    newMessage.setNickname(msg.getNickname());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.defaultView(game);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        }
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "fliphourglass" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        try{
                            FlipHourglassEvent event = new FlipHourglassEvent();
                            game.getGameState().handleEvent(event,game);
                            newMessage = new Message("String",null,"You flipped "+ hourglassCounter+" hourglass");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            for(SocketClientHandler client:socketServer.getClientsList()){
                                ObjectOutputStream objOut1 = client.getObjOut();
                                if(objOut1 != objOut){
                                    newMessage = new Message("String",null,msg.getNickname()+ " flipped "+ hourglassCounter+" hourglass");
                                    objOut1.writeObject(newMessage);
                                    objOut1.flush();
                                }
                            }
                            for(VirtualClient client1: rmiServer.getClients()){
                                    client1.printMessage(msg.getNickname()+ " flipped "+ hourglassCounter+" hourglass");
                            }
                            hourglassCounter++;
                        }catch (IllegalEventException e){
                            newMessage = new Message("String",null,e.getMessage());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/fliphourglass doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/fliphourglass doesn't support parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "setposition" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 1) {
                            String pos = firstParameters.get(0);
                            int position = Integer.parseInt(pos);
                            int maxNumberOfPlayers = game.getNumberOfPlayers();
                            if (position < 1 || position > maxNumberOfPlayers && maxNumberOfPlayers != -1) {
                                newMessage = new Message("String",null,"Position not valid. It must be between 1 and " + maxNumberOfPlayers);
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Position not valid. It must be between 1 and " + maxNumberOfPlayers);
                            }
                            else if (maxNumberOfPlayers == -1){
                                newMessage = new Message("String",null,"You need to set the number of players before setting the position.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("You need to set the number of players before setting the position.");
                            }
                            else {
                                ObjectOutputStream objOutHandler = null;
                                try {
                                    SetPositionEvent event = new SetPositionEvent(player, position);
                                    game.getGameState().handleEvent(event);
                                    newMessage = new Message("Game",game,"viewLeaderboard");
                                    newMessage.setNickname(msg.getNickname());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.viewLeaderboard(game);
                                    //List<VirtualClient> clientsRMI = rmiServer.getClients();
                                    List<SocketClientHandler> clientsSocket = socketServer.getClientsList();
                                    for (SocketClientHandler client : clientsSocket) {
                                        if (!client.getNickname().equals(msg.getNickname())) {
                                            newMessage = new Message("String",null,player.getNickname() + " has set the position to " + position);
                                            objOutHandler = client.getObjOut();
                                            objOutHandler.writeObject(newMessage);
                                            objOutHandler.flush();
                                            //virtualClient.printMessage(player.getNickname() + " has set the position to " + position);
                                        }
//                                        newMessage = new Message("String",null,game.getGameState().toString());
//                                        objOut.writeObject(newMessage);
//                                        objOut.flush();
                                        //client.printMessage(game.getGameState().toString());

                                        if (game.getGameState() instanceof TravellingState) {
                                            newMessage = new Message("Game",game,"viewCard");
                                            newMessage.setNickname(msg.getNickname());
                                            objOutHandler = client.getObjOut();
                                            objOutHandler.writeObject(newMessage);
                                            objOutHandler.flush();
                                            objOut.reset();
                                            //virtualClient.viewCard(game);
                                        }
                                    }
                                    if(game.getGameState() instanceof TravellingState){
                                        for(VirtualClient client1: rmiServer.getClients()){
                                            client1.viewCard(game);
                                        }
                                    }
                                } catch (IllegalArgumentException e) {
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                   //client.invalidCommand("Error: " + e.getMessage());
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        }

                    }
                    else{
                        newMessage = new Message("String",null,"/setposition requires only one parameter!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/setposition requires only one parameter!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "pickupfromship" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                        try{
                            PickUpFromShipEvent event = new PickUpFromShipEvent(player);
                            game.getGameState().handleEvent(event);
                            Tile currentTile = player.getShip().getLastPlacedTile();
                            newMessage = new Message("Game",game,"viewTile");
                            newMessage.setNickname(msg.getNickname());
                            newMessage.setTile(currentTile);
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            objOut.reset();
                            //client.viewTile(currentTile);
                        }catch (IllegalEventException e){
                            newMessage = new Message("String",null,e.getMessage());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/pickupfromship doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/pickupfromship doesn't support parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "pickupreservedtile" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 1) {
                            String indexStr = firstParameters.get(0);
                            int index = Integer.parseInt(indexStr);
                            Ship playerShip = player.getShip();
                            int numberOfReservedTiles = playerShip.getReservedTiles().size();
                            if (index < 1 || index > numberOfReservedTiles) {
                                newMessage = new Message("String",null,"Index not valid. It must be either 1 or 2");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Index not valid. It must be either 1 or 2");
                            } else {
                                try{
                                    PickUpReservedTileEvent event = new PickUpReservedTileEvent(player, index - 1);
                                    game.getGameState().handleEvent(event);
                                    Tile reservedTile = player.getShip().getReservedTiles().get(index-1);
                                    newMessage = new Message("Game",game,"viewTile");
                                    newMessage.setNickname(msg.getNickname());
                                    newMessage.setTile(reservedTile);
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.viewTile(reservedTile);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/pickupreservedtile supports only one parameter.");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/pickupreservedtile supports only one parameter.");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "activateengines" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                        if (firstParameters.size() % 2 != 0) {
                            newMessage = new Message("String",null,"/activateengines needs an even number of row and column for engines.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/activateengines needs an even number of row and column for engines.");
                        }
                        else {
                            if(secondParameters.size() % 3 != 0){
                                newMessage = new Message("String",null,"for each batteries specify the position and the quantity to remove.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("for each batteries specify the position and the quantity to remove.");
                            }
                            else{
                                List<List<Integer>> engines = new ArrayList<>();
                                List<List<Integer>> batteries = new ArrayList<>();
                                for (int i = 0; i < firstParameters.size(); i += 2){
                                    String rowEngStr = firstParameters.get(i);
                                    String colEngStr = firstParameters.get(i+1);

                                    int rowEng = Integer.parseInt(rowEngStr);
                                    int colEng = Integer.parseInt(colEngStr);
                                    boolean checkPosition = validTilePosition(rowEng, colEng);
                                    if ((rowEng < 5 || rowEng > 9 || colEng < 4 || colEng > 10) || !checkPosition) {
                                        newMessage = new Message("String",null,"Invalid row or column.");
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                        //client.invalidCommand("Invalid row or column.");
                                        break;
                                    }
                                    List<Integer> engineRow = new ArrayList<>();
                                    engineRow.add(rowEng-5);
                                    engineRow.add(colEng-4);
                                    engines.add(engineRow);
                                }
                                for (int j=0; j < secondParameters.size(); j += 3) {
                                    //Getting positions and value of batteries
                                    String rowBatStr = secondParameters.get(j);
                                    String colBatStr = secondParameters.get(j++);
                                    String valueBatStr = secondParameters.get(j += 2);

                                    int rowBat = Integer.parseInt(rowBatStr);
                                    int colBat = Integer.parseInt(colBatStr);
                                    int valueBat = Integer.parseInt(valueBatStr);
                                    boolean checkPosition = validTilePosition(rowBat, colBat);
                                    if ((rowBat < 5 || rowBat > 9 || colBat < 4 || colBat > 10) || !checkPosition) {
                                        newMessage = new Message("String",null,"Invalid row or column.");
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                        //client.invalidCommand("Invalid row or column.");
                                        break;
                                    } else {
                                        if (valueBat < 1 || valueBat > 3) {
                                            newMessage = new Message("String",null,"Invalid value. It must be between 1 and 3");
                                            objOut.writeObject(newMessage);
                                            objOut.flush();
                                            //client.invalidCommand("Invalid value. It must be between 1 and 3");
                                            break;
                                        } else {
                                            List<Integer> batteryRow = new ArrayList<>();
                                            batteryRow.add(rowBat - 5);
                                            batteryRow.add(colBat - 4);
                                            batteryRow.add(valueBat);
                                            batteries.add(batteryRow);
                                        }
                                    }
                                }
                                try{
                                    ActivateEnginesEvent event = new ActivateEnginesEvent(player, engines, batteries);
                                    game.getGameState().handleEvent(event);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        }

                    }
                    else{
                        newMessage = new Message("String",null,"/activateengines needs two sets of parameters");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/activateengines needs two sets of parameters");
                    }
                }
                else {
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "activatecannons" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                        if (firstParameters.size() % 2 != 0) {
                            newMessage = new Message("String",null,"/activatecannons needs an even number of row and column for cannons.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/activatecannons needs an even number of row and column for cannons.");
                        }
                        else{
                            if (secondParameters.size() % 3 != 0){
                                newMessage = new Message("String",null,"for each batteries specify the position and the quantity to remove.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("for each batteries specify the position and the quantity to remove.");
                            }
                            else{
                                List<List<Integer>> cannons = new ArrayList<>();
                                List<List<Integer>> batteries = new ArrayList<>();
                                for (int i = 0; i < firstParameters.size(); i += 2){
                                    String rowStr = firstParameters.get(i);
                                    String colStr = firstParameters.get(i+1);

                                    int rowEng = Integer.parseInt(rowStr);
                                    int colEng = Integer.parseInt(colStr);
                                    boolean checkPosition = validTilePosition(rowEng, colEng);
                                    if ((rowEng < 5 || rowEng > 9 || colEng < 4 || colEng > 10) ||  !checkPosition) {
                                        newMessage = new Message("String",null,"Invalid row or column.");
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                        //client.invalidCommand("Invalid row or column.");
                                        break;
                                    }
                                    List<Integer> cannonRow = new ArrayList<>();
                                    cannonRow.add(rowEng-5);
                                    cannonRow.add(colEng-4);
                                    cannons.add(cannonRow);
                                }
                                for (int j=0; j < secondParameters.size(); j += 3) {
                                    //Getting positions and value of batteries
                                    String rowBatStr = secondParameters.get(j);
                                    String colBatStr = secondParameters.get(j++);
                                    String valueBatStr = secondParameters.get(j += 2);

                                    int rowBat = Integer.parseInt(rowBatStr);
                                    int colBat = Integer.parseInt(colBatStr);
                                    int valueBat = Integer.parseInt(valueBatStr);
                                    boolean checkPosition = validTilePosition(rowBat, colBat);
                                    if ((rowBat < 5 || rowBat > 9 || colBat < 4 || colBat > 10) || !checkPosition) {
                                        newMessage = new Message("String",null,"Invalid row or column.");
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                        //client.invalidCommand("Invalid row or column.");
                                        break;
                                    } else {
                                        if (valueBat < 1 || valueBat > 3) {
                                            newMessage = new Message("String",null,"Invalid value. It must be between 1 and 3");
                                            objOut.writeObject(newMessage);
                                            objOut.flush();
                                            //client.invalidCommand("Invalid value. It must be between 1 and 3");
                                            break;
                                        } else {
                                            List<Integer> batteryRow = new ArrayList<>();
                                            batteryRow.add(rowBat - 5);
                                            batteryRow.add(colBat - 4);
                                            batteryRow.add(valueBat);
                                            batteries.add(batteryRow);
                                        }
                                    }
                                }
                                try{
                                    // batteries cannot be ArrayList<Pair<Integer, Integer>>
                                    ActivateCannonsEvent event = new ActivateCannonsEvent(player, cannons, batteries);
                                    game.getGameState().handleEvent(event);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        }

                    }
                    else{
                        newMessage = new Message("String",null,"/activatecannons needs two sets of parameters");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/activatecannons needs two sets of parameters");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "activateshield" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                        if (firstParameters.size() != 2) {
                            newMessage = new Message("String",null,"First set of /activateshield must have two parameters.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("First set of /activateshield must have two parameters.");
                        }
                        else if (secondParameters.size() != 2){
                            newMessage = new Message("String",null,"Second set of /activateshield must have two parameters.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("Second set of /activateshield must have two parameters.");
                        }
                        else{
                            String rowShieldStr = firstParameters.get(0);
                            String colShieldStr = firstParameters.get(1);

                            String rowBatStr = secondParameters.get(0);
                            String colBatStr = secondParameters.get(1);

                            int rowShield = Integer.parseInt(rowShieldStr);
                            int colShield = Integer.parseInt(colShieldStr);
                            int rowBat = Integer.parseInt(rowBatStr);
                            int colBat = Integer.parseInt(colBatStr);
                            boolean checkPositionShield = validTilePosition(rowShield, colShield);
                            boolean checkPositionBat = validTilePosition(rowBat, colBat);

                            if (checkPositionShield && checkPositionBat){
                                if ((rowShield < 5 || rowShield > 9 || colShield < 4 || colShield > 10) || (rowBat < 5 || rowBat > 9 || colBat < 4 || colBat > 10)) {
                                    newMessage = new Message("String",null,"Invalid row or column.");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Invalid row or column.");
                                }
                                else{
                                    try{
                                        ActivateShieldEvent event = new ActivateShieldEvent(player, rowShield-5, colShield-4, rowBat-5, colBat-4);
                                        game.getGameState().handleEvent(event);
                                    }catch (IllegalEventException e){
                                        newMessage = new Message("String",null,e.getMessage());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    }
                                }
                            }
                            else{
                                newMessage = new Message("String",null,"Invalid row or column.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid row or column");
                            }
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/activateshield needs two sets of parameters");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/activateshield needs two sets of parameters");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game");
                }
            } //ok
            case "removecargo" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if(firstParameters.size() != 3){
                            newMessage = new Message("String",null,"/removecargo supports only 3 parameters.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/removecargo supports only 3 parameters.");
                        }
                        else{
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);
                            String valueStr = firstParameters.get(2);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            int value = Integer.parseInt(valueStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                newMessage = new Message("String",null,"Invalid row or column.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                if (value < 1 || value > 3) {
                                    newMessage = new Message("String",null,"Invalid value. It must be between 1 and 3");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Invalid value. It must be between 1 and 3");
                                }
                                else{
                                    try{
                                        RemoveCargoEvent event = new RemoveCargoEvent(player, row-5, col-4, value);
                                        game.getGameState().handleEvent(event);
                                    }catch (IllegalEventException e){
                                        newMessage = new Message("String",null,e.getMessage());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    }
                                }

                            }
                        }

                    }
                    else{
                        newMessage = new Message("String",null,"/removecargo supports only one set of parameters");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/removecargo supports only one set of parameters");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "addcargo" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() != 3) {
                            newMessage = new Message("String",null,"/addcargo supports only 3 parameters.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/addcargo supports only 3 parameters.");
                        } else {
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);
                            String valueStr = firstParameters.get(2);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            int value = Integer.parseInt(valueStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                newMessage = new Message("String",null,"Invalid row or column.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid row or column.");
                            } else {
                                if (value < 1 || value > 3) {
                                    newMessage = new Message("String",null,"Invalid value. It must be between 1 and 3");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Invalid value. It must be between 1 and 3");
                                } else {
                                    try{
                                        AddCargoEvent event = new AddCargoEvent(player, row - 5, col - 4, value);
                                        game.getGameState().handleEvent(event);
                                    }catch (IllegalEventException e){
                                        newMessage = new Message("String",null,e.getMessage());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    }
                                }
                            }
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/addcargo supports only one set of parameters");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/addcargo supports only one set of parameters");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "switchcargo" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                        if (firstParameters.size() != 3 || secondParameters.size() != 2) {
                            newMessage = new Message("String",null,"For each cargo it's needed to specify the position and the quantity to switch.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("For each cargo it's needed to specify the position and the quantity to switch.");
                        } else {
                            String prevRowStr = firstParameters.get(0);
                            String prevColStr = firstParameters.get(1);
                            String prevValueStr = firstParameters.get(2);
                            String newRowStr = secondParameters.get(0);
                            String newColStr = secondParameters.get(1);

                            int prevRow = Integer.parseInt(prevRowStr);
                            int prevCol = Integer.parseInt(prevColStr);
                            int prevValue = Integer.parseInt(prevValueStr);
                            int newRow = Integer.parseInt(newRowStr);
                            int newCol = Integer.parseInt(newColStr);

                            boolean checkPositionPrev = validTilePosition(prevRow, prevCol);
                            boolean checkPositionNew = validTilePosition(newRow, newCol);
                            if ((prevRow < 5 || prevRow > 9 || prevCol < 4 || prevCol > 10) || (newRow < 5 || newRow > 9 || newCol < 4 || newCol > 10) || !checkPositionPrev || !checkPositionNew) {
                                newMessage = new Message("String",null,"Invalid row or column.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid row or column.");
                            } else if (prevValue < 1 || prevValue > 3) {
                                newMessage = new Message("String",null,"Invalid value. It must be between 1 and 3");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid value. It must be between 1 and 3");
                            } else {
                                try{
                                    SwitchCargoEvent event = new SwitchCargoEvent(player, prevRow - 5, prevCol - 4, newRow - 5, newCol - 4, prevValue);
                                    game.getGameState().handleEvent(event);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        }

                    }else{
                        newMessage = new Message("String",null,"/switchcargo needs two sets of parameters.");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/switchcargo needs two sets of parameters.");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "ejectpeople" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()){
                        List<List<Integer>> people = new ArrayList<>();
                        if (firstParameters.size() % 3 != 0){
                            newMessage = new Message("String",null,"/ejectpeople needs a numbero of parameters multiple of 3.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/ejectpeople needs a numbero of parameters multiple of 3.");
                        }
                        else{
                            for (int i = 0; i < firstParameters.size(); i += 3){
                                String rowStr = firstParameters.get(i);
                                String colStr = firstParameters.get(i+1);
                                String valueStr = firstParameters.get(i+2);

                                int row = Integer.parseInt(rowStr);
                                int col = Integer.parseInt(colStr);
                                int value = Integer.parseInt(valueStr);
                                boolean checkPosition = validTilePosition(row, col);
                                if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                    newMessage = new Message("String",null,"Invalid row or column.");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Invalid row or column.");
                                    break;
                                }
                                else {
                                    if (value < 1 || value > 2) {
                                        newMessage = new Message("String",null,"Invalid value. It must be 1 or 2.");
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                        //client.invalidCommand("Invalid value. It must be 1 or 2.");
                                        break;
                                    } else {
                                        List<Integer> peopleRow = new ArrayList<>();
                                        peopleRow.add(row - 5);
                                        peopleRow.add(col - 4);
                                        peopleRow.add(value);
                                        people.add(peopleRow);
                                        try{
                                            EjectPeopleEvent event = new EjectPeopleEvent(player, people);
                                            game.getGameState().handleEvent(event);
                                        }catch (IllegalEventException e){
                                            newMessage = new Message("String",null,e.getMessage());
                                            objOut.writeObject(newMessage);
                                            objOut.flush();
                                        }
                                    }
                                }
                            }
                        }


                    }
                    else{
                        newMessage = new Message("String",null,"/ejectpeople supports only one set of parameters");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/ejectpeople supports only one set of parameters");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "giveup" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                        try{
                            GiveUpEvent event = new GiveUpEvent(player);
                            game.getGameState().handleEvent(event);
                        }catch (IllegalEventException e){
                            newMessage = new Message("String",null,e.getMessage());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/giveup doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/giveup doesn't support parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "viewinventory" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                        //ViewInventoryEvent event = new ViewInventoryEvent(player);
                        //game.getGameState().handleEvent(event);

                    }
                    else{
                        newMessage = new Message("String",null,"/viewinventory doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/viewinventory doesn't support parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "claimreward" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 1){
                            String engage = firstParameters.get(0);
                            if (engage.equals("true") || engage.equals("false")){
                                boolean engageBool = Boolean.parseBoolean(engage);
                                try{
                                    ClaimRewardEvent event = new ClaimRewardEvent(player, engageBool);
                                    game.getGameState().handleEvent(event);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                            else{
                                newMessage = new Message("String",null,"The parameter must be either true or false.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("The parameter must be either true or false.");
                            }
                        }
                        else{
                            newMessage = new Message("String",null,"/claimreward supports only one parameter.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/claimreward supports only one parameter.");
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/claimreward supports only one paramter.");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/claimreward supports only one paramter.");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            }
            case "choosesubship" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 2){ // Choosing by specifying a random tile in the subship you want to keep
                            String rowStr = firstParameters.get(0);
                            String colstr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colstr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                newMessage = new Message("String",null,"Invalid row or column.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                try{
                                    ChooseSubShipEvent event = new ChooseSubShipEvent(player, row-5, col-4);
                                    game.getGameState().handleEvent(event);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }

                        }
                        else{
                            newMessage = new Message("String",null,"/choosesubship supports only one parameter.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/choosesubship supports only one parameter.");
                        }
                    }
                    else {
                        newMessage = new Message("String",null,"/choosesubship supports only one set of parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/choosesubship supports only one set of parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "nochoice" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                        try{
                            NoChoiceEvent event = new NoChoiceEvent(player);
                            game.getGameState().handleEvent(event);
                        }catch (IllegalEventException e){
                            newMessage = new Message("String",null,e.getMessage());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/nochoice doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/nochoice doesn't support parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "done" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                        try{
                            DoneEvent event = new DoneEvent(player);
                            game.getGameState().handleEvent(event);
                        }catch (IllegalEventException e){
                            newMessage = new Message("String",null,e.getMessage());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/done doesn't support parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/done doesn't support parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "placeorangealien" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 2){
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                newMessage = new Message("String",null,"Invalid row or column.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                try{
                                    PlaceOrangeAlienEvent event = new PlaceOrangeAlienEvent(player, row-5, col-4);
                                    game.getGameState().handleEvent(event);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }

                        }
                        else{
                            newMessage = new Message("String",null,"/placeorangealien supports only one set of parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/placeorangealien supports only one set of parameters!");
                        }
                    }
                    else {
                        newMessage = new Message("String",null,"/placeorangealien supports only one set of parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/placeorangealien supports only one set of parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "placepurplealien" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 2){
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                newMessage = new Message("String",null,"Invalid row or column.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                try{
                                    PlacePurpleAlienEvent event = new PlacePurpleAlienEvent(player, row-5, col-4);
                                    game.getGameState().handleEvent(event);
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }

                        }
                        else{
                            newMessage = new Message("String",null,"/placepurplealien supports only one set of parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/placepurplealien supports only one set of parameters!");
                        }
                    }
                    else {
                        newMessage = new Message("String",null,"/placepurplealien supports only one set of parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/placepurplealien supports only one set of parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "removetile" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 2){
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                newMessage = new Message("String",null,"Invalid row or column.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                try{
                                    RemoveTileEvent event = new RemoveTileEvent(player, row-5, col-4);
                                    game.getGameState().handleEvent(event);
                                    newMessage = new Message("Game",game,"viewMyShip");
                                    newMessage.setNickname(msg.getNickname());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    //client.viewMyShip(game, client.getNickname());
                                }catch (IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }

                        }
                        else{
                            newMessage = new Message("String",null,"/removetile supports only one set of parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/removetile supports only one set of parameters!");
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/removetile supports only one set of parameters!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/removetile supports only one set of parameters!");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "chooseplanet" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() != 1){
                            newMessage = new Message("String",null,"/chooseplanet supports only one parameter.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/chooseplanet supports only one parameter.");
                        }
                        else{
                            String indexStr = firstParameters.get(0);
                            int index = Integer.parseInt(indexStr);
                            try{
                                ChoosePlanetEvent event = new ChoosePlanetEvent(player, index);
                                game.getGameState().handleEvent(event);
                            }catch (IllegalEventException e){
                                newMessage = new Message("String",null,e.getMessage());
                                objOut.writeObject(newMessage);
                                objOut.flush();
                            }
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/chooseplanet supports only one set of parameters.");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("/chooseplanet supports only one set of parameters.");
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    //client.invalidCommand("You are not connected to the game!");
                }
            }
            case "nickname_pong"->{
                //serve per settare nickname a SocketClientHandler
            }
            default -> {
                Message newMessage;
                newMessage = new Message("String",null,"Invalid command. Type /help for a list of available commands.");
                objOut.writeObject(newMessage);
                objOut.flush();
                //client.invalidCommand("Invalid command. Type /help for a list of available commands.");
            }
        }
    }

    public void disconnect(String nickname) {
        Player player = checkPlayer(nickname);
        if (player != null) {
            //gestire la disconessione
            Optional<Player> playerOptional = game.getListOfPlayers().stream()
                    .filter(player1 -> player1.getNickname().equals(nickname))
                    .findAny();
            if (playerOptional.isPresent()) {
                Player player1 = playerOptional.get();
                DisconnectEvent event = new DisconnectEvent(player1);
                game.getGameState().handleEvent(event,game);
                //player.setOnlineStatus(false);
            }

        }
    }

    /*

    RMI CONTROLLER

     */

    public void handleUserInput(VirtualClient client, String input) throws RemoteException {
        if (input == null || !input.startsWith("/")) {
            client.invalidCommand("Invalid command");
            //System.out.println("Invalid command");
            return;
        }
        // Input to lowercase
        input = input.toLowerCase();

        // Remove / from the command
        String cleanInput = input.substring(1).trim();

        // Split input into command and parameters
        String[] parts = cleanInput.split(" ", 2);
        String command = parts[0];
        String par = parts.length > 1 ? parts[1].trim() : "";

        String[] subParameters = par.split(";", 2);

        List<String> firstParameters = new ArrayList<>();
        List<String> secondParameters = new ArrayList<>();

        if (subParameters.length >= 1 && !subParameters[0].isBlank()) {
            firstParameters = Arrays.stream(subParameters[0].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }
        if (subParameters.length == 2 && !subParameters[1].isBlank()) {
            secondParameters = Arrays.stream(subParameters[1].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }
        executeCommand(command, firstParameters, secondParameters, client);
    }

    /**
     * Executes the specified command with the given parameters.
     *
     * @param command          The command to execute.
     * @param firstParameters  The first set of parameters for the command.
     * @param secondParameters The second set of parameters for the command.
     * @param client           The VirtualClient instance representing the client.
     */

    private void executeCommand(String command, List<String> firstParameters, List<String> secondParameters, VirtualClient client) throws RemoteException {
        switch (command) {
            case "help" -> {
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                    client.invalidCommand("/help doesn't support parameters, but here is the help command anyway!");
                }
                client.helpMessage();
            } //ok
            case "viewcard" -> {
                client.viewCard(game);
            }
            case "viewleaderboard" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                        client.invalidCommand("/viewleaderboard doesn't support parameters!");
                    }
                    client.viewLeaderboard(game);
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }

            } //ok
            case "viewmyship" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                        client.invalidCommand("/viewship doesn't support parameters!");
                    }
                    client.viewMyShip(game, client.getNickname());
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }

            }
            case "viewtilepile" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                        client.invalidCommand("/viewtilepile doesn't support parameters!");
                    }
                    client.viewTilepile(game);
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            }
            case "viewships" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                        client.invalidCommand("/viewships doesn't support parameters!");
                    }
                    client.viewShips(game);
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "riconnect" ->{
                if(secondParameters.isEmpty()){
                    if(firstParameters.size()==1){
                        String clientNickname = client.getNickname();
                        String nickname = firstParameters.get(0);
                        if(clientNickname==null){
                            //trovare il Player di quel nickname
                            Optional<Player> playerOptional = game.getListOfPlayers().stream()
                                    .filter(player1 -> player1.getNickname().equals(nickname))
                                    .findAny();
                            if (playerOptional.isPresent()) {
                                //gestire la riconessione
                                //gestire lo status se offline riconetti,se no manda errore
                                Player player = playerOptional.get();
                                if(player.getOnlineStatus()){
                                    client.invalidCommand("Il giocatore è online");
                                }else{
                                    client.setNickname(nickname);
                                    player.setOnlineStatus(true);
                                    client.connectView(game);
                                }

                            } else {
                                client.invalidCommand("Il nickname di Player is not present");
                            }
                        }else{
                            client.invalidCommand("/You already riconnected to the game");
                        }
                    }else{
                        client.invalidCommand("/riconnect request one parameter.");
                    }
                }else{
                    client.invalidCommand("/riconnect request one parameter.");
                }
            }
            case "connect" -> {
                //GameState gameState = game.getGameState();
                if (secondParameters.isEmpty()) {
                    if (firstParameters.size() == 1) {
                        String clientNickname = client.getNickname();
                        String nickname = firstParameters.get(0);
                        if (clientNickname != null) {
                            client.invalidCommand("It's forbidden for one client to connect to the game more than once!");
                        } else {
                            Optional<Player> playerOptional = game.getListOfPlayers().stream()
                                    .filter(player1 -> player1.getNickname().equals(nickname))
                                    .findAny();

                            if (!playerOptional.isPresent()) {
                                try {
                                    ConnectEvent event = new ConnectEvent(nickname, "localhost");
                                    game.getGameState().handleEvent(event, game);
                                    client.setNickname(nickname);
                                    client.setMainCabin(game); // TODO fix
                                    client.connectView(game);
                                } catch (IllegalArgumentException e) {
                                    client.invalidCommand("Error: " + e.getMessage());
                                }

                            } else {
                                client.invalidCommand("Nickname already taken, please choose another one!");
                            }
                        }
                    } else {
                            client.invalidCommand("/connect request one parameter.");
                    }
                } else if (secondParameters.size() == 1 && game.getListOfPlayers().isEmpty()){
                    if (firstParameters.size() == 1){
                        String clientNickname = client.getNickname();
                        String nickname = firstParameters.get(0);
                        if(clientNickname != null){
                            client.invalidCommand("It's forbidden for one client to connect to the game more than once!");
                        } else{
                            Optional<Player> playerOptional = game.getListOfPlayers().stream()
                                    .filter(player1 -> player1.getNickname().equals(nickname))
                                    .findAny();
                            if (!playerOptional.isPresent()) {
                                String numberOfPlayersStr = secondParameters.get(0);
                                int numberOfPlayers = Integer.parseInt(numberOfPlayersStr);
                                if (numberOfPlayers < 2 || numberOfPlayers > 4){
                                    client.invalidCommand("Number of players not valid. It must be between 2 and 4");
                                }
                                else {
                                    try {
                                        ConnectEvent event = new ConnectEvent(nickname, "localhost");
                                        game.getGameState().handleEvent(event, game);
                                        SetNumberOfPlayersEvent setNumberOfPlayersEvent = new SetNumberOfPlayersEvent(numberOfPlayers);
                                        game.getGameState().handleEvent(setNumberOfPlayersEvent);
                                        client.setNickname(nickname);
                                        client.setMainCabin(game);
                                        client.connectView(game);
                                    } catch (IllegalArgumentException e) {
                                        client.invalidCommand("Error: " + e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
                else if (secondParameters.size() == 1 && !game.getListOfPlayers().isEmpty()){
                    client.invalidCommand("You are not the first player. You cannot set the number of players.");
                } else{
                    client.invalidCommand("/connect request one parameter.");
                }
            } //ok
            case "disconnect" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        DisconnectEvent event = new DisconnectEvent(player);
                        game.getGameState().handleEvent(event,game);
                    } else {
                        client.invalidCommand("/disconnect doesn't support parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "setnumberofplayers" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 1) {
                            String numberOfPlayersStr = firstParameters.get(0);
                            int numberOfPlayers = Integer.parseInt(numberOfPlayersStr);
                            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                                client.invalidCommand("Number of players not valid. It must be between 2 and 4");
                            } else {
                                SetNumberOfPlayersEvent event = new SetNumberOfPlayersEvent(numberOfPlayers);
                                game.getGameState().handleEvent(event);
                            }
                        } else {
                            client.invalidCommand("/setnumberofplayers supports only one parameter!");
                        }
                    } else {
                        client.invalidCommand("/setnumberofplayers supports only one parameter!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "pickuptile" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 2) {
                            String tileRow = firstParameters.get(0);
                            String tileColumn = firstParameters.get(1);
                            int tileRowInt = Integer.parseInt(tileRow);
                            int tileColumnInt = Integer.parseInt(tileColumn);
                            int tilePositionInt = (tileRowInt * 16) + tileColumnInt;
                            if (tilePositionInt >= 0 && tilePositionInt <= 152) {
                                PickUpTileEvent event = new PickUpTileEvent(player, tilePositionInt);
                                game.getGameState().handleEvent(event);
                                Tile currentTile = player.getShip().getTileInHand();
                                List<VirtualClient> clientsRMI = rmiServer.getClients();
                                for (VirtualClient virtualClient : clientsRMI) {
                                    if (virtualClient.getNickname().equals(client.getNickname())) {
                                        virtualClient.viewMyShip(game, client.getNickname());
                                    }
                                    else {
                                        virtualClient.viewTilepile(game);
                                    }
                                }
                            } else {
                                client.invalidCommand("Tile position not valid. It must be between 1 and 156");
                            }
                        } else {
                            client.invalidCommand("/pickuptile supports only one parameter!");
                        }
                    } else {
                        client.invalidCommand("/pickuptile supports only one parameter!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } // ok
            case "rotate" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 1) {
                            String side = firstParameters.get(0);
                            RotateTileEvent event = new RotateTileEvent(player, side);
                            game.getGameState().handleEvent(event);
                            client.viewMyShip(game, client.getNickname());
                        } else {
                            client.invalidCommand("/rotatetile supports only one parameter!");
                        }
                    } else {
                        client.invalidCommand("/rotatetile supports only one parameter.");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "putdowntile" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        PutDownTileEvent event = new PutDownTileEvent(player);
                        game.getGameState().handleEvent(event);
                        List<VirtualClient> clientsRMI = rmiServer.getClients();
                        for (VirtualClient virtualClient : clientsRMI) {
                            virtualClient.viewTilepile(game);
                        }
                    } else {
                        client.invalidCommand("/putdowntile doesn't support parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "placetile" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 2) {
                            String row = firstParameters.get(0);
                            String column = firstParameters.get(1);
                            int rowInt = Integer.parseInt(row);
                            int columnInt = Integer.parseInt(column);
                            boolean checkPosition = validTilePosition(rowInt, columnInt);
                            if ((rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) || !checkPosition) {
                                client.invalidCommand("Row or column not valid. It must be between 5 and 9 for rows and between 4 and 10 for columns");
                            } else {
                                PlaceTileEvent event = new PlaceTileEvent(player, rowInt - 5, columnInt - 4);
                                game.getGameState().handleEvent(event);
                                client.connectView(game);
                            }
                        } else {
                            client.invalidCommand("/placetile supports only two parameters!");
                        }
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "reservetile" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 1) {
                            String indexStr = firstParameters.get(0);
                            int index = Integer.parseInt(indexStr);
                            if (index < 1 || index > 2) {
                                client.invalidCommand("Index not valid. It must be either 1 or 2");
                            } else {
                                ReserveTileEvent event = new ReserveTileEvent(player, index - 1);
                                game.getGameState().handleEvent(event);
                                client.connectView(game);
                            }
                        }
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "fliphourglass" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        FlipHourglassEvent event = new FlipHourglassEvent();
                        game.getGameState().handleEvent(event,game);
                        client.printMessage("You flipped "+ hourglassCounter+" hourglass");
                        for(VirtualClient client1: rmiServer.getClients()){
                            if(!client1.getNickname().equals(client.getNickname())){
                                client1.printMessage(client.getNickname()+ " flipped "+ hourglassCounter+" hourglass");
                            }
                        }
                        for(SocketClientHandler client1: socketServer.getClientsList()){
                            ObjectOutputStream objOut1 = client1.getObjOut();
                                Message newMessage = new Message("String",null,client.getNickname()+ " flipped "+ hourglassCounter+" hourglass");
                                try{
                                    objOut1.writeObject(newMessage);
                                    objOut1.flush();
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                        }
                        hourglassCounter++;
                    } else {
                        client.invalidCommand("/fliphourglass doesn't support parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "setposition" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 1) {
                            String pos = firstParameters.get(0);
                            int position = Integer.parseInt(pos);
                            int maxNumberOfPlayers = game.getNumberOfPlayers();
                            if (position < 1 || position > maxNumberOfPlayers && maxNumberOfPlayers != -1) {
                                client.invalidCommand("Position not valid. It must be between 1 and " + maxNumberOfPlayers);
                            } else if (maxNumberOfPlayers == -1) {
                                client.invalidCommand("You need to set the number of players before setting the position.");
                            } else {
                                try {
                                    SetPositionEvent event = new SetPositionEvent(player, position);
                                    game.getGameState().handleEvent(event);
                                    client.viewLeaderboard(game);
                                    List<VirtualClient> clientsRMI = rmiServer.getClients();
                                    for (VirtualClient virtualClient : clientsRMI) {
                                        if (!virtualClient.getNickname().equals(client.getNickname())) {
                                            virtualClient.printMessage(player.getNickname() + " has set the position to " + position);
                                        }
                                        client.printMessage(game.getGameState().toString());

                                        if (game.getGameState() instanceof TravellingState) {
                                            virtualClient.viewCard(game);
                                        }
                                    }
                                    for(SocketClientHandler client1: socketServer.getClientsList()){
                                        if (game.getGameState() instanceof TravellingState) {
                                            ObjectOutputStream objOut1 = client1.getObjOut();
                                            Message newMessage = new Message("Game",game,"viewCard");
                                            try{
                                                objOut1.writeObject(newMessage);
                                                objOut1.flush();
                                                objOut1.reset();
                                            }catch(IOException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } catch (IllegalArgumentException e) {
                                    client.invalidCommand("Error: " + e.getMessage());
                                }
                            }
                        }

                    } else {
                        client.invalidCommand("/setposition requires only one parameter!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "pickupfromship" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        PickUpFromShipEvent event = new PickUpFromShipEvent(player);
                        game.getGameState().handleEvent(event);
                        client.viewMyShip(game, client.getNickname());
                    } else {
                        client.invalidCommand("/pickupfromship doesn't support parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "pickupreservedtile" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 1) {
                            String indexStr = firstParameters.get(0);
                            int index = Integer.parseInt(indexStr);
                            Ship playerShip = player.getShip();
                            int numberOfReservedTiles = playerShip.getReservedTiles().size();
                            if (index < 1 || index > numberOfReservedTiles) {
                                client.invalidCommand("Index not valid. It must be either 1 or 2");
                            } else {
                                PickUpReservedTileEvent event = new PickUpReservedTileEvent(player, index - 1);
                                game.getGameState().handleEvent(event);
                                client.viewMyShip(game, client.getNickname());
                            }
                        }
                    } else {
                        client.invalidCommand("/pickupreservedtile supports only one parameter.");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "activateengines" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() && !secondParameters.isEmpty()) {
                        if (firstParameters.size() % 2 != 0) {
                            client.invalidCommand("/activateengines needs an even number of row and column for engines.");
                        } else {
                            if (secondParameters.size() % 3 != 0) {
                                client.invalidCommand("for each batteries specify the position and the quantity to remove.");
                            } else {
                                List<List<Integer>> engines = new ArrayList<>();
                                List<List<Integer>> batteries = new ArrayList<>();
                                for (int i = 0; i < firstParameters.size(); i += 2) {
                                    String rowEngStr = firstParameters.get(i);
                                    String colEngStr = firstParameters.get(i + 1);

                                    int rowEng = Integer.parseInt(rowEngStr);
                                    int colEng = Integer.parseInt(colEngStr);
                                    boolean checkPosition = validTilePosition(rowEng, colEng);
                                    if ((rowEng < 5 || rowEng > 9 || colEng < 4 || colEng > 10) || !checkPosition) {
                                        client.invalidCommand("Invalid row or column.");
                                        break;
                                    }
                                    List<Integer> engineRow = new ArrayList<>();
                                    engineRow.add(rowEng - 5);
                                    engineRow.add(colEng - 4);
                                    engines.add(engineRow);
                                }
                                for (int j = 0; j < secondParameters.size(); j += 3) {
                                    //Getting positions and value of batteries
                                    String rowBatStr = secondParameters.get(j);
                                    String colBatStr = secondParameters.get(j++);
                                    String valueBatStr = secondParameters.get(j += 2);

                                    int rowBat = Integer.parseInt(rowBatStr);
                                    int colBat = Integer.parseInt(colBatStr);
                                    int valueBat = Integer.parseInt(valueBatStr);
                                    boolean checkPosition = validTilePosition(rowBat, colBat);
                                    if ((rowBat < 5 || rowBat > 9 || colBat < 4 || colBat > 10) || !checkPosition) {
                                        client.invalidCommand("Invalid row or column.");
                                        break;
                                    } else {
                                        if (valueBat < 1 || valueBat > 3) {
                                            client.invalidCommand("Invalid value. It must be between 1 and 3");
                                            break;
                                        } else {
                                            List<Integer> batteryRow = new ArrayList<>();
                                            batteryRow.add(rowBat - 5);
                                            batteryRow.add(colBat - 4);
                                            batteryRow.add(valueBat);
                                            batteries.add(batteryRow);
                                        }
                                    }
                                }
                                ActivateEnginesEvent event = new ActivateEnginesEvent(player, engines, batteries);
                                game.getGameState().handleEvent(event);
                            }
                        }

                    } else {
                        client.invalidCommand("/activateengines needs two sets of parameters");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "activatecannons" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() && !secondParameters.isEmpty()) {
                        if (firstParameters.size() % 2 != 0) {
                            client.invalidCommand("/activatecannons needs an even number of row and column for cannons.");
                        } else {
                            if (secondParameters.size() % 3 != 0) {
                                client.invalidCommand("for each batteries specify the position and the quantity to remove.");
                            } else {
                                List<List<Integer>> cannons = new ArrayList<>();
                                List<List<Integer>> batteries = new ArrayList<>();
                                for (int i = 0; i < firstParameters.size(); i += 2) {
                                    String rowStr = firstParameters.get(i);
                                    String colStr = firstParameters.get(i + 1);

                                    int rowEng = Integer.parseInt(rowStr);
                                    int colEng = Integer.parseInt(colStr);
                                    boolean checkPosition = validTilePosition(rowEng, colEng);
                                    if ((rowEng < 5 || rowEng > 9 || colEng < 4 || colEng > 10) || !checkPosition) {
                                        client.invalidCommand("Invalid row or column.");
                                        break;
                                    }
                                    List<Integer> cannonRow = new ArrayList<>();
                                    cannonRow.add(rowEng - 5);
                                    cannonRow.add(colEng - 4);
                                    cannons.add(cannonRow);
                                }
                                for (int j = 0; j < secondParameters.size(); j += 3) {
                                    //Getting positions and value of batteries
                                    String rowBatStr = secondParameters.get(j);
                                    String colBatStr = secondParameters.get(j++);
                                    String valueBatStr = secondParameters.get(j += 2);

                                    int rowBat = Integer.parseInt(rowBatStr);
                                    int colBat = Integer.parseInt(colBatStr);
                                    int valueBat = Integer.parseInt(valueBatStr);
                                    boolean checkPosition = validTilePosition(rowBat, colBat);
                                    if ((rowBat < 5 || rowBat > 9 || colBat < 4 || colBat > 10) || !checkPosition) {
                                        client.invalidCommand("Invalid row or column.");
                                        break;
                                    } else {
                                        if (valueBat < 1 || valueBat > 3) {
                                            client.invalidCommand("Invalid value. It must be between 1 and 3");
                                            break;
                                        } else {
                                            List<Integer> batteryRow = new ArrayList<>();
                                            batteryRow.add(rowBat - 5);
                                            batteryRow.add(colBat - 4);
                                            batteryRow.add(valueBat);
                                            batteries.add(batteryRow);
                                        }
                                    }
                                }
                                // batteries cannot be ArrayList<Pair<Integer, Integer>>
                                ActivateCannonsEvent event = new ActivateCannonsEvent(player, cannons, batteries);
                                game.getGameState().handleEvent(event);
                            }
                        }

                    } else {
                        client.invalidCommand("/activatecannons needs two sets of parameters");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "activateshield" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() && !secondParameters.isEmpty()) {
                        if (firstParameters.size() != 2) {
                            client.invalidCommand("First set of /activateshield must have two parameters.");
                        } else if (secondParameters.size() != 2) {
                            client.invalidCommand("Second set of /activateshield must have two parameters.");
                        } else {
                            String rowShieldStr = firstParameters.get(0);
                            String colShieldStr = firstParameters.get(1);

                            String rowBatStr = secondParameters.get(0);
                            String colBatStr = secondParameters.get(1);

                            int rowShield = Integer.parseInt(rowShieldStr);
                            int colShield = Integer.parseInt(colShieldStr);
                            int rowBat = Integer.parseInt(rowBatStr);
                            int colBat = Integer.parseInt(colBatStr);
                            boolean checkPositionShield = validTilePosition(rowShield, colShield);
                            boolean checkPositionBat = validTilePosition(rowBat, colBat);

                            if (checkPositionShield && checkPositionBat) {
                                if ((rowShield < 5 || rowShield > 9 || colShield < 4 || colShield > 10) || (rowBat < 5 || rowBat > 9 || colBat < 4 || colBat > 10)) {
                                    client.invalidCommand("Invalid row or column.");
                                } else {
                                    ActivateShieldEvent event = new ActivateShieldEvent(player, rowShield - 5, colShield - 4, rowBat - 5, colBat - 4);
                                    game.getGameState().handleEvent(event);
                                }
                            } else {
                                client.invalidCommand("Invalid row or column");
                            }
                        }
                    } else {
                        client.invalidCommand("/activateshield needs two sets of parameters");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game");
                }
            } //ok
            case "removecargo" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() != 3) {
                            client.invalidCommand("/removecargo supports only 3 parameters.");
                        } else {
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);
                            String valueStr = firstParameters.get(2);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            int value = Integer.parseInt(valueStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            } else {
                                if (value < 1 || value > 3) {
                                    client.invalidCommand("Invalid value. It must be between 1 and 3");
                                } else {
                                    RemoveCargoEvent event = new RemoveCargoEvent(player, row - 5, col - 4, value);
                                    game.getGameState().handleEvent(event);
                                }

                            }
                        }

                    } else {
                        client.invalidCommand("/removecargo supports only one set of parameters");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "addcargo" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() != 3) {
                            client.invalidCommand("/addcargo supports only 3 parameters.");
                        } else {
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);
                            String valueStr = firstParameters.get(2);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            int value = Integer.parseInt(valueStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            } else {
                                if (value < 1 || value > 3) {
                                    client.invalidCommand("Invalid value. It must be between 1 and 3");
                                } else {
                                    AddCargoEvent event = new AddCargoEvent(player, row - 5, col - 4, value);
                                    game.getGameState().handleEvent(event);
                                }
                            }
                        }
                    } else {
                        client.invalidCommand("/addcargo supports only one set of parameters");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "switchcargo" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (!firstParameters.isEmpty() && !secondParameters.isEmpty()) {
                        if (firstParameters.size() != 3 || secondParameters.size() != 2) {
                            client.invalidCommand("For each cargo it's needed to specify the position and the quantity to switch.");
                        } else {
                            String prevRowStr = firstParameters.get(0);
                            String prevColStr = firstParameters.get(1);
                            String prevValueStr = firstParameters.get(2);
                            String newRowStr = secondParameters.get(0);
                            String newColStr = secondParameters.get(1);

                            int prevRow = Integer.parseInt(prevRowStr);
                            int prevCol = Integer.parseInt(prevColStr);
                            int prevValue = Integer.parseInt(prevValueStr);
                            int newRow = Integer.parseInt(newRowStr);
                            int newCol = Integer.parseInt(newColStr);

                            boolean checkPositionPrev = validTilePosition(prevRow, prevCol);
                            boolean checkPositionNew = validTilePosition(newRow, newCol);
                            if ((prevRow < 5 || prevRow > 9 || prevCol < 4 || prevCol > 10) || (newRow < 5 || newRow > 9 || newCol < 4 || newCol > 10) || !checkPositionPrev || !checkPositionNew) {
                                client.invalidCommand("Invalid row or column.");
                            } else if (prevValue < 1 || prevValue > 3) {
                                client.invalidCommand("Invalid value. It must be between 1 and 3");
                            } else {
                                SwitchCargoEvent event = new SwitchCargoEvent(player, prevRow - 5, prevCol - 4, newRow - 5, newCol - 4, prevValue);
                                game.getGameState().handleEvent(event);
                            }
                        }

                    } else {
                        client.invalidCommand("/switchcargo needs two sets of parameters.");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "ejectpeople" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        List<List<Integer>> people = new ArrayList<>();
                        if (firstParameters.size() % 3 != 0) {
                            client.invalidCommand("/ejectpeople needs a numbero of parameters multiple of 3.");
                        } else {
                            for (int i = 0; i < firstParameters.size(); i += 3) {
                                String rowStr = firstParameters.get(i);
                                String colStr = firstParameters.get(i + 1);
                                String valueStr = firstParameters.get(i + 2);

                                int row = Integer.parseInt(rowStr);
                                int col = Integer.parseInt(colStr);
                                int value = Integer.parseInt(valueStr);
                                boolean checkPosition = validTilePosition(row, col);
                                if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                    client.invalidCommand("Invalid row or column.");
                                    break;
                                } else {
                                    if (value < 1 || value > 2) {
                                        client.invalidCommand("Invalid value. It must be 1 or 2.");
                                        break;
                                    } else {
                                        List<Integer> peopleRow = new ArrayList<>();
                                        peopleRow.add(row - 5);
                                        peopleRow.add(col - 4);
                                        peopleRow.add(value);
                                        people.add(peopleRow);
                                        EjectPeopleEvent event = new EjectPeopleEvent(player, people);
                                        game.getGameState().handleEvent(event);
                                    }
                                }
                            }
                        }


                    } else {
                        client.invalidCommand("/ejectpeople supports only one set of parameters");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "giveup" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        GiveUpEvent event = new GiveUpEvent(player);
                        game.getGameState().handleEvent(event);
                    } else {
                        client.invalidCommand("/giveup doesn't support parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "viewinventory" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        //ViewInventoryEvent event = new ViewInventoryEvent(player);
                        //game.getGameState().handleEvent(event);

                    } else {
                        client.invalidCommand("/viewinventory doesn't support parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "claimreward" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 1) {
                            String engage = firstParameters.get(0);
                            if (engage.equals("true") || engage.equals("false")) {
                                boolean engageBool = Boolean.parseBoolean(engage);
                                ClaimRewardEvent event = new ClaimRewardEvent(player, engageBool);
                                game.getGameState().handleEvent(event);
                            } else {
                                client.invalidCommand("The parameter must be either true or false.");
                            }
                        } else {
                            client.invalidCommand("/claimreward supports only one parameter.");
                        }
                    } else {
                        client.invalidCommand("/claimreward supports only one paramter.");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            }
            case "choosesubship" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 2) { // Choosing by specifying a random tile in the subship you want to keep
                            String rowStr = firstParameters.get(0);
                            String colstr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colstr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            } else {
                                ChooseSubShipEvent event = new ChooseSubShipEvent(player, row - 5, col - 4);
                                game.getGameState().handleEvent(event);
                            }

                        } else {
                            client.invalidCommand("/choosesubship supports only one parameter.");
                        }
                    } else {
                        client.invalidCommand("/choosesubship supports only one set of parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "nochoice" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        NoChoiceEvent event = new NoChoiceEvent(player);
                        game.getGameState().handleEvent(event);

                    } else {
                        client.invalidCommand("/nochoice doesn't support parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "done" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                        DoneEvent event = new DoneEvent(player);
                        game.getGameState().handleEvent(event);

                    } else {
                        client.invalidCommand("/done doesn't support parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "placeorangealien" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 2) {
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            } else {
                                PlaceOrangeAlienEvent event = new PlaceOrangeAlienEvent(player, row - 5, col - 4);
                                game.getGameState().handleEvent(event);
                                client.viewMyShip(game, client.getNickname());
                            }

                        } else {
                            client.invalidCommand("/placeorangealien supports only one set of parameters!");
                        }
                    } else {
                        client.invalidCommand("/placeorangealien supports only one set of parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "placepurplealien" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 2) {
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            } else {
                                PlacePurpleAlienEvent event = new PlacePurpleAlienEvent(player, row - 5, col - 4);
                                game.getGameState().handleEvent(event);
                                client.viewMyShip(game, client.getNickname());
                            }

                        } else {
                            client.invalidCommand("/placepurplealien supports only one set of parameters!");
                        }
                    } else {
                        client.invalidCommand("/placepurplealien supports only one set of parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "removetile" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 2) {
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            } else {
                                RemoveTileEvent event = new RemoveTileEvent(player, row - 5, col - 4);
                                game.getGameState().handleEvent(event);
                                client.viewMyShip(game, client.getNickname());
                            }

                        } else {
                            client.invalidCommand("/removetile supports only one set of parameters!");
                        }
                    } else {
                        client.invalidCommand("/removetile supports only one set of parameters!");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            } //ok
            case "chooseplanet" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() != 1) {
                            client.invalidCommand("/chooseplanet supports only one parameter.");
                        } else {
                            String indexStr = firstParameters.get(0);
                            int index = Integer.parseInt(indexStr);
                            ChoosePlanetEvent event = new ChoosePlanetEvent(player, index);
                            game.getGameState().handleEvent(event);
                        }
                    } else {
                        client.invalidCommand("/chooseplanet supports only one set of parameters.");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            }
            case "flipall" -> {
                TilePile tilePile = game.getTilePile();
                for (Tile tile : tilePile.getTilePile()){
                    tile.flip();
                }
                client.viewTilepile(game);
            }
            default -> {
                client.invalidCommand("Invalid command. Type /help for a list of available commands.");
            }
        }
    }

    /**
     * Check if a player with the given nickname is in the game.
     *
     * @param nickname The nickname of the player to check.
     * @return The Player instance if found, or null if not found.
     */

    // Check if the player is in the game
    public Player checkPlayer(String nickname) {
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }

    /**
     * Validates the tile position based on the game rules.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return True if the position is valid, false otherwise.
     */

    public boolean validTilePosition(int row, int col) {
        boolean checkPosition = false;
        if (row == 5) {
            if (col == 4 || col == 5 || col == 7 || col == 9 || col == 10) {
                checkPosition = false;
            } else {
                checkPosition = true;
            }
        } else if (row == 6) {
            if (col == 4 || col == 10) {
                checkPosition = false;
            } else {
                checkPosition = true;
            }
        } else if (row == 9) {
            if (col == 7) {
                checkPosition = false;
            } else {
                checkPosition = true;
            }
        } else {
            checkPosition = true;
        }
        return checkPosition;
    }
}