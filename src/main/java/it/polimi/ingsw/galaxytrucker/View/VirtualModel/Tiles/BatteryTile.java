package it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles;

public class BatteryTile extends Tile {
    private final int slotsNumber;
    private int slotsFilled;

    public BatteryTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, int slotsFilled) {
        super(north, west, south, east);
        this.slotsNumber = slotsNumber;
        this.slotsFilled = slotsFilled;
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
