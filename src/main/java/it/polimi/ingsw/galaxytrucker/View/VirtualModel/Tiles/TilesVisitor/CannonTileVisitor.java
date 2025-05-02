package it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles.*;

import java.util.ArrayList;

/**
 * The CannonTileVisitor class implements the TileVisitor interface
 * and is responsible for collecting CannonTile instances.
 */

public class CannonTileVisitor implements TileVisitor {
    private ArrayList<CannonTile> list;

    /**
     * Constructs a new CannonTileVisitor instance and initializes the list of CannonTile objects.
     */

    public CannonTileVisitor() {
        list = new ArrayList<CannonTile>();
    }

    /**
     * Retrieves the list of CannonTile objects collected by this visitor.
     *
     * @return A list of CannonTile objects.
     */

    public ArrayList<CannonTile> getList() {
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
     * Visits a CabinTile. This implementation does nothing for this tile type.
     *
     * @param tile The CabinTile to visit.
     */

    public void visit(CabinTile tile){}

    /**
     * Visits a CannonTile and adds it to the list of CannonTile objects.
     *
     * @param tile The CannonTile to visit.
     */

    @Override
    public void visit(CannonTile tile){
        list.add(tile);
    }
}
