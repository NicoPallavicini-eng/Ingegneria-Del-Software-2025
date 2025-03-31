package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;

// Handles OpenSpace Card
public class InteractiveTravellingState implements GameState {
    private final Game game;
    private final Card currentCard;
    private Card nextCard;
    private GameState nextState;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public InteractiveTravellingState(Game game, Card currentCard) {
        this.game = game;
        this.currentCard = currentCard;
    }

    public GameState getNextState() {
        return nextState;
    }

    public void setNextState(GameState nextState) {
        this.nextState = nextState;
    }

    @Override
    public GameState next() {
        nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            return new FinalState(game);
        } else {
            nextCard.acceptNextVisitor(this, nextCard.getCardVisitor(), game, nextCard);
            return nextState;
        }
    }

    public void process() {
        currentCard.acceptCardVisitorInteractive(this, currentCard.getCardVisitor(), game.getListOfPlayers());
    }
}