package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualConnectorType;

public class VirtualCannonTile extends VirtualTile {
    private final boolean doublePower;
    private boolean activeState;


    public VirtualCannonTile(VirtualConnectorType north, VirtualConnectorType west, VirtualConnectorType south, VirtualConnectorType east, boolean doublePower, boolean activeState) {
        super(north, west, south, east);
        this.doublePower = doublePower;
        this.activeState = activeState;
    }

    public boolean getDoublePower() {
        return doublePower;
    }

    public boolean getActiveState() {
        return activeState;
    }
}
