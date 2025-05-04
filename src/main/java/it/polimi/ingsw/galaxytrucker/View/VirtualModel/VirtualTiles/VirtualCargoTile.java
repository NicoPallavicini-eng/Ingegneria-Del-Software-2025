package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualConnectorType;

import java.util.List;

public class VirtualCargoTile extends VirtualTile {
    private final int slotsNumber;
    private final boolean fitsRed;
    private List<Integer> tileContent;

    public VirtualCargoTile(VirtualConnectorType north, VirtualConnectorType south, VirtualConnectorType east, VirtualConnectorType west, int slotsNumber, boolean fitsRed, List<Integer> tileContent) {
        super(north, west, south, east);
        this.fitsRed = fitsRed;
        this.slotsNumber = slotsNumber;
        this.tileContent = tileContent;
    }

    public List<Integer> getTileContent() {
        return tileContent;
    }

    public boolean fitsRed() {
        return fitsRed;
    }

    public int getSlotsNumber() {
        return slotsNumber;
    }
}