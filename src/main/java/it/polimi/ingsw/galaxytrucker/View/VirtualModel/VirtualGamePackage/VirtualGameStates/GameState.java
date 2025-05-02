package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents.GameEvent;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameEvents.IllegalEventException;

public class GameState {

    public void handleEvent(GameEvent gameEvent) throws IllegalEventException {
         throw new IllegalEventException("The player used a command not available in this phase of the game.");
    }

    public void next(){};
    public void init(){};

}
