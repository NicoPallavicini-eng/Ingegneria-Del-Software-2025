package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;

/**
 * Shiled Tile represent Shield Tile of Board game
 */
public class ShieldTile extends Tile implements Serializable {
    private ShieldOrientation orientation;
    private boolean activeState;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;

    /**
     * Constructor of Shield Tile,some parameters are set to default values
     * @param north
     * @param south
     * @param east
     * @param west
     * @param orientation
     * @param activeState
     */
    public ShieldTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, ShieldOrientation orientation, boolean activeState) {
        super(north, south, east, west);
        this.orientation = orientation;
        this.activeState = activeState;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
    }

    /**
     * This function sets an Active Status of Shield
     * @param activeState
     */
    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    /**
     * this function tells you whether shield is active or not
     * @return boolean
     */
    public boolean getActiveState(){
        return activeState;
    }

    /**
     * This function returns ShieldOrientation which Shield protect
     * @return ShieldOrientation
     */
    public ShieldOrientation getOrientation(){
        return orientation;
    }

    /**
     * This function overrides function of Tile.Some controls added to set properly values of Shield,when is rotated
     * @param side The direction to rotate the tile (LEFT or RIGHT).
     */
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

    /**
     * This function is used to find the Tile Type
     * @param visitor The visitor to accept.
     */
    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
