package it.polimi.ingsw.galaxytrucker.Controller.Server;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClientHandler;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.Network.Message;
import it.polimi.ingsw.galaxytrucker.Network.Server.RMIServer;
import it.polimi.ingsw.galaxytrucker.Network.Server.SocketServer;
import it.polimi.ingsw.galaxytrucker.View.IllegalGUIEventException;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.*;

/**
 * The ServerController class handles user input and manages the game state on the server side.
 * It also creates the game when instantiated.
 */

public class ServerController {
    private static final Game game = new Game();
    private static RMIServer rmiServer = null;
    private static SocketServer socketServer = null;
    private final RemoteObserver remoteObserver = new RemoteObserver(this, game);
    private List<VirtualClient> rmiClients = new ArrayList<>();
    private List<SocketClientHandler> socketClients = new ArrayList<>();
    private int hourglassCounter = 1;

    public ServerController(RMIServer rmiServer) {
        ServerController.rmiServer = rmiServer;
    }

    public ServerController(SocketServer socketServer) {
        ServerController.socketServer = socketServer;
    }

    public static Game getGame() {
        return game;
    }

    public void setRmiPlayers(List<VirtualClient> rmiClients){
        synchronized (rmiClients) {
            this.rmiClients = rmiClients;
        }
    }

    public void setSocketPlayers(List<SocketClientHandler> socketClients){
        synchronized(socketClients) {
         this.socketClients = socketClients;
        }
    }

