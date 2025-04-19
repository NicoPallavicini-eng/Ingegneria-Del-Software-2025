package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.GameEvent;

import java.util.Queue;

public abstract class GameState {

    private Queue<GameEvent> modelEvents;

    public void next(){};
    public void process(){};

    //to synchronize
    public GameEvent poolEventQueue() {
        return modelEvents.poll();
    }

    //to synchronize
    public void addEventQueue(GameEvent gameEvent) {
        modelEvents.add(gameEvent);
    }

}
