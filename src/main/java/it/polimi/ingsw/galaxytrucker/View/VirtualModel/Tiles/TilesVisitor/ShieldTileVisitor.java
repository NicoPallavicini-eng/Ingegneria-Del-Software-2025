package it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Tiles.*;

import java.util.ArrayList;

/**
 * The ShieldTileVisitor class implements the TileVisitor interface
 * and is responsible for collecting ShieldTile instances.
 */

public class ShieldTileVisitor implements TileVisitor {
    private ArrayList<ShieldTile> list;

    /**
     * Constructs a new ShieldTileVisitor instance and initializes the list of ShieldTile objects.
     */

    public ShieldTileVisitor() {
        list = new ArrayList<ShieldTile>();
    }

    /**
     * Retrieves the list of ShieldTile objects collected by this visitor.
     *
     * @return A list of ShieldTile objects.
     */

    public ArrayList<ShieldTile> getList() {
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
     * Visits a ShieldTile and adds it to the list of ShieldTile objects.
     *
     * @param tile The ShieldTile to visit.
     */

    @Override
    public void visit(ShieldTile tile){
        list.add(tile);
    }

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
