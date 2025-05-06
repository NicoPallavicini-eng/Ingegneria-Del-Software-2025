package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip;

import it.polimi.ingsw.galaxytrucker.Controller.ViewObserver.Listener;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Hourglass;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualColor;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.VirtualTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VirtualShip implements Listener {

    private ArrayList<ArrayList<VirtualTile>> floorplanArrayList;
    private final VirtualColor color;
    private int lostTiles;
    private ArrayList<VirtualTile> reservedTiles;

    private int credits;
    private Integer travelDays;

    int row_max;
    int col_max;
    VirtualTile pickedTile;
    VirtualTile lastPlacedTile;

    //serve per vedere se il giocatore decide di atterare;
    private boolean playerEngaged;

    //serve per I blocchi di pianeta
    private ArrayList<Integer> cargoFromCards;

    private boolean purpleAlien;
    private boolean orangeAlien;

    //player position
    private int playerPosition;

    @Override
    public void update(Ship ship) {
        // set all variables
    }

    public void update(Player player) {}
    public void update(Hourglass hourglass) {}
    public void update(TilePile tilePile) {}
    public void update(Game game) {}

    public VirtualShip(VirtualColor color){
        this.color = color;
        lostTiles=0;
        credits=0;
        travelDays=null;
        playerEngaged=false;
        reservedTiles=new ArrayList<>();
        cargoFromCards = new ArrayList<>();
        purpleAlien=false;
        orangeAlien=false;
        playerPosition=0;
        row_max=5;
        col_max=7;

        floorplanArrayList = new ArrayList<>();
        for(int i=0;i<row_max;i++){
            floorplanArrayList.add(new ArrayList<>());
            for(int j=0;j<col_max;j++){
                floorplanArrayList.get(i).add(null);
            }
        }
    }

    public int getRowMax(){
        return row_max;
    }
    public int getColMax(){
        return col_max;
    }

    public VirtualColor getColor() {
        return color;
    }

    public ArrayList<ArrayList<VirtualTile>> getFloorplanArrayList() {
        return floorplanArrayList;
    }

    /**
     * @return a list of components that are not connected to central piece
     */
    public ArrayList<VirtualTile> getNonValidTileList(){ // could be useful (only link observer, no logic here)
        ArrayList<VirtualTile> nonValidList = new ArrayList<>();

        // observer link

        return nonValidList;
    }

    /**
     * getShipPiecesList is used to obtain all sets of ship that are connected
     * @return arraylist
     */
    public ArrayList<ArrayList<VirtualTile>> getShipPiecesList(){
        ArrayList<ArrayList<VirtualTile>> piecesList = new ArrayList<>();

        // observer link

        return piecesList;
    }

    /**
     * checkFloorplanConnection is used to obtain a list that represent a set of united components that start from tileStart
     * @param tileStart
     * @return
     */
    public ArrayList<VirtualTile> checkFloorplanConnection(VirtualTile tileStart){
        ArrayList<VirtualTile> visitedList = new ArrayList<>();

        // observer link

        return visitedList;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public ArrayList<Integer> getCargoFromCards() {
        return cargoFromCards;
    }

    public boolean isPlayerEngaged(){
        return playerEngaged;
    }

    public int getCredits() {
        return credits;
    }

    public Integer getTravelDays() {
        return travelDays;
    }

    public Optional<VirtualTile> getTileOnFloorPlan(int row, int column){

        return Optional.ofNullable(floorplanArrayList.get(row).get(column));
    }

    public int getLostTiles(){
        return lostTiles;
    }

    public boolean getPurpleAlien(){
        return purpleAlien;
    }
    public boolean getOrangeAlien(){
        return orangeAlien;
    }

    public List<VirtualTile> getReservedTiles() {
        return reservedTiles;
    }

    /**
     * @return enginePower of ship
     */
    public int getEnginePower(){
        int enginePower = 0;

        // observer link

        return enginePower;
    }

    /**
     * <div>getRowListTiles takes as the input a column number,and returns an entire row without empty cells</div>
     * @param row
     * @return list of tile
     */

    public ArrayList<VirtualTile> getRowListTiles(int row){
        ArrayList<VirtualTile> rowList = new ArrayList<>();

        // observer link (might be useful)

        return rowList;
    }

    /**
     * <div>getColumnListTiles takes as the input a column number,and returns an entire column without empty cells</div>
     * @param column
     * @return list of tile
     */
    public ArrayList<VirtualTile> getColumnListTiles(int column){
        ArrayList<VirtualTile> columnListTiles = new ArrayList<>();

        // observer link (might be useful)

        return columnListTiles;
    }

    public VirtualTile getPickedTile() {
        return pickedTile;
    }

    public VirtualTile getLastPlacedTile() {
        return lastPlacedTile;
    }
}
