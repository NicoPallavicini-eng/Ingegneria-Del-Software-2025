package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.GameEvent;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;

import java.util.Queue;

public abstract class GameState {

    public void handleEvent(GameEvent gameEvent) throws IllegalEventException {
         throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void next(){};
    public void init(){};

}
