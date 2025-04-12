package it.polimi.ingsw.galaxytrucker.Model.PlayerShip;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ship {

    private ArrayList<ArrayList<Tile>> floorplanArrayList;

    private final Color color;
    private int lostTiles;
    private ArrayList<Tile> reservedTiles;

    private int exposedConnectors;
    private int batteries;
    private int crewMembers;
    private int credits;
    private Integer travelDays;

    int row_max;
    int col_max;


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

    public Color getColor() {
        return color;
    }

    public ArrayList<ArrayList<Tile>> getFloorplanArrayList() {
        return floorplanArrayList;
    }

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

    public ArrayList<Tile> getAdiacentTiles(Tile centralTile){
        int row= findTileOnFloorplanRow(centralTile);
        int column= findTileOnFloorPlanColumn(centralTile);

        ArrayList<Tile> adiacentTiles = new ArrayList<>();
        if(row-1>=0){
            adiacentTiles.add(floorplanArrayList.get(row-1).get(column));
        }else{
            adiacentTiles.add(null);
        }
        if(column+1<col_max){
            adiacentTiles.add(floorplanArrayList.get(row).get(column+1));
        }else{
            adiacentTiles.add(null);
        }
        if(row+1<row_max){
            adiacentTiles.add(floorplanArrayList.get(row+1).get(column));
        }else {
            adiacentTiles.add(null);
        }
        if(column-1>=0){
            adiacentTiles.add(floorplanArrayList.get(row).get(column-1));
        }else{
            adiacentTiles.add(null);
        }
        return adiacentTiles;
    }

    public ArrayList<Tile> getAdiacentTiles(int row,int column){

        ArrayList<Tile> adiacentTiles = new ArrayList<>();
        if(row-1>=0){
            adiacentTiles.add(floorplanArrayList.get(row-1).get(column));
        }else{
            adiacentTiles.add(null);
        }
        if(column+1<col_max){
            adiacentTiles.add(floorplanArrayList.get(row).get(column+1));
        }else{
            adiacentTiles.add(null);
        }
        if(row+1<row_max){
            adiacentTiles.add(floorplanArrayList.get(row+1).get(column));
        }else {
            adiacentTiles.add(null);
        }
        if(column-1>=0){
            adiacentTiles.add(floorplanArrayList.get(row).get(column-1));
        }else{
            adiacentTiles.add(null);
        }
        return adiacentTiles;
    }


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
                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.ENGINE)){
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
                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.ENGINE)){
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
                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.ENGINE)){
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
                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.ENGINE)){
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }

                }
            }
        }

        ArrayList<EngineTile> listOfEngine = getListOfEngine();
        for(EngineTile tile : listOfEngine){
            if(tile.getConnectors().get(2)!=ConnectorType.ENGINE){
                return false;
            }
        }

//        for(ArrayList<Tile> list : floorplanArrayList) {
//            for (Tile tile : list) {
//                if(tile.getType()==TileType.ENGINE){
//                    if(tile.getConnectors().get(2)!=ConnectorType.ENGINE){
//                        return false;
//                    }
//                }
//            }
//        }
        return true;
    }

