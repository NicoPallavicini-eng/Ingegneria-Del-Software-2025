package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;

import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;

public class CargoTileVisitor implements TileVisitor {
    private ArrayList<CargoTile> list;

    public CargoTileVisitor(ArrayList<CargoTile> list) {
        this.list = list;
    }

    public ArrayList<CargoTile> getList() {
        return list;
    }

    public void visit(BioadaptorTile tile){}
    public void visit(EngineTile tile){}
    @Override
    public void visit(CargoTile tile){
        list.add(tile);
    }
    public void visit(BatteryTile tile){}
    public void visit(ShieldTile tile){}
    public void visit(CabinTile tile){}
    public void visit(CannonTile tile){}
}
