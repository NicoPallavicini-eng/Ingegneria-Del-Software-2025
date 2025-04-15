package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

public class WaitingState implements GameState {
    private final Game game;

    public WaitingState( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void next() {
        getGame().setGameState(new BuildingState(game));
    }

    public void process() {
        // input max player
        // wait for connections
    }
}
