package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.GameEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;

import java.util.Queue;

public abstract class GameState {

    public void next(){};
    public void process(){};

    public void handleEvent(GameEvent gameEvent) throws IllegalEventException {
         new IllegalEventException("The player used a command not availeble in this phase of the game.");
    }

}
