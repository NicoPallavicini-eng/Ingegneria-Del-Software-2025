package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;
import java.util.List;

public class CargoTile extends Tile implements Serializable {
    private final int slotsNumber;
    private final boolean fitsRed;
    private List<Integer> tileContent;

    public CargoTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, boolean fitsRed, List<Integer> tileContent) {
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