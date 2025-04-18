package it.polimi.ingsw.galaxytrucker.Model.Tiles;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.TileType.CAPSULE;

public class CabinTile extends Tile {
    private CabinInhabitants inhabitants;
    private final boolean mainCapsule;
    private int pinkAdaptors;
    private int orangeAdaptors;

    public CabinTile(ConnectorType north, ConnectorType south, ConnectorType east, ConnectorType west, CabinInhabitants inhabitants, boolean mainCapsule, int pinkAdaptors, int orangeAdaptors) {
        super(north, west, south, east);
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

    public boolean isMainCapsule() {
        return this.mainCapsule;
    }

    public void setPurple(int purple){
        pinkAdaptors = purple;
    }

    public void setOrange(int orange){
        orangeAdaptors = orange;
    }

    public int getPurple() {
        return pinkAdaptors;
    }

    public int getOrange() {
        return orangeAdaptors;
    }

    @Override
    public void accept(TileVisitor visitor) {
        visitor.visit(this);
    }
}
