package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import java.util.List;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.TileType.STORAGE;

public class CargoTile extends Tile {
    private final int slotsNumber;
    private final boolean fitsRed;
    private List<Integer> tileContent;

    public CargoTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, int slotsNumber, boolean fitsRed, List<Integer> tileContent) {
        super(north, west, south, east, STORAGE);
        this.fitsRed = fitsRed;
        this.slotsNumber = slotsNumber;
        this.tileContent = tileContent;
    }

    public List<Integer> getTileContent() {
        return tileContent;
    }

    public Integer getSlotsContent() {
        //TODO logic for getSlotsContent
        return null;
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