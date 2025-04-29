package it.polimi.ingsw.galaxytrucker.Controller.Server;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import javafx.beans.binding.IntegerBinding;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Consumer;

public class ServerController {
    private final Game game;

    public ServerController() {
        this.game = new Game();
    }

    public void handleUserInput(VirtualView client, String input){
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

    private void executeCommand (String command, List<String> firstParameters, List<String> secondParameters, VirtualView client){
        switch(command){
            //TODO check illegal position on ship by shifting parameters
            case "help" -> {
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()){
                    client.invalidCommand("/help doesn't support parameters!");
                }
            }
            case "viewleaderboard" -> {
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()){
                    client.invalidCommand("/viewleaderboard doesn't support parameters!");
                }
            }
            case "viewships" -> {
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()){
                    client.invalidCommand("/viewships doesn't support parameters!");
                }
            }
            case "connect" -> {
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
                                client.setNickame(nickname);

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
            }
            case "disconnect" -> {
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
            }
            case "setnumberofplayers" -> {
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
                    client.InvalidCommand("/setnumberofplayers supports only one parameter!");
                }
            }
            case "pickuptile" -> {
                if (secondParameters.isEmpty()){
                    if(firstParameters.size() == 1) {
                        String tilePosition = firstParameters.get(0);
                        int tilePositionInt = Integer.parseInt(tilePosition); //CHECK MAX NUMBER OF TILES IN TILEPILE
                        if (tilePositionInt > 0 && tilePositionInt < 156) {
                            String clientNickname = client.getNickname();
                            Player player = checkPlayer(clientNickname);
                            if (player != null) {
                                PickupTileEvent event = new PickupTileEvent(player, tilePositionInt);
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
            }
            case "rotatetile" -> {
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 1) {
                        Player player = checkPlayer(client.getNickname());
                        if (player != null){
                            String side = firstParameters.get(0);
                            RotateTileEvent event = new RotateTileEvent(player, side);
                            gameState.hanleEvent(event);
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
            }
            case "putdowntile" -> {
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
            }
            case "placetile" -> {
                if (secondParameters.isEmpty()){
                    if (firstParameters.size() == 2){
                        String row = firstParameters.get(0);
                        String column = firstParameters.get(1);
                        int rowInt = Integer.parseInt(row);
                        int columnInt = Integer.parseInt(column);
                        boolean checkPosition = invalidTilePosition(rowInt, columnInt);
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
        }
            case "reservetile" -> {
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
            }
            case "fliphourglass" -> {
                if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        FlipHourglassEvent event = new FlipHourglassEvent(player);
                        gameState.handleEevent(event);
                    }
                    else{
                        client.ivalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/fliphourglass doesn't support parameters!");
                }
            }
            case "setposition" -> {
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
            }
            case "pickupfromship" -> {
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
            }
            case "pickupreservedtile" -> {
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
                                PickupReservedTileEvent event = new PickupReservedTileEvent(player, index - 1);
                                gameState.handleEvent(event);
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!")
                    }
                }
                else{
                    client.invalidCommand("/pickupreservedtile supports only one parameter.");
                }
            }
            case "activateengines" -> {
                if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
                        if (firstParameters.size() % 2 != 0) {
                            client.invalidCommand("/activateengines needs an even number of row and column for engines.")
                        }
                        else {
                            if(secondParameters.size() % 3 != 0){
                                client.invalidCommand("for each batteries specify the position and the quantity to remove.")
                            }
                            else{
                                ArrayList<Pair<Integer, Integer>> engines = new ArrayList<>();
                                for (int i = 0; i < firstParameters.size(); i += 2){
                                    String rowStr = firstParameters.get(i);
                                    String colStr = firstParameters.get(i+1);

                                    int row = Integer.parseInt(rowStr);
                                    int col = Integer.parseInt(colStr);
                                    boolean checkPosition = invalidTilePosition(row, col);
                                    if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                        client.invalidCommand("Invalid row or column.");
                                        break;
                                    }
                                    engines.add(new Pair<>(row-5, col-4));
                                }
                                // batteries cannot be ArrayList<Pair<Integer, Integer>>

                                ActivateEnginesEvent event = new ActivateEnginesEvent(player, engines, batteries);
                                gameState.handleEvent(event);
                            }
                        }
                    }
                    else {
                        client.invalidCommand("You are not connected to the game!")
                    }
                }
                else{
                    client.invalidCommand("/activateengines needs two sets of parameters")
                }
            }
            case "activatecannons" -> {
                if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
                        if (firstParameters.size() % 2 != 0) {
                            client.invalidCommand("/activatecannons needs an even number of row and column for cannons.")
                        }
                        else{
                            if (secondParameters.size() % 3 != 0){
                                client.invalidCommand("for each batteries specify the position and the quantity to remove.")
                            }
                            else{
                                ArrayList<Pair<Integer, Integer>> cannons = new ArrayList<>();
                                for (int i = 0; i < firstParameters.size(); i += 2){
                                    String rowStr = firstParameters.get(i);
                                    String colStr = firstParameters.get(i+1);

                                    int row = Integer.parseInt(rowStr);
                                    int col = Integer.parseInt(colStr);
                                    if (row < 5 || row > 9 || col < 4 || col > 10) {
                                        client.invalidCommand("Invalid row or column.");
                                        break;
                                    }
                                    cannons.add(new Pair<>(row-5, col-4));
                                }
                                // batteries cannot be ArrayList<Pair<Integer, Integer>>
                                ActivateCannonsEvent event = new ActivateCannonsEvent(player, cannons, batteries);
                                gameState.handleEvent(event);
                            }
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!")
                    }
                }
                else{
                    client.invalidCommand("/activatecannons needs two sets of parameters")
                }
            }
            case "activateshields" -> {
                if (!firstParameters.isEmpty() && !secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
                        if (firstParameters.size() % 2 != 0) {
                            client.invalidCommand("/activateshields needs an even number of row and column for shields.")
                        }
                        else{
                            ArrayList<Pair<Integer, Integer>> shields = new ArrayList<>();
                            for (int i = 0; i < firstParameters.size(); i += 2){
                                String rowStr = firstParameters.get(i);
                                String colStr = firstParameters.get(i+1);

                                int row = Integer.parseInt(rowStr);
                                int col = Integer.parseInt(colStr);

                                if (row < 5 || row > 9 || col < 4 || col > 10) {
                                    client.invalidCommand("Invalid row or column.");
                                    break;
                                }
                                shields.add(new Pair<>(row-5, col-4));
                            }
                            //batteries cannot be ArrayList<Pair<Intege,Integer>>
                            ActivateShieldsEvent event = new ActivateShieldsEvent(player, shields, batteries);
                            gameState.handleEvent(event);
                        }
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!")
                    }
                }
                else{
                    client.invalidCommand("/activateshields needs two sets of parameters")
                }
            }
            case "removecargo" -> {
                if (secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if(player != null){
                        if(firstParameters.size() != 3){
                            client.invalidCommand("/removecargo supports only 3 parameters.")
                        }
                        else{
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);
                            String valueStr = firstParameters.get(2);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            int value = Integer.parseInt(valueStr);

                            if (row < 5 || row > 9 || col < 4 || col > 10) {
                                client.invalidCommand("Invalid row or column.");
                            }
                            else if (value < 1 || value > 3){
                                client.invalidCommand("Invalid value. It must be between 1 and 3");
                            }
                            else{
                                RemoveCargoEvent event = new RemoveCargoEvent(player, row-5, col-4, value);
                                gameState.handleEvent(event);
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
            }
            case "addcargo" -> {
                if (secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if(player != null){
                        if(firstParameters.size() != 3){
                            client.invalidCommand("/addcargo supports only 3 parameters.")
                        }
                        else{
                            String rowStr = firstParameters.get(0);
                            String colStr = firstParameters.get(1);
                            String valueStr = firstParameters.get(2);

                            int row = Integer.parseInt(rowStr);
                            int col = Integer.parseInt(colStr);
                            int value = Integer.parseInt(valueStr);

                            if (row < 5 || row > 9 || col < 4 || col > 10) {
                                client.invalidCommand("Invalid row or column.");
                            }
                            else if (value < 1 || value > 3){
                                client.invalidCommand("Invalid value. It must be between 1 and 3");
                            }
                            else{
                                AddCargoEvent event = new AddCargoEvent(player, row-5, col-4, value);
                                gameState.handleEvent(event);
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
            }
            case "switchcargo" -> {
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

                            if ((prevRow < 5 || prevRow > 9 || prevCol < 4 || prevCol > 10) || (newRow < 5 || newRow > 9 || newCol < 4 || newCol > 10)) {
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
            }
            case "ejectpeople" -> {
                if (secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname());
                    if (player != null){
                        //ArrayList<ArrayList<Integer>> don't get it
                        if (firstParameters.size() != )
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                else{
                    client.invalidCommand("/ejectpeople supports only one set of parameters")
                }
            } //TODO ejectpeople
            case "giveup" -> {
                if (firstParameters.isEmpty() && secondParameters.isEmpty()){
                    Player player = checkPlayer(client.getNickname);
                    if (player != null){
                        GiveUpEvent event = new GiveUpEvent(player);
                        gameState.handleEvent(event);
                    }
                    else{
                        client.invalidCommand("You are not connected to the game!")
                    }
                }
                else{
                    client.invalidCommand("/giveup doesn't support parameters!");
                }
            }
            case "viewinventory" -> {
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
            }

        }
    }

    // Check if the player is in the game
    public Player checkPlayer(String nickname){
        Optional<Player> playerOptional = game.getListOfPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst();
        return playerOptional.orElse(null);
    }

    public boolean invalidTilePosition(int row, int col){
        if (row == 5){
            if (col == 4 || col == 5 || col == 7 || col == 9 || col == 10){
                return false;
            }
        }
        else if (row == 6){
            if(col == 4 || col == 10){
                return false;
            }
        }
        else if (row == 9){
            if (col == 7){
                return false;
            }
        }
        else{
            return true;
        }
    }

}
