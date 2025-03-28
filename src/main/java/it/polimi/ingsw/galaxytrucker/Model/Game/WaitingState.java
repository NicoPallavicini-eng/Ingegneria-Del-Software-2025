package it.polimi.ingsw.galaxytrucker.Model.Game;

public class WaitingState implements GameState {
    private final Game game;

    public WaitingState( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public GameState next() {
        return new BuildingState(game);
    }

    public void process() {
        //input max player
        // wait for connections
    }
}
