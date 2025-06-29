package it.polimi.ingsw.galaxytrucker.Model.Tiles;


import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.IllegalEventException;

import java.io.Serializable;


/**
 * BatteryTile represent Battery Tile of Board game
 */
public class BatteryTile extends Tile implements Serializable {
    private final int slotsNumber;
    private int slotsFilled;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;

    /**
     * Constructor of Batteries,some parameters are set default
     * @param north
     * @param south
     * @param east
     * @param west
     * @param slotsNumber
     * @param slotsFilled
     */
    public BatteryTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, int slotsFilled) {
        super(north, south, east, west);
        this.slotsNumber = slotsNumber;
        this.slotsFilled = slotsFilled;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
    }

    /**
     * This function removes batteries from BatteryTile
     * @param quantity int
     */
    public void removeBattery(int quantity){
        if(slotsFilled < quantity){
            throw new IllegalEventException("not enough batteries");
        }
        slotsFilled -= quantity;
    }

    /**
     * This function returns the total slots number
     * @return int
     */
    public int getSlotsNumber() {
        return slotsNumber;
    }

    /**
     * This function sets number of Slots filled
     * @param slotsFilled int
     */
    public void setSlotsFilled(int slotsFilled) {
        this.slotsFilled = slotsFilled;
    }

    /**
     * This function returns the number of slots filled
     * @return int
     */
    public int getSlotsFilled() {
        return slotsFilled;
    }

    /**
     * This function is used to find the Tile Type
     * @param visitor The visitor to accept.
     */
    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
