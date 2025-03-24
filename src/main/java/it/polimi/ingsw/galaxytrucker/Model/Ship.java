package it.polimi.ingsw.galaxytrucker.Model;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ship {

    private ArrayList<ArrayList<Tile>> floorplanArrayList;

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

    public Ship(Color color){

        this.color = color;
        exposedConnectors=0;
        lostTiles=0;
        crewMembers=0;
        batteries=0;
        credits=0;
        travelDays=null;
        playerEngaged=false;
        reservedTiles=new ArrayList<>();
        cargoFromCards = new ArrayList<>();
        purpleAllien=false;
        orangeAllien=false;
        playerPosition=0;

        ArrayList<ArrayList<Tile>> floorplanArrayList = new ArrayList<>();
        for(int i=0;i<8;i++){
            floorplanArrayList.add(new ArrayList<>());
            for(int j=0;j<8;j++){
                floorplanArrayList.get(j).add(null);
            }
        }
    }



    public ArrayList<ArrayList<Tile>> getFloorplanArrayList() {
        return floorplanArrayList;
    }

    public boolean checkFloorplan(){
       return true;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }
    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

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

        return Optional.ofNullable(floorplanArrayList.get(row).get(column));
    }

    public void setTileOnFloorPlan(int row,int column,Tile tile){
        //floorplan[row][column] = tile;
        floorplanArrayList.get(row).set(column,tile);
    }

    public Tile removeTileOnFloorPlan(int row,int column){

         Tile tile = floorplanArrayList.get(row).get(column);
         floorplanArrayList.get(row).set(column,null);
         return tile;
    }


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
    public double getFirepower(){
       ArrayList<CannonTile> cannonTiles = getListOfFirepower();
        double firepower = 0;
        int multiplicator = 1;
       for(CannonTile cannonTile : cannonTiles){
           if(cannonTile.getDoublePower()){
               multiplicator = 2;
           }
           if(cannonTile.getConnectors().get(0)==ConnectorType.CANNON){
               firepower+=multiplicator*1;
           }else{
               firepower+=multiplicator*0.5;
           }
           multiplicator = 1;
        }
        return firepower;
    }

    public void setFirepower(CannonTile tile,boolean active){
        if(tile!=null){
            if(tile.getDoublePower()){
                tile.setActiveState(active);
            }
        }
    }

    public ArrayList<CannonTile> getListOfFirepower(){
        CannonTileVisitor cannonTileVisitor = new CannonTileVisitor();
        ArrayList<CannonTile> listOfSingleFirepower = new ArrayList<>();

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null){
                    cannonTileVisitor.visit(tile);
                }
            }
        }

        listOfSingleFirepower = cannonTileVisitor.getList();
        return listOfSingleFirepower;
    }

    public ArrayList<CannonTile> getListOfDoubleFirepower(){
        ArrayList<CannonTile> doubleFirepowerList = new ArrayList<>();
        ArrayList<CannonTile> listOfFirepower = getListOfFirepower();

        for(CannonTile tile : listOfFirepower){
            if(tile.getDoublePower()){
                doubleFirepowerList.add(tile);
            }
        }

        return doubleFirepowerList;
    }

    public int getEnginePower(){
        ArrayList<EngineTile> listEngine = getListOfEngine();
        int enginePower = 0;
        int multiplicator = 1;
        for(EngineTile engineTile : listEngine){
            if(engineTile.getActiveState()){
                if(engineTile.GetDoublePower()){
                    multiplicator = 2;
                }
                enginePower+=multiplicator*1;
            }
            multiplicator=1;
        }
        return enginePower;
    }

    public void setEnginePower(EngineTile tile,boolean active){
        if(tile!=null){
            if(tile.GetDoublePower()){
                tile.setActiveState(active);
            }
        }

    }

    public ArrayList<EngineTile> getListOfEngine(){
        ArrayList<EngineTile> listOfEngine = new ArrayList<>();
        EngineTileVisitor engineTileVisitor = new EngineTileVisitor();

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null){
                    engineTileVisitor.visit(tile);
                }
            }
        }
        listOfEngine = engineTileVisitor.getList();
        return listOfEngine;
    }
    public ArrayList<EngineTile> getListOfDoubleEnginePower(){
        ArrayList<EngineTile> doubleEngineList = getListOfEngine();
        ArrayList<EngineTile> engineList = new ArrayList<>();

        for(EngineTile engineTile : engineList){
            if(engineTile.getDoublePower()){
                doubleEngineList.add(engineTile);
            }
        }

        return doubleEngineList;
    }

    public ArrayList<ShieldTile> getListOfShield() {
        ArrayList<ShieldTile> shieldList = new ArrayList<>();
        ShieldTileVisitor shieldTileVisitor = new ShieldTileVisitor();

        for (ArrayList<Tile> list : floorplanArrayList) {
            for (Tile tile : list) {
                if (tile != null) {
                    shieldTileVisitor.visit(tile);
                }
            }
        }
        shieldList = shieldTileVisitor.getList();
        return shieldList;
    }

    public ArrayList<ShieldOrientation> getListOfShieldOrientation(){
        ArrayList<ShieldOrientation> shieldOrientationList = new ArrayList<>();
        ArrayList<ShieldTile> shieldList = new ArrayList<>();
        shieldList = getListOfShield();
        for(Tile tile : shieldList){
            //ShieldTile shieldTile = (ShieldTile) tile;
            shieldOrientationList.add(shieldTile.getShieldOrientation());
        }
        return shieldOrientationList;
    }

    public ArrayList<Tile> getListOfCabin(){
        ArrayList<Tile> cabinList = new ArrayList<>();
        CabinTileVisitor cabinTileVisitor = new CabinTileVisitor();

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null){
                    cabinTileVisitor.visit(tile);
                }
            }
        }
        cabinList = cabinTileVisitor.getList();
        return cabinList;
    }

     */
    public ArrayList<Tile> getListOfBattery(){
        ArrayList<Tile> batteriesList = new ArrayList<>();
        BatteryTileVisitor batteryTileVisitor = new BatteryTileVisitor();

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null){
                    batteryTileVisitor.visit(tile);
                }
            }
        }
        batteriesList = batteryTileVisitor.getList();
        return batteriesList;
    }


    /*example of Tile Visitor*/
    public ArrayList<Tile> getListOfCargo(){
        CabinTileVisitor cabinTileVisitor = new CabinTileVisitor();
        ArrayList<Tile> cargoList = new ArrayList<>();


        for(ArrayList<Tile> tilelist : floorplanArrayList){
            for(Tile tile : tilelist){
                if(tile!=null){
                    cabinTileVisitor.visit(tile);
                }
            }
        }
        cargoList = cabinTileVisitor.getList();
        return cargoList;
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
