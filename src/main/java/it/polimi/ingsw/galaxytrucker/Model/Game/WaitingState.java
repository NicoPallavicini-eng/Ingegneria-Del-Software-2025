package it.polimi.ingsw.galaxytrucker.Model.Game;

public class WaitingState implements GameState {

    @Override
    public GameState next() {
        return new BuildingState();
    }
}
