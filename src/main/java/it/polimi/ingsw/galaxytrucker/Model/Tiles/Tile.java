package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import java.util.ArrayList;
import java.util.List;

public class Tile {

    private ConnectorType northConnector;
    private ConnectorType westConnector;
    private ConnectorType southConnector;
    private ConnectorType eastConnector;

    private final TileType type;
    private boolean attached;
    private boolean upsideDown;

    public Tile(ConnectorType northConnector, ConnectorType westConnector, ConnectorType southConnector, ConnectorType eastConnector, TileType type) {
        this.northConnector = northConnector;
        this.westConnector = westConnector;
        this.southConnector = southConnector;
        this.eastConnector = eastConnector;
        this.type = type;
    }

    public boolean isUpsideDown() {
        return upsideDown;
    }

    public TileType getType() {
        return type;
    }

    public List<ConnectorType> getAdiacentTiles(){
        //TODO add logic for the adiacent tiles
        return null;
    }

    public void rotateRight(){
        //TODO need to flip the connectors, do i need a temp variable?
    }

    public void rotateLeft(){

    }

    public List<ConnectorType> getConnectors(){
        //NOT TO SURE ABOUT THIS...
        List <ConnectorType> connectors = new ArrayList<ConnectorType>();
        connectors.add(northConnector);
        connectors.add(westConnector);
        connectors.add(southConnector);
        connectors.add(eastConnector);
        return connectors;
    }

    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public void flip(){
        upsideDown = !upsideDown;
    }

    public boolean getUpsideDown(){
        return upsideDown;
    }
}
