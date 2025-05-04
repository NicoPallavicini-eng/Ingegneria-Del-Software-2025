package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualDirection;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualConnectorType;


public class VirtualEngineTile extends VirtualTile {
    private final boolean doublePower;
    private boolean activeState;
    private VirtualDirection direction;

    public VirtualEngineTile(boolean doublePower, boolean activeState, VirtualConnectorType north, VirtualConnectorType south, VirtualConnectorType east, VirtualConnectorType west) {
        super(north, west, south, east);
        this.doublePower = doublePower;
        this.activeState = activeState;
    }

    public boolean getDoublePower(){
        return doublePower;
    }

    public boolean getActiveState() {
        return activeState;
    }

    public VirtualDirection getDirection(){
        return direction;
    }
}
