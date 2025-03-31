package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;

public class BuildingState implements GameState {
    private final Game game;
    private GameState nextState;
    private Card nextCard;

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
        nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            return new FinalState(game);
        } else {
            return GameState(game, nextCard);
        }
    }

    public void process() {
        // 4 processes in parallel
    }
}
