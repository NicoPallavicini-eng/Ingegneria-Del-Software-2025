package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The CargoTileVisitor class implements the TileVisitor interface
 * and is responsible for collecting CargoTile instances.
 */

public class CargoTileVisitor implements TileVisitor, Serializable {
    private ArrayList<CargoTile> list;

    /**
     * Constructs a new CargoTileVisitor instance and initializes the list of CargoTile objects.
     */

    public CargoTileVisitor() {
        list = new ArrayList<CargoTile>();
    }

    /**
     * Retrieves the list of CargoTile objects collected by this visitor.
     *
     * @return A list of CargoTile objects.
     */

    public ArrayList<CargoTile> getList() {
        return list;
    }

    /**
     * Visits a BioadaptorTile. This implemantation does nothing for this tile type.
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
     * Visits a CargoTile and adds it to the list of CargoTile objects.
     *
     * @param tile The CargoTile to visit.
     */

    @Override
    public void visit(CargoTile tile){
        list.add(tile);
    }

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
