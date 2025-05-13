package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ConnectEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.SetNumberOfPlayersEvent;

import java.io.Serializable;

public class WaitingState extends GameState implements Serializable {
    private final Game game;
    public WaitingState(Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void next() {
        getGame().setGameState(new BuildingState(game));
        game.getGameState().init();
    }

   public void handleEvent(ConnectEvent event, Game game) throws IllegalEventException {
        if(game.getNumberOfPlayers() == -1 && !game.getListOfPlayers().isEmpty()) {
            throw new IllegalEventException("The first player has to set the number of players");
        }
        EventHandler.handleEvent(event, game);
        if(game.getListOfActivePlayers().size() == game.getNumberOfPlayers()) {
            next();
        }
   }

   public void handleEvent(SetNumberOfPlayersEvent event){
        if(game.getNumberOfPlayers()!=-1){
            throw new IllegalEventException("The first player has already set the number of players");
        }
        else{
            game.setNumberOfPlayers(event.numberOfPlayers());
        }
   }
}
