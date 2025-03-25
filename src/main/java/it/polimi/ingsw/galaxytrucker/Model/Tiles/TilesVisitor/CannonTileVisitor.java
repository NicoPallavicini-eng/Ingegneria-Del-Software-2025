package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;

public class CannonTileVisitor implements TileVisitor {
    private ArrayList<CannonTile> list;

    public CannonTileVisitor(ArrayList<CannonTile> list) {
        this.list = list;
    }

    public ArrayList<CannonTile> getList() {
        return list;
    }

    public void visit(BioadaptorTile tile){}
    public void visit(EngineTile tile){}
    public void visit(CargoTile tile){}
    public void visit(BatteryTile tile){}
    public void visit(ShieldTile tile){}
    public void visit(CabinTile tile){}
    public void visit(CannonTile tile){
        list.add(tile);
    }
}
