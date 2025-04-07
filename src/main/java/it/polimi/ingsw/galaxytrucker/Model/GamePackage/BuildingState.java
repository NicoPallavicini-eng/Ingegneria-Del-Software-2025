package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;

public class BuildingState implements GameState {
    private final Game game;
    private GameState nextState;

    public BuildingState( Game game ) {
        this.game = game;
    }

    public GameState getNextState() {
        return nextState;
    }

    public void setNextState(GameState nextState) {
        this.nextState = nextState;
    }

    public Game getGame() {
        return game;
    }

    public GameState next() {
        Card nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            return new FinalState(game);
        } else {
            nextCard.acceptNextVisitor(this, nextCard.getCardVisitor(), game);
            return nextState;
        }
    }

    public void process() {
        // 4 processes in parallel
    }
}
