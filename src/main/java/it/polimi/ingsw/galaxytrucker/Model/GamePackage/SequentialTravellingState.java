package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;

// Handles Pirates, Ship, Slavers, Smugglers, Station Cards
public class SequentialTravellingState implements GameState {
    private final Game game;
    private final Card currentCard;
    private Card nextCard;
    private boolean accomplished = false;
    private GameState nextState;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public SequentialTravellingState(Game game, Card currentCard) {
        this.game = game;
        this.currentCard = currentCard;
    }

    public GameState getNextState() {
        return nextState;
    }

    public void setNextState(GameState nextState) {
        this.nextState = nextState;
    }

    public boolean getAccomplished() {
        return accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }

    @Override
    public GameState next() {
        nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            return new FinalState(game);
        } else {
            nextCard.acceptNextVisitor(this, nextCard.getCardVisitor(), game);
            return nextState;
        }
    }

    public void process() {
        currentCard.acceptCardVisitorSequential(this, currentCard.getCardVisitor(), game.getListOfPlayers());
    }
}