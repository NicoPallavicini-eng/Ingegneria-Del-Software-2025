package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualCabinInhabitants;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTileEnums.VirtualConnectorType;

public class VirtualCabinTile extends VirtualTile {
    private VirtualCabinInhabitants inhabitants;
    private final boolean mainCapsule;
    private int pinkAdaptors;
    private int orangeAdaptors;

    public VirtualCabinTile(VirtualConnectorType north, VirtualConnectorType south, VirtualConnectorType east, VirtualConnectorType west, VirtualCabinInhabitants inhabitants, boolean mainCapsule, int pinkAdaptors, int orangeAdaptors) {
        super(north, west, south, east);
        this.inhabitants = inhabitants;
        this.mainCapsule = mainCapsule;
        this.pinkAdaptors = pinkAdaptors;
        this.orangeAdaptors = orangeAdaptors;
    }

    public VirtualCabinInhabitants getInhabitants() {
        return inhabitants;
    }

    public boolean isMainCapsule() {
        return mainCapsule;
    }

    public int getPurple() {
        return pinkAdaptors;
    }

    public int getOrange() {
        return orangeAdaptors;
    }
}
