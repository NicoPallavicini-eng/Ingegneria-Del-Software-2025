package it.polimi.ingsw.galaxytrucker.Controller.Server;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.HelpEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;

import java.util.*;
import java.util.function.Consumer;

public class ServerController {
    private final Map<String, Runnable> commands;
    private final Game game;

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
                String nickname = params.get(0);
                String game = params.get(1);
                ConnectEvent event 
            }

        });

        commands.put("/connect", params -> {

        });

     /*
        commands.put("/help", params -> {
            try{
                HelpEvent event = HelpEvent.from(params);
            }
            catch (IllegalEventException e){
                System.out.println("Errore: " +e.getMessage());
                System.err.println(e.getMessage());
            }
        });

        commands.put("/viewships", params ->{
            try{
                ViewShipsEvent event = ViewShipsEvents.from(params);
            }
            catch (IllegalEventException e){
                System.out.println("Errore" +e.getMessage());
                System.err.println(e.getMessage())
            }
        });

        commands.put("/viewleaderboard", params -> {
           try{
               ViewLeaderboardEvent event = ViewLeaderBoardEvent.from(params);
           }
           catch (IllegalEventException e){
               System.out.println("Errore" +e.getMessage());
               System.err.println(e.getMessage());
           }
        });

        commands.put("/connect", params -> {
            try{
                ConnectEvent event = ConnectEvent.from(params);
            }
            catch (IllegalEventException e){
                System.out.println("Errore" +e.getMessage());
                System.err.println(e.getMessage();
            }
        });
*/
    }
}
