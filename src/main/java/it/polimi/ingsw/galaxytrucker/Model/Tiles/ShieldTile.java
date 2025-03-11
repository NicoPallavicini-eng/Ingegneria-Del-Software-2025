package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.TileType.SHIELD;

public class ShieldTile extends Tile {
    private final ShieldOrientation orientation;
    private boolean activeState;

    public ShieldTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, ShieldOrientation orientation, boolean activeState) {
        super(north, west, south, east, SHIELD);
        this.orientation = orientation;
        this.activeState = activeState;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public boolean getActiveState(){
        return activeState;
    }

}
