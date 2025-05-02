package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles;

public interface TileVisitor {
    void visit(BioadaptorTile tile);
    void visit(EngineTile tile);
    void visit(CargoTile tile);
    void visit(BatteryTile tile);
    void visit(ShieldTile tile);
    void visit(CabinTile tile);
    void visit(CannonTile tile);
}
