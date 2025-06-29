package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.galaxytrucker.Model.Direction;

import java.io.Serializable;

/**
 * CannonTile represent the Cannon Tile from Board Game
 */
public class CannonTile extends Tile implements Serializable {
    private final boolean doublePower;
    private boolean activeState;
    private Direction direction = Direction.NORTH;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;

    /**
     * Constructor of Cannon Tile,some parameters are set to default values
     * @param north
     * @param south
     * @param east
     * @param west
     * @param doublePower
     * @param activeState
     */
    @JsonCreator
    public CannonTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, boolean doublePower, boolean activeState) {
        super(north, south, east, west);
        this.doublePower = doublePower;
        this.activeState = activeState;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
    }

    /**
     * This function returns direction of a Cannon
     * @return Direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * This function Override method rotate from ,and is adding some changes for cannon
     * @param side The direction to rotate the tile (LEFT or RIGHT).
     */
    @Override
    public void rotate(Side side) {
        super.rotate(side);
        if (side == Side.RIGHT) {
            if (direction == Direction.NORTH) {
                direction = Direction.EAST;
            } else if (direction == Direction.EAST) {
                direction = Direction.SOUTH;
            } else if (direction == Direction.SOUTH) {
                direction = Direction.WEST;
            } else if (direction == Direction.WEST) {
                direction = Direction.NORTH;
            }
        } else if (side == Side.LEFT) {
            if (direction == Direction.NORTH) {
                direction = Direction.WEST;
            } else if (direction == Direction.EAST) {
                direction = Direction.NORTH;
            } else if (direction == Direction.SOUTH) {
                direction = Direction.EAST;
            } else if (direction == Direction.WEST) {
                direction = Direction.SOUTH;
            }
        }
    }

    /**
     * This function tells you whether ship is Double Power or no
     * @return boolean
     */
    public boolean getDoublePower() {
        return doublePower;
    }

    /**
     * This function set the active status of Cannon
     * @param activeState boolean
     */
    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    /**
     * This function tells you whether Cannon is activated or not
     * @return boolean
     */
    public boolean getActiveState() {
        return activeState;
    }

    /**
     * @param visitor The visitor to accept.
     */

    /**
     * This function is used to find the Tile Type
     * @param visitor The visitor to accept.
     */
    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
