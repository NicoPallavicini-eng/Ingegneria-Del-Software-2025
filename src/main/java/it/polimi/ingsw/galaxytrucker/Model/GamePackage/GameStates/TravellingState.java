package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;


public abstract class TravellingState extends GameState implements Serializable {
    protected final Game game;
    protected final Card currentCard;
    protected int handledPlayers = 0;
    protected Player currentPlayer;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public int getHandledPlayers() {
        return handledPlayers;
    }

    public void setHandledPlayers(int handledPlayers) {
        this.handledPlayers = handledPlayers;
    }

    public TravellingState(Game game, Card currentCard) {
        this.game = game;
        this.currentCard = currentCard;
        game.sortListOfPlayers();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void next() {
        game.getListOfPlayers().stream().forEach(player -> player.getShip().disactivateEverything());
        game.sortListOfPlayers();
        Card nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            getGame().setGameState(new FinalState(game));
        } else {
            getGame().setGameState(TravellingStateFactory.createGameState(game, nextCard));
        }
        game.getGameState().init();
    }

    protected void nextPlayer(){
        int index = game.getListOfPlayers().indexOf(currentPlayer) + 1;
        if(index == game.getListOfPlayers().size()){
            currentPlayer = null;
            }
        else{
            currentPlayer = game.getListOfPlayers().get(index);
        }
    }

    public void init(){
        currentPlayer = game.getListOfPlayers().getFirst();
    }

    public void process() {
    }
}