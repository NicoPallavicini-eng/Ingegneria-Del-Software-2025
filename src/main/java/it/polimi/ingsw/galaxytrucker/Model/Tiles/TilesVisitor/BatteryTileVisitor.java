package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;

/**
 * The BatteryTileVisitor class implements the TileVisitor interface
 * and is responsible for collecting BatteryTile instances.
 */

public class BatteryTileVisitor implements TileVisitor {
    private ArrayList<BatteryTile> list;

    /**
     * Constructs a new BatteryTileVisitor instance and initializes the list of BatteryTile objects.
     */

    public BatteryTileVisitor() {
        list = new ArrayList<BatteryTile>();
    }

    /**
     * Retrieves the list of BatteryTile objects collected by this visitor.
     *
     * @return A list of BatteryTile objects.
     */

    public ArrayList<BatteryTile> getList() {
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
     * Visits an CargoTile. This implementation does nothing for this tile type.
     *
     * @param tile The CargoTile to visit.
     */

    public void visit(CargoTile tile){}

    /**
     * Visit a BatteryTile and adds it to the list of batteryTile objects.
     *
     * @param tile The BatteryTile to visit.
     */

    @Override
    public void visit(BatteryTile tile){
        list.add(tile);
    }

    /**
     * Visits an ShieldTile. This implementation does nothing for this tile type.
     *
     * @param tile The ShieldTile to visit.
     */

    public void visit(ShieldTile tile){}

    /**
     * Visits an CabinTile. This implementation does nothing for this tile type.
     *
     * @param tile The CabinTile to visit.
     */

    public void visit(CabinTile tile){}

    /**
     * Visits an CannonTile. This implementation does nothing for this tile type.
     *
     * @param tile The CannonTile to visit.
     */

    public void visit(CannonTile tile){}
}
