package it.polimi.ingsw.galaxytrucker.Model.Tiles;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the basic tile of the game with connectors, orientation and state.
 */


public class Tile implements Serializable {
    private ConnectorType northConnector;
    private ConnectorType westConnector;
    private ConnectorType southConnector;
    private ConnectorType eastConnector;

    private boolean attached;
    private boolean facingUp;
    private boolean choosable;
    private boolean reserved;
    private String name;
    private int rotation = 0;

    /**
     * Constructs a Tile with specified connector types for each side
     *
     * @param north The connector type on the north side
     * @param west The connector type on the west side
     * @param south The connector type on the south side
     * @param east The connector type on the east side
     */

    public Tile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west) {
        this.northConnector = north;
        this.westConnector = west;
        this.southConnector = south;
        this.eastConnector = east;
        this.facingUp = false;
        this.choosable = true;
        this.attached = false;
        this.reserved = false;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    /**
     * Rotates the tile in the specified direction.
     *
     * @param side The direction to rotate the tile (LEFT or RIGHT).
     */

    public void rotate(Side side){
        ConnectorType tempRotate;
        if (side == Side.RIGHT) {
            tempRotate = northConnector;
            northConnector = westConnector;
            westConnector = southConnector;
            southConnector = eastConnector;
            eastConnector = tempRotate;
        }
        else if (side == Side.LEFT) {
            tempRotate = northConnector;
            northConnector = eastConnector;
            eastConnector = southConnector;
            southConnector = westConnector;
            westConnector = tempRotate;
        }
    }

    /**
     * Retrieves the list of connector types for all sides of the tile.
     *
     * @return A list of connector types.
     */

    public List<ConnectorType> getConnectors(){
        List <ConnectorType> connectors = new ArrayList<ConnectorType>();
        connectors.add(northConnector);
        connectors.add(westConnector);
        connectors.add(southConnector);
        connectors.add(eastConnector);
        return connectors;
    }

    /**
     * Checks if the tile is attached.
     *
     * @return true if the tile is attached, false otherwise.
     */

    public boolean isAttached() {
        return attached;
    }

    /**
     * Sets the attached state of the tile.
     *
     * @param attached true to set the tile as attached, false otherwise.
     */

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    /**
     * Flips the tile to its default orientation (not upside down).
     */

    public void flip(){
        facingUp = true;
    }

    /**
     * Retrieves the upside-down state of the tile.
     *
     * @return true if the tile is upside down, false otherwise.
     */

    public boolean getFacingUp(){
        return facingUp;
    }

    public void setFacingUp(boolean facingUp) {
        this.facingUp = facingUp;
    }

    /**
     * Stes wheter the tile is choosable
     *
     * @param choosable true to make the tile choosable, false otherwise.
     */

    public void setChoosable(boolean choosable){
        this.choosable = choosable;
    }

    /**
     * Checks if the tile is choosable.
     *
     * @return true if the tile is choosable, false otherwise.
     */

    public boolean isChoosable(){
        return choosable;
    }

    /**
     * Accept a visitor to perform operations on the tile.
     *
     * @param visitor The visitor to accept.
     */

    public void accept(TileVisitor visitor){}

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public void setRotation(int rotation){
        this.rotation = (this.rotation + rotation) % 360;
    }

    public int getRotation(){
        return this.rotation;
    }
}
