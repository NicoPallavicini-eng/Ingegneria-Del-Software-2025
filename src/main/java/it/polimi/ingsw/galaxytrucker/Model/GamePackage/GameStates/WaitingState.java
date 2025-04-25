package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ConnectEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.NumberOfPlayersNotSetException;

public class WaitingState extends GameState {
    private final Game game;

    public WaitingState( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void next() {
        getGame().setGameState(new BuildingState(game));
        game.getGameState().init();
    }

   public void handleEvent(ConnectEvent event) throws NumberOfPlayersNotSetException {
        if(game.getNumberOfPlayers() == 0){
            throw new NumberOfPlayersNotSetException("The first player has to set the number of players");
        }
        EventHandler.handleEvent(event);
        if(game.getListOfPlayers().size() == game.getNumberOfPlayers()) {
            next();
        }
   }
}