    public void updateView(Game game, String message){
        GameState gameState = game.getGameState();
        Thread rmiThread = new Thread(() -> {
            synchronized (rmiClients){
                if (rmiClients != null && !rmiClients.isEmpty()){
                    switch (message) {
                        case "game" -> {
                            for (VirtualClient rmiClient : rmiClients){
                                if (rmiClient != null){
                                    try {
                                        rmiClient.updateGame(game);
                                    } catch (RemoteException e) {
                                        e.printStackTrace(); // tmp
                                    }
                                }
                            }
                        }
                        case "gamestate" -> {
                            if  (gameState instanceof TravellingState){
                                for (VirtualClient rmiClient : rmiClients) {
                                    if (rmiClient != null) {
                                        try {
                                            rmiClient.printMessage("\nStarted Travelling State\n");
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                            else if (gameState instanceof FinalState){
                                for (VirtualClient rmiClient : rmiClients) {
                                    if (rmiClient != null) {

                                        try {
                                            Player player = checkPlayer(rmiClient.getNickname());
                                            if (player != null) {
                                                rmiClient.printMessage("\nGame is over, the final state has been reached.\n" +
                                                        "You have collected " + player.getShip().getCredits() + " credits and " + player.getShip().getTravelDays() + " travel days.\n");
                                            }
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        }
                        case "time" -> {
                            if (gameState instanceof BuildingState){
                                for (VirtualClient rmiClient : rmiClients) {
                                    if (rmiClient != null) {
                                        try {
                                            rmiClient.printMessage("\nHourglass flipped for the: " + game.getHourglass().getFlipNumber() + " time");
                                            rmiClient.printMessage("\nTime left: " + game.getHourglass().getElapsedTime() + " seconds");
                                            rmiClient.printMessage("\n");
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        }
                        case "timeisup" -> {
                            if (gameState instanceof BuildingState){
                                for (VirtualClient rmiClient : rmiClients) {
                                    if (rmiClient != null) {
                                        try {
                                            rmiClient.printMessage("\nTime is up! You have to place your ship now!");
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        }
                        case "leaderboard" -> {
                            for (VirtualClient rmiClient : rmiClients){
                                try {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        rmiClient.viewLeaderboard(game);
                                    }
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        case "shipPlaced" -> {
                            synchronized (((BuildingState) gameState).getPlayersWithLegalShips()) {
                                try {
                                    for (VirtualClient rmiClient : rmiClients) {
                                        Player player = checkPlayer(rmiClient.getNickname());
                                        if (player != null){
                                            if (((BuildingState) gameState).getPlayersWithLegalShips().contains(player) && !player.isChecked()) {
                                                rmiClient.printMessage("\n" + player.getNickname() + " Your ship is legal! You can now place your aliens. If you don't want to place any alien, type /done.\n");
                                                player.setChecked(true);
                                            }
                                            else {
                                                rmiClient.printMessage("\n" + player.getNickname() + " Your ship is illegal! You have to fix it by removing one tile at a time.\n");
                                            }
                                        }
                                    }
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        case "newcard" -> {
                            if (gameState instanceof TravellingState) {
                                synchronized (game.getListOfActivePlayers()){
                                    for (Player player : game.getListOfActivePlayers()) {
                                        player.setChecked(false);
                                    }
                                }
                                for (VirtualClient rmiClient : rmiClients) {
                                    if (rmiClient != null) {
                                        try {
                                            rmiClient.printMessage("\nNew card drawn\n");
                                            rmiClient.viewCard(game);
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        }
                        case "final" -> {
                            if (gameState instanceof FinalState) {
                                for (VirtualClient rmiClient : rmiClients) {
                                    if (rmiClient != null) {
                                        try {
                                            rmiClient.printMessage("\nGame is over, the final state has been reached");
                                            rmiClient.viewLeaderboard(game);
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        }
                        case "meteors" -> {
                            synchronized (((TravellingState) gameState).getHandledPlayers()){
                                try{
                                    for (VirtualClient rmiClient : rmiClients) {
                                        Player player = checkPlayer(rmiClient.getNickname());
                                        if (player != null && !((TravellingState) gameState).getHandledPlayers().contains(player)) {
                                            rmiClient.viewLeaderboard(game);
                                            rmiClient.printMessage("\n You have to defend yourself from the meteors!\n You can do so activating your shields or cannons.\n If you don't want to defend yourself, type /nochoice.\n");
                                            rmiClient.viewCard(game);
                                        }
                                    }
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        case "finalMeteors" -> {
                            for (VirtualClient rmiClient : rmiClients) {
                                if (rmiClient != null) {
                                    try {
                                        rmiClient.printMessage("\nMeteors card is now over. You will now see the damage done to your ship.\n");
                                        rmiClient.viewCard(game);
                                        rmiClient.viewMyShip(game, rmiClient.getNickname());
                                    } catch (RemoteException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                        case "openSpace" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        rmiClient.printMessage("\nYou are now in open space.\n You can choose to activate your engines or do nothing.\n If you want to do nothing, type /nochoice.\n Now playing: " + ((TravellingState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                        rmiClient.viewCard(game);
                                        rmiClient.viewMyShip(game, rmiClient.getNickname());
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "openSpaceAction" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null && ((OpenSpaceState) gameState).getHandledPlayers().contains(player)) {
                                        try {
                                            rmiClient.printMessage("\nYou have activated your engines and moved forward.\n");
                                            rmiClient.viewLeaderboard(game);
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    } else if (player != null && !((OpenSpaceState) gameState).getHandledPlayers().contains(player)) {
                                        rmiClient.printMessage("\n" + ((OpenSpaceState) gameState).getHandledPlayers().getLast() + "Has activated his/her engines and moved forward.\n");
                                        rmiClient.viewLeaderboard(game);
                                    }

                                }
                            }catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "nextplayer" -> {
                            if (gameState instanceof TravellingState) {
                                try {
                                    synchronized (((TravellingState) gameState).getCurrentPlayer()){
                                        for (VirtualClient rmiClient : rmiClients) {
                                            Player player = checkPlayer(rmiClient.getNickname());
                                            if (player != null) {
                                                rmiClient.printMessage("\nNow playing: " + ((TravellingState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                                rmiClient.viewCard(game);
                                            }
                                        }
                                    }
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        case "planets" -> {
                            try{
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null){
                                        rmiClient.printMessage("\nYou are now in the planets state. \n You can choose a planet to land on (/chooseplanet <index>) or do nothing (/nochoice).\n Once everyone has chosen a planet, you can add cargo to your ship (/addcargo <index> <cargoType>), remove cargo (/removecargo <index>), switch cargo (/switchcarg <index1>; <index2> or do nothing (/nochoice).\n Once you are dne with the cargo phase you have to signal it with the /done command.\n Now playing: " + ((PlanetsState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                        rmiClient.viewCard(game);
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "planetsSelection" -> {
                            try{
                                for (VirtualClient rmiClient : rmiClients){
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null){
                                        if (((PlanetsState) gameState).getChosenPlanets().containsKey(player) && !player.isChecked()) {
                                            rmiClient.printMessage("\nYou have chosen the planet: " + ((PlanetsState) gameState).getChosenPlanets().get(player) + ".\n");
                                            player.setChecked(true);
                                        } else {
                                            rmiClient.printMessage("\n" + ((PlanetsState) gameState).getCurrentPlayer().getNickname() + " has chosen the planet " + ((PlanetsState) gameState).getChosenPlanets().get(player) + ".\n");
                                        }
                                        rmiClient.viewCard(game);
                                    }
                                }
                            } catch (RemoteException e){
                                throw new RuntimeException(e);
                            }
                        }
                        case "planetsNoSelection" -> {
                            try{
                                Player target = ((PlanetsState)gameState).getSatisfiedPlayers().getLast();
                                String targetNick = target.getNickname();
                                for (VirtualClient rmiClient : rmiClients){
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null){
                                        if (!targetNick.equals(player.getNickname())) {
                                            rmiClient.printMessage("\n" + targetNick + "Has chosen to do nothing.\n");
                                        }
                                    }
                                }
                            } catch (RemoteException e){
                                throw new RuntimeException(e);
                            }
                        }
                        case "loseDays" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        try {
                                            rmiClient.viewLeaderboard(game);
                                        } catch (RemoteException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            } catch(RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "station" -> {
                            try{
                                for (VirtualClient rmiClient : rmiClients){
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null){
                                        rmiClient.printMessage("\n" + "You are now in the abbandoned station state.\n" +
                                                "If you have enough crew, you can decide to land on the station (/claimreward).\n" +
                                                "Once you landed you can decide to add, remove or switch cargo from your ship. If you don't want to do anything, type /nochoice.\n" +
                                                "Once you are done with the cargo phase, you have to signal it with the /done command.\n");
                                        rmiClient.viewCard(game);
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "stationAction" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients){
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        if (!player.getNickname().equals(((StationState) gameState).getRewardClaimer().getNickname())) {
                                            rmiClient.printMessage("\n" + ((StationState) gameState).getCurrentPlayer().getNickname() + " has landed on the station.\n");
                                        }
                                        rmiClient.viewLeaderboard(game);
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "stardust" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        rmiClient.printMessage("\nYou are now in the stardust state.\n You will lose 1 travel day for each exposed connectors.");
                                        rmiClient.viewMyShip(game, rmiClient.getNickname());
                                        rmiClient.printMessage("\n You will lose: " + player.getShip().getExposedConnectors() + " travel days.\n");
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "stardustEnd" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        rmiClient.viewLeaderboard(game);
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "epidemic" -> {
                            try{
                                for (VirtualClient rmiClient : rmiClients){
                                    if(rmiClient != null){
                                        rmiClient.printMessage("\nYou are now in the epidemic state.\n"+
                                                "You will lose 1 crew member from every occupied cabin that is joined to another occupied cabin.\n");
                                        rmiClient.viewCard(game);
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "pirates" -> {
                            try{
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null){
                                        rmiClient.printMessage("\nYou are now in the pirates state.\n" +
                                                "Every player have to fight the pirates.\n" +
                                                "You can activate your cannons (/activatecannons), your shields (/activateshields) or do nothing (/nochoice).\n" +
                                                "The player that defeats the pirates can loot them with the /claimreward command.\n");
                                        rmiClient.viewCard(game);
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "piratesDefeated" -> {
                            try{
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null && !player.getNickname().equals(((PiratesState) gameState).getPiratesSlayer().getNickname())) {
                                        rmiClient.printMessage("\nThe pirates have been defeated by: " + ((PiratesState) gameState).getPiratesSlayer().getNickname());
                                        rmiClient.viewMyShip(game, rmiClient.getNickname());
                                    } else if (player.getNickname().equals(((PiratesState) gameState).getPiratesSlayer().getNickname())) {
                                        rmiClient.printMessage("\nYou have defeated the pirates! You can now claim your reward with the /claimreward command or do nothing with the /nochoice command.\n");
                                    }
                                }
                            }catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "ship" -> {
                            try{
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        rmiClient.printMessage("\nYou are now in the ship state.\n"+
                                                "Every player in order has to choose whether to eject people (/ejectpeople)  if they have enough or no action (/nochoice).\n" +
                                                "Now playing: " + ((ShipState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                        rmiClient.viewCard(game);
                                        rmiClient.viewMyShip(game, rmiClient.getNickname());
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "slavers" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        rmiClient.printMessage("\nYou are now in the slavers state.\n" +
                                                "Every player in order has to choose whether to activate cannons (/activatecannons) or no action (/nochoice).\n" +
                                                "Now playing: " + ((SlaversState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                        rmiClient.viewCard(game);
                                        rmiClient.viewMyShip(game, rmiClient.getNickname());
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "slaversDefeated" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null && !player.getNickname().equals(((SlaversState) gameState).getSlaversSlayer().getNickname())) {
                                        rmiClient.printMessage("\nThe slavers have been defeated by: " +  ((SlaversState)gameState).getSlaversSlayer().getNickname());
                                        rmiClient.viewMyShip(game, rmiClient.getNickname());
                                    }
                                    else if (player.getNickname().equals(((SlaversState) gameState).getSlaversSlayer().getNickname())){
                                        rmiClient.printMessage("\nYou have defeated the slavers! You can now claim your reward with the /claimreward command or do nothing with the /nochoice command.\n");
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "smugglers" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null) {
                                        rmiClient.printMessage("\nYou are now in the smugglers state.\n" +
                                                "Every player have to fight the smugglers.\n" +
                                                "You can activate your cannons (/activatecannons) or do nothing (/nochoice).\n" +
                                                "When defeated you can claim your reward (/claimreward) and the adjust your cargo with the commands (/addcargo, /removecargo or /switchcargo).\n" +
                                                "Now playing: " + ((SmugglersState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                        rmiClient.viewCard(game);
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "smugglersNoChoice" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null && !((SmugglersState) gameState).getHandledPlayers().contains(player)) {
                                        rmiClient.printMessage("\n" + ((SmugglersState) gameState).getHandledPlayers().getLast() + " has chosen to do nothing.\n");
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "smugglersDefeated" -> {
                            try {
                                for (VirtualClient rmiClient : rmiClients) {
                                    Player player = checkPlayer(rmiClient.getNickname());
                                    if (player != null && !player.getNickname().equals(((SmugglersState) gameState).getSmugglersSlayer().getNickname())) {
                                        rmiClient.printMessage("\nThe smugglers have been defeated by: " + ((SmugglersState) gameState).getSmugglersSlayer().getNickname());
                                        rmiClient.viewMyShip(game, rmiClient.getNickname());
                                    } else if (player.getNickname().equals(((SmugglersState) gameState).getSmugglersSlayer().getNickname())) {
                                        rmiClient.printMessage("\nYou have defeated the smugglers! You can now claim your reward with the /claimreward command or do nothing with the /nochoice command.\n");
                                    }
                                }
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case "combatZone" -> {
                            //TODO finish this card
                        }
                        case "combatZoneL" -> {
                            //TODO finish this card
                        }

                    }
                }

            }
        });

        Thread socketThread = new Thread(() -> {
            synchronized(socketClients){
                if (socketClients == null || socketClients.isEmpty()){
                    return;
                }
                switch (message) {
                    case "gamestate" -> {
                        if  (gameState instanceof TravellingState){
                            for (SocketClientHandler socketClient : socketClients) {
                                if (socketClient != null) {
                                    try {
                                        Message msg = new Message("String", null, "\nStarted Travelling State\n");
                                        ObjectOutputStream objOut = socketClient.getObjOut();
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                        objOut.reset();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        else if (gameState instanceof FinalState){
                            for (SocketClientHandler socketClient : socketClients) {
                                if (socketClient != null) {
                                    try {
                                        Player player = checkPlayer(socketClient.getNickname());
                                        if (player != null) {
                                            Message msg = new Message("String", null, "\nGame is over, the final state has been reached.\n" +
                                                    "You have collected " + player.getShip().getCredits() + " credits and " + player.getShip().getTravelDays() + " travel days.\n");
                                            ObjectOutputStream objOut = socketClient.getObjOut();
                                            objOut.writeObject(msg);
                                            objOut.flush();
                                            objOut.reset();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    case "time" -> {
                        if (gameState instanceof BuildingState){
                            for (SocketClientHandler socketClient : socketClients) {
                                if (socketClient != null) {
                                    try {
                                        Message msg = new Message("String", null, "\nHourglass flipped for the: " + game.getHourglass().getFlipNumber() + " time\n" +
                                                "Time left: " + game.getHourglass().getElapsedTime() + " seconds\n");
                                        ObjectOutputStream objOut = socketClient.getObjOut();
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    case "timeisup" -> {
                        if (gameState instanceof BuildingState){
                            for (SocketClientHandler socketClient : socketClients) {
                                if (socketClient != null) {
                                    try {
                                        Message msg = new Message ("String", null, "\nTime is up! You have to place your ship now!");
                                        ObjectOutputStream objOut = socketClient.getObjOut();
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    case "leaderboard" -> {
                        for (SocketClientHandler socketClient : socketClients){
                            try {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null) {
                                    Message msg = new Message ("Game", game, "viewLeaderboard");
                                    ObjectOutputStream objOut = socketClient.getObjOut();
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                    objOut.reset();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    case "shipPlaced" -> {
                        synchronized(((BuildingState) gameState).getPlayersWithLegalShips()){
                            //System.out.println("Sono dentro shipPlaced!");
                            try{
                                for (SocketClientHandler socketClient : socketClients){
                                    Player player = checkPlayer(socketClient.getNickname());
                                    if (player != null){
                                        if (((BuildingState) gameState).getFinishedBuildingPlayers().contains(player) && !player.isChecked()) {
                                            Message msg = new Message("String", null, "\n" + player.getNickname() + "Your ship is legal! You can now place your aliens. If you don't want to place any alien, type /done.\n");
                                            ObjectOutputStream objOut = socketClient.getObjOut();
                                            objOut.writeObject(msg);
                                            objOut.flush();
                                            player.setChecked(true);
                                        } else {
                                            Message msg = new Message("String", null, "\n" + player.getNickname() + "Your ship is legal! You can now place your aliens. If you don't want to place any alien, type /done.\n");
                                            ObjectOutputStream objOut = socketClient.getObjOut();
                                            objOut.writeObject(msg);
                                            objOut.flush();
                                        }
                                    }
                                }
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    case "newcard" -> {
                        if (gameState instanceof TravellingState) {
                            synchronized (game.getListOfActivePlayers()){
                                for (Player player : game.getListOfActivePlayers()) {
                                    player.setChecked(false);
                                }
                            }
                            for (SocketClientHandler socketClient : socketClients) {
                                if (socketClient != null) {
                                    try {
                                        System.out.println("Sono dentro newCard!");
                                        Message msg = new Message("String", null, "\nNew card drawn\n");
                                        msg.setNickname(socketClient.getNickname());
                                        ObjectOutputStream objOut = socketClient.getObjOut();
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                        Message msg2 = new Message("Game", game, "viewCard");
                                        msg2.setNickname(socketClient.getNickname());
                                        objOut.writeObject(msg2);
                                        objOut.flush();
                                        objOut.reset();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    case "final" -> {
                        if (gameState instanceof FinalState) {
                            for (SocketClientHandler socketClient : socketClients) {
                                if (socketClient != null) {
                                    try {
                                        Message msg = new Message("String", game, "\nGame is over, the final state has been reached\n");
                                        ObjectOutputStream objOut = socketClient.getObjOut();
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                        objOut.reset();
                                        Message msg2 = new Message("Game", game, "viewLeaderboard");
                                        msg2.setNickname(socketClient.getNickname());
                                        objOut.writeObject(msg2);
                                        objOut.flush();
                                        objOut.reset();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    case "meteors" -> {
                        synchronized (((TravellingState) gameState).getHandledPlayers()){
                            try{
                                for (SocketClientHandler socketClient : socketClients) {
                                    Player player = checkPlayer(socketClient.getNickname());
                                    if (player != null && ((TravellingState) gameState).getHandledPlayers().contains(player)) {
                                        ObjectOutputStream objOut = socketClient.getObjOut();
                                        Message msg2 = new Message("Game", game, "viewLeaderboard");
                                        msg2.setNickname(socketClient.getNickname());
                                        objOut.writeObject(msg2);
                                        objOut.flush();
                                        objOut.reset();
                                        Message msg = new Message ("String", null, "\nYou are now in the meteors state.\n" +
                                                "You have to defend yourself from the meteors!\n" +
                                                "You can do so activating your shields or cannons.\n" +
                                                "If you don't want to defend yourself, type /nochoice.\n");
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                        objOut.reset();
                                       }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    case "finalMeteors" -> {
                        for (SocketClientHandler socketClient : socketClients) {
                            if (socketClient != null) {
                                try {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nMeteors card is now over. You will now see the damage done to your ship.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                    objOut.reset();

                                    msg = new Message ("Game", game, "viewCard");
                                    msg.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                    objOut.reset();

                                    Message msg2 = new Message("Game", game, "viewMyShip");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    case "openSpace" -> {
                        for (SocketClientHandler socketClient : socketClients) {
                            if (socketClient != null) {
                                try{
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou are now in open space.\n You can choose to activate your engines or do nothing.\n If you want to do nothing, type /nochoice.\n Now playing: " + ((TravellingState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                    objOut.reset();

                                    Message msg2 = new Message("Game", game, "viewMyShip");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    case "openSpaceAction" -> {
                        try{
                            for(SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if(player != null&& ((OpenSpaceState) gameState).getHandledPlayers().contains(player)) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou have activated your engines and moved forward.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                    Message msg2 = new Message("Game", game, "viewLeaderboard");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();
                                } else if (player != null && !((OpenSpaceState) gameState).getHandledPlayers().contains(player)) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\n" + ((OpenSpaceState) gameState).getHandledPlayers().getLast() + "Has activated his/her engines and moved forward.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                    Message msg2 = new Message("Game", game, "viewLeaderboard");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();
                                }
                            }
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    case "nextplayer" -> {
                        if (gameState instanceof TravellingState) {
                            try {
                                synchronized (((TravellingState) gameState).getCurrentPlayer()){
                                    for(SocketClientHandler socketClient : socketClients) {
                                        Player player = checkPlayer(socketClient.getNickname());
                                        if(player != null) {
                                            ObjectOutputStream objOut = socketClient.getObjOut();

                                            Message msg = new Message ("String", null, "\nNow playing: " + ((TravellingState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                            objOut.writeObject(msg);
                                            objOut.flush();

                                            msg = new Message ("Game", game, "viewCard");
                                            msg.setNickname(socketClient.getNickname());
                                            objOut.writeObject(msg);
                                            objOut.flush();
                                            objOut.reset();
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    case "planets" -> {
                        try{
                            for(SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if(player != null) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou are now in the planets state. \n You can choose a planet to land on (/chooseplanet <index>) or do nothing (/nochoice).\n Once everyone has chosen a planet, you can add cargo to your ship (/addcargo <index> <cargoType>), remove cargo (/removecargo <index>), switch cargo (/switchcarg <index1>; <index2> or do nothing (/nochoice).\n Once you are dne with the cargo phase you have to signal it with the /done command.\n Now playing: " + ((PlanetsState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    case "planetsSelection" -> {
                        try{
                            for(SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if(player != null) {
                                    if(((PlanetsState) gameState).getChosenPlanets().containsKey(player) && !player.isChecked()){
                                        ObjectOutputStream objOut = socketClient.getObjOut();
                                        Message msg = new Message("String",null,"\nYou have chosen the planet: " + ((PlanetsState) gameState).getChosenPlanets().get(player) + ".\n");
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                        player.setChecked(true);
                                    }else{
                                        ObjectOutputStream objOut = socketClient.getObjOut();

                                        Message msg = new Message ("String", null, "\n" + ((PlanetsState) gameState).getCurrentPlayer().getNickname() + " has chosen the planet " + ((PlanetsState) gameState).getChosenPlanets().get(player) + ".\n");
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                    }
                                    ObjectOutputStream objOut = socketClient.getObjOut();
                                    Message msg = new Message ("Game", game, "viewCard");
                                    msg.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                    objOut.reset();
                                }
                            }
                        } catch (IOException e){
                           e.printStackTrace();
                        }
                    }
                    case "planetsNoSelection" -> {
                        try{
                            Player target = ((PlanetsState)gameState).getSatisfiedPlayers().getLast();
                            String targetNick = target.getNickname();
                            for(SocketClientHandler socketClient : socketClients){
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null){
                                    if (!targetNick.equals(player.getNickname())) {
                                        ObjectOutputStream objOut = socketClient.getObjOut();

                                        Message msg = new Message ("String", null, "\n" + targetNick + "Has chosen to do nothing.\n");
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                    }
                                }
                            }
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    case "loseDays" -> {
                        try {
                            for(SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null) {
                                    try {
                                        ObjectOutputStream objOut = socketClient.getObjOut();

                                        Message msg2 = new Message("Game", game, "viewLeaderboard");
                                        msg2.setNickname(socketClient.getNickname());
                                        objOut.writeObject(msg2);
                                        objOut.flush();
                                        objOut.reset();

                                    } catch (RemoteException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "station" -> {
                        try{
                            for(SocketClientHandler socketClient : socketClients){
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null){
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\n" + "You are now in the abbandoned station state.\n" +
                                            "If you have enough crew, you can decide to land on the station (/claimreward).\n" +
                                            "Once you landed you can decide to add, remove or switch cargo from your ship. If you don't want to do anything, type /nochoice.\n" +
                                            "Once you are done with the cargo phase, you have to signal it with the /done command.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "stationAction" -> {
                        try {
                            for(SocketClientHandler socketClient : socketClients){
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null) {
                                    if (!player.getNickname().equals(((StationState) gameState).getRewardClaimer().getNickname())) {
                                        ObjectOutputStream objOut = socketClient.getObjOut();

                                        Message msg = new Message ("String", null, "\n" + ((StationState) gameState).getCurrentPlayer().getNickname() + " has landed on the station.\n");
                                        objOut.writeObject(msg);
                                        objOut.flush();
                                    }
                                    ObjectOutputStream objOut = socketClient.getObjOut();
                                    Message msg2 = new Message("Game", game, "viewLeaderboard");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "stardust" -> {
                        try {
                            for (SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou are now in the stardust state.\n You will lose 1 travel day for each exposed connectors.");
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                    Message msg2 = new Message("Game", game, "viewMyShip");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();

                                    msg = new Message ("String", null, "\n You will lose: " + player.getShip().getExposedConnectors() + " travel days.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "stardustEnd" -> {
                        try {
                            for (SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg2 = new Message("Game", game, "viewLeaderboard");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "epidemic" -> {
                        try{
                            for (VirtualClient rmiClient : rmiClients){
                                if(rmiClient != null){
                                    rmiClient.printMessage("\nYou are now in the epidemic state.\n"+
                                            "You will lose 1 crew member from every occupied cabin that is joined to another occupied cabin.\n");
                                }
                            }
                            for(SocketClientHandler socketClient : socketClients){
                                if(socketClient != null){
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou are now in the epidemic state.\n"+
                                            "You will lose 1 crew member from every occupied cabin that is joined to another occupied cabin.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "pirates" -> {
                        try{
                            for(SocketClientHandler socketClient : socketClients){
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null){
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou are now in the pirates state.\n" +
                                            "Every player have to fight the pirates.\n" +
                                            "You can activate your cannons (/activatecannons), your shields (/activateshields) or do nothing (/nochoice).\n" +
                                            "The player that defeats the pirates can loot them with the /claimreward command.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "piratesDefeated" -> {
                        try{
                            for(SocketClientHandler socketClient : socketClients){
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null && !player.getNickname().equals(((PiratesState) gameState).getPiratesSlayer().getNickname())) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nThe pirates have been defeated by: " + ((PiratesState) gameState).getPiratesSlayer().getNickname());
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                    Message msg2 = new Message("Game", game, "viewMyShip");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();
                                } else if (player.getNickname().equals(((PiratesState) gameState).getPiratesSlayer().getNickname())) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou have defeated the pirates! You can now claim your reward with the /claimreward command or do nothing with the /nochoice command.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "ship" -> {
                        try{
                            for (SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou are now in the ship state.\n"+
                                            "Every player in order has to choose whether to eject people (/ejectpeople)  if they have enough or no action (/nochoice).\n" +
                                            "Now playing: " + ((ShipState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                    Message msg2 = new Message("Game", game, "viewMyShip");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "slavers" -> {
                        try {
                            for (SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou are now in the slavers state.\n" +
                                            "Every player in order has to choose whether to activate cannons (/activatecannons) or no action (/nochoice).\n" +
                                            "Now playing: " + ((SlaversState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                    Message msg2 = new Message("Game", game, "viewMyShip");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();

                                }
                            }
                        }  catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "slaversDefeated" -> {

                        try {
                            for (SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null && !player.getNickname().equals(((SlaversState) gameState).getSlaversSlayer().getNickname())) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nThe slavers have been defeated by: " +  ((SlaversState)gameState).getSlaversSlayer().getNickname() +
                                            "Now playing: " + ((SlaversState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                    Message msg2 = new Message("Game", game, "viewMyShip");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();

                                }
                                else if (player.getNickname().equals(((SlaversState) gameState).getSlaversSlayer().getNickname())){
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou have defeated the slavers! You can now claim your reward with the /claimreward command or do nothing with the /nochoice command.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "smugglers" -> {
                        try {
                            for (SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou are now in the smugglers state.\n" +
                                            "Every player have to fight the smugglers.\n" +
                                            "You can activate your cannons (/activatecannons) or do nothing (/nochoice).\n" +
                                            "When defeated you can claim your reward (/claimreward) and the adjust your cargo with the commands (/addcargo, /removecargo or /switchcargo).\n" +
                                            "Now playing: " + ((SmugglersState) gameState).getCurrentPlayer().getNickname() + ".\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "smugglersNoChoice" -> {

                        try {
                            for (SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null && !((SmugglersState) gameState).getHandledPlayers().contains(player)) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\n" + ((SmugglersState) gameState).getHandledPlayers().getLast() + " has chosen to do nothing.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "smugglersDefeated" -> {
                        try {
                            for (SocketClientHandler socketClient : socketClients) {
                                Player player = checkPlayer(socketClient.getNickname());
                                if (player != null && !player.getNickname().equals(((SmugglersState) gameState).getSmugglersSlayer().getNickname())) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nThe smugglers have been defeated by: " + ((SmugglersState) gameState).getSmugglersSlayer().getNickname());
                                    objOut.writeObject(msg);
                                    objOut.flush();

                                    Message msg2 = new Message("Game", game, "viewMyShip");
                                    msg2.setNickname(socketClient.getNickname());
                                    objOut.writeObject(msg2);
                                    objOut.flush();
                                    objOut.reset();

                                } else if (player.getNickname().equals(((SmugglersState) gameState).getSmugglersSlayer().getNickname())) {
                                    ObjectOutputStream objOut = socketClient.getObjOut();

                                    Message msg = new Message ("String", null, "\nYou have defeated the smugglers! You can now claim your reward with the /claimreward command or do nothing with the /nochoice command.\n");
                                    objOut.writeObject(msg);
                                    objOut.flush();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case "combatZone" -> {
                        //TODO finish this card
                    }
                    case "combatZoneL" -> {
                        //TODO finish this card
                    }
                }
            }
        });

            rmiThread.start();
            socketThread.start();

            try{
                rmiThread.join();
                socketThread.join();
            } catch (InterruptedException e) {
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
        if (Objects.equals(input, "GAME")) {
            Message newMessage = new Message("String", ServerController.game, "NewGame");
            objOut.writeObject(newMessage);
            objOut.flush();
            return;
        }
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
                case "ship" -> {
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        Ship ship = player.getShip();
                        game.getGameState().handleEvent(ship);
                    } else {
                        Message newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                    }
                }
                case "viewcard" -> {
                    Message newMessage;
                    newMessage = new Message("Game", game, "viewCard");
                    newMessage.setNickname(msg.getNickname());
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    objOut.reset();
                }
                case "GAME" -> {
                    Message newMessage;
                    newMessage = new Message("Game", game, "NewGame");
                    newMessage.setNickname(msg.getNickname());
                    objOut.writeObject(newMessage);
                    objOut.flush();
                    objOut.reset();
                }
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
                            newMessage = new Message("String", null, "/viewleaderboard doesn't support parameters!");
                            newMessage.setNickname(msg.getNickname());
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/viewleaderboard doesn't support parameters!");
                        }
                        newMessage = new Message("Game", game, "viewLeaderboard");
                        newMessage.setNickname(msg.getNickname());
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        objOut.reset();
                        //client.viewLeaderboard(game);
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                    }

            } //ok
            case "connect" -> {
                Message newMessage;
                if (secondParameters.isEmpty()) {
                    if (firstParameters.size() == 1) {
                        String clientNickname = msg.getNickname();
                        String nickname = firstParameters.getFirst();
                        if (clientNickname != null) {
                            newMessage = new Message("String", null, "It's forbidden for one client to connect to the game more than once!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        } else {
                            Optional<Player> playerOptional = game.getListOfPlayers().stream()
                                    .filter(player1 -> player1.getNickname().equals(nickname))
                                    .findAny();
                             try {
                                    ConnectEvent event = new ConnectEvent(nickname, "localhost");
                                    game.getGameState().handleEvent(event, game);
                                    newMessage = new Message("String", null, "setNickname");
                                    newMessage.setNickname(nickname);
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    newMessage = new Message("Game", ServerController.game, "defaultView");
                                    newMessage.setNickname(nickname);
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
                                    // TODO update view
                                } catch (IllegalArgumentException | IllegalEventException e) {
                                    newMessage = new Message("String", null, e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
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
                }
            } //ok
            case "setnumberofplayers" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 1) {
                            String numberOfPlayersStr = firstParameters.getFirst();
                            int numberOfPlayers = Integer.parseInt(numberOfPlayersStr);
                            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                                newMessage = new Message("String", null, "Number of players not valid. It must be between 2 and 4");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                            } else {
                                SetNumberOfPlayersEvent event = new SetNumberOfPlayersEvent(numberOfPlayers);
                                try {
                                    game.getGameState().handleEvent(event);
                                    newMessage = new Message("String", null, "Hai settato il numero dei giocatori");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                } catch (IllegalArgumentException | IllegalEventException e) {
                                    newMessage = new Message("String", null, e.getMessage());
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

                        newMessage = new Message("Game", game, "viewMyShip");
                        newMessage.setNickname(msg.getNickname());
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        objOut.reset();
                        //client.viewMyShip(game, client.getNickname());
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
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
                            if (tilePositionInt >= 0 && tilePositionInt <= 152) {
                                try{
                                    PickUpTileEvent event = new PickUpTileEvent(player, tilePositionInt);
                                    game.getGameState().handleEvent(event);
                                    Tile currentTile = player.getShip().getTileInHand();
                                    for (SocketClientHandler socketClient : socketClients) {
                                        if (socketClient.getNickname() != msg.getNickname()) {
                                            ObjectOutputStream objOutClient = socketClient.getObjOut();
                                            newMessage = new Message("Game", game, "viewMyShip");
                                            newMessage.setNickname(msg.getNickname());
                                            objOutClient.writeObject(newMessage);
                                            objOutClient.flush();
                                            objOutClient.reset();
                                            //client.viewMyShip(game, client.getNickname());
                                            newMessage = new Message("Game", game, "viewTilepile");
                                            newMessage.setNickname(msg.getNickname());
                                            objOutClient.writeObject(newMessage);
                                            objOutClient.flush();
                                            objOutClient.reset();
                                        } else {
                                            ObjectOutputStream objOutClient = socketClient.getObjOut();
                                            newMessage = new Message("Game", game, "viewMyShip");
                                            newMessage.setNickname(socketClient.getNickname());
                                            objOutClient.writeObject(newMessage);
                                            objOutClient.flush();
                                            objOutClient.reset();
                                        }
                                    }
                                }catch(IllegalEventException e){
                                    newMessage = new Message("String",null,e.getMessage());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }


                            } else {
                                newMessage = new Message("String",null,"Tile position not valid. It must be between 1 and 156");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                            }
                        }
                        else{
                            newMessage = new Message("String",null,"/pickuptile supports only one parameter!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    }
                    else{
                        newMessage = new Message("String",null,"/pickuptile supports only one parameter!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                    }
                }
                else {
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
                }
            }
            case "rotate" -> {
                Message newMessage;
                Player player = checkPlayer(msg.getNickname());
                if (player != null){
                    if (secondParameters.isEmpty()){
                        if (firstParameters.size() == 1) {
                            try{
                                String side = firstParameters.getFirst();
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
                            for (SocketClientHandler socketClient : socketClients){
                                ObjectOutputStream objOutClient = socketClient.getObjOut();
                                newMessage = new Message("Game",game,"defaultView");
                                newMessage.setNickname(msg.getNickname());
                                objOutClient.writeObject(newMessage);
                                objOutClient.flush();
                                objOutClient.reset();
                            }
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
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
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
                        }
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
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
                            }
                            else{
                                try{
                                    ReserveTileEvent event = new ReserveTileEvent(player);
                                    game.getGameState().handleEvent(event);
                                    newMessage = new Message("Game",game,"defaultView");
                                    newMessage.setNickname(msg.getNickname());
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    objOut.reset();
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
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
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
                            }
                            else if (maxNumberOfPlayers == -1){
                                newMessage = new Message("String",null,"You need to set the number of players before setting the position.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
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
                                    List<SocketClientHandler> clientsSocket = socketServer.getClientsList();
                                    for (SocketClientHandler client : clientsSocket) {
                                        if (!client.getNickname().equals(msg.getNickname())) {
                                            newMessage = new Message("String",null,player.getNickname() + " has set the position to " + position);
                                            objOutHandler = client.getObjOut();
                                            objOutHandler.writeObject(newMessage);
                                            objOutHandler.flush();
                                        }
                                        if (game.getGameState() instanceof TravellingState) {
                                            newMessage = new Message("Game",game,"viewCard");
                                            newMessage.setNickname(msg.getNickname());
                                            objOutHandler = client.getObjOut();
                                            objOutHandler.writeObject(newMessage);
                                            objOutHandler.flush();
                                            objOut.reset();
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
                                }  catch (IllegalEventException e) {
                                    newMessage = new Message("String", null, e.getMessage());
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
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
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
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
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
                    }
                }
                else{
                    newMessage = new Message("String",null,"You are not connected to the game!");
                    objOut.writeObject(newMessage);
                    objOut.flush();
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
                        }
                        else {
                            if(secondParameters.size() % 3 != 0){
                                newMessage = new Message("String",null,"for each batteries specify the position and the quantity to remove.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
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
                                            newMessage = new Message("String", null, "Invalid row or column.");
                                            objOut.writeObject(newMessage);
                                            objOut.flush();
                                            break;
                                        }
                                        List<Integer> engineRow = new ArrayList<>();
                                        engineRow.add(rowEng - 5);
                                        engineRow.add(colEng - 4);
                                        engines.add(engineRow);
                                    }
                                    for (int j = 0; j < secondParameters.size(); j += 3) {
                                        String rowBatStr = secondParameters.get(j);
                                        String colBatStr = secondParameters.get(j + 1);
                                        String valueBatStr = secondParameters.get(j + 2);

                                        int rowBat = Integer.parseInt(rowBatStr);
                                        int colBat = Integer.parseInt(colBatStr);
                                        int valueBat = Integer.parseInt(valueBatStr);
                                        boolean checkPosition = validTilePosition(rowBat, colBat);
                                        if ((rowBat < 5 || rowBat > 9 || colBat < 4 || colBat > 10) || !checkPosition) {
                                            newMessage = new Message("String", null, "Invalid row or column.");
                                            objOut.writeObject(newMessage);
                                            objOut.flush();
                                            break;
                                        } else {
                                            if (valueBat < 1 || valueBat > 3) {
                                                newMessage = new Message("String", null, "Invalid value. It must be between 1 and 3");
                                                objOut.writeObject(newMessage);
                                                objOut.flush();
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
                                    try {
                                        ActivateEnginesEvent event = new ActivateEnginesEvent(player, engines, batteries);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        newMessage = new Message("String", null, e.getMessage());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    }
                                }
                            }

                        } else {
                            newMessage = new Message("String", null, "/activateengines needs two sets of parameters");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                    }
                } //ok
                case "activatecannons" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (!firstParameters.isEmpty() && !secondParameters.isEmpty()) {
                            if (firstParameters.size() % 2 != 0) {
                                newMessage = new Message("String", null, "/activatecannons needs an even number of row and column for cannons.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                            } else {
                                if (secondParameters.size() % 3 != 0) {
                                    newMessage = new Message("String", null, "for each batteries specify the position and the quantity to remove.");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
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
                                            newMessage = new Message("String", null, "Invalid row or column.");
                                            objOut.writeObject(newMessage);
                                            objOut.flush();
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
                                        String colBatStr = secondParameters.get(j + 1);
                                        String valueBatStr = secondParameters.get(j + 2);

                                        int rowBat = Integer.parseInt(rowBatStr);
                                        int colBat = Integer.parseInt(colBatStr);
                                        int valueBat = Integer.parseInt(valueBatStr);
                                        boolean checkPosition = validTilePosition(rowBat, colBat);
                                        if ((rowBat < 5 || rowBat > 9 || colBat < 4 || colBat > 10) || !checkPosition) {
                                            newMessage = new Message("String", null, "Invalid row or column.");
                                            objOut.writeObject(newMessage);
                                            objOut.flush();
                                            break;
                                        } else {
                                            if (valueBat < 1 || valueBat > 3) {
                                                newMessage = new Message("String", null, "Invalid value. It must be between 1 and 3");
                                                objOut.writeObject(newMessage);
                                                objOut.flush();
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
                                    try {
                                        // batteries cannot be ArrayList<Pair<Integer, Integer>>
                                        ActivateCannonsEvent event = new ActivateCannonsEvent(player, cannons, batteries);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        newMessage = new Message("String", null, e.getMessage());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    }
                                }
                            }

                        } else {
                            newMessage = new Message("String", null, "/activatecannons needs two sets of parameters");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                    }
                } //ok
                case "activateshield" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (!firstParameters.isEmpty() && !secondParameters.isEmpty()) {
                            if (firstParameters.size() != 2) {
                                newMessage = new Message("String", null, "First set of /activateshield must have two parameters.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                            } else if (secondParameters.size() != 2) {
                                newMessage = new Message("String", null, "Second set of /activateshield must have two parameters.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
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
                                        newMessage = new Message("String", null, "Invalid row or column.");
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    } else {
                                        try {
                                            ActivateShieldEvent event = new ActivateShieldEvent(player, rowShield - 5, colShield - 4, rowBat - 5, colBat - 4);
                                            game.getGameState().handleEvent(event);
                                        } catch (IllegalEventException e) {
                                            newMessage = new Message("String", null, e.getMessage());
                                            objOut.writeObject(newMessage);
                                            objOut.flush();
                                        }
                                    }
                                } else {
                                    newMessage = new Message("String", null, "Invalid row or column.");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                }
                            }
                        } else {
                            newMessage = new Message("String", null, "/activateshield needs two sets of parameters");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                    }
                } //ok
                case "removecargo" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (secondParameters.isEmpty()) {
                            if (firstParameters.size() != 3) {
                                newMessage = new Message("String", null, "/removecargo supports only 3 parameters.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("/removecargo supports only 3 parameters.");
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

                        } else {
                            newMessage = new Message("String", null, "/removecargo supports only one set of parameters");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/removecargo supports only one set of parameters");
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("You are not connected to the game!");
                    }
                } //ok
                case "addcargo" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (secondParameters.isEmpty()) {
                            if (firstParameters.size() != 3) {
                                newMessage = new Message("String", null, "/addcargo supports only 3 parameters.");
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

                        } else {
                            newMessage = new Message("String", null, "/switchcargo needs two sets of parameters.");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/switchcargo needs two sets of parameters.");
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("You are not connected to the game!");
                    }
                } //ok
                case "ejectpeople" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (secondParameters.isEmpty()) {
                            List<List<Integer>> people = new ArrayList<>();
                            if (firstParameters.size() % 3 != 0) {
                                newMessage = new Message("String", null, "/ejectpeople needs a numbero of parameters multiple of 3.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("/ejectpeople needs a number of parameters multiple of 3.");
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
                                    newMessage = new Message("String", null, "Invalid row or column.");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Invalid row or column.");
                                } else {
                                    try {
                                        ChooseSubShipEvent event = new ChooseSubShipEvent(player, row - 5, col - 4);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        newMessage = new Message("String", null, e.getMessage());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    }
                                }

                            } else {
                                newMessage = new Message("String", null, "/choosesubship supports only one parameter.");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("/choosesubship supports only one parameter.");
                            }
                        } else {
                            newMessage = new Message("String", null, "/choosesubship supports only one set of parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/choosesubship supports only one set of parameters!");
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("You are not connected to the game!");
                    }
                } //ok
                case "nochoice" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                            try {
                                NoChoiceEvent event = new NoChoiceEvent(player);
                                game.getGameState().handleEvent(event);
                            } catch (IllegalEventException e) {
                                newMessage = new Message("String", null, e.getMessage());
                                objOut.writeObject(newMessage);
                                objOut.flush();
                            }
                        } else {
                            newMessage = new Message("String", null, "/nochoice doesn't support parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/nochoice doesn't support parameters!");
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("You are not connected to the game!");
                    }
                } //ok
                case "done" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (firstParameters.isEmpty() && secondParameters.isEmpty()) {
                            try {
                                DoneEvent event = new DoneEvent(player);
                                game.getGameState().handleEvent(event);
                            } catch (IllegalEventException e) {
                                newMessage = new Message("String", null, e.getMessage());
                                objOut.writeObject(newMessage);
                                objOut.flush();
                            }
                        } else {
                            newMessage = new Message("String", null, "/done doesn't support parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/done doesn't support parameters!");
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("You are not connected to the game!");
                    }
                } //ok
                case "placeorangealien" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (secondParameters.isEmpty()) {
                            if (firstParameters.size() == 2) {
                                String rowStr = firstParameters.get(0);
                                String colStr = firstParameters.get(1);

                                int row = Integer.parseInt(rowStr);
                                int col = Integer.parseInt(colStr);
                                boolean checkPosition = validTilePosition(row, col);
                                if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                    newMessage = new Message("String", null, "Invalid row or column.");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Invalid row or column.");
                                } else {
                                    try {
                                        PlaceOrangeAlienEvent event = new PlaceOrangeAlienEvent(player, row - 5, col - 4);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        newMessage = new Message("String", null, e.getMessage());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    }
                                }

                            } else {
                                newMessage = new Message("String", null, "/placeorangealien supports only one set of parameters!");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("/placeorangealien supports only one set of parameters!");
                            }
                        } else {
                            newMessage = new Message("String", null, "/placeorangealien supports only one set of parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/placeorangealien supports only one set of parameters!");
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("You are not connected to the game!");
                    }
                } //ok
                case "placepurplealien" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (secondParameters.isEmpty()) {
                            if (firstParameters.size() == 2) {
                                String rowStr = firstParameters.get(0);
                                String colStr = firstParameters.get(1);

                                int row = Integer.parseInt(rowStr);
                                int col = Integer.parseInt(colStr);
                                boolean checkPosition = validTilePosition(row, col);
                                if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                    newMessage = new Message("String", null, "Invalid row or column.");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Invalid row or column.");
                                } else {
                                    try {
                                        PlacePurpleAlienEvent event = new PlacePurpleAlienEvent(player, row - 5, col - 4);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        newMessage = new Message("String", null, e.getMessage());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                    }
                                }

                            } else {
                                newMessage = new Message("String", null, "/placepurplealien supports only one set of parameters!");
                                objOut.writeObject(newMessage);
                                objOut.flush();
                                //client.invalidCommand("/placepurplealien supports only one set of parameters!");
                            }
                        } else {
                            newMessage = new Message("String", null, "/placepurplealien supports only one set of parameters!");
                            objOut.writeObject(newMessage);
                            objOut.flush();
                            //client.invalidCommand("/placepurplealien supports only one set of parameters!");
                        }
                    } else {
                        newMessage = new Message("String", null, "You are not connected to the game!");
                        objOut.writeObject(newMessage);
                        objOut.flush();
                        //client.invalidCommand("You are not connected to the game!");
                    }
                } //ok
                case "removetile" -> {
                    Message newMessage;
                    Player player = checkPlayer(msg.getNickname());
                    if (player != null) {
                        if (secondParameters.isEmpty()) {
                            if (firstParameters.size() == 2) {
                                String rowStr = firstParameters.get(0);
                                String colStr = firstParameters.get(1);

                                int row = Integer.parseInt(rowStr);
                                int col = Integer.parseInt(colStr);
                                boolean checkPosition = validTilePosition(row, col);
                                if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                    newMessage = new Message("String", null, "Invalid row or column.");
                                    objOut.writeObject(newMessage);
                                    objOut.flush();
                                    //client.invalidCommand("Invalid row or column.");
                                } else {
                                    try {
                                        RemoveTileEvent event = new RemoveTileEvent(player, row - 5, col - 4);
                                        game.getGameState().handleEvent(event);
                                        newMessage = new Message("Game", game, "viewMyShip");
                                        newMessage.setNickname(msg.getNickname());
                                        objOut.writeObject(newMessage);
                                        objOut.flush();
                                        objOut.reset();
                                        //client.viewMyShip(game, client.getNickname());
                                    } catch (IllegalEventException e) {
                                        newMessage = new Message("String", null, e.getMessage());
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
            }

        }
    }

    /*

    RMI CONTROLLER

     */

    public void handleUserInput(VirtualClient client, String input) throws RemoteException {
        if (input == null || !input.startsWith("/")) {
            client.invalidCommand("Invalid command");
            return;
        }
        if (input.equals("GAME")){
            updateView(ServerController.game, "game");
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
            case "ship" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    Ship ship = player.getShip();
                    game.getGameState().handleEvent(ship);
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            }
            //
            case "help" -> {
                if (!firstParameters.isEmpty() || !secondParameters.isEmpty()) {
                    client.invalidCommand("/help doesn't support parameters, but here is the help command anyway!");
                }
                client.helpMessage();
            } //ok
            case "viewcard" -> client.viewCard(game);
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
                                try {
                                    ConnectEvent event = new ConnectEvent(nickname, "localhost");
                                    game.getGameState().handleEvent(event, game);
                                    client.setNickname(nickname);
                                    client.setMainCabin(game); // TODO fix - what? how?
                                    client.connectView(game);
                                } catch (IllegalEventException e) {
                                    client.invalidCommand("Error: " + e.getMessage());
                                    //
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
                                        client.updateGame(game); //trying for gui update
                                    } catch (IllegalEventException e) {
                                        client.invalidCommand("Error: command not valid!");
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
                                try {
                                    SetNumberOfPlayersEvent event = new SetNumberOfPlayersEvent(numberOfPlayers);
                                    game.getGameState().handleEvent(event);
                                } catch (IllegalEventException e){
                                    client.invalidCommand("Error: " + e.getMessage());
                                }
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
                                try {
                                    PickUpTileEvent event = new PickUpTileEvent(player, tilePositionInt);
                                    game.getGameState().handleEvent(event);
                                    Tile currentTile = player.getShip().getTileInHand();
                                    List<VirtualClient> clientsRMI = rmiServer.getClients();
                                    for (VirtualClient virtualClient : clientsRMI) {
                                        if (virtualClient.getNickname().equals(client.getNickname())) {
                                            virtualClient.viewMyShip(game, client.getNickname());
                                        } else {
                                            virtualClient.viewTilepile(game);
                                        }
                                    }
                                } catch (IllegalEventException e) {
                                    client.invalidCommand("Error: " + e.getMessage());
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
                        try {
                            PutDownTileEvent event = new PutDownTileEvent(player);
                            game.getGameState().handleEvent(event);
                            List<VirtualClient> clientsRMI = rmiServer.getClients();
                            for (VirtualClient virtualClient : clientsRMI) {
                                virtualClient.viewTilepile(game);
                            }
                        } catch (IllegalEventException e) {
                            client.invalidCommand("Error: " + e.getMessage());
                            
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
                                try {
                                    PlaceTileEvent event = new PlaceTileEvent(player, rowInt - 5, columnInt - 4);
                                    game.getGameState().handleEvent(event);
                                    client.connectView(game);
                                } catch (IllegalEventException e) {
                                    client.invalidCommand("Error: " + e.getMessage());
                                    
                                }
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
                    if (secondParameters.isEmpty() && firstParameters.isEmpty()) {
                        try {
                            ReserveTileEvent event = new ReserveTileEvent(player);
                            game.getGameState().handleEvent(event);
                            client.connectView(game);
                        } catch (IllegalEventException e) {
                            client.invalidCommand("Error: " + e.getMessage());
                            
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
                                } catch (IOException e) {
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
                                        client.updateGame(game);
                                        //client.viewLeaderboard(game);
                                        if (rmiServer != null) {
                                            List<VirtualClient> clientsRMI = rmiServer.getClients();
                                            for (VirtualClient virtualClient : clientsRMI) {
                                                if (!virtualClient.getNickname().equals(client.getNickname())) {
                                                    virtualClient.printMessage(player.getNickname() + " has set the position to " + position);
                                                }
                                                if (game.getGameState() instanceof TravellingState) {
                                                    virtualClient.viewCard(game);
                                                }
                                            }
                                        } else {
                                            for (SocketClientHandler client1 : socketServer.getClientsList()) {
                                                if (game.getGameState() instanceof TravellingState) {
                                                    ObjectOutputStream objOut1 = client1.getObjOut();
                                                    Message newMessage = new Message("Game", game, "viewCard");
                                                    try {
                                                        objOut1.writeObject(newMessage);
                                                        objOut1.flush();
                                                        objOut1.reset();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
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
                        try {
                            PickUpFromShipEvent event = new PickUpFromShipEvent(player);
                            game.getGameState().handleEvent(event);
                            client.viewMyShip(game, client.getNickname());
                        } catch (IllegalEventException e) {
                            client.invalidCommand("Error: " + e.getMessage());
                            
                        }
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
                                try {
                                    PickUpReservedTileEvent event = new PickUpReservedTileEvent(player, index - 1);
                                    game.getGameState().handleEvent(event);
                                    client.viewMyShip(game, client.getNickname());
                                } catch (IllegalEventException e) {
                                    client.invalidCommand("Error: " + e.getMessage());
                                    
                                }
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
                                        String colBatStr = secondParameters.get(j + 1);
                                        String valueBatStr = secondParameters.get(j + 2);

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
                                    try {
                                        ActivateEnginesEvent event = new ActivateEnginesEvent(player, engines, batteries);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        client.invalidCommand("Error: " + e.getMessage());
                                        //
                                    }
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
                                    for (int j = 0; j < secondParameters.size(); j += 2) {
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
                                    try {
                                        ActivateCannonsEvent event = new ActivateCannonsEvent(player, cannons, batteries);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        client.invalidCommand("Error: " + e.getMessage());
                                        //
                                    }
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
                                        try {
                                            ActivateShieldEvent event = new ActivateShieldEvent(player, rowShield - 5, colShield - 4, rowBat - 5, colBat - 4);
                                            game.getGameState().handleEvent(event);
                                        } catch (IllegalEventException e) {
                                            client.invalidCommand("Error: " + e.getMessage());
                                            //
                                        }
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
                                        try {
                                            RemoveCargoEvent event = new RemoveCargoEvent(player, row - 5, col - 4, value);
                                            game.getGameState().handleEvent(event);
                                        } catch (IllegalEventException e) {
                                            client.invalidCommand("Error: " + e.getMessage());
                                            //
                                        }
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
                                        try {
                                            AddCargoEvent event = new AddCargoEvent(player, row - 5, col - 4, value);
                                            game.getGameState().handleEvent(event);
                                        } catch (IllegalEventException e) {
                                            client.invalidCommand("Error: " + e.getMessage());
                                            //
                                        }
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
                                    try {
                                        SwitchCargoEvent event = new SwitchCargoEvent(player, prevRow - 5, prevCol - 4, newRow - 5, newCol - 4, prevValue);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        client.invalidCommand("Error: " + e.getMessage());
                                        //
                                    }
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
                                            try {
                                                List<Integer> peopleRow = new ArrayList<>();
                                                peopleRow.add(row - 5);
                                                peopleRow.add(col - 4);
                                                peopleRow.add(value);
                                                people.add(peopleRow);
                                                EjectPeopleEvent event = new EjectPeopleEvent(player, people);
                                                game.getGameState().handleEvent(event);
                                            } catch (IllegalEventException e) {
                                                client.invalidCommand("Error: " + e.getMessage());
                                                //
                                            }
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
                    if (secondParameters.isEmpty() && firstParameters.isEmpty()) {
                        try {
                            ClaimRewardEvent event = new ClaimRewardEvent(player, true);
                            game.getGameState().handleEvent(event);
                        } catch (IllegalEventException e) {
                            client.invalidCommand("Error: " + e.getMessage());
                        }
                    } else {
                        client.invalidCommand("/claimreward doesn't require any parameters.");
                    }
                } else {
                    client.invalidCommand("You are not connected to the game!");
                }
            }
            case "choosesubship" -> {
                Player player = checkPlayer(client.getNickname());
                if (player != null) {
                    if (secondParameters.isEmpty()) {
                        if (firstParameters.size() == 2) {
                            String rowStr = firstParameters.get(0);
                            String colstr = firstParameters.get(1);

                                int row = Integer.parseInt(rowStr);
                                int col = Integer.parseInt(colstr);
                                boolean checkPosition = validTilePosition(row, col);
                                if ((row < 5 || row > 9 || col < 4 || col > 10) || !checkPosition) {
                                    client.invalidCommand("Invalid row or column.");
                                } else {
                                    try {
                                        ChooseSubShipEvent event = new ChooseSubShipEvent(player, row - 5, col - 4);
                                        game.getGameState().handleEvent(event);
                                    } catch (IllegalEventException e) {
                                        client.invalidCommand("Error: " + e.getMessage());
                                    }
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
                            try {
                                NoChoiceEvent event = new NoChoiceEvent(player);
                                game.getGameState().handleEvent(event);
                            } catch (IllegalEventException e) {
                                client.invalidCommand("Error: " + e.getMessage());
                                //
                            }
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
                            try {
                                DoneEvent event = new DoneEvent(player);
                                game.getGameState().handleEvent(event);
                            } catch (IllegalEventException e) {
                                client.invalidCommand("Error: " + e.getMessage());
                                //
                            }
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
                                    try {
                                        PlaceOrangeAlienEvent event = new PlaceOrangeAlienEvent(player, row - 5, col - 4);
                                        game.getGameState().handleEvent(event);
                                        client.printMessage("Alien placed correctly.");
                                        client.viewMyShip(game, client.getNickname());
                                    } catch (IllegalEventException e) {
                                        client.invalidCommand("Error: " + e.getMessage());
                                        //
                                    }
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
                                    try {
                                        PlacePurpleAlienEvent event = new PlacePurpleAlienEvent(player, row - 5, col - 4);
                                        game.getGameState().handleEvent(event);
                                        client.viewMyShip(game, client.getNickname());
                                        client.printMessage("Alien placed correctly.");
                                    } catch (IllegalEventException e) {
                                        client.invalidCommand("Error: " + e.getMessage());
                                        //
                                    }
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
                                    try {
                                        RemoveTileEvent event = new RemoveTileEvent(player, row - 5, col - 4);
                                        game.getGameState().handleEvent(event);
                                        client.viewMyShip(game, client.getNickname());
                                    } catch (IllegalEventException e) {
                                        client.invalidCommand("Error: " + e.getMessage());
                                        //
                                    }
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
                                try {
                                    String indexStr = firstParameters.get(0);
                                    int index = Integer.parseInt(indexStr);
                                    ChoosePlanetEvent event = new ChoosePlanetEvent(player, index);
                                    game.getGameState().handleEvent(event);
                                } catch (IllegalEventException e) {
                                    client.invalidCommand("Error: " + e.getMessage());
                                    //
                                }
                            }
                        } else {
                            client.invalidCommand("/chooseplanet supports only one set of parameters.");
                        }
                    } else {
                        client.invalidCommand("You are not connected to the game!");
                    }
                }
                case "viewdeck" -> {
                    Player player = checkPlayer(client.getNickname());
                    if (player != null) {
                        if (!secondParameters.isEmpty()) {
                            client.invalidCommand("/viewdeck supports only one set of parameters.");
                        }
                        if (firstParameters.size() == 1) {
                            String indexStr = firstParameters.get(0);
                            int index = Integer.parseInt(indexStr);
                            //ViewDeckEvent event = new ViewDeckEvent(player, index);
                            //game.getGameState().handleEvent(event);
                            client.viewDeck(game, index);
                        } else {
                            client.invalidCommand("/viewdeck supports only one parameter or no parameters at all.");
                        }
                    }
                }
                case "flipall" -> {
                    TilePile tilePile = game.getTilePile();
                    for (Tile tile : tilePile.getTilePile()) {
                        if (tile != null) {
                            tile.flip();
                        }
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
        boolean checkPosition;
        if (row == 5) {
            checkPosition = col != 4 && col != 5 && col != 7 && col != 9 && col != 10;
        } else if (row == 6) {
            checkPosition = col != 4 && col != 10;
        } else if (row == 9) {
            checkPosition = col != 7;
        } else {
            checkPosition = true;
        }
        return checkPosition;
    }

}