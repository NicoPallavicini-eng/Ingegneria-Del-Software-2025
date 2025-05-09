package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;

public class ShieldTile extends Tile implements Serializable {
    private ShieldOrientation orientation;
    private boolean activeState;

    public ShieldTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, ShieldOrientation orientation, boolean activeState) {
        super(north, west, south, east);
        this.orientation = orientation;
        this.activeState = activeState;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public boolean getActiveState(){
        return activeState;
    }

    public ShieldOrientation getOrientation(){
        return orientation;
    }

    @Override
    public void rotate(Side side){
        super.rotate(side);
        if (side == Side.RIGHT) {
            if (orientation == ShieldOrientation.NORTHWEST) {
                this.orientation = ShieldOrientation.NORTHEAST;
            }
            else if (orientation == ShieldOrientation.SOUTHWEST) {
                this.orientation = ShieldOrientation.NORTHWEST;
            }
            else if (orientation == ShieldOrientation.SOUTHEAST) {
                this.orientation = ShieldOrientation.SOUTHWEST;
            }
            else if (orientation == ShieldOrientation.NORTHEAST){
                this.orientation = ShieldOrientation.SOUTHEAST;
            }
        }
        else if (side == Side.LEFT) {
            if (orientation == ShieldOrientation.NORTHWEST) {
                this.orientation = ShieldOrientation.SOUTHWEST;
            }
            else if (orientation == ShieldOrientation.SOUTHWEST) {
                this.orientation = ShieldOrientation.SOUTHEAST;
            }
            else if (orientation == ShieldOrientation.SOUTHEAST) {
                this.orientation = ShieldOrientation.NORTHEAST;
            }
            else if (orientation == ShieldOrientation.NORTHEAST){
                this.orientation = ShieldOrientation.NORTHWEST;
            }
        }
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
