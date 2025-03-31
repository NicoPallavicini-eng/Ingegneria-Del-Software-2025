package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;

public class BatteryTileVisitor implements TileVisitor {
    private ArrayList<BatteryTile> list;

    public BatteryTileVisitor() {
        list = new ArrayList<BatteryTile>();
    }
    public ArrayList<BatteryTile> getList() {
        return list;
    }

    public void visit(BioadaptorTile tile){}
    public void visit(EngineTile tile){}
    public void visit(CargoTile tile){}
    @Override
    public void visit(BatteryTile tile){
        list.add(tile);
    }
    public void visit(ShieldTile tile){}
    public void visit(CabinTile tile){}
    public void visit(CannonTile tile){}
}
