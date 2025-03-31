package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;

public class BioadaptorTileVisitor implements TileVisitor {
    private ArrayList<BioadaptorTile> list;

    public BioadaptorTileVisitor(){
        list = new ArrayList<BioadaptorTile>();
    }

    public ArrayList<BioadaptorTile> getList() {
        return list;
    }

    @Override
    public void visit(BioadaptorTile tile){
        list.add(tile);
    }
    public void visit(EngineTile tile){}
    public void visit(CargoTile tile){}
    public void visit(BatteryTile tile){}
    public void visit(ShieldTile tile){}
    public void visit(CabinTile tile){}
    public void visit(CannonTile tile){}
}
