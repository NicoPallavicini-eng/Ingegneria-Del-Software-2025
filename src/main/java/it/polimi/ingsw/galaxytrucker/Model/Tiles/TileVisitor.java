package it.polimi.ingsw.galaxytrucker.Model.Tiles;

import java.io.Serializable;

public interface TileVisitor extends Serializable {
    void visit(BioadaptorTile tile);
    void visit(EngineTile tile);
    void visit(CargoTile tile);
    void visit(BatteryTile tile);
    void visit(ShieldTile tile);
    void visit(CabinTile tile);
    void visit(CannonTile tile);
}
