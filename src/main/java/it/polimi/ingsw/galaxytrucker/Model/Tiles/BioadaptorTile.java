package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.TileType.BIOADAPTOR;

public class BioadaptorTile extends Tile {
    private final AlienColor color;

    public BioadaptorTile(ConnectorType north, ConnectorType west, ConnectorType south, ConnectorType east, AlienColor color){
        super(north, west, south, east, BIOADAPTOR);
        this.color = color;
    }
    public AlienColor getColor() {
        return this.color;
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
