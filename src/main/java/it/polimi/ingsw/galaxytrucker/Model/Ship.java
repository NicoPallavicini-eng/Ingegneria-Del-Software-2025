package it.polimi.ingsw.galaxytrucker.Model;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

//ritornare ArrayLists?

public class Ship {
    //private Tile[][] floorplan;

    private ArrayList<ArrayList<Tile>> floorplanArrayList;

    //private Graph<Tile>
    private ArrayList<ArrayList<Boolean>> adjacencyMatrix;
    private int vertici;

    private Color color;
    private int lostTiles;
    private ArrayList<Tile> reservedTiles;

    private int exposedConnectors;
    private int batteries;
    private int crewMembers;
    private int credits;
    private Integer travelDays;

    //serve per vedere se il giocatore decide di atterare;
    private boolean playerEngaged;
    //serve per I blocchi di pianeta
    private ArrayList<Integer> cargoFromCards;

    private boolean purpleAllien;
    private boolean orangeAllien;

    //player position
    private int playerPosition;
   public Ship(){

   }

   public void addEdge(){

   }
    /*
    public Tile[][] getFloorPlan() {
        return floorplan;
    }
    */
    public ArrayList<ArrayList<Tile>> getFloorplanArrayList() {
        return floorplanArrayList;
    }
    /*
    public boolean checkFloorPlan(){
        for(ArrayList<Tile> list : floorplanArrayList) {
            if(list.equals(floorplanArrayList.getFirst())) {
                for(Tile tile : list) {
                    if(tile==list.get(1)&&tile!=null) {

                    }else if(tile == list.get(list.size()-1)&&tile!=null) {

                    }else{
                        if(tile!=null) {
                            tile.
                        }
                    }
                }
            } else if (list.equals(floorplanArrayList.get(floorplanArrayList.size()-1))) {
                for(Tile tile : list) {
                    if(tile==list.get(1)&&tile!=null) {

                    }else if(tile == list.get(list.size()-1)&&tile!=null){

                    }else{

                    }
                }
            }else{
                for(Tile tile : list) {
                    if(tile==list.get(1)&&tile!=null) {

                    }else if(tile == list.get(list.size()-1)&&tile!=null){

                    }else{

                    }
                }

            }
        }
    }
    */
    /*
    public boolean checkFloorPlanConnection() {
        for (ArrayList<Tile> list : floorplanArrayList) {
            for (Tile tile : list) {
                if(tile!=null){
                    List<Tile> adiacentTile = tile.getAdiacentTiles();
                    List<ConnectorType> connectors = tile.getConnectors();
                    if(adiacentTile.get(0)!=null){
                        //north
                        ConnectorType connector = connectors.get(0);
                        ConnectorType connectorAdiacent = adiacentTile.get(0).getConnectors().get(2);
                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON))||connector.equals(ConnectorType.ENGINE){
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                    if(adiacentTile.get(1)!=null){
                        //west
                        ConnectorType connector = connectors.get(1);
                        ConnectorType connectorAdiacent = adiacentTile.get(1).getConnectors().get(3);
                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON))||connector.equals(ConnectorType.ENGINE){
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                    if(adiacentTile.get(2)!=null){
                        //south
                        ConnectorType connector = connectors.get(2);
                        ConnectorType connectorAdiacent = adiacentTile.get(2).getConnectors().get(0);
                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON))||connector.equals(ConnectorType.ENGINE){
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                    if(adiacentTile.get(3)!=null){
                        //east
                        ConnectorType connector = connectors.get(3);
                        ConnectorType connectorAdiacent = adiacentTile.get(3).getConnectors().get(1);
                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON))||connector.equals(ConnectorType.ENGINE){
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }

                }
            }
        }
        for(ArrayList<Tile> list : floorplanArrayList) {
            for (Tile tile : list) {
                if(tile.getType()==TileType.ENGINE){
                    if(tile.getConnectors().get(2)!=ConnectorType.ENGINE){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    */
    public ArrayList<Integer> getCargoFromCards() {
        return cargoFromCards;
    }
    public void addBlocks(List<Integer> cargoFromCards) {
        cargoFromCards.forEach(c -> this.cargoFromCards.add(c));
    }
    public boolean isPlayerEngaged(){
        return playerEngaged;
    }
    public void setPlayerEngaged(boolean playerEngaged){
        this.playerEngaged = playerEngaged;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public int getCredits() {
        return credits;
    }

    public Integer getTravelDays() {
        return travelDays;
    }
    public void setTravelDays(Integer travelDays) {
        this.travelDays = travelDays;
    }

    public Optional<Tile> getTileOnFloorPlan(int row,int column){
        //return Optional.ofNullable(floorplan[row][column]);

        return Optional.ofNullable(floorplanArrayList.get(row).get(column));
    }

    public void setTileOnFloorPlan(int row,int column,Tile tile){
        //floorplan[row][column] = tile;
        floorplanArrayList.get(row).set(column,tile);
    }

    public Tile removeTileOnFloorPlan(int row,int column){
        /*
        Tile tile = floorplan[row][column];
        floorplan[row][column] = null;
        return tile;
        */
         Tile tile = floorplanArrayList.get(row).get(column);
         floorplanArrayList.get(row).set(column,null);
         return tile;
    }

    /*
    public void setGraphConnection(){}
    public void updateGraphConnection(){}
    */

    public int getNumberOfBatteries(){
        return batteries;
    }
    public void removeBatteries(int batteries){
        this.batteries -= batteries;
    }

    public int getNumberOfCrewMembers(){
        return crewMembers;
    }
    public void removeCrewMembers(int crewMembers){
        this.crewMembers -= crewMembers;
    }

    public void addLostTiles(int lostTiles){
       this.lostTiles += lostTiles;
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
        purpleAllien = allien;
    }
    public void setOrangeAllien(boolean allien){
        orangeAllien = allien;
    }
    public List<Tile> getReservedTiles() {
        return reservedTiles;
    }
    public void addReservedTile(Tile tile){
        reservedTiles.add(tile);
    }

    public void moveReservedToDiscard(){
        for(Tile tile : this.reservedTiles){
            this.reservedTiles.remove(tile);
            lostTiles++;
        }
    }

    public int getExposedConnectors() {
        return exposedConnectors;
    }
    public void setExposedConnectors(int exposedConnectors) {
        this.exposedConnectors = exposedConnectors;
    }


    //direzione dei cannoni è importante!
    public int getFirepower(){
        int firepower = 0;
        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null&&tile.getType()==TileType.CANNON){
                    CannonTile cannonTile = (CannonTile) tile;
                    if(!cannonTile.getDoublePower()){
                        firepower++;
                    }else{
                        if(cannonTile.getActiveState()){
                            firepower+=2;
                        }
                    }
                }
            }
        }
        return firepower;
    }
    public void setFirepower(Tile tile){
        if(tile!=null&&tile.getType()==TileType.CANNON){
            CannonTile cannonTile = (CannonTile) tile;
            if(cannonTile.getDoublePower()){
                cannonTile.setActiveState(true);
            }
        }
    }

    public ArrayList<Tile> getListOfSingleFirepower(){
        ArrayList<Tile> listOfSingleFirepower = new ArrayList<>();
        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null&&tile.getType()==TileType.CANNON){
                    CannonTile cannonTile = (CannonTile) tile;
                    if(!cannonTile.getDoublePower()){
                        listOfSingleFirepower.add(tile);
                    }
                }
            }
        }
        return listOfSingleFirepower;
    }

    public ArrayList<Tile> getListOfDoubleFirepower(){
        ArrayList<Tile> doubleFirepowerList = new ArrayList<>();
        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null&&tile.getType()==TileType.CANNON){
                    CannonTile cannonTile = (CannonTile) tile;
                    if(cannonTile.getDoublePower()){
                        doubleFirepowerList.add(tile);
                    }
                }
            }
        }
        return doubleFirepowerList;
    }

    public int getEnginePower(){
        int enginePower = 0;
        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null&&tile.getType()==TileType.CANNON){
                    CannonTile cannonTile = (CannonTile) tile;
                    if(!cannonTile.getDoublePower()){
                        enginePower++;
                    }else{
                        if(cannonTile.getActiveState()){
                            enginePower+=2;
                        }
                    }
                }
            }
        }
        return enginePower;
    }
    public void setEnginePower(Tile tile){
        if(tile!=null&&tile.getType()==TileType.ENGINE){
            EngineTile engineTile = (EngineTile) tile;
            if(engineTile.GetDoublePower()){
                engineTile.setActiveState(true);
            }
        }

    }
    public ArrayList<Tile> getListOfDoubleEnginePower(){
        ArrayList<Tile> doubleEngineList = new ArrayList<>();
        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null&&tile.getType()==TileType.ENGINE){
                    EngineTile engineTile = (EngineTile) tile;
                    if(engineTile.GetDoublePower()){
                        doubleEngineList.add(tile);
                    }
                }
            }
        }
        return doubleEngineList;
    }
    public ArrayList<Tile> getListOfShield() {
        ArrayList<Tile> shieldList = new ArrayList<>();
        for (ArrayList<Tile> list : floorplanArrayList) {
            for (Tile tile : list) {
                if (tile != null && tile.getType() == TileType.SHIELD) {
                    shieldList.add(tile);
                }
            }
        }
        return shieldList;
    }

    public ArrayList<ShieldOrientation> getListOfShieldOrientation(){
        ArrayList<ShieldOrientation> shieldOrientationList = new ArrayList<>();
        ArrayList<Tile> shieldList = new ArrayList<>();
        shieldList = getListOfShield();
        for(Tile tile : shieldList){
            ShieldTile shieldTile = (ShieldTile) tile;
            shieldOrientationList.add(shieldTile.getShieldOrientation());
        }
        return shieldOrientationList;
    }
    public ArrayList<Tile> getListOfCabin(){
        ArrayList<Tile> cabinList = new ArrayList<>();
        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null&&tile.getType()==TileType.CAPSULE){
                    cabinList.add(tile);
                }
            }
        }
        return cabinList;
    }
    public ArrayList<Tile> getListOfBattery(){
        ArrayList<Tile> batteriesList = new ArrayList<>();
        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null&&tile.getType()==TileType.BATTERY){
                    batteriesList.add(tile);
                }
            }
        }
        return batteriesList;
    }
    public ArrayList<Tile> getListOfCargo(){
        ArrayList<Tile> cargoList = new ArrayList<>();
        for(ArrayList<Tile> tilelist : floorplanArrayList){
            for(Tile tile : tilelist){
                if(tile!=null&&tile.getType()==TileType.STORAGE){
                    cargoList.add(tile);
                }
            }
        }
        return cargoList;
    }
    public ArrayList<Tile> getListOfSpecialCargo(){
        ArrayList<Tile> specialCargo = new ArrayList<>();
        for(ArrayList<Tile> tiles : floorplanArrayList){
            for(Tile tile : tiles){
                if(tile!=null&&tile.getType()== TileType.STORAGE ){
                    CargoTile cargoTile = (CargoTile) tile;
                    //funzione non definita fitsRed()
                    if(cargoTile.fitsRed()==1){
                        specialCargo.add(tile);
                    }
                }
            }
        }
        return specialCargo;
    }
    //chiedere se restituire la lista intera?
    public ArrayList<Tile> getRowListTiles(int row){
        ArrayList<Tile> rowList = new ArrayList<>();
        for(Tile tile: floorplanArrayList.get(row)){
            if(tile!=null){
                rowList.add(tile);
            }
        }
        return rowList;
    }
    public ArrayList<Tile> getColumnListTiles(int column){
        ArrayList<Tile> columnListTiles = new ArrayList<>();
        for(ArrayList<Tile> rowList : floorplanArrayList){
            if(rowList.get(column)!=null) {
                columnListTiles.add(rowList.get(column));
            }
        }
        return columnListTiles;
    }
}
