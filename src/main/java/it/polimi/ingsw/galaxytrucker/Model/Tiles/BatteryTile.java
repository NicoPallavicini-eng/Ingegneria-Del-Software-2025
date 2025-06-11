package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;

import java.io.Serializable;

public class BatteryTile extends Tile implements Serializable {
    private final int slotsNumber;
    private int slotsFilled;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;

    public BatteryTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, int slotsFilled) {
        super(north, south, east, west);
        this.slotsNumber = slotsNumber;
        this.slotsFilled = slotsFilled;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
    }

    public void removeBattery(int quantity){
        if(slotsFilled <= quantity){
            throw new IllegalEventException("not enough batteries");
        }
        slotsFilled -= quantity;
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
