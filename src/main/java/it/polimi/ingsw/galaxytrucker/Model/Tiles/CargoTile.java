package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CargoTile extends Tile implements Serializable {
    private final int slotsNumber;
    private final boolean fitsRed;
    private List<Integer> tileContent;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;

    public CargoTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, boolean fitsRed, List<Integer> tileContent) {
        super(north, south, east, west);
        this.fitsRed = fitsRed;
        this.slotsNumber = slotsNumber;
        //this.tileContent = tileContent;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
        this.tileContent = new ArrayList<>();

    }

    public List<Integer> getTileContent() {
        return tileContent;
    }

    public void addBlock(Integer block) {
        this.tileContent.add(block);
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