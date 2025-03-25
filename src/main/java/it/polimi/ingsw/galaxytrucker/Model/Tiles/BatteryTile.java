package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.TileType.BATTERY;

public class BatteryTile extends Tile{
    private final int slotsNumber;
    private int slotsFilled;

    public BatteryTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, int slotsFilled) {
        super(north, west, south, east, BATTERY);
        this.slotsNumber = slotsNumber;
        this.slotsFilled = slotsFilled;
    }

    public int removeBattery(int quantity){
        slotsFilled -= quantity;
        return slotsFilled;
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
