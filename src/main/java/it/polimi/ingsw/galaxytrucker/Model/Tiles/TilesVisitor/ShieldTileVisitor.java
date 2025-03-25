package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.TileVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;

public class ShieldTileVisitor implements TileVisitor {
    private ArrayList<ShieldTile> list;

    public ShieldTileVisitor(ArrayList<ShieldTile> list) {
        this.list = list;
    }

    public ArrayList<ShieldTile> getList() {
        return list;
    }

    public void visit(BioadaptorTile tile){}
    public void visit(EngineTile tile){}
    public void visit(CargoTile tile){}
    public void visit(BatteryTile tile){}
    @Override
    public void visit(ShieldTile tile){
        list.add(tile);
    }
    public void visit(CabinTile tile){}
    public void visit(CannonTile tile){}
}
