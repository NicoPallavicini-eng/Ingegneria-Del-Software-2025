package it.polimi.ingsw.galaxytrucker.Controller.Server;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import java.util.*;
import java.util.function.Consumer;

public class ServerController {
    private final Map<String, Runnable> commands;
    private final Game game;
    private String nickname;

    public ServerController(Map<String, Consumer<List<String>>> commands, Game game) {

        this.game = game;
        this.commands = new HashMap<>();

        commands.put("/help", params -> {
            try {
                if (!params.isEmpty()) {
                    System.out.println("Errore: /help non supporta parametri!");
                    throw new IllegalEventException("Errore: /help non supporta parametri!");
                }
                HelpEvent event = new HelpEvent();
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/viewleaderboard", params -> {
            try {
                if (!params.isEmpty()) {
                    System.out.println("Errore: /viewleaderboard non supporta parametri!");
                    throw new IllegalEventException("Errore: /viewleaderboard non supporta parametri!");
                }
                ViewLeaderboardEvent event = new ViewLeaderboardEvent();
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/viewships", params -> {
            try {
                if (!params.isEmpty()) {
                    System.out.println("Errore: /viewships non supporta parametri!");
                    throw new IllegalEventException("Errore: /viewships non supporta parametri!");
                }
                ViewShips event = new ViewShipEvent();
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/connect", params -> {
            try {
                if (params.size() != 2) {
                    System.out.println("Errore: /connect richiede due parametri");
                    return;
                }
                this.nickname = params.get(0);
                String IP = params.get(1);

                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();

                if (playerOptional.isPresent()) {
                    System.out.println("Errore: nickname già presente. Sceglierne uno univoco");
                    return;
                }

                ConnectEvent event = new ConnectEvent(nickname, IP);

            }
            catch (IllegalEventException e){
                System.out.println("Errore: " + e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/disconnect", params -> {
            try{
                if (!params.isEmpty()) {
                    System.out.println("Errore: /disconnect non richiede parametri");
                    throw new IllegalEventException("Errore: /disconnect non richiede parametri");
                }
                String nickname = this.nickname;

                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(nickname))
                        .findFirst();

                Player player = playerOptional.get();

                DisconnectEvent event = new DisconnectEvent(player);
                System.out.println("Disconnessione avvenuta con successo");
                // Logic for disconnecting the player with RMI
            }
            catch (IllegalEventException e){
                System.out.println("Errore: " + e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/setnumberofplayers", params -> {
            try{
                if (params.size() != 1){
                    System.out.println("Errore:/setnumberofplayers richiede un parametro");
                    throw new IllegalEventException("Errore:/setnumberofplayers richiede un parametro");
                }
                String numberOfPlayersStr = params.get(0);
                int numberOfPlayers = Integer.parseInt(numberOfPlayersStr);

                if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                    System.out.println("Errore: numero di giocatori non valido. Deve essere compreso tra 2 e 4");
                    throw new IllegalEventException("Errore: numero di giocatori non valido. Deve essere compreso tra 2 e 4");
                }

                SetNumberOfPlayers event = new SetNumberOfPlayersEvent(numberOfPlayers);
            }
            catch (IllegalEventException e){
                System.out.println("Errore: " + e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/pickuptile", params -> {
            try{
                if (params.size() != 2){
                    System.out.println("Errore: /pickuptile richiede due parametri");
                    throw new IllegalEventException("Errore: /pickuptile richiede due parametri");
                }
                String row = params.get(0);
                String column = params.get(1);

                int rowInt = Integer.parseInt(row);
                int columnInt = Integer.parseInt(column);

                //change tilepile from list to arraylist?

            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/rotatetile", params -> {
            try{
                if (params.size() != 2){
                    System.out.println("Errore: /rotatetile richiede due parametri");
                    throw new IllegalEventException("Errore: /rotatetile richiede due parametri");
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
                if(playerOptional.isEmpty()){
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }
                Player player = playerOptional.get();

                RotateTileEvent event = new RotateTileEvent(player, rowInt, columnInt);

            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/putdowntile", params -> {
            try{
                if (params.size != 4){
                    System.out.println("Errore: /putdowntile richiede quattro parametri");
                    throw new IllegalEventException("Errore: /putdowntile richiede quattro parametri");
                }
                String row = params.get(0);
                String column = params.get(1);

                int rowInt = Integer.parseInt(row);
                int columnInt = Integer.parseInt(column);

                //logic for pile

                Optional<Player> playerOptional = game.getListOfPlayers().stream()
                        .filter(player1 -> player1.getNickname().equals(this.nickname))
                        .findFirst();
                if(playerOptional.isEmpty()){
                    System.out.println("Errore: giocatore non trovato");
                    throw new IllegalEventException("Errore: giocatore non trovato");
                }

                PutDownTileEvent event = new PutDownTileEvent(player, rowInt, columnInt);

            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/placetile", params -> {
            try {
                if (params.size() != 4) {
                    System.out.println("Errore: /placetile richiede quattro parametri");
                    throw new IllegalEventException("Errore: /placetile richiede quattro parametri");
                }
                String rowPile = params.get(0);
                String columnPile = params.get(1);
                String rowShip = params.get(2);
                String columnShip = params.get(3);

                int rowPileInt = Integer.parseInt(rowPile);
                int columnPileInt = Integer.parseInt(columnPile);
                int rowShipInt = Integer.parseInt(rowShip);
                int columnShipInt = Integer.parseInt(columnShip);

                if (rowShipInt < 5 || rowShipInt > 9 || columnShipInt < 4 || columnShipInt > 10) {
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
                PlaceTileEvent event = new PlaceTileEvent(player, rowPileInt, columnPileInt, rowShipInt, columnShipInt);
            }
            catch (IllegalEventException e){
                System.out.println(e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/reservetile", params -> {
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

        });

        commands.put("/activatecannons", params -> {});

        commands.put("/activateshields", params -> {});

        commands.put("/removecargo", params -> {});

        commands.put("/addcargo", params -> {});

        commands.put("/switchcargo", params -> {});

        commands.put("/ejectpeople", params -> {});

        commands.put("/giveup", params -> {});

        commands.put("/viewinventory", params -> {});

    }
}
