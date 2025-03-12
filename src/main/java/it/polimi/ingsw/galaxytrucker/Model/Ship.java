package it.polimi.ingsw.galaxytrucker.Model;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import java.util.List;
import java.util.Optional;


public class Ship {
    private Tile[][] floorplan;
    //private Graph<Tile>

    //private Color color;
    private int lostTiles;
    private List<Tile> reservedTiles;

    private int exposedConnectors;
    private int batteries;
    private int crewMembers;

    private boolean purpleAllien;
    private boolean orangeAllien;

   public Ship(){

   }

    public Tile[][] getFloorPlan() {
        return floorplan;
    }

    public boolean checkFloorPlan(){

    }

    public Optional<Tile> getTileOnFloorPlan(int row,int column){

    }
    public void setTileOnFloorPlan(int row,int column,Tile tile){

    }

    public Tile removeTileOnFloorPlan(int row,int column){

    }

    /*
    public void setGraphConnection(){}
    public void updateGraphConnection(){}
    */

    public int getNumberOfBatteries(){
        return batteries;
    }
    public void removeBatteries(int batteries){

    }

    public int getNumberOfCrewMembers(){
        return crewMembers;
    }
    public void removeCrewMembers(int crewMembers){

    }

    public void addLostTiles(int lostTiles){

    }
    public int getLostTiles(){
        return lostTiles;
    }

    public boolean getPurpleAllien(){
        return purpleAllien;
    }
    public boolean getOrangeAllien(){
        return orangeAllien;
    }

    public void setPurpleAllien(boolean allien){

    }
    public void setOrangeAllien(boolean allien){

    }
    public List<Tile> getReservedTiles() {
        return reservedTiles;
    }
    public void addReservedTile(Tile tile){

    }
    public void moveReservedToDiscard(List<Tile> reservedTiles){

    }

    public int getExposedConnectors() {
        return exposedConnectors;
    }
    public void setExposedConnectors(int exposedConnectors) {

    }
    public int getFirepower(){

    }
    public void setFirepower(Tile tile){

    }
    public List<Tile> getListOfDoubleFirepower(){

    }

    public int getEnginePower(){

    }
    public void setEnginePower(Tile tile){

    }
    public List<Tile> getListOfDoubleEnginePower(){

    }
    public List<Tile> getListOfShield(){

    }
    public List<Tile> getListOfCabin(){

    }
    public List<Tile> getListOfBattery(){

    }
    public List<Tile> getListOfCargo(){

    }
    public List<Tile> getListOfSpecialCargo(){

    }
    public List<Tile> getRowListTiles(int row){

    }
    public List<Tile> getColumnListTiles(int column){

    }
}
