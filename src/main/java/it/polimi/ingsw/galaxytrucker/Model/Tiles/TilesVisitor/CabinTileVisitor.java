package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The CabinTileVisitor class implements the TileVisitor interface
 * and is responsible for collecting CabinTile instances.
 */

public class CabinTileVisitor implements TileVisitor, Serializable {
    private ArrayList<CabinTile> list;

    /**
     * Constructs a new CabinTileVisitor instance and initializes the list of CabinTile objects.
     */

    public CabinTileVisitor() {
        list = new ArrayList<CabinTile>();
    }

    /**
     * Retrieves the list of CabinTile objects collected by this visitor.
     *
     * @return A list of CabinTile objects.
     */

    public ArrayList<CabinTile> getList() {
        return list;
    }

    /**
     * Visits a BioadaptorTile. This implementation does nothing for this tile type.
     *
     * @param tile The BioadaptorTile to visit.
     */

    public void visit(BioadaptorTile tile){}

    /**
     * Visits an EngineTile. This implementation does nothing for this tile type.
     *
     * @param tile The EngineTile to visit.
     */

    public void visit(EngineTile tile){}

    /**
     * Visits a CargoTile. This implementation does nothing for this tile type.
     *
     * @param tile The CargoTile to visit.
     */

    public void visit(CargoTile tile){}

    /**
     * Visits a BatteryTile. This implementation does nothing for this tile type.
     *
     * @param tile The BatteryTile to visit.
     */

    public void visit(BatteryTile tile){}

    /**
     * Visits a ShieldTile. This implementation does nothing for this tile type.
     *
     * @param tile The ShieldTile to visit.
     */

    public void visit(ShieldTile tile){}

    /**
     * Visits a CabinTile and adds it to the list of CabinTile objects.
     *
     * @param tile The CabinTile to visit.
     */

    @Override
    public void visit(CabinTile tile){
        list.add(tile);
    }

    /**
     * Visits a CannonTile. This implementation does nothing for this tile type.
     *
     * @param tile The CannonTile to visit.
     */

    public void visit(CannonTile tile){}
}
