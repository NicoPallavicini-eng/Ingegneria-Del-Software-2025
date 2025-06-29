package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.Cards.StardustCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.DisconnectEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.GiveUpEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.List;


/**
 * Abstract class representing a travelling state in the game.
 * This class extends GameState and implements Serializable.
 * It manages the current card, handled players, and the current player.
 * It is extended by specific states that are representing different cards in the game.
 */

public abstract class TravellingState extends GameState implements Serializable {
    protected final Card currentCard;
    protected List<Player> handledPlayers;
    protected Player currentPlayer;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public List<Player> getHandledPlayers() {
        return handledPlayers;
    }

    public void setHandledPlayers(List<Player> handledPlayers) {
        this.handledPlayers = handledPlayers;
    }

    /**
     * Constructor for TravellingState.
     * Initializes the game and the current card.
     * Sorts the list of active players in the game.
     *
     * @param game The current game instance.
     * @param currentCard The card associated with this travelling state.
     */
    public TravellingState(Game game, Card currentCard) {
        this.game = game;
        this.currentCard = currentCard;
        game.sortListOfActivePlayers();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Handles the event of a player giving up during the game.
     * It checks if the event is valid and processes the give-up action.
     *
     * @param event The GiveUpEvent containing the player who gave up.
     * @throws IllegalEventException If the event is not valid.
     */
    @Override
    public void next() {
        game.getListOfActivePlayers().stream().forEach(player -> player.getShip().disactivateEverything());
        EventHandler.checkGiveUp(game);
        game.updateListOfActivePlayers();
        Card nextCard = getGame().getDeck().drawCard();
        if (nextCard == null || game.getListOfActivePlayers().isEmpty()) {
            getGame().setGameState(new FinalState(game));
        } else {
            //getGame().setGameState(new StardustState(game,new StardustCard(true,true)));
            getGame().setGameState(nextCard.createGameState(game));
            //game.notifyObservers(game, "newcard");
        }
        game.getGameState().init();
    }

    /**
     * Handles the event of a player disconnecting from the game.
     * It processes the disconnection consequences and notifies observers.
     *
     * @param event The DisconnectEvent containing the player who disconnected.
     */
    protected void nextPlayer(){
        if(currentPlayer!=null) {
            int index = game.getListOfActivePlayers().indexOf(currentPlayer) + 1;
            if (index == game.getListOfActivePlayers().size()) {
                currentPlayer = null;
            } else {
                currentPlayer = game.getListOfActivePlayers().get(index);
                game.notifyObservers(game, "nextplayer");
            }
        }
    }

    /**
     * Initializes the travelling state by setting the current player to the first active player.
     * If there are no active players, it calls next() to transition to the next state.
     * Notifies observers about the new card.
     */
    public void init(){
        synchronized (game.getListOfActivePlayers()) {
            if(game.getListOfActivePlayers().isEmpty()){
                next();
            }
            currentPlayer = game.getListOfActivePlayers().getFirst();
        }
        game.notifyObservers(game, "newcard");
    }

    /**
     * Handles the event of a player giving up during the game.
     * It processes the give-up action and notifies observers.
     *
     * @param event The GiveUpEvent containing the player who gave up.
     * @throws IllegalEventException If the event is not valid.
     */
    public void handleEvent(GiveUpEvent event) throws IllegalEventException {
        EventHandler.handleEvent(event);
    }

    /**
     * Handles the event of a player disconnecting from the game.
     * It processes the disconnection consequences and notifies observers.
     *
     * @param event The DisconnectEvent containing the player who disconnected.
     */
    protected void disconnectionConsequences(Player p){
        super.disconnectionConsequences(p);
        if(currentPlayer!=null && currentPlayer.equals(p)){
            nextPlayer();
        }
    }
}