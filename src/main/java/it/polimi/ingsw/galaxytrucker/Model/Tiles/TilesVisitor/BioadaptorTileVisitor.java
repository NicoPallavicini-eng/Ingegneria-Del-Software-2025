package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The BioadaptorTileVisitor class implements the TileVisitor interface
 * and is responsible for collecting BioadaptorTile instances.
 */

public class BioadaptorTileVisitor implements TileVisitor, Serializable {
    private ArrayList<BioadaptorTile> list;

    /**
     * Constructs a new BioadaptorTileVisitor instance and initializes the list of BioadaptorTile objects.
     */

    public BioadaptorTileVisitor(){
        list = new ArrayList<BioadaptorTile>();
    }

    /**
     * Retrieves the list of BioadaptorTile objects collected by this visitor.
     *
     * @return A list of BioadaptorTile objects.
     */

    public ArrayList<BioadaptorTile> getList() {
        return list;
    }

    /**
     * Visits a BioadaptorTile and adds it to the list of BioadaptorTile objects.
     *
     * @param tile The BioadaptorTile to visit.
     */

    @Override
    public void visit(BioadaptorTile tile){
        list.add(tile);
    }

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
