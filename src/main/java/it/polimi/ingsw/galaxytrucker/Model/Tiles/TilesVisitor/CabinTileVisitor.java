package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;

public class CabinTileVisitor implements TileVisitor {
    private ArrayList<CabinTile> list;

    public CabinTileVisitor() {
        list = new ArrayList<CabinTile>();
    }

    public ArrayList<CabinTile> getList() {
        return list;
    }


    public void visit(BioadaptorTile tile){    }
    public void visit(EngineTile tile){}
    public void visit(CargoTile tile){}
    public void visit(BatteryTile tile){}
    public void visit(ShieldTile tile){}
    @Override
    public void visit(CabinTile tile){
        list.add(tile);
    }
    public void visit(CannonTile tile){}
}
