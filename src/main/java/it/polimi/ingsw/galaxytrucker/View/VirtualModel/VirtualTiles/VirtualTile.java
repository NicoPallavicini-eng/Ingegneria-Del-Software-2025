package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualConnectorType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the basic tiel of the game with connectors, orientation and state.
 */

public class VirtualTile {
    private VirtualConnectorType northConnector;
    private VirtualConnectorType westConnector;
    private VirtualConnectorType southConnector;
    private VirtualConnectorType eastConnector;

    private boolean attached;
    private boolean upsideDown;
    private boolean choosable;

    /**
     * Constructs a VirtualTile with specified connector types for each side
     *
     * @param northConnector The connector type on the north side
     * @param westConnector The connector type on the west side
     * @param southConnector The connector type on the south side
     * @param eastConnector The connector type on the east side
     */

    public VirtualTile(VirtualConnectorType northConnector, VirtualConnectorType westConnector, VirtualConnectorType southConnector, VirtualConnectorType eastConnector) {
        this.northConnector = northConnector;
        this.westConnector = westConnector;
        this.southConnector = southConnector;
        this.eastConnector = eastConnector;
        this.upsideDown = false;
        this.choosable = true;
        this.attached = false;
    }

    /**
     * Retrieves the list of connector types for all sides of the tile.
     *
     * @return A list of connector types.
     */

    public List<VirtualConnectorType> getConnectors(){
        List <VirtualConnectorType> connectors = new ArrayList<VirtualConnectorType>();
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
     * Retrieves the upside-down state of the tile.
     *
     * @return true if the tile is upside down, false otherwise.
     */

    public boolean getUpsideDown(){
        return upsideDown;
    }

    /**
     * Checks if the tile is choosable.
     *
     * @return true if the tile is choosable, false otherwise.
     */

    public boolean isChoosable(){
        return choosable;
    }
}
