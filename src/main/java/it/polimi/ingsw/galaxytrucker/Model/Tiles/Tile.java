package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the basic tiel of the game with connectors, orientation and state.
 */

public class Tile{
    private ConnectorType northConnector;
    private ConnectorType westConnector;
    private ConnectorType southConnector;
    private ConnectorType eastConnector;

    private boolean attached;
    private boolean upsideDown;

    private boolean choosable;

    /**
     * Constructs a Tile with specified connector types for each side
     *
     * @param northConnector The connector type on the north side
     * @param westConnector The connector type on the west side
     * @param southConnector The connector type on the south side
     * @param eastConnector The connector type on the east side
     */

    public Tile(ConnectorType northConnector, ConnectorType westConnector, ConnectorType southConnector, ConnectorType eastConnector) {
        this.northConnector = northConnector;
        this.westConnector = westConnector;
        this.southConnector = southConnector;
        this.eastConnector = eastConnector;
        this.upsideDown = false;
        this.choosable = true;
    }

    /**
     * Checks if the tile is upside down
     *
     * @return true if the tile is upside down, false otherwise
     */

    public boolean isUpsideDown() {
        return upsideDown;
    }

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
        upsideDown = false;
    }

    /**
     * Retrieves the upside-down state of the tile.
     *
     * @return true if the tile is upside down, false otherwise.
     */

    public boolean getUpsideDown(){
        return upsideDown;
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
}
