package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.TileType.ENGINE;


public class EngineTile extends Tile{
    private final boolean doublePower;
    private boolean activeState;
    private Direction direction;

    public EngineTile(boolean doublePower, boolean activeState, ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west) {
        super(north, west, south, east, ENGINE);
        this.doublePower = doublePower;
        this.activeState = activeState;
    }

    public boolean GetDoublePower(){
        return doublePower;
    }

    public boolean getActiveState() {
        return activeState;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public Direction GetDirection(){
        return direction;
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visitEnginge(this);
    }
}
