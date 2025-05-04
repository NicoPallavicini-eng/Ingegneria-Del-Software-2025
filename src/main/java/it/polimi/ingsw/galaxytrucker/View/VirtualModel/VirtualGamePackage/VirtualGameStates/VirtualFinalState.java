package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGameStates;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualGamePackage.VirtualGame;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.VirtualShip;

import java.util.ArrayList;
import java.util.List;

public class VirtualFinalState extends VirtualGameState {
    private final VirtualGame game;
    private List<VirtualShip> ships = new ArrayList<>();

    public VirtualFinalState(VirtualGame game) {
        this.game = game;
    }

    public VirtualGame getGame() {
        return game;
    }

    public List<VirtualShip> getShips() {
        return ships;
    }
}