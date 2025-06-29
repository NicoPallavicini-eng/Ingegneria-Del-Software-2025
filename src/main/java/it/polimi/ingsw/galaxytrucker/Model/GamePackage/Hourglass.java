package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.BuildingState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.FinalState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hourglass Class represent a physical Hourglass form a Board Game,it used to calculate time during Building Phase
 */
public class Hourglass implements Serializable {
    private int flipNumber = 0;
    private boolean hasEnded = true;
    private long startTime;
    private BuildingState state;
    private transient final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private boolean someoneHasReconnected = true;

    /**
     * @param state
     */
    public Hourglass(BuildingState state) {
        this.state = state;
    }

    /**
     * This function Flips hourglass,and starts 60 second count
     */
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

    /**
     * This function return the number of times that Hourglass was flipped
     * @return int
     */
    public int getFlipNumber() {
        return flipNumber;
    }

    /**
     * This function returns whether Hourglass has ended or not
     * @return boolean
     */
    public boolean getHasEnded() {
        return hasEnded;
    }

    /**
     * This function returns the time that has remained
     * @return long
     */
    public long getElapsedTime(){
        long remaining = (60000 - System.currentTimeMillis() + startTime)/1000;
        return remaining > 0 ? remaining : 0;
    }

    /**
     * This function checks whether player win by disconession
     * @param game
     * @param winner
     */
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


    /**
     * This function sets someoneHasReconnected to True
     */
    public void connection(){
        someoneHasReconnected = true;
    }
    /**
     * This function checks if someone was reconnected .
     */
    public boolean hasSomeoneReconnected() {
        return someoneHasReconnected;
    }
}
