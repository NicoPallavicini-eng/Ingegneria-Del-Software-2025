package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The EngineTileVisitor class implements the TileVisitor interface
 * and is responsible for collecting EngineTile instances.
 */

public class EngineTileVisitor implements TileVisitor, Serializable {
    private ArrayList<EngineTile> list;

    /**
     * Constructs a new EngineTileVisitor instance and initializes the list of EngineTile objects.
     */

    public EngineTileVisitor() {
        list = new ArrayList<EngineTile>();
    }

    /**
     * Retrieves the list of EngineTile objects collected by this visitor.
     *
     * @return A list of EngineTile objects.
     */

    public ArrayList<EngineTile> getList() {
        return list;
    }

    /**
     * Visits an EngineTile and adds it to the list of EngineTile objects.
     *
     * @param tile The EngineTile to visit.
     */

    public void visit(BioadaptorTile tile){}

    /**
     * Visits an EngineTile and adds it to the list of EngineTile objects.
     *
     * @param tile The EngineTile to visit.
     */

    @Override
    public void visit(EngineTile tile){
        list.add(tile);
    }

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
     * Visits a CabinTile. This implementation does nothing for this tile type.
     *
     * @param tile The CabinTile to visit.
     */

    public void visit(CabinTile tile){}

    /**
     * Visits a CannonTile. This implementation does nothing for this tile type.
     *
     * @param tile The CannonTile to visit.
     */

    public void visit(CannonTile tile){}
}
