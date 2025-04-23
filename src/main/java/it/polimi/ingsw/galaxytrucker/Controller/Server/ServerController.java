package it.polimi.ingsw.galaxytrucker.Controller.Server;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.HelpEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.*;
import java.util.function.Consumer;

public class ServerController {
    private final Map<String, Runnable> commands;
    private final Game game;
    private String nickname;

    public ServerController(Map<String, Consumer<List<String>>> commands) {
        this.commands = new HashMap<>();

        commands.put("/help", params -> {
            if (!params.isEmpty()){
                System.out.println("Errore: /help non supporta parametri!");
                return;
            }
            HelpEvent event = new HelpEvent();
        });

        commands.put("/viewleaderboard", params -> {
            if (!params.isEmpty()){
                System.out.println("Errore: /viewleaderboard non supporta parametri!");
                return;
            }
            ViewLeaderboardEvent event = new ViewLeaderboardEvent();
        });

        commands.put("/viewships", params -> {
            try {
                if (!params.isEmpty()) {
                    System.out.println("Errore: /viewships non supporta parametri!");
                    return;
                }
                ViewShips event = new ViewShipEvent();
            }
            catch (IllegalEventException e){
                System.out.println("Errore: " + e.getMessage());
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

                ArrayList<Player> listOfPlayers = listOfPlayers.getListOfPlayers();
                ArrayList<String> listOfPlayersString = new ArrayList<String>();
                for (Player player : listOfPlayers){
                    String nickPlayer = player.getNickname();
                    listOfPlayersString.add(nickPlayer);
                }
                if (listOfPlayersString.contains(nickname)){
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

                DisconnectEvent event = new DisconnectEvent(this.nickname);
            }
            catch (IllegalEventException e){
                System.out.println("Errore: " + e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/setnumberofplayers", params -> {});

        commands.put("/setcolor", params -> {});

        commands.put("/pickuptile", params -> {});

        commands.put("/rotatetile", params -> {});

        commands.put("/putdowntile", params -> {});

        commands.put("/placetile", params -> {});

        commands.put("/reservetile", params -> {});

        commands.put("/fliphourglass", params -> {});

        commands.put("/setposition", params -> {});

        commands.put("/pickupfromship", params -> {});

        commands.put("/pickupreservedtile", params -> {});

        commands.put("/acrivateengine", params -> {});

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
