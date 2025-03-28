package it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import java.util.ArrayList;

public class EngineTileVisitor implements TileVisitor {
    private ArrayList<EngineTile> list;

    public EngineTileVisitor(ArrayList<EngineTile> list) {
        this.list = list;
    }

    public ArrayList<EngineTile> getList() {
        return list;
    }

    public void visit(BioadaptorTile tile){}
    @Override
    public void visit(EngineTile tile){
        list.add(tile);
    }
    public void visit(CargoTile tile){}
    public void visit(BatteryTile tile){}
    public void visit(ShieldTile tile){}
    public void visit(CabinTile tile){}
    public void visit(CannonTile tile){}
}
