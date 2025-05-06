package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage;

import it.polimi.ingsw.galaxytrucker.Controller.ViewObserver.Listener;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Hourglass;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

import java.util.Timer;

public class VirtualHourglass implements Listener {
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

    @Override
    public void update(Hourglass hourglass) {
        // set all variables
    }

    public void update(Ship ship) {}
    public void update(Player player) {}
    public void update(TilePile tilePile) {}
    public void update(Game game) {}
}
