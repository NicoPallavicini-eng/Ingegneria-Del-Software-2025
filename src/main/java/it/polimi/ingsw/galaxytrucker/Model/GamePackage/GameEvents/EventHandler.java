package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents;

import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventHandler implements Serializable {

    //ez skip
    public static void handleEvent(ChooseSubShipEvent event) {
        Ship ship = event.player().getShip();
        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.row(), event.col());

        BatteryTileVisitor batteryTileVisitor = new BatteryTileVisitor();
        BioadaptorTileVisitor bioadaptorTileVisitor = new BioadaptorTileVisitor();
        CabinTileVisitor cabinTileVisitor = new CabinTileVisitor();
        CannonTileVisitor cannonTileVisitor = new CannonTileVisitor();
        CargoTileVisitor cargoTileVisitor = new CargoTileVisitor();
        EngineTileVisitor engineTileVisitor = new EngineTileVisitor();
        ShieldTileVisitor shieldTileVisitor = new ShieldTileVisitor();


        if (!optionalTile.isPresent()) {
            throw new IllegalEventException("You chose an empty tile");
        }

        Tile tile = optionalTile.get();
        ArrayList<Tile> pieceOfShip = ship.checkFloorplanConnection(tile);
        //setto a null

        for (ArrayList<Tile> list : ship.getFloorplanArrayList()) {
            for (Tile t : list) {
                if (!pieceOfShip.contains(t)) {
                    t.accept(batteryTileVisitor);
                    t.accept(bioadaptorTileVisitor);
                    t.accept(cabinTileVisitor);
                    t.accept(cannonTileVisitor);
                    t.accept(cargoTileVisitor);
                    t.accept(engineTileVisitor);
                    t.accept(shieldTileVisitor);
                }
            }
        }
        //tolgo batteries
        ArrayList<BatteryTile> batteryList = batteryTileVisitor.getList();
        for (BatteryTile batteryTile : batteryList) {
            ship.setTileOnFloorPlan(ship.findTileOnFloorplanRow(batteryTile), ship.findTileOnFloorPlanColumn(batteryTile), null);
        }
        //tolgo Bioadaptors
        ArrayList<BioadaptorTile> bioadaptorList = bioadaptorTileVisitor.getList();
        for (BioadaptorTile bioadaptorTile : bioadaptorList) {
            CabinTileVisitor cabinNear = new CabinTileVisitor();
            ArrayList<Tile> list = ship.getAdiacentTiles(bioadaptorTile);
            for (Tile t : list) {
                tile.accept(cabinNear);
            }
            ArrayList<CabinTile> cabinList = cabinNear.getList();
            for (CabinTile cabinTile : cabinList) {
                if (bioadaptorTile.getColor() == AlienColor.ORANGE) {
                    cabinTile.removeOrangeAdaptors();
                } else {
                    cabinTile.removePinkAdaptors();
                }
            }
        }
        //tolgo Cabins
        ArrayList<CabinTile> cabinList = cabinTileVisitor.getList();
        for (CabinTile cabinTile : cabinList) {
            if (cabinTile.getInhabitants() == CabinInhabitants.ALIEN) {
                if (cabinTile.getAlienColor() == AlienColor.ORANGE) {
                    ship.setOrangeAlien(false);
                } else {
                    ship.setPurpleAlien(false);
                }
            }
            ship.setTileOnFloorPlan(ship.findTileOnFloorplanRow(cabinTile), ship.findTileOnFloorPlanColumn(cabinTile), null);
        }
        //tolgo Cannons
        ArrayList<CannonTile> cannonList = cannonTileVisitor.getList();
        for (CannonTile cannonTile : cannonList) {
            ship.setTileOnFloorPlan(ship.findTileOnFloorplanRow(cannonTile), ship.findTileOnFloorPlanColumn(cannonTile), null);
        }
        //tolgo Cargo
        ArrayList<CargoTile> cargoList = cargoTileVisitor.getList();
        for (CargoTile cargoTile : cargoList) {
            ship.setTileOnFloorPlan(ship.findTileOnFloorplanRow(cargoTile), ship.findTileOnFloorPlanColumn(cargoTile), null);
        }
        //tolgo Engine
        ArrayList<EngineTile> engineList = engineTileVisitor.getList();
        for (EngineTile engineTile : engineList) {
            ship.setTileOnFloorPlan(ship.findTileOnFloorplanRow(engineTile), ship.findTileOnFloorPlanColumn(engineTile), null);
        }
        //tolgo Shield
        ArrayList<ShieldTile> shieldList = shieldTileVisitor.getList();
        for (ShieldTile shieldTile : shieldList) {
            ship.setTileOnFloorPlan(ship.findTileOnFloorplanRow(shieldTile), ship.findTileOnFloorPlanColumn(shieldTile), null);
        }

    }

    /*
    public record ConnectEvent(String nickname, String IP) implements GameEvent
    */
    //istances a new player and adds it to the list of players in game
    // public static void handleEvent(ConnectEvent event, Game game){
    public static void handleEvent(ConnectEvent event, Game game) {
        boolean finished = false;
        Color color = Color.values()[game.getListOfPlayers().size()];

        Player playerNew = new Player(event.IP(), event.nickname(), color);
        Ship shipNew = playerNew.getShip();
        ArrayList<CabinTile> mainCabins = (ArrayList<CabinTile>) game.getMainCabins();
        for (CabinTile cabinTile : mainCabins) {
            if (cabinTile.getColor() == color) {
                shipNew.setTileOnFloorPlan(7-5, 7-4, cabinTile);
            }
        }
        game.addPlayer(playerNew);
    }

    /*
    public record DisconnectEvent(Player player) implements GameEvent
        removes it from the list of players and ends game
        to change if we implement disconnection resilience
     */
    //FATTO
    public static void handleEvent(DisconnectEvent event) {
        Player player = event.player();
        player.setOnlineStatus(false);
    }

    // evitabile
    //FATTO
    public static void handleEvent(PickUpTileEvent event, Game game) {
        TilePile pile = game.getTilePile();
        Tile tile = pile.getTilePile().get(event.index());
        if (tile == null) {
            throw new IllegalEventException("Tile is not present");
        }
        Ship ship = event.player().getShip();
        ship.setTileInHand(tile);
        tile.setFacingUp(true);
        tile.setChoosable(false);
        pile.getTilePile().set(event.index(), null);
    }
    /*
    public record RotateTileEvent(Player player, boolean right) implements GameEvent
     */

    //checks tile in hand not null and rotates
    //FATTO
    public static void handleEvent(RotateTileEvent event) {
        Ship ship = event.player().getShip();
        if (ship.getTileInHand() == null) {
            throw new IllegalEventException("You need to have a Tile in hand");
        }
        if (event.right().equals("right")) {
            ship.getTileInHand().rotate(Side.RIGHT);
        } else {
            ship.getTileInHand().rotate(Side.LEFT);
        }
    }

    //checks that it is not null
    //FATTO
    public static void handleEvent(PutDownTileEvent event, Game game) {
        Ship ship = event.player().getShip();
        if (ship.getTileInHand() == null) {
            throw new IllegalEventException("You need to place a Tile in hand before putting it back down");
        }
        List<Tile> pile = game.getTilePile().getTilePile();
        pile.set(pile.indexOf(null), ship.getTileInHand());
    }

    //checks correct tile and life support
    //FATTO
    public static void handleEvent(PlaceOrangeAlienEvent event) {
        Ship ship = event.player().getShip();
        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.row(), event.col());
        if (!optionalTile.isPresent()) {
            throw new IllegalEventException("Tile is not present");
        }

        Tile tile = optionalTile.get();
        CabinTileVisitor cabinTileVisitor = new CabinTileVisitor();
        tile.accept(cabinTileVisitor);
        if (cabinTileVisitor.getList().isEmpty()) {
            throw new IllegalEventException("You chose not a CabinTile");
        }
        CabinTile cabinTile = cabinTileVisitor.getList().getFirst();
        if (cabinTile.getOrange() <= 0) {
            throw new IllegalEventException("There is no Bioadaptors of OrangeType");
        }
        if (cabinTile.getInhabitants().equals(CabinInhabitants.ALIEN)) {
            throw new IllegalEventException("You already placed an alien in this CabinTile");
        }
        cabinTile.updateInhabitants(CabinInhabitants.ALIEN);
        cabinTile.setAlienColor(AlienColor.ORANGE);

    }

    //checks correct tile and life support
    //FATTO
    public static void handleEvent(PlacePurpleAlienEvent event) {
        Ship ship = event.player().getShip();
        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.row(), event.col());
        if (!optionalTile.isPresent()) {
            throw new IllegalEventException("Tile is not present");
        }

        Tile tile = optionalTile.get();
        CabinTileVisitor cabinTileVisitor = new CabinTileVisitor();
        tile.accept(cabinTileVisitor);
        if (cabinTileVisitor.getList().isEmpty()) {
            throw new IllegalEventException("You chose not a CabinTile");
        }
        CabinTile cabinTile = cabinTileVisitor.getList().getFirst();
        if (cabinTile.getPurple() <= 0) {
            throw new IllegalEventException("There is no Bioadaptors of PurpleType");
        }
        if (cabinTile.getInhabitants().equals(CabinInhabitants.ALIEN)) {
            throw new IllegalEventException("You already placed an alien in this CabinTile");
        }
        cabinTile.updateInhabitants(CabinInhabitants.ALIEN);
        cabinTile.setAlienColor(AlienColor.PURPLE);
    }

    //FATTO
    public static void handleEvent(PlaceTileEvent event) {
        Ship ship = event.player().getShip();
        if (ship.getTileInHand() == null) {
            throw new IllegalEventException("You need to place a Tile in hand");
        }
        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.row(), event.col());
        if (optionalTile.isPresent()) {
            throw new IllegalEventException("Can't place Tile because spot is occupied");
        }
        ship.setTileOnFloorPlan(event.row(), event.col(), ship.getTileInHand());
    }

    //checks for available spots and tile in hand not null
    //FATTO
    public static void handleEvent(ReserveTileEvent event) {
        Ship ship = event.player().getShip();
        if (ship.getTileInHand() == null) {
            throw new IllegalEventException("You need to place a Tile in hand");
        }
        if (ship.getReservedTiles().size() == 2) {
            throw new IllegalEventException("You can't reserve more than two tiles");
        }
        ship.getReservedTiles().add(ship.getTileInHand());
        ship.setTileInHand(null);
    }

    // gira
    //FATTO
    public static void handleEvent(FlipHourglassEvent event, Game game) {
        game.getHourglass().flip();
    }

    //FATTO
    public static void handleEvent(SetPositionEvent event, Game game) {
        int position = event.position();
        int place = 0;
        if (position == 1) {
            place = 6;
        } else if (position == 2) {
            place = 3;
        } else if (position == 3) {
            place = 1;
        } else {
            place = 0;
        }

        boolean present = false;
        for (Player player : game.getListOfActivePlayers()) {
            if (player.getShip().getTravelDays() == place) {
                present = true;
                break;
            }
        }
        if (!present) {
            event.player().getShip().setTravelDays(place);
            game.sortListOfPlayers();
        } else {
            throw new IllegalEventException("You can't your position is occupied");
        }
    }

    // picks up the last placed
    //FATTO
    public static void handleEvent(PickUpFromShipEvent event) {
        Ship ship = event.player().getShip();
        if (ship.getTileInHand() != null) {
            throw new IllegalEventException("You need to place a Tile in hand first");
        }
        if (ship.getLastPlacedTile() != null) {
            ship.setTileInHand(ship.getLastPlacedTile());
            ship.setTileOnFloorPlan(ship.findTileOnFloorplanRow(ship.getTileInHand()), ship.findTileOnFloorPlanColumn(ship.getTileInHand()), null);
        } else {
            throw new IllegalEventException("You need to have placed the tile before");
        }
    }

    //FATTO
    public static void handleEvent(PickUpReservedTileEvent event) {
        Ship ship = event.player().getShip();
        if(event.index() > ship.getReservedTiles().size() - 1) {
            throw new IllegalEventException("you have not saved a tile at index" + event.index());
        }
        Tile tile = ship.getReservedTiles().get(event.index());
        ship.setTileInHand(tile);
    }

    //todo da qui

    public static void handleEvent(ViewDeckEvent event) {
    }

    /*
    public record ChoosePlanetEvent(Player player, int planetIndex) implements GameEvent
    //il riferimento a Game
     */
    /*
    public record ActivateEnginesEvent(Player player, List<List<Integer>> engines, List<List<Integer>> batteries) implements GameEvent
     */
    public static void handleEvent(ActivateEnginesEvent event) throws IllegalEventException {
        Ship ship = event.player().getShip();
        //controllo la lista di Engines
        for (List<Integer> selectedEngines : event.engines()) {
            EngineTileVisitor engineVisitor = new EngineTileVisitor();
            int row = selectedEngines.get(0);
            int column = selectedEngines.get(1);

            Optional<Tile> engineOptional = ship.getTileOnFloorPlan(row, column);

            if (engineOptional.isPresent()) {
                Tile tile = engineOptional.get();
                tile.accept(engineVisitor);
            } else {
                throw new IllegalEventException("Tile is not Present");
            }

            ArrayList<EngineTile> enginelist = engineVisitor.getList();

            if (enginelist.size() > 0) {
                EngineTile engine = enginelist.getFirst();
                if (!engine.getDoublePower()) {
                    throw new IllegalEventException("Engine Tile is not Double");
                }
            } else {
                throw new IllegalEventException("Engine Tile is not Selected");
            }
        }
        //controlla la lista di Battery
        int batteriesPresent = 0;
        for (List<Integer> selectedBatteries : event.batteries()) {
            BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();
            int row = selectedBatteries.get(0);
            int column = selectedBatteries.get(1);
            int batteriesToUse = selectedBatteries.get(2);

            Optional<Tile> batteryOptional = ship.getTileOnFloorPlan(row, column);

            if (batteryOptional.isPresent()) {
                Tile tile = batteryOptional.get();
                tile.accept(batteryVisitor);
            } else {
                throw new IllegalEventException("Tile is not Present");
            }

            ArrayList<BatteryTile> batterylist = batteryVisitor.getList();

            if (batterylist.size() != 0) {
                BatteryTile battery = batterylist.getFirst();
                //controllo quantita di batteries
                if (battery.getSlotsFilled() - batteriesToUse < 0) {
                    throw new IllegalEventException("Batteries are not enough for selected BatteryTile");
                }
            } else {
                throw new IllegalEventException("Battery Tile is not Present");
            }


        }
        //aggiorno Engine e Battery
        for (List<Integer> selectedEngines : event.engines()) {
            EngineTileVisitor engineVisitor = new EngineTileVisitor();
            int row = selectedEngines.get(0);
            int column = selectedEngines.get(1);

            Optional<Tile> engineOptional = ship.getTileOnFloorPlan(row, column);

            Tile tile = engineOptional.get();
            tile.accept(engineVisitor);

            ArrayList<EngineTile> enginelist = engineVisitor.getList();

            EngineTile engine = enginelist.getFirst();

            engine.setActiveState(true);
        }
        for (List<Integer> selectedBatteries : event.batteries()) {
            BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();
            int row = selectedBatteries.get(0);
            int column = selectedBatteries.get(1);
            int batteriesToUse = selectedBatteries.get(2);

            Optional<Tile> batteryOptional = ship.getTileOnFloorPlan(row, column);

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
        for (List<Integer> selectedTile : event.cannons()) {
            CannonTileVisitor cannonVisitor = new CannonTileVisitor();
            int row = selectedTile.get(0);
            int column = selectedTile.get(1);

            Optional<Tile> cannonOptional = ship.getTileOnFloorPlan(row, column);

            if (cannonOptional.isPresent()) {
                Tile tile = cannonOptional.get();
                tile.accept(cannonVisitor);
            } else {
                throw new IllegalEventException("ShieldTile is not present in cell");
            }

            ArrayList<CannonTile> listCannon = cannonVisitor.getList();

            if (listCannon.size() == 0) {
                CannonTile cannon = listCannon.getFirst();
                if (!cannon.getDoublePower()) {
                    throw new IllegalEventException("Can't select Single Cannon");
                }
            } else {
                throw new IllegalEventException("Tile selected is not Cannon");
            }


        }
        //controllo la lista di batteries
        int batteriesPresent = 0;
        for (List<Integer> selectedBatteries : event.batteries()) {
            BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();
            int row = selectedBatteries.get(0);
            int column = selectedBatteries.get(1);
            int batteriesToUse = selectedBatteries.get(2);

            Optional<Tile> batteryOptional = ship.getTileOnFloorPlan(row, column);

            if (batteryOptional.isPresent()) {
                Tile tile = batteryOptional.get();
                tile.accept(batteryVisitor);
            } else {
                throw new IllegalEventException("Tile is not Present");
            }

            ArrayList<BatteryTile> batteryList = batteryVisitor.getList();

            if (batteryList.size() != 0) {
                BatteryTile battery = batteryList.getFirst();
                //controllo quantita di batteries
                if (battery.getSlotsFilled() - batteriesToUse < 0) {
                    throw new IllegalEventException("Batteries are not enough for selected BatteryTile");
                }

            } else {
                throw new IllegalEventException("Battery Tile is not Present");
            }

        }

        //aggiorno tutti batteries e cannons
        for (List<Integer> selectedTile : event.cannons()) {
            CannonTileVisitor cannonVisitor = new CannonTileVisitor();
            int row = selectedTile.get(0);
            int column = selectedTile.get(1);

            Optional<Tile> cannonOptional = ship.getTileOnFloorPlan(row, column);

            Tile tile = cannonOptional.get();
            tile.accept(cannonVisitor);

            ArrayList<CannonTile> listCannon = cannonVisitor.getList();

            CannonTile cannon = listCannon.getFirst();

            cannon.setActiveState(true);

        }
        for (List<Integer> selectedBatteries : event.batteries()) {
            BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();
            int row = selectedBatteries.get(0);
            int column = selectedBatteries.get(1);
            int batteriesToUse = selectedBatteries.get(2);

            Optional<Tile> batteryOptional = ship.getTileOnFloorPlan(row, column);

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
    public static void handleEvent(ActivateShieldEvent event) throws IllegalEventException {
        Ship ship = event.player().getShip();

        int rowShield = event.shieldRow();
        int columnShield = event.shieldCol();

        int rowBattery = event.batteryRow();
        int columnBattery = event.batteryCol();

        ShieldTileVisitor shieldVisitor = new ShieldTileVisitor();
        BatteryTileVisitor batteryVisitor = new BatteryTileVisitor();

        Optional<Tile> optionalShield = ship.getTileOnFloorPlan(rowShield, columnShield);
        Optional<Tile> optionalBattery = ship.getTileOnFloorPlan(rowBattery, columnBattery);

        if (optionalShield.isPresent()) {
            Tile tile = optionalShield.get();
            tile.accept(shieldVisitor);
        } else {
            throw new IllegalEventException("ShieldTile is not present in cell");
        }

        if (optionalBattery.isPresent()) {
            Tile tile2 = optionalBattery.get();
            tile2.accept(batteryVisitor);
        } else {
            throw new IllegalEventException("BatteryTile is not present in cell");
        }

        ArrayList<ShieldTile> listShield = shieldVisitor.getList();
        ArrayList<BatteryTile> listBattery = batteryVisitor.getList();

        if (listShield.size() == 0) {
            throw new IllegalEventException("Tile is not Shield");

        } else {
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

    //FATTO
    public static void handleEvent(RemoveCargoEvent event) throws IllegalEventException {
        Ship ship = event.player().getShip();
        CargoTileVisitor cargoVisitor = new CargoTileVisitor();
        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.row(), event.col());
        if (optionalTile.isPresent()) {
            Tile tile = optionalTile.get();
            tile.accept(cargoVisitor);
        } else {
            throw new IllegalEventException("Tile is not present");
        }

        ArrayList<CargoTile> list = cargoVisitor.getList();

        if (list.size() != 0) {
            CargoTile cargoTile = list.getFirst();

            boolean present = false;
            List<Integer> listGoods = cargoTile.getTileContent();
            for (Integer good : listGoods) {
                if (good == event.resource()) {
                    present = true;
                }
            }
            if (present) {
                cargoTile.removeBlock(event.resource());
            } else {
                throw new IllegalEventException("Selected cargoTile doesn't have selected Good");
            }
        } else {
            throw new IllegalEventException("Selected Tile is not CargoTile");
        }

    }

    //FATTO
    public static void handleEvent(AddCargoEvent event) throws IllegalEventException {
        Ship ship = event.player().getShip();
        CargoTileVisitor cargoVisitor = new CargoTileVisitor();
        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.row(), event.column());
        if (optionalTile.isPresent()) {
            Tile tile = optionalTile.get();
            tile.accept(cargoVisitor);
        } else {
            throw new IllegalEventException("Tile is not present");
        }

        ArrayList<CargoTile> list = cargoVisitor.getList();

        if (list.size() == 0) {
            throw new IllegalEventException("Selected Tile is not CargoTile");
        } else {
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

    //FATTO
    public static void handleEvent(SwitchCargoEvent event) {
        Ship ship = event.player().getShip();

        CargoTileVisitor cargoVisitor = new CargoTileVisitor();
        CargoTileVisitor cargoVisitor2 = new CargoTileVisitor();

        Optional<Tile> optionalTile = ship.getTileOnFloorPlan(event.prevRow(), event.prevCol());
        Optional<Tile> optionalTile2 = ship.getTileOnFloorPlan(event.nextRow(), event.nextCol());

        if (optionalTile.isPresent()) {
            Tile tile = optionalTile.get();
            tile.accept(cargoVisitor);
        } else {
            throw new IllegalEventException("Previous Tile is not present");
        }

        if (optionalTile.isPresent()) {
            Tile tile2 = optionalTile2.get();
            tile2.accept(cargoVisitor2);
        } else {
            throw new IllegalEventException("Next Tile is not present");
        }

        ArrayList<CargoTile> list = cargoVisitor.getList();
        ArrayList<CargoTile> list2 = cargoVisitor2.getList();

        if (list.size() == 0) {
            throw new IllegalEventException("Previous Tile is not CargoTile");

        } else {
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

    //FATTO
    public static void handleEvent(EjectPeopleEvent event) throws IllegalEventException {
        int counter = 0;
        Ship ship = event.player().getShip();
        CabinTileVisitor cabinTileVisitor = new CabinTileVisitor();
        CabinTile cabin = null;
        for (List<Integer> listOfParameters : event.people()) {
            for (Integer parameter : listOfParameters) {
                cabinTileVisitor = new CabinTileVisitor();
                int row = 0;
                int column = 0;
                int peopleInCabin = 0;
                int peopleToLoose = 0;
                if (counter == 0) {
                    row = parameter;
                }
                if (counter == 1) {
                    column = parameter;
                }
                Optional<Tile> optionalTile = ship.getTileOnFloorPlan(row, column);

                if (optionalTile.isPresent()) {
                    Tile tile = optionalTile.get();
                    tile.accept(cabinTileVisitor);
                } else {
                    throw new IllegalEventException("Selected Tile is not present");
                }

                //tile.accept(cabinTileVisitor);
                ArrayList<CabinTile> listCabin = cabinTileVisitor.getList();
                if (listCabin.size() != 0) {
                    cabin = listCabin.getFirst();
                } else {
                    throw new IllegalEventException("Selected Tile is not cabin");
                }

                //controllo che il tile è cabinTile

                if (counter == 2) {
                    peopleToLoose = parameter;
                    peopleInCabin = 0;
                    if (cabin.getInhabitants() == CabinInhabitants.ONE || cabin.getInhabitants() == CabinInhabitants.ALIEN) {
                        peopleInCabin++;
                    } else if (cabin.getInhabitants() == CabinInhabitants.TWO) {
                        peopleInCabin += 2;
                    }

                    if (peopleInCabin - peopleToLoose < 0) {
                        throw new IllegalEventException("Cabin doesn't have enough Inhabitants");
                    }

                    //controllo che posso Togliere il peopleToLoose


                }
                counter++;
            }
            counter = 0;
        }


        //se andato a buon fine aggiorno tutti cabinTile
        for (List<Integer> listOfParameters : event.people()) {
            for (Integer parameter : listOfParameters) {
                cabinTileVisitor = new CabinTileVisitor();
                int row = listOfParameters.get(0);
                int column = listOfParameters.get(1);

                int peopleInCabin = 0;
                int peopleToLoose = 0;
                if (counter == 0) {
                    row = parameter;

                }
                if (counter == 1) {
                    column = parameter;
                }
                Optional<Tile> optionalTile = ship.getTileOnFloorPlan(row, column);
                Tile tile = optionalTile.get();
                tile.accept(cabinTileVisitor);
                ArrayList<CabinTile> listCabin = cabinTileVisitor.getList();
                cabin = listCabin.getFirst();

                if (counter == 2) {
                    peopleToLoose = parameter;
                    if (cabin.getInhabitants() == CabinInhabitants.ONE) {
                        cabin.updateInhabitants(CabinInhabitants.NONE);
                    } else if (cabin.getInhabitants() == CabinInhabitants.ALIEN) {
                        cabin.updateInhabitants(CabinInhabitants.NONE);
                        AlienColor color = cabin.getAlienColor();
                        //gestire se Allieno e orange o purple;
                        if (color == AlienColor.ORANGE) {
                            ship.setOrangeAlien(false);
                        } else {
                            ship.setPurpleAlien(false);
                        }


                    } else if (cabin.getInhabitants() == CabinInhabitants.TWO) {
                        if (peopleToLoose == 2) {
                            cabin.updateInhabitants(CabinInhabitants.NONE);
                        } else {
                            cabin.updateInhabitants(CabinInhabitants.ONE);
                        }
                    }
                }
                counter++;
            }
            counter = 0;
        }
    }

    //FATTO
    public static void handleEvent(GiveUpEvent event) {
        Ship ship = event.player().getShip();
        ship.setTravelDays(null);
    }

    public static void moveForward(Ship ship, int days, Game game) {

        int startposition = ship.getTravelDays();
        long overturned = 0;
        int finalPosition = startposition + days;
        for (int i = 0; i < 3; i++) {
            int curFinalPosition = finalPosition;
            overturned = game.getListOfActivePlayers().stream().map(p -> p.getShip())
                    .filter(s -> s.getTravelDays() > startposition && s.getTravelDays() <= curFinalPosition)
                    .count();
            finalPosition = startposition + days + (int) overturned;
        }
        ship.setTravelDays(finalPosition);
    }

    //todo check go in spazio aperto, aggiungi al next con lo spegnimento
    //todo fai le variabili delle tile in player/ship e copia il metodo di dima
    public static void moveBackward(Ship ship, int days, Game game) {

        int startposition = ship.getTravelDays();
        long overturned = 0;
        int finalPosition = startposition - days;
        for (int i = 0; i < 3; i++) {
            int curFinalPosition = finalPosition;
            overturned = game.getListOfActivePlayers().stream().map(p -> p.getShip())
                    .filter(s -> s.getTravelDays() < startposition && s.getTravelDays() >= curFinalPosition)
                    .count();
            finalPosition = startposition - days - (int) overturned;
        }
        ship.setTravelDays(finalPosition);
    }

    public static void handleEvent(RemoveTileEvent event) {

    }

    public static void handleEvent(FlipHourglassEvent event) {

    }

    public static void checkGiveUp(Game game){
        game.sortListOfPlayers();
        int maxDays = game.getListOfActivePlayers().getFirst().getShip().getTravelDays();
        for(Ship ship : game.getListOfActivePlayers().stream().map(Player::getShip).toList()){
            if(ship.getTravelDays() + game.getLapLenght() < maxDays){
                ship.setTravelDays(null);
                break;
            }
            List<CabinInhabitants> humans = ship.getListOfCabin().stream()
                    .map(CabinTile::getInhabitants)
                    .filter(i -> i != CabinInhabitants.NONE && i != CabinInhabitants.ALIEN)
                    .toList();
            if (!humans.isEmpty()) {
                ship.setTravelDays(null);
            }
        }
    }
}

