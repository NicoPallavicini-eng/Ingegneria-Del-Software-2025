package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.TileType.CANNON;

public class CannonTile extends Tile {
    private final boolean doublePower;
    private boolean activeState;
    private final Direction direction;

    public CannonTile(ConnectorType north, ConnectorType west, ConnectorType south, ConnectorType east, Direction direction, boolean doublePower, boolean activeState) {
        super(north, west, south, east, CANNON);
        this.doublePower = doublePower;
        this.activeState = activeState;
        this.direction = direction;
    }

    public boolean getDoublePower() {
        return doublePower;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public boolean getActiveState() {
        return activeState;
    }
}
