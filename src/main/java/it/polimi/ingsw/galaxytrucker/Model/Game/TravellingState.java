package it.polimi.ingsw.galaxytrucker.Model.Game;

public class TravellingState implements GameState {
    private Game game;

    public TravellingState( Game game ) {
        this.game = game;
    }
    @Override
    public GameState next() {
        return new FinalState();
    }
}
