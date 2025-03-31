package it.polimi.ingsw.galaxytrucker.Model.Game;

public interface GameState {

    public GameState next();
    public void process();
    public GameState getNextState();
    public void setNextState(GameState nextState);

}
