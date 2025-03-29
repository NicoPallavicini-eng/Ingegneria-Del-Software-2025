package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;

public class BuildingState implements GameState {
    private final Game game;

    public BuildingState( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public GameState next() {
        Deck deck = game.getDeck();
        Card card = deck.drawCard();
        // TODO usa visitor to differentiate category
        return new ParallelTravellingState(game);
    }

    public void process() {
        // 4 processes in parallelo
    }
}
