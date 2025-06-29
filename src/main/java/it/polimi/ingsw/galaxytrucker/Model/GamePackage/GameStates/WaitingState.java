package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.ConnectEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.SetNumberOfPlayersEvent;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.Optional;

/*first state at game creation
 */

/**
 * The WaitingState class represents the initial state of the game where players first connect.
 * waits for the first player to connect, set the number of players
 * and then for all the numberofplayers - 1 remaining players to connect.
 * It extends the GameState class and implements Serializable for serialization purposes.
 */
public class WaitingState extends GameState implements Serializable {
    public WaitingState(Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    /**
     * This method transitions the game to the BuildingState after all players have connected.
     * It initializes the new state and notifies observers about the change.
     */
    public void next() {
        getGame().setGameState(new BuildingState(game));
        game.getGameState().init();
        game.notifyObservers(game, "buildingState");
    }

    /**
     * Handles the ConnectEvent by checking if the player is already online,
     * updating their online status, and adding them to the list of active players.
     * If the number of players is set, it transitions to the next state when all players are connected.
     *
     * @param event The ConnectEvent containing the player's nickname.
     * @param game The current game instance.
     * @throws IllegalEventException if the first player has not set the number of players or if the player is already online.
     */
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

   /**
    * Handles the SetNumberOfPlayersEvent by setting the number of players for the game.
    * It throws an exception if the number of players has already been set.
    *
    * @param event The SetNumberOfPlayersEvent containing the number of players.
    * @throws IllegalEventException if the number of players has already been set.
    */
   public void handleEvent(SetNumberOfPlayersEvent event){
        if(game.getNumberOfPlayers()!=-1){
            throw new IllegalEventException("The first player has already set the number of players");
        }
        else{
            game.setNumberOfPlayers(event.numberOfPlayers());
        }
   }
}
