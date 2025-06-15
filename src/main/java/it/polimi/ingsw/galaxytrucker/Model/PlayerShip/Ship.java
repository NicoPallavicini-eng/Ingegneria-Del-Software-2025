package it.polimi.ingsw.galaxytrucker.Model.PlayerShip;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class Ship implements Serializable {

    private ArrayList<ArrayList<Tile>> floorplanArrayList;
    private final Color color;
    private int lostTiles;
    private ArrayList<Tile> reservedTiles;
    private Tile tileInHand;
    private Tile lastPlacedTile;
    private ArrayList<Integer> cargoFromCards;

    private int credits;
    private Integer travelDays;

    int row_max;
    int col_max;

    //serve per I blocchi di pianeta

    private boolean purpleAlien;
    private boolean orangeAlien;

    public Ship(Color color){
        this.color = color;
        lostTiles=0;
        credits=0;
        travelDays=null;
        reservedTiles=new ArrayList<>();
        cargoFromCards = new ArrayList<>();
        purpleAlien=false;
        orangeAlien=false;
        row_max=5;
        col_max=7;

        tileInHand=null;
        floorplanArrayList = new ArrayList<>();
        for(int i=0;i<row_max;i++){
            floorplanArrayList.add(new ArrayList<>());
            for(int j=0;j<col_max;j++){
                floorplanArrayList.get(i).add(null);
            }
        }
    }
    public Tile getLastPlacedTile(){

        return lastPlacedTile;
    }
    public void setLastPlacedTile(Tile lastPlacedTile){

        this.lastPlacedTile = lastPlacedTile;
    }
    public Tile getTileInHand() {
        return tileInHand;
    }

    public void setTileInHand(Tile tileInHand) {
        this.tileInHand = tileInHand;
    }

    public int getRowMax(){
        return row_max;
    }
    public int getColMax(){
        return col_max;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<ArrayList<Tile>> getFloorplanArrayList() {
        return floorplanArrayList;
    }

    /**function finds a Row of Tile if present
     * @param tile
     * @return row(if not present -1)
     */
    public int findTileOnFloorplanRow(Tile tile){
        int index=0;
        for(ArrayList<Tile> row:floorplanArrayList){
            for(int j=0;j<col_max;j++){
                if(row.get(j)!=null && row.get(j).equals(tile)){
                    return index;
                }
            }
            index++;
        }
        return -1;

    }

    /**function finds a column of Tile if present
     * @param tile
     * @return column(if not present -1)
     */
    public int findTileOnFloorPlanColumn(Tile tile){
        for(ArrayList<Tile> row:floorplanArrayList){
            for(int j=0;j<col_max;j++){
                if(row.get(j)!=null && row.get(j).equals(tile)){
                    return j;
                }
            }
        }
        return -1;
    }
    /**getAdiacentTiles returns an ArrayList of tiles around a specific tile(specified by centralTile,the order of tiles is N W S E)
     * @param centralTile
     * @return arrayList
     */
    public ArrayList<Tile> getAdiacentTiles(Tile centralTile){
        int row= findTileOnFloorplanRow(centralTile);
        int column= findTileOnFloorPlanColumn(centralTile);

        ArrayList<Tile> adiacentTiles = new ArrayList<>();
        if(row-1>=0){
            adiacentTiles.add(floorplanArrayList.get(row-1).get(column));
        }else{
            adiacentTiles.add(null);
        }
        if(column-1>=0){
            adiacentTiles.add(floorplanArrayList.get(row).get(column-1));
        }else{
            adiacentTiles.add(null);
        }
        if(row+1<row_max){
            adiacentTiles.add(floorplanArrayList.get(row+1).get(column));
        }else {
            adiacentTiles.add(null);
        }
        if(column+1<col_max){
            adiacentTiles.add(floorplanArrayList.get(row).get(column+1));
        }else{
            adiacentTiles.add(null);
        }

        return adiacentTiles;
    }

    /**getAdiacentTiles returns an ArrayList of tiles around a specific tile(specified by Row and Column,the order of tiles is N W S E
     * @param row
     * @param column
     * @return arrayList
     */
    public ArrayList<Tile> getAdiacentTiles(int row,int column){

        ArrayList<Tile> adiacentTiles = new ArrayList<>();
        if(row-1>=0){
            adiacentTiles.add(floorplanArrayList.get(row-1).get(column));
        }else{
            adiacentTiles.add(null);
        }
        if(column-1>=0){
            adiacentTiles.add(floorplanArrayList.get(row).get(column-1));
        }else{
            adiacentTiles.add(null);
        }
        if(row+1<row_max){
            adiacentTiles.add(floorplanArrayList.get(row+1).get(column));
        }else {
            adiacentTiles.add(null);
        }
        if(column+1<col_max){
            adiacentTiles.add(floorplanArrayList.get(row).get(column+1));
        }else{
            adiacentTiles.add(null);
        }

        return adiacentTiles;
    }


    /**this function checks if connectors of ship were build correctly or not
     * @return boolean
     */
    public boolean checkFloorPlanConnection() {
        for (ArrayList<Tile> list : floorplanArrayList) {
            for (Tile tile : list) {
                if(tile!=null){
                    ArrayList<Tile> adiacentTile = getAdiacentTiles(tile);
                    List<ConnectorType> connectors = tile.getConnectors();
                    if(adiacentTile.get(0)!=null){

                        //north
                        ConnectorType connector = connectors.get(0);
                        ConnectorType connectorAdiacent = adiacentTile.get(0).getConnectors().get(2);
                        if (checkEngineOrCannon(connector, connectorAdiacent)) return false;
                    }
                    if(adiacentTile.get(1)!=null){
                        //west
                        ConnectorType connector = connectors.get(1);
                        ConnectorType connectorAdiacent = adiacentTile.get(1).getConnectors().get(3);
                        if (checkEngineOrCannon(connector, connectorAdiacent)) return false;
                    }
                    if(adiacentTile.get(2)!=null){
                        //south
                        ConnectorType connector = connectors.get(2);
                        ConnectorType connectorAdiacent = adiacentTile.get(2).getConnectors().get(0);
                        if (checkEngineOrCannon(connector, connectorAdiacent)) return false;
                    }
                    if(adiacentTile.get(3)!=null){
                        //east
                        ConnectorType connector = connectors.get(3);
                        ConnectorType connectorAdiacent = adiacentTile.get(3).getConnectors().get(1);
                        if (checkEngineOrCannon(connector, connectorAdiacent)) return false;
                    }
//                    else if (adiacentTile.get(0)==null && adiacentTile.get(1)==null && adiacentTile.get(2)==null && adiacentTile.get(3)==null) {
//                        //return findTileOnFloorplanRow(tile) == 2 && findTileOnFloorPlanColumn(tile) == 3;
//                    }
                }
            }
        }

        ArrayList<EngineTile> listOfEngine = getListOfEngine();
        for(EngineTile tile : listOfEngine){
            if(tile.getConnectors().get(2)!=ConnectorType.ENGINE_SINGLE && tile.getConnectors().get(2)!=ConnectorType.ENGINE_DOUBLE){
                return false;
            }
        }

        if(isShipBroken()){
            return false;
        }

        return true;
    }

    private boolean checkEngineOrCannon(ConnectorType connector, ConnectorType connectorAdiacent) {
        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
            if(connectorAdiacent.equals(ConnectorType.ENGINE_SINGLE)
                    ||connectorAdiacent.equals(ConnectorType.ENGINE_DOUBLE)
                    ||connectorAdiacent.equals(ConnectorType.CANNON_SINGLE)
                    ||connectorAdiacent.equals(ConnectorType.CANNON_DOUBLE)
                    ||connector.equals(ConnectorType.CANNON_SINGLE)
                    ||connector.equals(ConnectorType.CANNON_DOUBLE)
                    ||connector.equals(ConnectorType.ENGINE_SINGLE)
                    ||connector.equals(ConnectorType.ENGINE_DOUBLE)){
                return true;
            }
        }else{
            return true;
        }
        return false;
    }

    /**
     * checkFloorplanConnection is used to obtain a list that represent a set of united components that start from centralCabin
     * @return
     */
    public ArrayList<Tile> checkFloorplanConnection(){
        Tile centralTile = floorplanArrayList.get(2).get(3);

        Stack<Tile> toVisitStack = new Stack<>();
        toVisitStack.push(centralTile);

        ArrayList<Tile> visitedList = new ArrayList<>();


        while(!toVisitStack.isEmpty()){

            Tile visitTile = toVisitStack.pop();

            if(!visitedList.contains(visitTile)){
                visitedList.add(visitTile);

                ArrayList<Tile> adiacentTileList = getAdiacentTiles(visitTile);
                List<ConnectorType> connectorsList = visitTile.getConnectors();

                for(int i=0;i<4;i++){
                    if(adiacentTileList.get(i)!=null){
                        ConnectorType connector = connectorsList.get(i);
                        ConnectorType connectorAdiacent = adiacentTileList.get(i).getConnectors().get((i+2)%4);

                        if(connector!=ConnectorType.ENGINE_SINGLE
                                &&connector!=ConnectorType.ENGINE_DOUBLE
                                &&connector!=ConnectorType.CANNON_SINGLE
                                &&connector!=ConnectorType.CANNON_DOUBLE
                                &&connector!=ConnectorType.NONE
                                &&connectorAdiacent!=ConnectorType.ENGINE_SINGLE
                                &&connectorAdiacent!=ConnectorType.ENGINE_DOUBLE
                                &&connectorAdiacent!=ConnectorType.CANNON_SINGLE
                                &&connectorAdiacent!=ConnectorType.CANNON_DOUBLE
                                &&connectorAdiacent!=ConnectorType.NONE){
                            toVisitStack.push(adiacentTileList.get(i));
                        }
                    }
                }
            }

        }

        return visitedList;
    }

    /**
     * @return a list of components that are not connected to central piece
     */
    public ArrayList<Tile> getNonValidTileList(){
        ArrayList<Tile> nonValidList = new ArrayList<>();
        ArrayList<Tile> validList = new ArrayList<>();
        boolean shipNotEmpty = false;
        if(getTileOnFloorPlan(2,3).isPresent()){
            validList = checkFloorplanConnection();
            shipNotEmpty = true;
        }else{
            for(ArrayList<Tile> list : floorplanArrayList){
                for(Tile tile: list){
                    if(tile!=null&&!shipNotEmpty){
                        validList = checkFloorplanConnection(tile);
                        shipNotEmpty = true;
                    }
                }
            }
        }

        if(shipNotEmpty){
            for(ArrayList<Tile> list : floorplanArrayList){
                for(Tile tile: list){
                    if(tile!=null&&!validList.contains(tile)){
                        nonValidList.add(tile);
                    }
                }
            }
        }

        return nonValidList;
    }
    public ArrayList<Tile> getNonValidTileList(Tile tile1){
        ArrayList<Tile> nonValidList = new ArrayList<>();
        ArrayList<Tile> validList = new ArrayList<>();
        validList = checkFloorplanConnection(tile1);

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile: list){
                if(tile!=null&&!validList.contains(tile)){
                    nonValidList.add(tile);
                }
            }
        }
        return nonValidList;
    }

    /**
     * checks whether ship is broken or not
     * @return boolean
     */
    public boolean isShipBroken(){
        ArrayList<Tile> nonValidList = getNonValidTileList();
        if(nonValidList.size()==0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * getShipPiecesList is used to obtain all sets of ship that are connected
     * @return arraylist
     */
    public ArrayList<ArrayList<Tile>> getShipPiecesList(){
        ArrayList<ArrayList<Tile>> piecesList = new ArrayList<>();
        ArrayList<Tile> validList = new ArrayList<>();
        ArrayList<Tile> nonValidList = new ArrayList<>();
        boolean shipNotEmpty = false;
        if(getTileOnFloorPlan(2,3).isPresent()){
            validList = checkFloorplanConnection();
            nonValidList = getNonValidTileList();
        }else{
            for(ArrayList<Tile> list : floorplanArrayList){
                for(Tile tile: list){
                    if(tile!=null&&!shipNotEmpty){
                        validList = checkFloorplanConnection(tile);
                        nonValidList = getNonValidTileList(tile);
                    }
                }
            }
        }

        //ArrayList<Tile> nonValidList = getNonValidTileList();

        piecesList.add(validList);

        while(!nonValidList.isEmpty()){
            validList = checkFloorplanConnection(nonValidList.getFirst());

            for(Tile tile: validList){
                if(nonValidList.contains(tile)){
                    nonValidList.remove(tile);
                }
            }

            piecesList.add(validList);

        }



        return piecesList;
    }

    /**
     * checkFloorplanConnection is used to obtain a list that represent a set of united components that start from tileStart
     * @param tileStart
     * @return
     */
    public ArrayList<Tile> checkFloorplanConnection(Tile tileStart){

        Stack<Tile> toVisitStack = new Stack<>();
        toVisitStack.push(tileStart);

        ArrayList<Tile> visitedList = new ArrayList<>();


        while(!toVisitStack.isEmpty()){

            Tile visitTile = toVisitStack.pop();

            if(!visitedList.contains(visitTile)){
                visitedList.add(visitTile);

                ArrayList<Tile> adiacentTileList = getAdiacentTiles(visitTile);
                List<ConnectorType> connectorsList = visitTile.getConnectors();

                for(int i=0;i<4;i++){
                    if(adiacentTileList.get(i)!=null){
                        ConnectorType connector = connectorsList.get(i);
                        ConnectorType connectorAdiacent = adiacentTileList.get(i).getConnectors().get((i+2)%4);

                        if(connector!=ConnectorType.ENGINE_SINGLE
                                &&connector!=ConnectorType.ENGINE_DOUBLE
                                &&connector!=ConnectorType.CANNON_SINGLE
                                &&connector!=ConnectorType.CANNON_DOUBLE
                                &&connector!=ConnectorType.NONE
                                &&connectorAdiacent!=ConnectorType.ENGINE_SINGLE
                                &&connectorAdiacent!=ConnectorType.ENGINE_DOUBLE
                                &&connectorAdiacent!=ConnectorType.CANNON_SINGLE
                                &&connectorAdiacent!=ConnectorType.CANNON_DOUBLE
                                &&connectorAdiacent!=ConnectorType.NONE){
                            toVisitStack.push(adiacentTileList.get(i));
                        }
                    }
                }
            }

        }

        return visitedList;

    }


    public ArrayList<Integer> getCargoFromCards() {
        return cargoFromCards;
    }
    public void addBlocks(List<Integer> cargoFromCards) {
        this.cargoFromCards.addAll(cargoFromCards);
    }
    public void resetCargoFromCards() {
        this.cargoFromCards.clear();
    }
    public void removeBlockCargoFromCards(Integer cargoF) {
        this.cargoFromCards.remove(cargoF);
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

    public void removeTileOnFloorPlan(int row, int column){

        Tile tile = floorplanArrayList.get(row).get(column);
        floorplanArrayList.get(row).set(column,null);
    }

    public void addLostTiles(int lostTiles){
        this.lostTiles += lostTiles;
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

    public void setPurpleAlien(boolean alien){
        purpleAlien = alien;
    }
    public void setOrangeAlien(boolean alien){
        orangeAlien = alien;
    }
    public List<Tile> getReservedTiles() {
        return reservedTiles;
    }
    public void addReservedTile(Tile tile){
        reservedTiles.add(tile);
    }

    public void moveReservedToDiscard(){
        for(Tile tile : this.reservedTiles){
            this.lostTiles++;
        }
        this.reservedTiles.clear();

    }
    public void moveReservedToBoard(Tile tile){
        if(tile!=null){
            reservedTiles.remove(tile);
        }
    }

    public int getExposedConnectors() {
        int exposedConnectors = 0;
        for (ArrayList<Tile> list : floorplanArrayList) {
            for (Tile tile : list) {
                if (tile != null) {
                    ArrayList<Tile> adjacentTiles = getAdiacentTiles(tile);
                    List<ConnectorType> connectors = tile.getConnectors();
                    int j = 0;
                    for (Tile adjacentTile : adjacentTiles) {
                        adjacentTile = adjacentTiles.get(j);
                        if (adjacentTile == null) {
                            if (connectors.get(j) != ConnectorType.NONE){
                                if(!(connectors.get(j).equals(ConnectorType.ENGINE_SINGLE)||connectors.get(j).equals(ConnectorType.ENGINE_DOUBLE)||connectors.get(j).equals(ConnectorType.CANNON_SINGLE)||connectors.get(j).equals(ConnectorType.CANNON_DOUBLE))){
                                    exposedConnectors++;
                                }
                            }
                        }
                        j += 1;
                    }
                }
            }
        }
        return exposedConnectors;
    }
    /**
     * @return firepower of ship
     */
    //direzione dei cannoni è importante!
    public double getFirepower(){
        ArrayList<CannonTile> cannonTiles = getListOfFirepower();
        double firepower = 0;
        int multiplicator = 1;
        for(CannonTile cannonTile : cannonTiles){
            if(cannonTile.getActiveState()){
                if(cannonTile.getDoublePower()){
                    multiplicator = 2;
                }
                if(cannonTile.getConnectors().get(0)==ConnectorType.CANNON_SINGLE || cannonTile.getConnectors().get(0)==ConnectorType.CANNON_DOUBLE){
                    firepower+=multiplicator;
                }else{
                    firepower+=multiplicator*0.5;
                }
                multiplicator = 1;
            }
        }
        ArrayList<CabinTile> cabinList = getListOfCabin();
        boolean visited = false;
        for(CabinTile cabinTile : cabinList){
            AlienColor alienColor = cabinTile.getAlienColor();
            if(alienColor!=null&&alienColor.equals(AlienColor.PURPLE)&&!visited){
                firepower+=2;
                visited = true;
            }
        }
//        if(getPurpleAlien()){
//            firepower+=2;
//        }
        return firepower;
    }

    /**
     * setFirepower is used to activate or disactivate Double Cannons
     * @param tile
     * @param active
     */
    public void setFirepower(CannonTile tile,boolean active){
        if(tile!=null){
            if(tile.getDoublePower()){
                tile.setActiveState(active);
            }
        }
    }

    /**
     * @return array list of All Cannons
     */
    public ArrayList<CannonTile> getListOfFirepower(){
        CannonTileVisitor cannonTileVisitor = new CannonTileVisitor();
        ArrayList<CannonTile> listOfSingleFirepower = new ArrayList<>();

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null){
                    tile.accept(cannonTileVisitor);
                }
            }
        }

        listOfSingleFirepower = cannonTileVisitor.getList();
        return listOfSingleFirepower;
    }

    /**
     * @return array list of Double Cannons
     */
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

    /**
     * @return enginePower of ship
     */
    public int getEnginePower(){
        ArrayList<EngineTile> listEngine = getListOfEngine();
        int enginePower = 0;
        int multiplicator = 1;
        for(EngineTile engineTile : listEngine){
            if(engineTile.getActiveState()){
                if(engineTile.getDoublePower()){
                    multiplicator = 2;
                }
                enginePower+=multiplicator;
            }
            multiplicator=1;
        }
        ArrayList<CabinTile> cabinList = getListOfCabin();
        boolean visited = false;
        for(CabinTile cabinTile : cabinList){
            AlienColor alienColor = cabinTile.getAlienColor();
            if(alienColor!=null&&alienColor.equals(AlienColor.ORANGE)&&!visited){
                enginePower+=2;
                visited = true;
            }
        }
//        if(getOrangeAlien()){
//            enginePower+=2;
//        }
        return enginePower;
    }

    /**
     * setEnginePower is used to activate or disactivate a DoubleEngine
     * @param tile
     * @param active
     */
    public void setEnginePower(EngineTile tile,boolean active){
        if(tile!=null){
            if(tile.getDoublePower()){
                tile.setActiveState(active);
            }
        }

    }

    /**
     * @return array list of Engines
     */
    public ArrayList<EngineTile> getListOfEngine(){
        ArrayList<EngineTile> listOfEngine = new ArrayList<>();
        EngineTileVisitor engineTileVisitor = new EngineTileVisitor();

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null){
                    tile.accept(engineTileVisitor);
                }
            }
        }
        listOfEngine = engineTileVisitor.getList();
        return listOfEngine;
    }

    /**
     * @return array list of Double Engine Power
     */
    public ArrayList<EngineTile> getListOfDoubleEnginePower(){
        ArrayList<EngineTile> doubleEnginePower = new ArrayList<>();
        ArrayList<EngineTile> listEngine = getListOfEngine();

        for(EngineTile engineTile : listEngine){
            if(engineTile.getDoublePower()){
                doubleEnginePower.add(engineTile);
            }
        }

        return doubleEnginePower;

    }

    /**
     * setShield is used to activate or disactivate a shield
     * @param tile
     * @param active
     */
    public void setShield(ShieldTile tile,boolean active){
        if(tile!=null){
            tile.setActiveState(active);
        }
    }

    /**
     * @return array list of shields
     */
    public ArrayList<ShieldTile> getListOfShield() {
        ArrayList<ShieldTile> shieldList = new ArrayList<>();
        ShieldTileVisitor shieldTileVisitor = new ShieldTileVisitor();

        for (ArrayList<Tile> list : floorplanArrayList) {
            for (Tile tile : list) {
                if (tile != null) {
                    tile.accept(shieldTileVisitor);
                }
            }
        }
        shieldList = shieldTileVisitor.getList();
        return shieldList;
    }

    /**
     * @return array list of all Shield Orientation
     */
    public ArrayList<ShieldOrientation> getListOfShieldOrientation(){
        ArrayList<ShieldOrientation> shieldOrientationList = new ArrayList<>();
        ArrayList<ShieldTile> shieldList = new ArrayList<>();
        shieldList = getListOfShield();
        for(ShieldTile shieldTile : shieldList){
            //ShieldTile shieldTile = (ShieldTile) tile;
            shieldOrientationList.add(shieldTile.getOrientation());
        }
        return shieldOrientationList;
    }

    /**
     * @return array list of Active shieldOrientation
     */
    public ArrayList<ShieldOrientation> getListOfActiveShieldOrientation(){
        ArrayList<ShieldOrientation> shieldOrientationList = new ArrayList<>();
        ArrayList<ShieldTile> shieldList = new ArrayList<>();
        shieldList = getListOfShield();
        for(ShieldTile shieldTile : shieldList){
            //ShieldTile shieldTile = (ShieldTile) tile;
            if(shieldTile.getActiveState()){
                shieldOrientationList.add(shieldTile.getOrientation());
            }
        }
        return shieldOrientationList;
    }

    /**
     * @return array list of Cabin
     */
    public ArrayList<CabinTile> getListOfCabin(){
        ArrayList<CabinTile> cabinList = new ArrayList<>();
        CabinTileVisitor cabinTileVisitor = new CabinTileVisitor();

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null){
                    tile.accept(cabinTileVisitor);
                }
            }
        }
        cabinList = cabinTileVisitor.getList();
        return cabinList;
    }

    /**
     * @return array list of batteries
     */
    public ArrayList<BatteryTile> getListOfBattery(){
        ArrayList<BatteryTile> batteriesList = new ArrayList<>();
        BatteryTileVisitor batteryTileVisitor = new BatteryTileVisitor();

        for(ArrayList<Tile> list : floorplanArrayList){
            for(Tile tile : list){
                if(tile!=null){
                    tile.accept(batteryTileVisitor);
                }
            }
        }
        batteriesList = batteryTileVisitor.getList();
        return batteriesList;
    }
    //chiedere i metodi get di Batteries
    public int getBatteries(){
        ArrayList<BatteryTile> batteryList = getListOfBattery();
        int batteries = 0;
        for(BatteryTile batteryTile : batteryList){
            batteries+=batteryTile.getSlotsFilled();
        }
        return batteries;
    }
    public int getNumberOfInhabitants(){
        ArrayList<CabinTile> cabinList = getListOfCabin();
        int inhabitants = 0;
        for(CabinTile cabinTile : cabinList){
            if(cabinTile.getInhabitants()==CabinInhabitants.ALIEN || cabinTile.getInhabitants()==CabinInhabitants.ONE){
                inhabitants++;
            } else if (cabinTile.getInhabitants()==CabinInhabitants.TWO) {
                inhabitants+=2;
            }
        }
        return inhabitants;
    }

    /**
     * @return array list of Cargo
     */
    /*example of Tile Visitor*/
    public ArrayList<CargoTile> getListOfCargo(){
        CargoTileVisitor cabinTileVisitor = new CargoTileVisitor();
        ArrayList<CargoTile> cargoList = new ArrayList<>();


        for(ArrayList<Tile> tilelist : floorplanArrayList){
            for(Tile tile : tilelist){
                if(tile!=null){
                    tile.accept(cabinTileVisitor);
                }
            }
        }
        cargoList = cabinTileVisitor.getList();
        return cargoList;
    }
    /**
     * function returns an arraylist of PurpleAdaptors
     * @return list Of Purple Adaptors
     */
    public ArrayList<BioadaptorTile> getListOfPurpleAdaptors(){
        ArrayList<BioadaptorTile> bioadaptorList = getListOfAdaptors();
        ArrayList<BioadaptorTile> purpleList = new ArrayList<>();
        for( BioadaptorTile bioadaptorTile : bioadaptorList ){
            if(bioadaptorTile.getColor()==AlienColor.PURPLE){
                purpleList.add(bioadaptorTile);
            }
        }
        return purpleList;
    }

    /**
     * function returns an arraylist of OrangeAdaptors
     * @return list Of Orange Adaptors
     */
    public ArrayList<BioadaptorTile> getListOfOrangeAdaptors(){
        ArrayList<BioadaptorTile> bioadaptorList = getListOfAdaptors();
        ArrayList<BioadaptorTile> orangeList = new ArrayList<>();
        for( BioadaptorTile bioadaptorTile : bioadaptorList ){
            if(bioadaptorTile.getColor()==AlienColor.ORANGE){
                orangeList.add(bioadaptorTile);
            }
        }
        return orangeList;
    }

    /**
     * function returns a arraylist of BioadaptorsTile
     * @return list of BioadaptorTile
     */
    public ArrayList<BioadaptorTile> getListOfAdaptors(){
        BioadaptorTileVisitor bioadaptorTileVisitor = new BioadaptorTileVisitor();
        ArrayList<BioadaptorTile> bioadaptorList = new ArrayList<>();


        for(ArrayList<Tile> tilelist : floorplanArrayList){
            for(Tile tile : tilelist){
                if(tile!=null){
                    //bioadaptorList = bioadaptorTileVisitor.accept(tile);
                    tile.accept(bioadaptorTileVisitor);
                }
            }
        }
        bioadaptorList = bioadaptorTileVisitor.getList();
        return bioadaptorList;
    }

    /**
     * <div>getRowListTiles takes as the input a column number,and returns an entire row without empty cells</div>
     * @param row
     * @return list of tile
     */
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

    /**
     * <div>getColumnListTiles takes as the input a column number,and returns an entire column without empty cells</div>
     * @param column
     * @return list of tile
     */
    public ArrayList<Tile> getColumnListTiles(int column){
        ArrayList<Tile> columnListTiles = new ArrayList<>();
        for(ArrayList<Tile> rowList : floorplanArrayList){
            if(rowList.get(column)!=null) {
                columnListTiles.add(rowList.get(column));
            }
        }
        return columnListTiles;
    }
    //chiedere i metodi get() per Bioadaptors
    public void updateCabinTiles(){
        ArrayList<CabinTile> cabinTilesList = getListOfCabin();
        BioadaptorTileVisitor bioadaptorTileVisitor = new BioadaptorTileVisitor();
        ArrayList<BioadaptorTile> bioadaptorList = null;

        for(CabinTile cabinTile : cabinTilesList){
            ArrayList<Tile> adiacentTileList = getAdiacentTiles(cabinTile);

            for(Tile adiacentTile : adiacentTileList){
                if(adiacentTile!=null){
                    adiacentTile.accept(bioadaptorTileVisitor);
                }
            }
            bioadaptorList = bioadaptorTileVisitor.getList();
            int purple=0;
            if(bioadaptorList.size()>0){
                for(BioadaptorTile bioadaptorTile : bioadaptorList){
                    if(bioadaptorTile.getColor()==AlienColor.PURPLE){
                        purple++;
                    }
                }
                cabinTile.setPurple(purple);
                cabinTile.setOrange(bioadaptorList.size()-purple);

            }else{
                cabinTile.setPurple(0);
                cabinTile.setOrange(0);
                if(cabinTile.getInhabitants()==CabinInhabitants.ALIEN){
                    cabinTile.updateInhabitants(CabinInhabitants.NONE);
                }
            }
            purple=0;
        }
    }

    public void disactivateEverything(){
        ArrayList<CannonTile> cannonList = getListOfDoubleFirepower();
        for(CannonTile cannonTile : cannonList){
            cannonTile.setActiveState(false);
        }
        ArrayList<ShieldTile> shieldlist = getListOfShield();
        for( ShieldTile shieldTile : shieldlist){
            shieldTile.setActiveState(false);
        }
        ArrayList<EngineTile> enginelist = getListOfDoubleEnginePower();
        for( EngineTile engineTile : enginelist){
            engineTile.setActiveState(false);
        }
    }

    public void ejectAll(){
        for(CabinTile cabinTile : getListOfCabin()){
            cabinTile.updateInhabitants(CabinInhabitants.NONE);
        }
    }

    public void removeAllCargo(){
        for(CargoTile cargoTile : getListOfCargo()){
            for(Integer i : new ArrayList<>(cargoTile.getTileContent())){
                cargoTile.removeBlock(i);
            }
        }
    }

    public void removeAllBatteries(){
        for(BatteryTile batteryTile : getListOfBattery()){
            batteryTile.removeBattery(batteryTile.getSlotsFilled());
        }
    }

    public void removeFirstTile(int index, Direction direction){
        Tile t;
        int index2;
        switch(direction){
            case NORTH:
                t = getColumnListTiles(index).getFirst();
                index2 = findTileOnFloorplanRow(t);
                removeTileOnFloorPlan(index2, index);
                break;
            case SOUTH:
                t = getColumnListTiles(index).getLast();
                index2 = findTileOnFloorplanRow(t);
                removeTileOnFloorPlan(index2, index);
                break;
            case EAST:
                t = getRowListTiles(index).getFirst();
                index2 = findTileOnFloorPlanColumn(t);
                removeTileOnFloorPlan(index, index2);
                break;
            case WEST:
                t = getRowListTiles(index).getLast();
                index2 = findTileOnFloorPlanColumn(t);
                removeTileOnFloorPlan(index, index2);
                break;
        }
    }

    public void fill(){
        for(CabinTile cabinTile : getListOfCabin()){
            if(cabinTile.getInhabitants()!=CabinInhabitants.ALIEN){
                cabinTile.updateInhabitants(CabinInhabitants.TWO);
            }
        }
    }

}
