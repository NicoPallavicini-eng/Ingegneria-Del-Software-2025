package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.GameEvent;

public abstract class TravellingState implements GameState {
    protected final Game game;
    protected final Card currentCard;
    protected int handledPlayers = 0;

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

    @Override
    public void next() {
         Card nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            getGame().setGameState(new FinalState(game));
        } else {
            getGame().setGameState(TravellingStateFactory.createGameState(game, nextCard));
        }
    }

    public void process() {
    }
}