package it.polimi.ingsw.galaxytrucker.Model.Tiles;

public interface TileVisitor {
    void visitBioadptor(BioadaptorTile tile);
    void visitEnginge(EngineTile tile);
    void visitCargo(CargoTile tile);
    void visitBattery(BatteryTile tile);
    void visitShield(ShieldTile tile);
    void visitCabin(CabinTile tile);
    void visitCannon(CannonTile tile);
}
