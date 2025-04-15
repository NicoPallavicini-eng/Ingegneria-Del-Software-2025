package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class FinalState implements GameState {
    private final Game game;

    public FinalState(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void next() {
        getGame().setGameState(null);
    }

    @Override
    public void process() {

    }
}
