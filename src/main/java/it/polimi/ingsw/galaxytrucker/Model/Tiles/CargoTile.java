package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class CargoTile extends Tile implements Serializable {
    private final int slotsNumber;
    private final boolean fitsRed;
    private List<Integer> tileContent;

    @JsonCreator
    public CargoTile(@JsonProperty("northConnector")ConnectorType north, @JsonProperty("southConnector")ConnectorType south, @JsonProperty("eastConnector")ConnectorType east, @JsonProperty("westConnector")ConnectorType west,@JsonProperty("slotsNumber") int slotsNumber,@JsonProperty("fitsRed") boolean fitsRed,@JsonProperty("tileContent") List<Integer> tileContent) {
        super(north, west, south, east);
        this.fitsRed = fitsRed;
        this.slotsNumber = slotsNumber;
        this.tileContent = tileContent;
    }

    public List<Integer> getTileContent() {
        return tileContent;
    }

    public void setTileContent(Integer tileContent) {
        this.tileContent.add(tileContent);
    }

    public void removeBlock(Integer block) {
        tileContent.remove(block);
    }

    public boolean fitsRed() {
        return fitsRed;
    }

    public int getSlotsNumber() {
        return slotsNumber;
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}