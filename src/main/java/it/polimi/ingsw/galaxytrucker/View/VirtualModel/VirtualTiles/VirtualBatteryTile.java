package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualConnectorType;

public class VirtualBatteryTile extends VirtualTile {
    private final int slotsNumber;
    private int slotsFilled;

    public VirtualBatteryTile(VirtualConnectorType north, VirtualConnectorType south, VirtualConnectorType east, VirtualConnectorType west, int slotsNumber, int slotsFilled) {
        super(north, west, south, east);
        this.slotsNumber = slotsNumber;
        this.slotsFilled = slotsFilled;
    }

    public int getSlotsNumber() {
        return slotsNumber;
    }

    public int getSlotsFilled() {
        return slotsFilled;
    }
}
