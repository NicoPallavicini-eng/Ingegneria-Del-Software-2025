package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage;

import java.util.Timer;

public class VirtualHourglass {
    private int FlipNumber = 0;
    private Boolean hasEnded;
    private Timer timer = new Timer();
    private long startTime;
    private long elapsedTime;

    public int getFlipNumber() {
        return FlipNumber;
    }

    public Boolean getHasEnded() {
        return hasEnded;
    }

    // link observer
    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getStartTime() {
        return startTime;
    }

    Timer getTimer() {
        return timer;
    }
}
