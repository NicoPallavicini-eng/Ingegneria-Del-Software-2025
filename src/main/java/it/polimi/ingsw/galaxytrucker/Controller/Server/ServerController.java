package it.polimi.ingsw.galaxytrucker.Controller.Server;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualView;
import javafx.beans.binding.IntegerBinding;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.util.*;
import java.util.function.Consumer;

/**
 * The ServerController class handles user input and manages the game state on the server side.
 * It also create the game when instantiated.
 */

public class ServerController {
    private final Game game;

    /**
     * Constructs a new ServerController and intializes a new game.
     */
    public ServerController() {
        this.game = new Game();
    }

    /**
     * Handles user input from the client and executes the corresponding command.
     *
     * @param client The VirutalView instance representing the client.
     * @param input The input string received from the client.
     */
    public void handleUserInput(VirtualView client, String input) throws RemoteException {
        if (input == null || !input.startsWith("/")) {
            System.out.println("Invalid command");
            return;
        }
        // Input to lowercase
        input = input.toLowerCase();

         // Remove / from the command
        String cleanInput = input.substring(1).trim();

        // Split input into command and parameters
        String[] parts = cleanInput.split(" ", 2);
        String command = parts[0];
        String parString = parts.length > 1 ? parts[1] : "";

        // Split parameters into two lists
        String[] subParameters = parString.split(";", 2);
        List<String> firstParameters = new ArrayList<>();
        List<String> secondParameters = new ArrayList<>();

        if (subParameters.length > 0){
            // Split parameters with comma
            firstParameters = Arrays.stream(subParameters[0].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

            secondParameters = Arrays.stream(subParameters[1].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
        }

        // Check if the command exists
        executeCommand(command, firstParameters, secondParameters, client);
    }

    /**
     * Executes the specified command with the given parameters.
     *
     * @param command   The command to execute.
     * @param firstParameters   The first set of parameters for the command.
     * @param secondParameters  The second set of parameters for the command.
     * @param client    The VirtualView instance representing the client.
     */

    private void executeCommand (String command, List<String> firstParameters, List<String> secondParameters, VirtualView client) throws RemoteException {
        switch(command){
            case "help" -> {
                GameState gameState = new GameState();
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()){
                    client.invalidCommand("/help doesn't support parameters!");
                }
                HelpEvent event = new HelpEvent();
                gameState.handleEvent(event);
            } //ok
            case "viewleaderboard" -> {
                GameState gameState = new GameState();
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()){
                    client.invalidCommand("/viewleaderboard doesn't support parameters!");
                }
                ViewLeaderboardEvent event = new ViewLeaderboardEvent();
                gameState.handleEvent(event);
            } //ok
            case "viewships" -> {
                GameState gameState = new GameState();
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()){
                    client.invalidCommand("/viewships doesn't support parameters!");
                }
                ViewShipsEvent event = new ViewShipsEvent();
                gameState.handleEvent(event);
            } //ok
            case "connect" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.isEmpty()) {
                        client.invalidCommand("/connect request one parameter.");
                    }
                    else if (firstParameters.size() == 1) {
                        String clientNickname = client.getNickname();
                        String nickname = firstParameters.get(0);
                        if (nickname != null) {
                            client.invalidCommand("It's forbidden for one client to connect to the game more than once!");
                        } else {
                            Optional<Player> playerOptional = game.getListOfPlayers().stream()
                                    .filter(player1 -> player1.getNickname().equals(nickname))
                                    .findAny();

                            if (!playerOptional.isPresent()) {
                                client.setNickname(nickname);

                                ConnectEvent event = new ConnectEvent(nickname, "localhost");
                                gameState.handleEvent(event);
                            } else {
                                client.invalidCommand("Nickname already taken, please choose another one!");

                            }
                        }
                    }
                else{
                    client.invalidCommand("/connect request one parameter.");
                }
                }
            } //ok
            case "disconnect" -> {
                GameState gameState = new GameState();
                if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                    String clientNickname = client.getNickname();
                    Player player = checkPlayer(clientNickname);
                    if (player != null){
                        DisconnectEvent event = new DisconnectEvent(player);
                        gameState.handleEvent(event);
                    }
                    else {
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else {
                    client.invalidCommand("/disconnect doesn't support parameters!");
                }
            } //ok
            case "setnumberofplayers" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 1) {
                        String numberOfPlayersStr = firstParameters.get(0);
                        int numberOfPlayers = Integer.parseInt(numberOfPlayersStr);
                        String clientNickname = client.getNickname();
                        Player player = checkPlayer(clientNickname);
                        if (player != null){
                            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                                client.invalidCommand("Number of players not valid. It must be between 2 and 4");
                            } else {
                                SetNumberOfPlayersEvent event = new SetNumberOfPlayersEvent(numberOfPlayers);
                                gameState.handleEvent(event);
                            }
                        }
                        else{
                            client.invalidCommand("You are not connected to the game!");
                        }

                    }
                    else{
                        client.invalidCommand("/setnumberofplayers supports only one parameter!");
                    }
                }
                else{
                    client.invalidCommand("/setnumberofplayers supports only one parameter!");
                }
            } //ok
            case "pickuptile" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if(firstParameters.size() == 1) {
                        String tilePosition = firstParameters.get(0);
                        int tilePositionInt = Integer.parseInt(tilePosition); //CHECK MAX NUMBER OF TILES IN TILEPILE
                        if (tilePositionInt > 0 && tilePositionInt < 156) {
                            String clientNickname = client.getNickname();
                            Player player = checkPlayer(clientNickname);
                            if (player != null) {
                                PickUpTileEvent event = new PickUpTileEvent(player, tilePositionInt);
                                gameState.handleEvent(event);
                            } else {
                                client.invalidCommand("You are not connected to the game!");
                            }
                        } else {
                            client.invalidCommand("Tile position not valid. It must be between 1 and 156");
                        }
                    }
                    else{
                        client.invalidCommand("/pickuptile supports only one parameter!");
                    }
                }
                else{
                    client.invalidCommand("/pickuptile supports only one parameter!");
                }
            } // ok
            case "rotatetile" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 1) {
                        Player player = checkPlayer(client.getNickname());
                        if (player != null){
                            String side = firstParameters.get(0);
                            RotateTileEvent event = new RotateTileEvent(player, side);
                            gameState.handleEvent(event);
                        }
                        else{
                            client.invalidCommand("You are not connected to the game!");
                        }
                    }
                    else{
                        client.invalidCommand("/rotatetile supports only one parameter!");
                    }
                }
                else{
                    client.invalidCommand("/rotatetile supports only one parameter.");
                }
            } //ok
            case "putdowntile" -> {
                GameState gameState = new GameState();
                if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        PutDownTileEvent event = new PutDownTileEvent(player);
                        gameState.handleEvent(event);
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/putdowntile doesn't support parameters!");
                }
            } //ok
            case "placetile" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 2){
                        String row = firstParameters.get(0);
                        String column = firstParameters.get(1);
                        int rowInt = Integer.parseInt(row);
                        int columnInt = Integer.parseInt(column);
                        boolean checkPosition = validTilePosition(rowInt, columnInt);
                        if ((rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) || !checkPosition) {
                            client.invalidCommand("Row or column not valid. It must be between 5 and 9 for rows and between 4 and 10 for columns");
                        }
                        else{
                            Player player = checkPlayer(client.getNickname());
                            if (player != null){
                                PlaceTileEvent event = new PlaceTileEvent(player, rowInt-5, columnInt-4);
                                gameState.handleEvent(event);
                            }
                            else{
                                client.invalidCommand("You are not connected to the game!");
                            }
                        }
                    }
                    else{
                        client.invalidCommand("/placetile supports only two parameters!");
                    }
                }
        } //ok
            case "reservetile" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 1){
                        String indexStr = firstParameters.get(0);
                        int index = Integer.parseInt(indexStr);

                        Player player = checkPlayer(client.getNickname());
                        if (player != null){
                            if (index < 1 || index > 2) {
                                client.invalidCommand("Index not valid. It must be either 1 or 2");
                            }
                            else{
                                ReserveTileEvent event = new ReserveTileEvent(player, index-1);
                                gameState.handleEvent(event);
                            }
                        }
                        else{
                            client.invalidCommand("You are not connected to the game!");
                        }
                    }
                }
            } //ok
            case "fliphourglass" -> {
                GameState gameState = new GameState();
                if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        FlipHourglassEvent event = new FlipHourglassEvent();
                        gameState.handleEvent(event);
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/fliphourglass doesn't support parameters!");
                }
            } //ok
            case "setposition" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
                        if (firstParameters.size() == 1) {
                            String pos = firstParameters.get(0);
                            int position = Integer.parseInt(pos);
                            int maxNumberOfPlayers = game.getNumberOfPlayers();
                            if (position < 1 || position > maxNumberOfPlayers) {
                                client.invalidCommand("Position not valid. It must be between 1 and " + maxNumberOfPlayers);
                            } else {
                                // Check if position is valid?
                                SetPositionEvent event = new SetPositionEvent(player, position);
                                gameState.handleEvent(event);
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/setposition requires only one parameter!");
                }
            } //ok
            case "pickupfromship" -> {
                GameState gameState = new GameState();
                if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        PickUpFromShipEvent event = new PickUpFromShipEvent(player);
                        gameState.handleEvent(event);
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/pickupfromship doesn't support parameters!");
                }
            } //ok
            case "pickupreservedtile" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
                        if (firstParameters.size() == 1) {
                            String indexStr = firstParameters.get(0);
                            int index = Integer.parseInt(indexStr);
                            Ship playerShip = player.getShip();
                            int numberOfReservedTiles = playerShip.getReservedTiles().size();
                            if (index < 1 || index > numberOfReservedTiles) {
                                client.invalidCommand("Index not valid. It must be either 1 or 2");
                            } else {
                                PickUpReservedTileEvent event = new PickUpReservedTileEvent(player, index - 1);
                                gameState.handleEvent(event);
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/pickupreservedtile supports only one parameter.");
                }
            } //ok
            case "activateengines" -> {
                GameState gameState = new GameState();
                if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
                        if (firstParameters.size() % 2 != 0) {
                            client.invalidCommand("/activateengines needs an even number of row and column for engines.");
                        }
                        else {
                            if(secondParameters.size() % 3 != 0){
                                client.invalidCommand("for each batteries specify the position and the quantity to remove.");
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
                                        client.invalidCommand("Invalid row or column.");
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
                                gameState.handleEvent(event);
                            }
                        }
                    }
                    else {
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/activateengines needs two sets of parameters");
                }
            } //ok
            case "activatecannons" -> {
                GameState gameState = new GameState();
                if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
                        if (firstParameters.size() % 2 != 0) {
                            client.invalidCommand("/activatecannons needs an even number of row and column for cannons.");
                        }
                        else{
                            if (secondParameters.size() % 3 != 0){
                                client.invalidCommand("for each batteries specify the position and the quantity to remove.");
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
                                        client.invalidCommand("Invalid row or column.");
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
                                gameState.handleEvent(event);
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/activatecannons needs two sets of parameters");
                }
            } //ok
            case "activateshield" -> {
                GameState gameState = new GameState();
                if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        if (firstParameters.size() != 2) {
                            client.invalidCommand("First set of /activateshield must have two parameters.");
                        }
                        else if (secondParameters.size() != 2){
                            client.invalidCommand("Second set of /activateshield must have two parameters.");
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
                                    client.invalidCommand("Invalid row or column.");
                                }
                                else{
                                    ActivateShieldEvent event = new ActivateShieldEvent(player, rowShield-5, colShield-4, rowBat-5, colBat-4);
                                    gameState.handleEvent(event);
                                }
                            }
                            else{
                                client.invalidCommand("Invalid row or column");
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game");
                    }
                }
                else{
                    client.invalidCommand("/activateshield needs two sets of parameters");
                }
            } //ok
            case "removecargo" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if(player != null){
                        if(firstParameters.size() != 3){
                            client.invalidCommand("/removecargo supports only 3 parameters.");
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
                                client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                if (value < 1 || value > 3) {
                                    client.invalidCommand("Invalid value. It must be between 1 and 3");
                                }
                                else{
                                    RemoveCargoEvent event = new RemoveCargoEvent(player, row-5, col-4, value);
                                    gameState.handleEvent(event);
                                }

                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/removecargo supports only one set of parameters");
                }
            } //ok
            case "addcargo" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if(player != null) {
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
                                    gameState.handleEvent(event);
                                }
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/addcargo supports only one set of parameters");
                }
            } //ok
            case "switchcargo" -> {
                GameState gameState = new GameState();
                if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
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
                                gameState.handleEvent(event);
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }else{
                    client.invalidCommand("/switchcargo needs two sets of parameters.");
                }
            } //ok
            case "ejectpeople" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        List<List<Integer>> people = new ArrayList<>();
                        if (firstParameters.size() % 3 != 0){
                            client.invalidCommand("/ejectpeople needs a numbero of parameters multiple of 3.");
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
                                    client.invalidCommand("Invalid row or column.");
                                    break;
                                }
                                else {
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
                                        gameState.handleEvent(event);
                                    }
                                }
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/ejectpeople supports only one set of parameters");
                }
            } //ok
            case "giveup" -> {
                GameState gameState = new GameState();
                if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        GiveUpEvent event = new GiveUpEvent(player);
                        gameState.handleEvent(event);
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/giveup doesn't support parameters!");
                }
            } //ok
            case "viewinventory" -> {
                GameState gameState = new GameState();
                if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        ViewInventoryEvent event = new ViewInventoryEvent(player);
                        gameState.handleEvent(event);
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/viewinventory doesn't support parameters!");
                }
            } //ok
            case "claimreward" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 1){
                        Player player =  checkPlayer(client.getNickname());
                        String engage = firstParameters.get(0);
                        if (player != null){
                            if (engage.equals("true") || engage.equals("false")){
                                boolean engageBool = Boolean.parseBoolean(engage);
                                ClaimRewardEvent event = new ClaimRewardEvent(player, engageBool);
                                gameState.handleEvent(event);
                            }
                            else{
                                client.invalidCommand("The parameter must be either true or false.");
                            }
                        }
                        else{
                            client.invalidCommand("You are not connected to the game!");
                        }
                    }
                    else{
                        client.invalidCommand("/claimreward supports only one parameter.");
                    }
                }
                else{
                    client.invalidCommand("/claimreward supports only one paramter.");
                }
            }
            case "choosesubship" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 2){ // Choosing by specifying a random tile in the subship you want to keep
                        Player player = checkPlayer(client.getNickname());
                        if (player != null){
                            String rowStr = firstParameters.get(0);
                            String colstr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colstr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                ChooseSubShipEvent event = new ChooseSubShipEvent(player, row-5, col-4);
                                gameState.handleEvent(event);
                            }

                        }
                        else{
                            client.invalidCommand("You are not connected to the game!");
                        }
                    }
                    else{
                        client.invalidCommand("/choosesubship supports only one parameter.");
                    }
                }
                else{
                    client.invalidCommand("/choosesubship supports only one set of parameters!");
                }
            } //ok
            case "nochoice" -> {
                GameState gameState = new GameState();
                if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        NoChoiceEvent event = new NoChoiceEvent(player);
                        gameState.handleEvent(event);
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/nochoice doesn't support parameters!");
                }
            } //ok
            case "done" -> {
                GameState gameState = new GameState();
                if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        DoneEvent event = new DoneEvent(player);
                        gameState.handleEvent(event);
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/done doesn't support parameters!");
                }
            } //ok
            case "placeorangealien" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 2){
                        Player player = checkPlayer(client.getNickname());
                        if (player != null){
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                PlaceOrangeAlienEvent event = new PlaceOrangeAlienEvent(player, row-5, col-4);
                                gameState.handleEvent(event);
                            }
                        }
                        else{
                            client.invalidCommand("You are not connected to the game!");
                        }
                    }
                    else{
                        client.invalidCommand("/placeorangealien supports only one set of parameters!");
                    }
                }
                else {
                    client.invalidCommand("/placeorangealien supports only one set of parameters!");
                }
            } //ok
            case "placepurplealien" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 2){
                        Player player = checkPlayer(client.getNickname());
                        if (player != null){
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                PlacePurpleAlienEvent event = new PlacePurpleAlienEvent(player, row-5, col-4);
                                gameState.handleEvent(event);
                            }
                        }
                        else{
                            client.invalidCommand("You are not connected to the game!");
                        }
                    }
                    else{
                        client.invalidCommand("/placepurplealien supports only one set of parameters!");
                    }
                }
                else {
                    client.invalidCommand("/placepurplealien supports only one set of parameters!");
                }
            } //ok
            case "removetile" -> {
                GameState gameState = new GameState();
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 2){
                        Player player = checkPlayer(client.getNickname());
                        if (player != null){
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            boolean checkPosition = validTilePosition(row, col);
                            if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                client.invalidCommand("Invalid row or column.");
                            }
                            else{
                                RemoveTileEvent event = new RemoveTileEvent(player, row-5, col-4);
                                gameState.handleEvent(event);
                            }
                        }
                        else{
                            client.invalidCommand("You are not connected to the game!");
                        }
                    }
                    else{
                        client.invalidCommand("/removetile supports only one set of parameters!");
                    }
                }
                else{
                    client.invalidCommand("/removetile supports only one set of parameters!");
                }
            } //ok
        }
    }

    /**
     * Check if a player with the given nickname is in the game.
     *
     * @param nickname  The nickname of the player to check.
     * @return  The Player instance if found, or null if not found.
     */

    // Check if the player is in the game
    public Player checkPlayer(String nickname){
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }

    /**
     * Validates the tile position based on the game rules.
     *
     * @param row   The row of the tile.
     * @param col   The column of the tile.
     * @return  True if the position is valid, false otherwise.
     */

    public boolean validTilePosition(int row, int col){
        boolean checkPosition = false;
        if (row == 5){
            if (col == 4 || col == 5 || col == 7 || col == 9 || col == 10){
                checkPosition = false;
            }
        }
        else if (row == 6){
            if(col == 4 || col == 10){
                checkPosition = false;
            }
        }
        else if (row == 9){
            if (col == 7){
                checkPosition = false;
            }
        }
        else{
            checkPosition = true;
        }
        return checkPosition;
    }

}
