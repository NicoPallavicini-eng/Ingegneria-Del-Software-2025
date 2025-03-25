package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import java.util.ArrayList;
import java.util.List;

public class Tile{
    private ConnectorType northConnector;
    private ConnectorType westConnector;
    private ConnectorType southConnector;
    private ConnectorType eastConnector;

    private final TileType type;
    private boolean attached;
    private boolean upsideDown;

    private boolean choosable;

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

    public List<Tile> getAdjacentTiles(){
        //Move to ship class
        return null;
    }

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


    public List<ConnectorType> getConnectors(){
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
        upsideDown = true;
    }

    public boolean getUpsideDown(){
        return upsideDown;
    }

    public void setChoosable(boolean choosable){
        this.choosable = choosable;
    }

    public boolean isChoosable(){
        return choosable;
    }

    public void accept(TileVisitor visitor){}
}
