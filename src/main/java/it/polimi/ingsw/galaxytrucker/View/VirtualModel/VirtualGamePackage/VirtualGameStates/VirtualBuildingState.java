package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualPlayer;

import java.util.ArrayList;
import java.util.Map;

public class VirtualBuildingState extends VirtualGameState {
    private final VirtualGame game;
    private ArrayList<VirtualPlayer> finishedBuildingPlayers;
    private ArrayList<VirtualPlayer> playersWithLegalShips;
    private boolean timeIsUp = false;
    // frist for orange and second for purple
    private Map<VirtualPlayer, Boolean[]> placedAliens;

    public VirtualBuildingState(VirtualGame game ) {
        this.game = game;
    }

    public VirtualGame getGame() {
        return game;
    }

    public ArrayList<VirtualPlayer> getFinishedBuildingPlayers() {
        return finishedBuildingPlayers;
    }

    public ArrayList<VirtualPlayer> getPlayersWithLegalShips() {
        return playersWithLegalShips;
    }

    public boolean isTimeIsUp() {
        return timeIsUp;
    }

    public Map<VirtualPlayer, Boolean[]> getPlacedAliens() {
        return placedAliens;
    }
}
