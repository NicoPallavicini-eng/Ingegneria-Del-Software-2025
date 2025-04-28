package it.polimi.ingsw.galaxytrucker.Controller.Server;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import javafx.beans.binding.IntegerBinding;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Consumer;

public class ServerController {
    //private final Map<String, Runnable> commands;
    private final Game game;
    //private String nickname;

    public ServerController() {
        this.game = new Game();
    }
        /*        commands.put("/reservetile", params -> {
            try {
                if (params.size() != 2) {
                    System.out.println("Errore: /reservetile richiede due parametri");
                    throw new IllegalEventException("Errore: /reservetile richiede due parametri");
                }
                String row = params.get(0);
                String column = params.get(1);
                int rowInt = Integer.parseInt(row);
                int columnInt = Integer.parseInt(column);

                //logic for pile

                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                Player player = playerOptional.get();
                ArrayList<Tile> reservedTiles = (ArrayList<Tile>) player.getShip().getReservedTiles(); //In ship maybe return arrayList
                if (reservedTiles.size() == 2) {
                    System.out.println("Errore: Non è presente più spazio per tenere da parte la tile, scartarne una prima di chiamare /reservedtile");
                    throw new IllegalEventException("Errore: Non è presente più spazio per tenere da parte la tile, scartarne una prima di chiamare /reservedtile");
                }
                ReserveTileEvent event = new ReserveTileEvent(player, rowInt, columnInt);
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/fliphourglass", params -> {
            try {
                if (!params.isEmpty()) {
                    System.out.println("Errore: /fliphourglass non supporta parametri!");
                    throw new IllegalEventException("Errore: /fliphourglass non supporta parametri!");
                }
                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                Player player = playerOptional.get();

                FlipHourglassEvent event = new FlipHourglassEvent(player);
            }
            catch (IllegalEventException e) {
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/setposition", params -> {
            try{
                if (params.size() != 1){
                    System.out.println("Errore: /setposition richiede un parametro");
                    throw new IllegalEventException("Errore: /setposition richiede un parametro");
                }
                String position = params.get(0);
                int positionInt = Integer.parseInt(position);

                int maxNumberOfPlayers = game.getNumberOfPlayers();

                if (positionInt < 1 || positionInt > maxNumberOfPlayers) {
                    System.out.println("Errore: posizione non valida. Deve essere compresa tra 1 e 4");
                    throw new IllegalEventException("Errore: posizione non valida. Deve essere compresa tra 1 e 4");
                }

                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                Player player = playerOptional.get();

                SetPositionEvent event = new SetPositionEvent(player, positionInt);
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/pickupfromship", params -> {
            try{
                if (params.size() != 2){
                    System.out.println("Errore: /pickupfromship richiede due parametri");
                    throw new IllegalEventException("Errore: /pickupfromship richiede due parametri");
                }
                String row = params.get(0);
                String column = params.get(1);

                int rowInt = Integer.parseInt(row);
                int columnInt = Integer.parseInt(column);

                if (rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) {
                    System.out.println("Errore: riga o colonna non valida");
                    throw new IllegalEventException("Errore: riga o colonna non valida");
                }

                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                Player player = playerOptional.get();

                PickupFromShipEvent event = new PickupFromShipEvent(player, rowInt, columnInt);
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/pickupreservedtile", params -> {}); //Really needed?

        commands.put("/acrivateengine", params -> {
            try{
                if (params.size() % 2 != 0){
                    System.out.println("Errore: /activateengine richiede un numero pari di parametri");
                    throw new IllegalEventException("Errore: /activateengine richiede un numero pari di parametri");
                }
                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                ArrayList<Pair<Integer, Integer>> engineNumbers = new ArrayList<>();
                for (int i = 0; i < params.size(); i+=2){
                    String row = params.get(i);
                    String column = params.get(i + 1);

                    int rowInt = Integer.parseInt(row);
                    int columnInt = Integer.parseInt(column);
                    if(rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) {
                        System.out.println("Errore: riga o colonna non valida");
                        throw new IllegalEventException("Errore: riga o colonna non valida");
                    }

                    Player player = playerOptional.get();
                    //engineNumbers.add(rowInt);
                    //engineNumbers.add(columnInt);


                }
                ActivateEngineEvent event = new ActivateEngineEvent(player, rowInt, columnInt);
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/activatecannons", params -> {
            try{
                if (params.size() % 2 != 0){
                    System.out.println("Errore: /activatecannons richiede un numero pari di parametri");
                    throw new IllegalEventException("Errore: /activatecannons richiede un numero pari di parametri");
                }
                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                for (int i = 0; i < params.size(); i+=2){
                    String row = params.get(i);
                    String column = params.get(i + 1);

                    int rowInt = Integer.parseInt(row);
                    int columnInt = Integer.parseInt(column);
                    if(rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) {
                        System.out.println("Errore: riga o colonna non valida");
                        throw new IllegalEventException("Errore: riga o colonna non valida");
                    }

                    Player player = playerOptional.get();

                    ActivateCannonsEvent event = new ActivateCannonsEvent(player, rowInt, columnInt);
                }
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/activateshields", params -> {
            //batterie
            try{
                if (params.size() % 2 != 0){
                    System.out.println("Errore: /activateshields richiede un numero pari di parametri");
                    throw new IllegalEventException("Errore: /activateshields richiede un numero pari di parametri");
                }
                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                for (int i = 0; i < params.size(); i+=2){
                    String row = params.get(i);
                    String column = params.get(i + 1);

                    int rowInt = Integer.parseInt(row);
                    int columnInt = Integer.parseInt(column);
                    if(rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) {
                        System.out.println("Errore: riga o colonna non valida");
                        throw new IllegalEventException("Errore: riga o colonna non valida");
                    }

                    Player player = playerOptional.get();

                    ActivateShieldsEvent event = new ActivateShieldsEvent(player, rowInt, columnInt);
                }
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/removecargo", params -> {
            try{

                if (params.size() % 3 != 0) {
                    System.out.println("Errore: /removecargo richiede un numero di parametri divisibili per 3");
                    throw new IllegalEventException("Errore: /removecargo richiede un numero di parametri divisibili per 3");
                }
                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                Player player = playerOptional.get();
                for (int i = 0; i < params.size(); i += 3){
                    String row = params.get(i);
                    String column = params.get(i + 1);
                    String valueOfCargo = params.get(i + 2);

                    int rowInt = Integer.parseInt(row);
                    int columnInt = Integer.parseInt(column);
                    if(rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) {
                        System.out.println("Errore: riga o colonna non valida");
                        throw new IllegalEventException("Errore: riga o colonna non valida");
                    }
                    int valueOfCargoInt = Integer.parseInt(valueOfCargo);
                    if (valueOfCargoInt < 1 || valueOfCargoInt > 3) {
                        System.out.println("Errore: valore del cargo non valido. Deve essere compreso tra 1 e 3");
                        throw new IllegalEventException("Errore: valore del cargo non valido. Deve essere compreso tra 1 e 3");
                    }
                    RemoveCargoEvent event = new RemoveCargoEvent(player, rowInt, columnInt, valueOfCargoInt);
                }
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/addcargo", params -> {
            try {
                if (params.size() % 3 != 0) {
                    System.out.println("Errore: /addcargo richiede un numero di parametri divisibili per 3");
                    throw new IllegalEventException("Errore: /addcargo richiede un numero di parametri divisibili per 3");
                }
                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if (playerOptional.isEmpty()) {
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                Player player = playerOptional.get();
                for (int i = 0; i < params.size(); i += 3) {
                    String row = params.get(i);
                    String column = params.get(i + 1);
                    String valueOfCargo = params.get(i + 2);

                    int rowInt = Integer.parseInt(row);
                    int columnInt = Integer.parseInt(column);
                    if (rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) {
                        System.out.println("Errore: riga o colonna non valida");
                        throw new IllegalEventException("Errore: riga o colonna non valida");
                    }
                    int valueOfCargoInt = Integer.parseInt(valueOfCargo);
                    if (valueOfCargoInt < 1 || valueOfCargoInt > 3) {
                        System.out.println("Errore: valore del cargo non valido. Deve essere compreso tra 1 e 3");
                        throw new IllegalEventException("Errore: valore del cargo non valido. Deve essere compreso tra 1 e 3");
                    }
                    AddCargoEvent event = new AddCargoEvent(player, rowInt, columnInt, valueOfCargoInt);
                }
            }
            catch(IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/switchcargo", params -> {

        });

        commands.put("/ejectpeople", params -> {});

        commands.put("/giveup", params -> {});

        commands.put("/viewinventory", params -> {});

    }

         */
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

                        if (rowInt < 5 || rowInt > 9 || columnInt < 4 || columnInt > 10) {
                            client.invalidCommand("Row or column not valid. It must be between 5 and 9 for rows and between 4 and 10 for columns");
                        }
                        else{
                            Player player = checkPlayer(client.getNickname());
                            if (player != null){
                                PlaceTileEvent event = new PlaceTileEvent(player, rowInt, columnInt);
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
                                ReserveTileEvent event = new ReserveTileEvent(player, index);
                                gameState.handleEvent(event);
                            }
                        }
                    }
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

}