//    public ArrayList<Tile> checkFloorPlanConnection() {
//        ArrayList<Tile> eliminatedTilesList = new ArrayList<>();
//        boolean centralTileAdded = false;
//
//        for (ArrayList<Tile> list : floorplanArrayList) {
//            for (Tile tile : list) {
//                if(tile!=null){
//                    ArrayList<Tile> adiacentTile = getAdiacentTiles(tile);
//                    List<ConnectorType> connectors = tile.getConnectors();
//                    if(adiacentTile.get(0)!=null){
//                        //north
//                        ConnectorType connector = connectors.get(0);
//                        ConnectorType connectorAdiacent = adiacentTile.get(0).getConnectors().get(2);
//                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
//                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.ENGINE)){
//                                eliminatedTilesList.add(adiacentTile.get(0));
//                            }
//                        }else{
//                            eliminatedTilesList.add(tile);
//                        }
//                    }
//                    if(adiacentTile.get(1)!=null){
//                        //west
//                        ConnectorType connector = connectors.get(1);
//                        ConnectorType connectorAdiacent = adiacentTile.get(1).getConnectors().get(3);
//                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
//                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.ENGINE)){
//                                return false;
//                            }
//                        }else{
//                            return false;
//                        }
//                    }
//                    if(adiacentTile.get(2)!=null){
//                        //south
//                        ConnectorType connector = connectors.get(2);
//                        ConnectorType connectorAdiacent = adiacentTile.get(2).getConnectors().get(0);
//                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
//                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.ENGINE)){
//                                return false;
//                            }
//                        }else{
//                            return false;
//                        }
//                    }
//                    if(adiacentTile.get(3)!=null){
//                        //east
//                        ConnectorType connector = connectors.get(3);
//                        ConnectorType connectorAdiacent = adiacentTile.get(3).getConnectors().get(1);
//                        if(connectorAdiacent.equals(connector)||(connectorAdiacent==ConnectorType.UNIVERSAL&&connector!=ConnectorType.NONE)||(connector==ConnectorType.UNIVERSAL&&connectorAdiacent!=ConnectorType.NONE)){
//                            if(connectorAdiacent.equals(ConnectorType.ENGINE)||connectorAdiacent.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.CANNON)||connector.equals(ConnectorType.ENGINE)){
//                                return false;
//                            }
//                        }else{
//                            return false;
//                        }
//                    }
//
//                }
//            }
//        }
//        for(ArrayList<Tile> list : floorplanArrayList) {
//            for (Tile tile : list) {
//                if(tile.getType()==TileType.ENGINE){
//                    if(tile.getConnectors().get(2)!=ConnectorType.ENGINE){
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
    public ArrayList<Tile> checkFloorplanConnection(){
        Tile centralTile = floorplanArrayList.get(2).get(3);

        ArrayList<Tile> tileToVisitList = getAdiacentTiles(centralTile);
        ArrayList<Tile> tileVisitedList = new ArrayList<>();

        for(Tile tileToVisit: tileToVisitList) {
            for (Tile tileVisited : tileVisitedList) {
                if (tileToVisit!=null&&!tileToVisit.equals(tileVisited)) {
                    tileVisitedList.add(tileToVisit);
                    ArrayList<Tile> nearTile = getAdiacentTiles(tileToVisit);
                    tileToVisitList.addAll(nearTile);

                }
            }
            //tileVisitedList.add(tileToVisit);


        }

       return tileVisitedList;
    }

    public ArrayList<Tile> checkFloorplanConnection(Tile tile){

        ArrayList<Tile> tileToVisitList = getAdiacentTiles(tile);
        ArrayList<Tile> tileVisitedList = new ArrayList<>();

        for(Tile tileToVisit: tileToVisitList) {
            for (Tile tileVisited : tileVisitedList) {
                if (tileToVisit!=null&&!tileToVisit.equals(tileVisited)) {
                    tileVisitedList.add(tileToVisit);
                    ArrayList<Tile> nearTile = getAdiacentTiles(tileToVisit);
                    tileToVisitList.addAll(nearTile);

                }
            }
            //tileVisitedList.add(tileToVisit);
        }

        return tileVisitedList;
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
        this.cargoFromCards.addAll(cargoFromCards);
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

    public void removeTileOnFloorPlan(int row, int column){

         Tile tile = floorplanArrayList.get(row).get(column);
         floorplanArrayList.get(row).set(column,null);
    }


    public int getNumberOfBatteries(){
        return batteries;
    }
    public void setBatteries(int batteries){
        this.batteries = batteries;
    }

    public int getNumberOfCrewMembers(){
        return crewMembers;
    }
    public void setCrewMembers(int crewMembers){
        this.crewMembers = crewMembers;
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
           if(cannonTile.getActiveState()){
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
                    tile.accept(cannonTileVisitor);
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
                if(engineTile.getDoublePower()){
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
            if(tile.getDoublePower()){
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
                    tile.accept(engineTileVisitor);
                }
            }
        }
        listOfEngine = engineTileVisitor.getList();
        return listOfEngine;
    }
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

    public void setShield(ShieldTile tile,boolean active){
        if(tile!=null){
                tile.setActiveState(active);
        }
    }

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
