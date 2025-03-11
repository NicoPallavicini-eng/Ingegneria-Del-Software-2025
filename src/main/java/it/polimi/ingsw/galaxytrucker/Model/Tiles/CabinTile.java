package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.TileType.CAPSULE;

public class CabinTile extends Tile {
    private CabinInhabitants inhabitants;
    private final boolean mainCapsule;
    private int pinkAdaptors;
    private int orangeAdaptors;

    public CabinTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, CabinInhabitants inhabitants, boolean mainCapsule, int pinkAdaptors, int orangeAdaptors) {
        super(north, west, south, east, CAPSULE);
        this.inhabitants = inhabitants;
        this.mainCapsule = mainCapsule;
        this.pinkAdaptors = pinkAdaptors;
        this.orangeAdaptors = orangeAdaptors;
    }

    public void updateInhabitants(CabinInhabitants inhabitants){
        this.inhabitants = inhabitants;
    }

    public CabinInhabitants getInhabitants() {
        return inhabitants;
    }

    public void removePinkAdaptors() {
        pinkAdaptors -= 1;
    }

    public void removeOrangeAdaptors() {
        orangeAdaptors -= 1;
    }
}
