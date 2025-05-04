package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualAlienColor;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualConnectorType;

public class VirtualBioadaptorTile extends VirtualTile {
    private final VirtualAlienColor color;
    private boolean isOrange;
    private boolean isPurple;

    public VirtualBioadaptorTile(VirtualConnectorType north, VirtualConnectorType west, VirtualConnectorType south, VirtualConnectorType east, VirtualAlienColor color){
        super(north, west, south, east);
        this.color = color;
    }
    public VirtualAlienColor getColor() {
        return this.color;
    }

    public boolean isOrange() {
        return isOrange;
    }

    public boolean isPurple() {
        return isPurple;
    }
}
