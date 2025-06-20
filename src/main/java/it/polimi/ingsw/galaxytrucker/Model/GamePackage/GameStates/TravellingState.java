package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.DisconnectEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.EventHandler;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.GiveUpEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.List;

/*Superclass to all card states, handles all the turns logic
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

    @Override
    public void next() {
        game.getListOfActivePlayers().stream().forEach(player -> player.getShip().disactivateEverything());
        EventHandler.checkGiveUp(game);
        game.updateListOfActivePlayers();
        Card nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            getGame().setGameState(new FinalState(game));
            game.notifyObservers(game, "final");
        } else {
            getGame().setGameState(nextCard.createGameState(game));
            //game.notifyObservers(game, "newcard");
        }
        game.getGameState().init();
    }

    protected void nextPlayer(){
        int index = game.getListOfActivePlayers().indexOf(currentPlayer) + 1;
        if(index == game.getListOfActivePlayers().size()){
            currentPlayer = null;
            }
        else{
            currentPlayer = game.getListOfActivePlayers().get(index);
            game.notifyObservers(game, "nextplayer");
        }
    }

    public void init(){
        currentPlayer = game.getListOfActivePlayers().getFirst();
    }

    public void handleEvent(GiveUpEvent event) throws IllegalEventException {
        EventHandler.handleEvent(event);
    }

    protected void disconnectionConsequences(Player p){
        if(currentPlayer.equals(p)){
            nextPlayer();
        }
    }
}