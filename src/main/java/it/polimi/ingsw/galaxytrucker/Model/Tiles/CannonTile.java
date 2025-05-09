package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import it.polimi.ingsw.galaxytrucker.Model.Direction;

import java.io.Serializable;

public class CannonTile extends Tile implements Serializable {
    private final boolean doublePower;
    private boolean activeState;
    private Direction direction;


    public CannonTile(ConnectorType north, ConnectorType west, ConnectorType south, ConnectorType east, boolean doublePower, boolean activeState) {
        super(north, west, south, east);
        this.doublePower = doublePower;
        this.activeState = activeState;
    }

    public Direction getDirection() {
        return direction;
    }

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

    public boolean getDoublePower() {
        return doublePower;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public boolean getActiveState() {
        return activeState;
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
