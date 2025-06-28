package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ConnectEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.SetNumberOfPlayersEvent;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.Optional;

/*first state at game creation waits for the first player to connect, set the number of players
and then for all the numberofplayers - 1 remaining players to connect
 */

public class WaitingState extends GameState implements Serializable {
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
       Optional<Player> playerOptional = game.getListOfPlayers().stream().filter(p -> p.getNickname().equals(event.nickname())).findAny();
        if(playerOptional.isPresent()) {
            Player player = playerOptional.get();
            if(player.getOnlineStatus()){
                throw new IllegalEventException("The player is already online");
            }
            else{
                player.setOnlineStatus(true);
                game.getListOfActivePlayers().add(player);
            }
        }
        else{
            EventHandler.handleEvent(event, game);
            if (game.getListOfPlayers().size() == game.getNumberOfPlayers()) {
                next();
            }
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
