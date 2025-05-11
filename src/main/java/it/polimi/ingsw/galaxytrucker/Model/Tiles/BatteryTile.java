package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BatteryTile extends Tile implements Serializable {
    private final int slotsNumber;
    private int slotsFilled;

    @JsonCreator
    public BatteryTile(@JsonProperty("northConnector")ConnectorType north, @JsonProperty("southConnector")ConnectorType south, @JsonProperty("eastConnector")ConnectorType east, @JsonProperty("westConnector")ConnectorType west,@JsonProperty("slotsNumber") int slotsNumber,@JsonProperty("slotsFilled") int slotsFilled) {
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
