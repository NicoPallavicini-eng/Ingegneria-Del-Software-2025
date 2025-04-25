package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.FInishBuildingEvent;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.ArrayList;

public class BuildingState extends GameState {
    private final Game game;
    private ArrayList<Player> finishedBuildingPlayers;
    private ArrayList<Player> playersWithLegalShips;
    private boolean checkPhase = false;

    public BuildingState( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void next() {
        Card nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            getGame().setGameState(new FinalState(game));
        } else {
            getGame().setGameState(TravellingStateFactory.createGameState(game, nextCard));
        }
        game.getGameState().init();
    }

    public void init(){
        finishedBuildingPlayers = new ArrayList<>();
    }


    public void handleEvent(SetPositionEvent event) {
        if(finishedBuildingPlayers.contains(event.player())){
            throw new IllegalEventException("You have already placed your rocket");
        }
        else{
            EventHandler.handleEvent(event);
            if(finishedBuildingPlayers.containsAll(game.getListOfPlayers())) {
                checkPhase = true;
                //controlla le navi
                if(playersWithLegalShips.containsAll(game.getListOfPlayers())) {
                    next();
                }
            }

        }
    }

    public void handleEvent(RemoveTileEvent event) {
        if(!checkPhase){
            throw new IllegalEventException("You have to wait for all rockets to be placed");
        }
        else if(playersWithLegalShips.contains(event.player())){
            throw new IllegalEventException("You have already a functioning spaceship");
        }
        else{
            EventHandler.handleEvent(event);
            // se la nave è legale
            playersWithLegalShips.add(event.player());
        }
    }

    public void handleEvent(PickUpTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player())){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(PutDownTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player())){
            throw new IllegalEventException("You can no longer put down any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(PickUpFromShipEvent event) {
        if(finishedBuildingPlayers.contains(event.player())){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(PickUpReservedTileEvent event) {
        if(finishedBuildingPlayers.contains(event.player())){
            throw new IllegalEventException("You can no longer pick up any tiles");
        }
        else{
            EventHandler.handleEvent(event);
        }
    }

    public void handleEvent(FlipHourglassEvent event) {
        if (checkPhase) {
            throw new IllegalEventException("Hourglass flipping phase is over");
        }

    }

}
