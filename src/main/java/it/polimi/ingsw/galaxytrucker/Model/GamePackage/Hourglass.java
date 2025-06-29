package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.BuildingState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.FinalState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Hourglass implements Serializable {
    private int flipNumber = 0;
    private boolean hasEnded = true;
    private long startTime;
    private BuildingState state;
    private transient final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private boolean someoneHasReconnected = true;

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
            if(flipNumber == 3){
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

    public void disconnectionTimer(Game game, Player winner){
        someoneHasReconnected = false;
        scheduler.schedule(() -> {
            if(!someoneHasReconnected) {
                game.setGameState(new FinalState(game));
                game.getGameState().init();
                throw new IllegalEventException(winner + "has won for disconnections");
            }
        }, 30, TimeUnit.SECONDS);
    }

    public void connection(){
        someoneHasReconnected = true;
    }

    public boolean hasSomeoneReconnected() {
        return someoneHasReconnected;
    }
}
