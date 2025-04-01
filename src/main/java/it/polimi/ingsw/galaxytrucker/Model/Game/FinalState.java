package it.polimi.ingsw.galaxytrucker.Model.Game;

public class FinalState implements GameState {
    private final Game game;

    public FinalState( Game game ) {
        this.game = game;
    }

    @Override
    public GameState next() {
        return null;
    }

    @Override
    public void process() {
        // output points
    }

    @Override
    public GameState getNextState() {
        return null;
    }

    @Override
    public void setNextState(GameState nextState) {}

    public Game getGame() {
        return game;
    }
}
