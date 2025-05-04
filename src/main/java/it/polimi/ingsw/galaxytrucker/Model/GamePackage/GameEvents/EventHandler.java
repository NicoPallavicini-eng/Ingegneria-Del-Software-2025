package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.Cards.Planet;
import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.PlanetsState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventHandler {

    /*
    GLOBAL:
 - /help
 - /viewships
 - /viewleaderboard

BEFORE GAME:
 - /connect
 - /disconnect
 - /setnumberofplayers
 - /setcolor *

BUILDING:
 - /pickuptile
 - /rotatetile
 - /putdowntile
 - /placetile
 - /reservetile
 - /fliphourglass
 - /setposition
 - /pickupfromship
 - /choosedreservedtile

TRAVELLING:
 - /activatenengines
 - /activatecannons
 - /activateshields
 - /removecargo
 - /addcargo
 - /switchcargo
 - /ejectpeople
 - /giveup
 - /viewinventory


     */
    public static void handleEvent(DoneEvent event){}
    public static void handleEvent(NoChoiceEvent event){}
    /*
    public record ConnectEvent(String nickname, String IP) implements GameEvent
     */
    //aggiunger il riferimento a Game
    public static void handleEvent(ConnectEvent event) {
        Game game = event.game();
        List<Player> listPlayer = game.getListOfPlayers();
        boolean finished = false;

        //check se giocatore era presente
        for(Player player : listPlayer){
            if(player.getNickname()==event.nickname()&&player.getPlayerIp()==event.IP()){
                player.setOnlineStatus(true);
                finished=true;
            }
        }


        if(!finished){
            //check se siamo nella fase iniziale
            GameState gamestate = game.getGameState();
            //chiedere sul funzionamento;
            //DA FINIRE
            //check sulla quantita dei giocatori
            if(game.getListOfPlayers().size()-game.getNumberOfPlayers()<=0){
                throw new IllegalEventException("Number of players is maximum");
            }else{
                Player playerNew = new Player(event.nickname(),event.IP(), Color.BLUE); //COLOR TO BE CHANGED!! PLACEHOLDER SO THAT CODE RUNS
                game.addPlayer(playerNew);
            }
        }else{

        }


    }
    /*
    public record DisconnectEvent(Player player) implements GameEvent
     */
    public static void handleEvent(DisconnectEvent event) {
        Player player = event.player();
        player.setOnlineStatus(false);
    }
    public static void handleEvent(SetNumberOfPlayersEvent event) {}
    public static void handleEvent(PickUpTileEvent event) {}
    /*
    public record RotateTileEvent(Player player, boolean right) implements GameEvent
     */
    public static void handleEvent(RotateTileEvent event) {}
    public static void handleEvent(PutDownTileEvent event) {}
    public static void handleEvent(PlaceOrangeAlienEvent event) {}
    public static void handleEvent(PlacePurpleAlienEvent event) {}
    public static void handleEvent(PlaceTileEvent event) {}
    public static void handleEvent(ReserveTileEvent event) {}
    public static void handleEvent(RemoveTileEvent event) {}

    public static void handleEvent(FlipHourglassEvent event) {}
    public static void handleEvent(SetPositionEvent event) {}
    public static void handleEvent(PickUpFromShipEvent event) {}
    public static void handleEvent(PickUpReservedTileEvent event) {}
    public static void handleEvent(ViewDeckEvent event) {}
    /*
    public record ChoosePlanetEvent(Player player, int planetIndex) implements GameEvent
    //il riferimento a Game
     */
    public static void handleEvent(ChoosePlanetEvent event)throws IllegalEventException {
        Ship ship = event.player().getShip();
        PlanetsState planetState = (PlanetsState) event.game().getGameState();
        int planetIndex = event.planetIndex();
        PlanetsCard planetsCard = (PlanetsCard) planetState.getCurrentCard();
        List<Planet> planetList = planetsCard.getPlanetsList();
        if (planetIndex < planetList.size()&&planetIndex>=0) {
            //?
        }else{
            throw new IllegalEventException("Index of Planet is not valid");
        }

        Planet planet = planetList.get(planetIndex);
        ArrayList<Integer> listCargo = new ArrayList<>(planet.getBlocks());
        ship.addBlocks(listCargo);

    }
    /*
    public record ActivateEnginesEvent(Player player, List<List<Integer>> engines, List<List<Integer>> batteries) implements GameEvent
     */
    public static void handleEvent(ActivateEnginesEvent event) throws IllegalEventException {
        Ship ship = event.player().getShip();
        //controllo la lista di Engines
        for(List<Integer> selectedEngines : event.engines()){
            EngineTileVisitor engineVisitor = new EngineTileVisitor();
            int row = selectedEngines.get(0);
            int column = selectedEngines.get(1);

            Optional<Tile> engineOptional = ship.getTileOnFloorPlan(row,column);

            if(engineOptional.isPresent()){
                Tile tile = engineOptional.get();
                tile.accept(engineVisitor);
            }else{
                throw new IllegalEventException("Tile is not Present");
            }

            ArrayList<EngineTile> enginelist = engineVisitor.getList();

            if(enginelist.size() > 0){
                EngineTile engine = enginelist.getFirst();
                if(!engine.getDoublePower()){
                    throw new IllegalEventException("Engine Tile is not Double");
                }
            }else{
                throw new IllegalEventException("Engine Tile is not Selected");
            }
        }
        //controlla la lista di Battery
        int batteriesPresent = 0;
        for(List<Integer> selectedBatteries : event.batteries()){
            BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();
            int row = selectedBatteries.get(0);
            int column = selectedBatteries.get(1);
            int batteriesToUse = selectedBatteries.get(2);

            Optional<Tile> batteryOptional = ship.getTileOnFloorPlan(row,column);

            if(batteryOptional.isPresent()){
                Tile tile = batteryOptional.get();
                tile.accept(batteryVisitor);
            }else{
                throw new IllegalEventException("Tile is not Present");
            }

            ArrayList<BatteryTile> batterylist = batteryVisitor.getList();

            if(batterylist.size()!=0){
                BatteryTile battery = batterylist.getFirst();
                //controllo quantita di batteries
                if(battery.getSlotsFilled()-batteriesToUse<0){
                    throw new IllegalEventException("Batteries are not enough for selected BatteryTile");
                }
            }else{
                throw new IllegalEventException("Battery Tile is not Present");
            }


        }
        //aggiorno Engine e Battery
        for(List<Integer> selectedEngines : event.engines()){
            EngineTileVisitor engineVisitor = new EngineTileVisitor();
            int row = selectedEngines.get(0);
            int column = selectedEngines.get(1);

            Optional<Tile> engineOptional = ship.getTileOnFloorPlan(row,column);

            Tile tile = engineOptional.get();
            tile.accept(engineVisitor);

            ArrayList<EngineTile> enginelist = engineVisitor.getList();

            EngineTile engine = enginelist.getFirst();

            engine.setActiveState(true);
        }
        for(List<Integer> selectedBatteries : event.batteries()){
            BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();
            int row = selectedBatteries.get(0);
            int column = selectedBatteries.get(1);
            int batteriesToUse = selectedBatteries.get(2);

            Optional<Tile> batteryOptional = ship.getTileOnFloorPlan(row,column);

            Tile tile = batteryOptional.get();
            tile.accept(batteryVisitor);

            ArrayList<BatteryTile> batteryList = batteryVisitor.getList();

            BatteryTile battery = batteryList.getFirst();

            battery.removeBattery(batteriesToUse);

        }

    }
    /*
    <Pair<Pair<Integer,Integer>, Integer>
     //da rifare perchè non c'è piu Pair
    public record ActivateCannonsEvent(Player player, ArrayList<Pair<Integer, Integer>> cannons, ArrayList<Pair<Integer, Integer>> batteries) implements GameEvent
     */
    public static void handleEvent(ActivateCannonsEvent event) throws IllegalEventException {
        Ship ship = event.player().getShip();
        //controllo la lista di cannoni
        for(List<Integer> selectedTile : event.cannons()){
            CannonTileVisitor cannonVisitor = new CannonTileVisitor();
            int row = selectedTile.get(0);
            int column = selectedTile.get(1);

            Optional<Tile> cannonOptional = ship.getTileOnFloorPlan(row,column);

            if(cannonOptional.isPresent()){
                Tile tile = cannonOptional.get();
                tile.accept(cannonVisitor);
            }else{
                throw new IllegalEventException("ShieldTile is not present in cell");
            }

            ArrayList<CannonTile> listCannon = cannonVisitor.getList();

            if(listCannon.size()==0){
                CannonTile cannon = listCannon.getFirst();
                if(!cannon.getDoublePower()){
                    throw new IllegalEventException("Can't select Single Cannon");
                }
            }else{
                throw new IllegalEventException("Tile selected is not Cannon");
            }



        }
        //controllo la lista di batteries
        int batteriesPresent = 0;
        for(List<Integer> selectedBatteries : event.batteries()){
            BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();
            int row = selectedBatteries.get(0);
            int column = selectedBatteries.get(1);
            int batteriesToUse = selectedBatteries.get(2);

            Optional<Tile> batteryOptional = ship.getTileOnFloorPlan(row,column);

            if(batteryOptional.isPresent()){
                Tile tile = batteryOptional.get();
                tile.accept(batteryVisitor);
            }else{
                throw new IllegalEventException("Tile is not Present");
            }

            ArrayList<BatteryTile> batteryList = batteryVisitor.getList();

            if(batteryList.size()!=0){
                BatteryTile battery = batteryList.getFirst();
                //controllo quantita di batteries
                if(battery.getSlotsFilled()-batteriesToUse<0){
                    throw new IllegalEventException("Batteries are not enough for selected BatteryTile");
                }

            }else{
                throw new IllegalEventException("Battery Tile is not Present");
            }

        }

        //aggiorno tutti batteries e cannons
        for(List<Integer> selectedTile : event.cannons()){
            CannonTileVisitor cannonVisitor = new CannonTileVisitor();
            int row = selectedTile.get(0);
            int column = selectedTile.get(1);

            Optional<Tile> cannonOptional = ship.getTileOnFloorPlan(row,column);

            Tile tile = cannonOptional.get();
            tile.accept(cannonVisitor);

            ArrayList<CannonTile> listCannon = cannonVisitor.getList();

            CannonTile cannon = listCannon.getFirst();

            cannon.setActiveState(true);

        }
        for(List<Integer> selectedBatteries : event.batteries()){
            BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();
            int row = selectedBatteries.get(0);
            int column = selectedBatteries.get(1);
            int batteriesToUse = selectedBatteries.get(2);

            Optional<Tile> batteryOptional = ship.getTileOnFloorPlan(row,column);

            Tile tile = batteryOptional.get();
            tile.accept(batteryVisitor);

            ArrayList<BatteryTile> batteryList = batteryVisitor.getList();

            BatteryTile battery = batteryList.getFirst();

            battery.removeBattery(batteriesToUse);

        }
    }
    //da rifare perchè non c'è piu Pair
    /*
    public record ActivateShieldEvent(Pair<Integer,Integer> shield, Pair<Integer,Integer> battery) implements GameEvent
     */
    public static void handleEvent(ActivateShieldEvent event)throws IllegalEventException {
        Ship ship = event.player().getShip();

        int rowShield = event.shieldRow();
        int columnShield = event.shieldCol();

        int rowBattery = event.batteryRow();
        int columnBattery = event.batteryCol();

        ShieldTileVisitor shieldVisitor = new ShieldTileVisitor();
        BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();

        Optional<Tile> optionalShield = ship.getTileOnFloorPlan(rowShield,columnShield);
        Optional<Tile> optionalBattery = ship.getTileOnFloorPlan(rowBattery,columnBattery);

        if(optionalShield.isPresent()){
            Tile tile = optionalShield.get();
            tile.accept(shieldVisitor);
        }else{
            throw new IllegalEventException("ShieldTile is not present in cell");
        }

        if(optionalBattery.isPresent()){
            Tile tile2 = optionalBattery.get();
            tile2.accept(batteryVisitor);
        }else{
            throw new IllegalEventException("BatteryTile is not present in cell");
        }

        ArrayList<ShieldTile> listShield = shieldVisitor.getList();
        ArrayList<BatteryTile> listBattery = batteryVisitor.getList();

        if(listShield.size()==0){
            throw new IllegalEventException("Tile is not Shield");

        }else {
            ShieldTile shield = listShield.getFirst();
            if (listBattery.size() != 0) {
                BatteryTile battery = listBattery.getFirst();
                if (battery.getSlotsFilled() > 0) {
                    battery.removeBattery(1);
                    shield.setActiveState(true);
                } else {
                    throw new IllegalEventException("BatteryTile doesn't have enough batteries");
                }
            } else {
                throw new IllegalEventException("Tile is not Battery");
            }
        }
    }
    /*
         public record RemoveCargoEvent(Player player, int row, int col, Integer resource) implements GameEvent
    */
    public static void handleEvent(RemoveCargoEvent event)throws IllegalEventException {
        Ship ship = event.player().getShip();
        CargoTileVisitor cargoVisitor = new CargoTileVisitor();
        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.row(),event.col());
        if(optionalTile.isPresent()){
            Tile tile = optionalTile.get();
            tile.accept(cargoVisitor);
        }else{
            throw new IllegalEventException("Tile is not present");
        }

        ArrayList<CargoTile> list = cargoVisitor.getList();

        if(list.size()!=0){
            CargoTile cargoTile = list.getFirst();

            boolean present = false;
            List<Integer> listGoods = cargoTile.getTileContent();
            for(Integer good : listGoods){
                if(good == event.resource()){
                    present = true;
                }
            }
            if(present){
                cargoTile.removeBlock(event.resource());
            }else{
                throw new IllegalEventException("Selected cargoTile doesn't have selected Good");
            }
        }else{
            throw new IllegalEventException("Selected Tile is not CargoTile");
        }

    }
    /*
     public record AddCargoEvent(Player player, int row, int column, Integer resource) implements GameEvent {
    }
    */
    //implementare che l'utente cerca il cargo nella propria lista
    public static void handleEvent(AddCargoEvent event) throws IllegalEventException{
        Ship ship = event.player().getShip();
        CargoTileVisitor cargoVisitor = new CargoTileVisitor();
        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.row(),event.column());
        if(optionalTile.isPresent()){
            Tile tile = optionalTile.get();
            tile.accept(cargoVisitor);
        }else{
            throw new IllegalEventException("Tile is not present");
        }

        ArrayList<CargoTile> list = cargoVisitor.getList();

        if(list.size()==0){
            throw new IllegalEventException("Selected Tile is not CargoTile");
        }else {
            CargoTile cargoTile = list.getFirst();
            if (event.resource() == 4) {
                if (!cargoTile.fitsRed()) {
                    throw new IllegalEventException("CargoTile doesn't fit red(4)");
                }
            }
            //check if resource is in cargoFromCards
            ArrayList<Integer> cargoFromShip = ship.getCargoFromCards();
            if (!cargoFromShip.contains(event.resource())) {
                throw new IllegalEventException("Doesn't have specified resource on ship");
            }

            List<Integer> listGoods = cargoTile.getTileContent();

            if ((cargoTile.getSlotsNumber() - listGoods.size()) > 0) {
                cargoTile.setTileContent(event.resource());
            } else {
                throw new IllegalEventException("CargoTile is full");
            }
        }
    }
    /*
    public record SwitchCargoEvent(Player player, int prevRow, int prevCol, int nextRow, int nextCol, Integer resource) implements GameEvent
     */
    public static void handleEvent(SwitchCargoEvent event) {
        Ship ship = event.player().getShip();

        CargoTileVisitor cargoVisitor = new CargoTileVisitor();
        CargoTileVisitor cargoVisitor2 = new CargoTileVisitor();

        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.prevRow(),event.prevCol());
        Optional<Tile> optionalTile2 = ship.getTileOnFloorPlan(event.nextRow(),event.nextCol());

        if(optionalTile.isPresent()){
            Tile tile = optionalTile.get();
            tile.accept(cargoVisitor);
        }else{
            throw new IllegalEventException("Previous Tile is not present");
        }

        if(optionalTile.isPresent()){
            Tile tile2 = optionalTile2.get();
            tile2.accept(cargoVisitor2);
        }else{
            throw new IllegalEventException("Next Tile is not present");
        }

        ArrayList<CargoTile> list = cargoVisitor.getList();
        ArrayList<CargoTile> list2 = cargoVisitor2.getList();

        if(list.size()==0){
            throw new IllegalEventException("Previous Tile is not CargoTile");

        }else {
            CargoTile cargoTile = list.getFirst();


            if (list2.size() == 0) {
                throw new IllegalEventException("Next Tile is not CargoTile");

            } else {
                CargoTile cargoTile2 = list2.getFirst();


                boolean present = false;
                List<Integer> listGoods = cargoTile.getTileContent();
                List<Integer> listGoods2 = cargoTile2.getTileContent();

                for (Integer good : listGoods) {
                    if (good == event.resource()) {
                        present = true;
                    }
                }
                if (!present) {
                    throw new IllegalEventException("Prev cargoTile doesn't have selected Good");
                }

                if (event.resource() == 4) {
                    if (!cargoTile2.fitsRed()) {
                        throw new IllegalEventException("Next cargoTile doesn't fit red(4)");
                    }
                }

                if ((cargoTile2.getSlotsNumber() - listGoods2.size()) > 0) {
                    cargoTile.removeBlock(event.resource());
                    cargoTile2.setTileContent(event.resource());
                } else {
                    throw new IllegalEventException("Next CargoTile is full");
                }
            }
        }
    }

    /*
    public record EjectPeopleEvent(Player player, ArrayList<ArrayList<Integer>> people) implements GameEvent
     */
    //////aggiustare ALIEN --> CONTROLLA BENE COSA TI HO AGGIUNTO IN BIOADAPTORTILE
    public static void handleEvent(EjectPeopleEvent event) throws IllegalEventException {
        int counter = 0;
        Ship ship = event.player().getShip();
        CabinTileVisitor cabinTileVisitor = new CabinTileVisitor();
        CabinTile cabin = null;
        for(List<Integer> listOfParameters : event.people()){
            for(Integer parameter : listOfParameters){
                cabinTileVisitor = new CabinTileVisitor();
                int row=0;
                int column=0;
                int peopleInCabin=0;
                int peopleToLoose = 0;
                if(counter==0){
                    row=parameter;
                }
                if(counter==1){
                    column=parameter;
                }
                Optional<Tile> optionalTile = ship.getTileOnFloorPlan(row,column);

                if(optionalTile.isPresent()){
                    Tile tile = optionalTile.get();
                    tile.accept(cabinTileVisitor);
                }else{
                    throw new IllegalEventException("Selected Tile is not present");
                }

                //tile.accept(cabinTileVisitor);
                ArrayList<CabinTile> listCabin = cabinTileVisitor.getList();
                if(listCabin.size()!=0){
                    cabin = listCabin.getFirst();
                }else{
                    throw new IllegalEventException("Selected Tile is not cabin");
                }

                //controllo che il tile è cabinTile

                if(counter==2){
                    peopleToLoose=parameter;
                    peopleInCabin = 0;
                    if(cabin.getInhabitants()==CabinInhabitants.ONE || cabin.getInhabitants()==CabinInhabitants.ALIEN){
                        peopleInCabin++;
                    } else if (cabin.getInhabitants()==CabinInhabitants.TWO) {
                        peopleInCabin+=2;
                    }

                    if(peopleInCabin-peopleToLoose<0){
                        throw new IllegalEventException("Cabin doesn't have enough Inhabitants");
                    }

                    //controllo che posso Togliere il peopleToLoose


                }
                counter++;
            }
            counter=0;
        }


        //se andato a buon fine aggiorno tutti cabinTile
        for(List<Integer> listOfParameters : event.people()){
            for(Integer parameter : listOfParameters){
                cabinTileVisitor = new CabinTileVisitor();
                int row=listOfParameters.get(0);
                int column=listOfParameters.get(1);

                int peopleInCabin=0;
                int peopleToLoose = 0;
                if(counter==0){
                    row=parameter;

                }
                if(counter==1){
                    column=parameter;
                }
                Optional<Tile> optionalTile = ship.getTileOnFloorPlan(row,column);
                Tile tile = optionalTile.get();
                tile.accept(cabinTileVisitor);
                ArrayList<CabinTile> listCabin = cabinTileVisitor.getList();
                cabin = listCabin.getFirst();

                if(counter==2){
                    peopleToLoose=parameter;
                    if(cabin.getInhabitants()==CabinInhabitants.ONE){
                        cabin.updateInhabitants(CabinInhabitants.NONE);
                    } else if (cabin.getInhabitants()==CabinInhabitants.ALIEN) {
                        cabin.updateInhabitants(CabinInhabitants.NONE);
                        //gestire se Allieno e orange o purple;

                    } else if (cabin.getInhabitants()==CabinInhabitants.TWO) {
                        if(peopleToLoose==2){
                            cabin.updateInhabitants(CabinInhabitants.NONE);
                        }else{
                            cabin.updateInhabitants(CabinInhabitants.ONE);
                        }
                    }
                }
                counter++;
            }
            counter=0;
        }
    }
    /*
    public record GiveUpEvent(Player player)
     */
    //ipotizzo che il reference a traveDays in Ship diventa Null
    public static void handleEvent(GiveUpEvent event) {
        Ship ship = event.player().getShip();
        ship.setTravelDays(null);
    }

    public static void moveFoward(Ship ship, int days, Game game){

        int startposition = ship.getTravelDays();
        long overturned = 0;
        int finalPosition = startposition + days;
        for(int i=0; i<3; i++){
            int curFinalPosition = finalPosition;
            overturned = game.getListOfPlayers().stream().map(p -> p.getShip())
                    .filter(s -> s.getTravelDays() > startposition && s.getTravelDays() <= curFinalPosition)
                    .count();
            finalPosition = startposition + days + (int)overturned;
        }
        ship.setTravelDays(finalPosition);
    }

    //todo check go in spazio aperto, aggiungi al next con lo spegnimento
    //todo fai le variabili delle tile in player/ship e copia il metodo di dima
    public static void moveBackward(Ship ship, int days, Game game){

        int startposition = ship.getTravelDays();
        long overturned = 0;
        int finalPosition = startposition - days;
        for(int i=0; i<3; i++){
            int curFinalPosition = finalPosition;
            overturned = game.getListOfPlayers().stream().map(p -> p.getShip())
                    .filter(s -> s.getTravelDays() < startposition && s.getTravelDays() >= curFinalPosition)
                    .count();
            finalPosition = startposition - days - (int)overturned;
        }
        ship.setTravelDays(finalPosition);
    }
}

