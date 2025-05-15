package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.BuildingState;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//todo fix
//the Timer class implementation is kept (in comments) for possible future use
public class Hourglass implements Serializable {
    private int flipNumber = -1;
    private boolean hasEnded;
    private long startTime;
    private BuildingState state;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Hourglass(BuildingState state) {
        this.state = state;
    }

    public void flip(){
        if(!hasEnded){
            throw new IllegalEventException("The hourglass is not ready to be flipped yet");
        }
        hasEnded = false;
        startTime = System.currentTimeMillis();
        flipNumber++;
        scheduler.schedule(() -> {
            hasEnded = true;
            if(flipNumber == 2){
                state.timeUp();
            }
        }, 60, TimeUnit.SECONDS);
    }

    public int getFlipNumber() {
        return flipNumber;
    }

    public boolean getHasEnded() {
        return hasEnded;
    }

    public long getElapsedTime(){
        long remaining = (60000 - System.currentTimeMillis() + startTime)/1000;
        return remaining > 0 ? remaining : 0;
    }

}
