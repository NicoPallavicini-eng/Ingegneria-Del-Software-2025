package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BatteryTile extends Tile implements Serializable {
    private final int slotsNumber;
    private int slotsFilled;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;

    public BatteryTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, int slotsFilled) {
        super(north, west, south, east);
        this.slotsNumber = slotsNumber;
        this.slotsFilled = slotsFilled;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
    }

    public int removeBattery(int quantity){
        slotsFilled -= quantity;
        return slotsFilled;
    }

    public int getSlotsNumber() {
        return slotsNumber;
    }

    public void setSlotsFilled(int slotsFilled) {
        this.slotsFilled = slotsFilled;
    }

    public int getSlotsFilled() {
        return slotsFilled;
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
