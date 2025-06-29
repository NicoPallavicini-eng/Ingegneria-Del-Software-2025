package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StardustCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * StardustState is a game state that represents the Stardust phase in the game.
 * During this phase, players' ships are moved backward along their exposed connectors.
 * The state processes the turns of all active players in reverse order.
 */
public class StardustState extends TravellingState implements Serializable {
    private StardustCard currentCard;

    /**
     * Constructor for StardustState.
     * Initializes the game state with the given game and StardustCard.
     *
     * @param game The current game instance.
     * @param card The StardustCard associated with this state.
     */
    public StardustState(Game game, StardustCard card) {
        super(game, card);
        currentCard = card;
    }
    private List<Player> turns;

    /**
     * Initializes the StardustState by reversing the order of active players
     * and processing their turns to move their ships backward.
     * Notifies observers about the start of the Stardust phase.
     */
    public void init(){
        turns = new ArrayList<>(game.getListOfActivePlayers());
        Collections.reverse(turns);
        process();
        game.notifyObservers(game, "stardust");

    }

    /**
     * Processes the Stardust phase by moving each player's ship backward
     * along their exposed connectors and notifying observers of the game state.
     */
    private void process(){
        for (Player player : turns) {
            EventHandler.moveBackward(player.getShip(), player.getShip().getExposedConnectors(), game);
        }
        game.notifyObservers(game, "stardustEnd");
        game.notifyObservers(game, "newcard");
        next();
    }
}
