package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualConnectorType;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualShieldOrientation;

public class VirtualShieldTile extends VirtualTile {
    private VirtualShieldOrientation orientation;
    private boolean activeState;

    public VirtualShieldTile(VirtualConnectorType north, VirtualConnectorType south, VirtualConnectorType east, VirtualConnectorType west, VirtualShieldOrientation orientation, boolean activeState) {
        super(north, west, south, east);
        this.orientation = orientation;
        this.activeState = activeState;
    }

    public boolean getActiveState(){
        return activeState;
    }

    public VirtualShieldOrientation getOrientation(){
        return orientation;
    }
}
