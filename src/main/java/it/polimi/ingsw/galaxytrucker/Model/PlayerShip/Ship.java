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


/**
 * This class represent Ship from board game
 */
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

    /**
     * Constructor of the Ship,it sets the parameters to default
     * @param color
     */
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

    /**
     * Return the last tile you placed on Ship
     * @return Tile
     */
    public Tile getLastPlacedTile(){
        return lastPlacedTile;
    }

    /**This function sets lastPlacedTile to the last placed tie on Ship
     * @param lastPlacedTile
     */
    public void setLastPlacedTile(Tile lastPlacedTile){
        this.lastPlacedTile = lastPlacedTile;
    }

    /**It return a Tile that you picked up during a building state
     * @return Tile
     */
    public Tile getTileInHand() {
        return tileInHand;
    }

    /**It sets tileInHand with the tile that you previously have chosen from TilePile during Building state
     * @param tileInHand
     */
    public void setTileInHand(Tile tileInHand) {
        this.tileInHand = tileInHand;
    }

    /**
     * This function returns the number of Rows present on Ship
     * @return int
     */
    public int getRowMax(){
        return row_max;
    }

    /**
     * This function returns the number of Columns present on Ship
     * @return int
     */
    public int getColMax(){
        return col_max;
    }

    /**
     * This function returns a color of the Ship(Of the main capsule)
     * @return COLOR
     */
    public Color getColor() {
        return color;
    }

    /**
     * This function returns an entire floorplan(matrix) of the ship
     * @return ArrayList<ArrayList<Tile>>
     */
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
     * @return column(int)(if not present -1)
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
     * @return arrayList ArrayList<Tile>
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
     * @return arrayList ArrayList<Tile>
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


    /**this function checks if connectors of ship were build correctly or not,it's also checking whether ship is broken or not
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

    /**
     * This function checks if the connection between connector and connectorAdiacent are valid
     * @param connector
     * @param connectorAdiacent
     * @return boolean
     */
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
     * @return ArrayList<Tile>
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
     * This function return a list of components that are not connected to central piece
     * @return ArrayList<Tile>
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

    /**This function return a list of components that are not connected to param
     * @param tile1
     * @return ArrayList<Tile>
     */
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
     * @return arraylist ArrayList<ArrayList<Tile>>
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


    /**
     * This function returns Cargo that player collected during Travelling State
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getCargoFromCards() {
        return cargoFromCards;
    }

    /**
     * This function sets Cargo that player collected during Travelling State
     * @param cargoFromCards
     */
    public void addBlocks(List<Integer> cargoFromCards) {
        this.cargoFromCards.addAll(cargoFromCards);
    }

    /**
     *  This function clear all Cargo that player collected during Travelling State
     */
    public void resetCargoFromCards() {
        this.cargoFromCards.clear();
    }

    /**
     *  This function remove one block of Cargos that player collected during Travelling State
     * @param cargoF
     */
    public void removeBlockCargoFromCards(Integer cargoF) {
        this.cargoFromCards.remove(cargoF);
    }

    /**
     * This function sets Credits of Ship
     * @param credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * This function returns Credits of Ship
     * @return int
     */
    public int getCredits() {
        return credits;
    }

    /**
     * This function returns the position of Ship on the Board
     * @return Integer
     */
    public Integer getTravelDays() {
        return travelDays;
    }

    /**
     * This function sets the position of Ship on the Board
     * @param travelDays
     */
    public void setTravelDays(Integer travelDays) {
        this.travelDays = travelDays;
    }

    /**
     * This function returns Optional<Tile> from specific position(row,column) on floorplan
     * @param row int
     * @param column int
     * @return Optional<Tile>
     */
    public Optional<Tile> getTileOnFloorPlan(int row,int column){

        return Optional.ofNullable(floorplanArrayList.get(row).get(column));
    }

    /**
     * This function sets Tile on floorplan to specific position(row,column)
     * @param row int
     * @param column int
     * @param tile Tile
     */
    public void setTileOnFloorPlan(int row,int column,Tile tile){
        //floorplan[row][column] = tile;
        floorplanArrayList.get(row).set(column,tile);
    }

    /**This function removes Tile from specific position(row,column) and sets that position to null
     * @param row
     * @param column
     */
    public void removeTileOnFloorPlan(int row, int column){

        Tile tile = floorplanArrayList.get(row).get(column);
        floorplanArrayList.get(row).set(column,null);
    }

    /**
     * This function add disconnected Tiles during Travelling State to the Lost Tiles
     * @param lostTiles int
     */
    public void addLostTiles(int lostTiles){
        this.lostTiles += lostTiles;
    }

    /**
     * Tis function returns the number of Tiles that were separated from Ship during Travelling State
     * @return int
     */
    public int getLostTiles(){
        return lostTiles;
    }

    /**
     * This function tells you whether ship has purpleALien or no
     * @return boolean
     */
    public boolean getPurpleAlien(){
        return purpleAlien;
    }
    /**
     * This function tells you whether ship has orangeALien or no
     * @return boolean
     */
    public boolean getOrangeAlien(){
        return orangeAlien;
    }

    /**
     * This function sets PurpleAlien on Ship
     * @param alien
     */
    public void setPurpleAlien(boolean alien){
        purpleAlien = alien;
    }

    /**
     * This function sets OrangeAlien on Ship
     * @param alien
     */
    public void setOrangeAlien(boolean alien){
        orangeAlien = alien;
    }

    /**
     * This function returns tiles,that player reserved during Building State
     * @return List<Tile>
     */
    public List<Tile> getReservedTiles() {
        return reservedTiles;
    }

    /**This function adds a Tile to Reserved Tiles
     * @param tile
     */
    public void addReservedTile(Tile tile){
        reservedTiles.add(tile);
    }

    /**
     * This function removes all reserved Tiles to Lost Tiles
     */
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

    /**
     * This function returns a number of exposed connectors that ship has
     * @return int
     */
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
     * This function returns firepower of ship
     * @return int
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
     * This function returns array list of All Cannons
     * @return ArrayList<CannonTile>
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

    /**This function returns array list of all Double Cannons
     * @return ArrayList<CannonTile>
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

    /**This function returns enginePower of ship
     * @return int
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

    /**This function returns array list of all Engines
     * @return ArrayList<EngineTile>
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

    /**This function returns array list of all Double Engine Power
     * @return ArrayList<EngineTile>
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
     * This function returns array list of all shields
     * @return ArrayList<ShieldTile>
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
     * This function returns array list of all Shield Orientation
     * @return ArrayList<ShieldOrientation>
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
     * This function returns array list of Active shieldOrientation
     * @return ArrayList<ShieldOrientation>
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
     * This function returns array list of all Cabin
     * @return ArrayList<CabinTile>
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
     * This function returns array list of all batteries
     * @return ArrayList<BatteryTile>
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

    /**
     * This function returns number of batteries present on Ship
     * @return int
     */
    //chiedere i metodi get di Batteries
    public int getBatteries(){
        ArrayList<BatteryTile> batteryList = getListOfBattery();
        int batteries = 0;
        for(BatteryTile batteryTile : batteryList){
            batteries+=batteryTile.getSlotsFilled();
        }
        return batteries;
    }

    /**
     * This function returns number of Inhabitant present on Ship
     * @return int
     */
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
     * This function returns array list of all Cargo
     * @return ArrayList<CargoTile>
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
     * function returns an arraylist of all PurpleAdaptors
     * @return list Of Purple Adaptors ArrayList<BioadaptorTile>
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
     * @return list Of Orange Adaptors ArrayList<BioadaptorTile>
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
     * function returns a arraylist of all BioadaptorsTile
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
     * @return list of tile ArrayList<Tile>
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
     * @return list of tile ArrayList<Tile>
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

    /**
     * This function disactivate everything(Double Cannons,Double Engines,Active Shields)
     */
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

    /**
     * This function eject all Inhabitants that are present on Ship
     */
    public void ejectAll(){
        for(CabinTile cabinTile : getListOfCabin()){
            cabinTile.updateInhabitants(CabinInhabitants.NONE);
        }
        setPurpleAlien(false);
        setOrangeAlien(false);
    }

    /**
     * This function remove all cargo present on Ship
     */
    public void removeAllCargo(){
        for(CargoTile cargoTile : getListOfCargo()){
            for(Integer i : new ArrayList<>(cargoTile.getTileContent())){
                cargoTile.removeBlock(i);
            }
        }
    }

    /**
     * This function removes all batteries present on Ship
     */
    public void removeAllBatteries(){
        for(BatteryTile batteryTile : getListOfBattery()){
            batteryTile.removeBattery(batteryTile.getSlotsFilled());
        }
    }

    /**
     * This function removes specific Tile if it's present
     * @param index
     * @param direction
     */
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
                t = getRowListTiles(index).getLast();
                index2 = findTileOnFloorPlanColumn(t);
                removeTileOnFloorPlan(index, index2);
                break;
            case WEST:
                t = getRowListTiles(index).getFirst();
                index2 = findTileOnFloorPlanColumn(t);
                removeTileOnFloorPlan(index, index2);
                break;

        }
    }

    /**
     * This function fills CabinsTile with people during Building State
     */
    public void fill(){
        for(CabinTile cabinTile : getListOfCabin()){
            if(cabinTile.getInhabitants()!=CabinInhabitants.ALIEN){
                cabinTile.updateInhabitants(CabinInhabitants.TWO);
            }
        }
    }

}
