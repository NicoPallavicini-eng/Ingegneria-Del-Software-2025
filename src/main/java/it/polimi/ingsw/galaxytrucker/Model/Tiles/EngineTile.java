package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import it.polimi.ingsw.galaxytrucker.Model.Direction;

import java.io.Serializable;


/**
 * EngineTile represent Engine Tile of Board game
 */
public class EngineTile extends Tile implements Serializable {
    private final boolean doublePower;
    private boolean activeState;
    private Direction direction = Direction.SOUTH;
    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;

    /**
     * Constructor of Engine Tile,some parameters are set to default values
     * @param doublePower
     * @param activeState
     * @param north
     * @param south
     * @param east
     * @param west
     */
    public EngineTile(boolean doublePower, boolean activeState, ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west) {
        super(north, south, east, west);
        this.doublePower = doublePower;
        this.activeState = activeState;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
    }

    /**
     * This function tells you whether engineTile is double or no
     * @return boolean
     */
    public boolean getDoublePower(){
        return doublePower;
    }

    /**
     * This function tells you whether engine is Active or Not
     * @return boolean
     */
    public boolean getActiveState() {
        return activeState;
    }

    /**
     * This function set Active Status of Engine
     * @param activeState
     */
    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    /**
     * This function returns a Direction of Engine
     * @return Direction
     */
    public Direction getDirection(){
        return direction;
    }

    /**This function overrides function of Tile.Some controls added to set properly values of Engine,when is rotated
     * @param side The direction to rotate the tile (LEFT or RIGHT).
     */
    @Override
    public void rotate(Side side){
        super.rotate(side);
        if (side == Side.RIGHT){
            if (direction == Direction.NORTH){
                direction = Direction.EAST;
            }
            else if (direction == Direction.EAST){
                direction = Direction.SOUTH;
            }
            else if (direction == Direction.SOUTH){
                direction = Direction.WEST;
            }
            else if (direction == Direction.WEST){
                direction = Direction.NORTH;
            }
        }
        else if (side == Side.LEFT){
            if (direction == Direction.NORTH){
                direction = Direction.WEST;
            }
            else if (direction == Direction.EAST){
                direction = Direction.NORTH;
            }
            else if (direction == Direction.SOUTH){
                direction = Direction.EAST;
            }
            else if (direction == Direction.WEST){
                direction = Direction.SOUTH;
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
