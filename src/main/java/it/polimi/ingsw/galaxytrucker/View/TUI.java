package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;
import java.util.List;

public class TUI {
    private Game game;
    private String nickname;


    public void start(){

    }

    public void viewLeaderboard(Game game){
        System.out.println("LeaderBoard: ");
        int i = 1;
        for (Player player : game.getListOfPlayers()) {
            System.out.println(i +": " + player.getNickname() + ", " + player.getShip().getColor() +
                    ": " + player.getShip().getTravelDays() + " Travel Days\n");
            i++;
        }
    }

    public void viewTilePile(Game game){
        this.game = game;
        TilePile tilePile = game.getTilePile();
        List<String> upperRow = new ArrayList<>();
        List<String> middleRow = new ArrayList<>();
        List<String> lowerRow = new ArrayList<>();
        int i = 0;
        System.out.println("Tile pile: ");
        List<Tile> pile = tilePile.getTilePile();
        printHeaders();
        for (Tile tile : pile) {
            if (i%16 == 0){
                int ii = i++;
                upperRow.add("╭─────╮ ");
                middleRow.add("│  " + ii + "  │ ");
                lowerRow.add("╰─────╯ ");
            }
            List<List<String>> allRow = buildTile(tile);
            upperRow.addAll(allRow.get(0));
            middleRow.addAll(allRow.get(1));
            lowerRow.addAll(allRow.get(2));
            i++;
        }
        for (int j =0; j<upperRow.size(); j++){
            if(j%16 == 0){
                System.out.println();
            }
            System.out.print(upperRow.get(j));
            System.out.print(middleRow.get(j));
            System.out.print(lowerRow.get(j));
        }
    }

    public void printShips(Game game){
        this.game = game;
        for (Player player : game.getListOfPlayers()) {
            System.out.println(nickname + "'s ship: \n");
            printMyShip(game, player.getNickname());
            System.out.println();
        }
    }

    public void printMyShip(Game game, String nickname) {
        this.game = game;
        ArrayList<ArrayList<Tile>> shipList;
        Ship ship = null;
        for (Player p : game.getListOfPlayers()) {
            if (p.getNickname().equals(nickname)) {
                ship = p.getShip();
            }
        }
        shipList = ship.getFloorplanArrayList();
        List<String> upperRow = new ArrayList<>();
        List<String> middleRow = new ArrayList<>();
        List<String> lowerRow = new ArrayList<>();
        int i = 5;
        printShipHeaders();
        for (ArrayList<Tile> row : shipList) {
            upperRow.add("╭─────╮ ");
            middleRow.add("│  " + i + "  │ ");
            lowerRow.add("╰─────╯ ");
            for (Tile tile : row) {
                List<List<String>> allRow = buildTile(tile);
                upperRow.addAll(allRow.get(0));
                middleRow.addAll(allRow.get(1));
                lowerRow.addAll(allRow.get(2));
            }
            upperRow.add("╭─────╮ ");
            middleRow.add("│     │ ");
            lowerRow.add("╰─────╯ ");
            i++;
            for (int j = 0; j < upperRow.size(); j++) {
                System.out.print(upperRow.get(j));
                System.out.print(middleRow.get(j));
                System.out.print(lowerRow.get(j));
            }
        }
        printShipFooters();
        System.out.println();
        printReservedTiles(ship);
        System.out.println("Crew: " + ship.getNumberOfInhabitants() + " \n");
        int humans = 0;
        int aliens = 0;
        AlienColor color = null;
        List <CabinTile> cabinsList = ship.getListOfCabin();
        for (CabinTile cabin : cabinsList) {
            CabinInhabitants n = cabin.getInhabitants();
            if (n == CabinInhabitants.TWO) {
                humans += 2;
            } else if (n == CabinInhabitants.ONE) {
                humans += 1;
            } else if (n == CabinInhabitants.ALIEN) {
                aliens += 1;
                color = cabin.getAlienColor();
            }
        }
        System.out.println("   Humans: " + humans + ", \n");
        if (aliens == 2) {
            System.out.println("   Aliens: 2 - both colors \n");
        } else if (aliens == 1) {
            if (color == AlienColor.ORANGE) {
                System.out.println("   Aliens: 1 - orange \n");
            } else if (color == AlienColor.PURPLE) {
                System.out.println("   Aliens: 1 - purple \n");
            }
        } else if (aliens == 0) {
            System.out.println("   Aliens: 0 \n");
        }
        System.out.println("Batteries: " + ship.getBatteries() + "\n");
        int normCargo = 0;
        int redCargo = 0;
        List <CargoTile> cargoList = ship.getListOfCargo();
        for (CargoTile cargo : cargoList) {
            if (cargo.fitsRed()) {
                redCargo += cargo.getSlotsNumber();
            } else {
                normCargo += cargo.getSlotsNumber();
            }
        }
        int allCargo = redCargo + normCargo;
        System.out.println("Cargo: " + allCargo + " \n" +
                "   Normal: " + normCargo + ", \n" +
                "   Red: " + redCargo + " \n");
        int firepower = 0;
        List <CannonTile> cannonList = ship.getListOfFirepower();
        for (CannonTile cannon : cannonList) {
            if (cannon.getDoublePower()) {
                firepower += 2;
            } else {
                firepower += 1;
            }
        }
        System.out.println("Firepower: " + firepower + " \n");
        int enginePower = 0;
        List <EngineTile> engineList = ship.getListOfEngine();
        for (EngineTile engine : engineList) {
            if (engine.getDoublePower()) {
                enginePower += 2;
            } else {
                enginePower += 1;
            }
        }
        System.out.println("Engine Power: " + enginePower + " \n");
        boolean north = false;
        boolean east = false;
        boolean south = false;
        boolean west = false;
        List <ShieldTile> shieldList = ship.getListOfShield();
        for (ShieldTile shield : shieldList) {
            if (shield.getOrientation() == ShieldOrientation.NORTHEAST) {
                north = true;
                east = true;
            } else if (shield.getOrientation() == ShieldOrientation.SOUTHEAST) {
                east = true;
                south = true;
            } else if (shield.getOrientation() == ShieldOrientation.SOUTHWEST) {
                south = true;
                west = true;
            } else if (shield.getOrientation() == ShieldOrientation.NORTHWEST) {
                west = true;
                north = true;
            }
        }
        System.out.println("Shielded sides: ");
        if (north) {
            System.out.println("N ");
        }
        if (east) {
            System.out.println("E ");
        }
        if (south) {
            System.out.println("S ");
        }
        if (west) {
            System.out.println("W ");
        }
        if (!north && !east && !south) {
            // west is automatically false because north & south are
            System.out.println("none \n");
        } else {
            System.out.println("\n");
        }
        System.out.println();
    }

    private void printReservedTiles(Ship ship) {
        List <Tile> reserved = ship.getReservedTiles();
        if (!reserved.isEmpty()) {
            System.out.println("Reserved Tiles: \n");
            List<String> upperRow = new ArrayList<>();
            List<String> middleRow = new ArrayList<>();
            List<String> lowerRow = new ArrayList<>();
            for (Tile tile : reserved) {
                List<List<String>> allRow = buildTile(tile);
                upperRow.addAll(allRow.get(0));
                middleRow.addAll(allRow.get(1));
                lowerRow.addAll(allRow.get(2));
            }
            for (int j = 0; j < upperRow.size(); j++) {
                System.out.print(upperRow.get(j));
                System.out.print(middleRow.get(j));
                System.out.print(lowerRow.get(j));
            }
            System.out.println();
        }
    }

    private List<List<String>> buildTile(Tile tile){
        List<String> upperRow = new ArrayList<>();
        List<String> middleRow = new ArrayList<>();
        List<String> lowerRow = new ArrayList<>();
        List <List <String>> allRow = new ArrayList<>();
        if (tile != null){
            if(tile.getUpsideDown()){
                if(tile instanceof BioadaptorTile){
                    BioadaptorTile bioadaptorTile = (BioadaptorTile) tile;
                    String type = "     ";
                    if(bioadaptorTile.getAlienColor() == AlienColor.ORANGE){
                        type = " O ⚘ ";
                    }
                    else if(bioadaptorTile.getAlienColor() == AlienColor.PURPLE){
                        type = " P ⚘ ";
                    }
                    List<ConnectorType> connectors = bioadaptorTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    middleRow.add(strConnectors.get(1)+type+strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof BatteryTile){
                    BatteryTile batteryTile = (BatteryTile) tile;
                    String type = "     ";
                    if(batteryTile.getSlotsNumber() == 2){
                        type = " 2 § ";
                    }
                    else if (batteryTile.getSlotsNumber() == 3){
                        type = " 3 § ";
                    }
                    List<ConnectorType> connectors = batteryTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    middleRow.add(strConnectors.get(1)+type+strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));

                }
                else if(tile instanceof CabinTile){
                    CabinTile cabinTile = (CabinTile) tile;
                    String type;
                    if (cabinTile.isMainCapsule()) { // in ship this is used
                        type = "  ⌂  ";
                    } else {
                        type = "  ⚲  ";
                    }
                    List<ConnectorType> connectors = cabinTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    middleRow.add(strConnectors.get(1)+type+strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof CargoTile){
                    CargoTile cargoTile = (CargoTile) tile;
                    String type = "     ";
                    if (cargoTile.fitsRed()){
                        if (cargoTile.getSlotsNumber() == 1) {
                            type = " 1 ■ ";
                        } else if (cargoTile.getSlotsNumber() == 2) {
                            type = " 2 ■ ";
                        }
                    } else {
                        if (cargoTile.getSlotsNumber() == 2) {
                            type = " 2 □ ";
                        } else if (cargoTile.getSlotsNumber() == 3) {
                            type = " 3 □ ";
                        }
                    }
                    List<ConnectorType> connectors = cargoTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    middleRow.add(strConnectors.get(1)+type+strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof EngineTile){
                    EngineTile engineTile = (EngineTile) tile;
                    String type;
                    if (engineTile.getDoublePower()){
                        type = " 2 ¤ ";
                    } else {
                        type = " 1 ¤ ";
                    }
                    List<ConnectorType> connectors = engineTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    middleRow.add(strConnectors.get(1)+type+strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof ShieldTile){
                    ShieldTile shieldTile = (ShieldTile) tile;
                    String type = "     ";
                    if (shieldTile.getOrientation() == ShieldOrientation.NORTHWEST){
                        type = " NW# ";
                    }
                    else if (shieldTile.getOrientation() == ShieldOrientation.NORTHEAST){
                        type = " NE# ";
                    }
                    else if (shieldTile.getOrientation() == ShieldOrientation.SOUTHWEST){
                        type = " SW# ";
                    }
                    else if (shieldTile.getOrientation() == ShieldOrientation.SOUTHEAST){
                        type = " SE# ";
                    }
                    List<ConnectorType> connectors = shieldTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    middleRow.add(strConnectors.get(1)+type+strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof CannonTile){
                    CannonTile cannonTile = (CannonTile) tile;
                    String type;
                    if(cannonTile.getDoublePower()){
                        type = " 2 + ";
                    } else {
                        type = " 1 + ";
                    }
                    List<ConnectorType> connectors = cannonTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    middleRow.add(strConnectors.get(1)+type+strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));
                }
            }
            else {
                upperRow.add("╭─────╮ ");
                middleRow.add("│     │ ");
                lowerRow.add("╰─────╯ ");
            }
        }
        else{
            upperRow.add("       ");
            middleRow.add("       ");
            lowerRow.add("       ");
        }
        allRow.add(upperRow);
        allRow.add(middleRow);
        allRow.add(lowerRow);

        return allRow;
    }

    public static void printHelpMessage(){
/*
        System.out.println(
                "╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮\n" +
                "-  1  -  -  2  -  -  3  -  -  4  -  -  5  -  -  6  -  -  7  -  -  8  -  -  9  -  - 1 0 -  - 1 1 -  - 1 2 -  - 1 3 -  - 1 4 -  - 1 5 -  - 1 6 -\n" +
                "╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯"
        );
        System.out.println(
                "╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮\n" +
                "-  1  -  -  2  -  -  3  -  -  4  -  -  5  -  -  6  -  -  7  -  -  8  -  -  9  -  - 1 0 -  - 1 1 -  - 1 2 -  - 1 3 -  - 1 4 -  - 1 5 -  - 1 6 -\n" +
                "╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯"
        );*/
        for (int i = 0; i<150; i++){
            if (i == 50){
                System.out.println();
            }
            System.out.print("╭─────╮");

        }
/*
        System.out.println(
                "Help message: \n" +
                        "Every command must be preceded by a slash (/) and could require parameters divided by a comma (,)\n" +
                        "Some commands require two set of parameters that must be divided by a semicolon (;)\n" +
                        "Example: /command set1param1, set1param2; set2param1, set2param2\n" +
                        "Available commands: \n"+
                        "\nALWAYS AVAILABLE\n" +
                        "/help - Show this help message\n" +
                        "/disconnect - If you are connected to the game, disconnect from it. No parameters needed.\n" +
                        "\nPRE BUILDING PHASE\n" +
                        "/connect - Connect to the game. Require the nickname for the game.\n" +
                        "/setnumberofplayers - Set the max number of player only if you are the host. Require the number of players.\n" +
                        "\nBUILDING PHASE\n"+
                        "/pickuptile - Pick up a tile from the tile pile. Require the row and column of the tile in the pile.\n" +
                        "/putdowntile - Put down the tile that you have last picked up. No parameters needed.\n" +
                        "/placetile - Place the tile that you have last picked up on your ship. Require the row and column where you want to to place your tile in the ship.\n" +
                        "/reservetile - Reserve the tile that you have last picked up. Requires the position (index) where tu put the tile (can only be 1 or 2).\n" +
                        "/rotate - Rotate the tile that you last placed on your ship. Requires the side of the rotation, either LEFT or RIGHT.\n" +
                        "/pickupfromship - Pick up the last tile you placed on your ship. No parameters needed.\n" +
                        "/pikcupreservedtile - Pick up one of the tiles in the reserved tiles. Requires the position (index) of the tile you want to pick up.\n" +
                        "/fliphourglass - Flip the hourglass. No parameters needed.\n" +
                        "/setposition - Set your ship into the board. Requires the place where you want to put your ship.\n" +
                        "/placeorangealien - Place the orange alien on your ship. Requires the row and column where you want to place the alien.\n" +
                        "/placepurplealien - Place the purple alien on your ship. Requires the row and column where you want to place the alien.\n" +
                        "/removetile - Remove a tile from your ship. Requires the row and column of the tile you want to remove.\n" +
                        "/viewships - View the ships of all players. No parameters needed.\n" +
                        "/viewmyship - View your ship. No parameters needed.\n" +
                        "\nTRAVELLING PHASE\n" +
                        "/viewleaerboard - View the leaderboard. No parameters needed.\n" +
                        "/activateengines - Activate double engines. Requires two sets of parameters. The first one with the position (row and column) of the engines and the second one with the position of the battery tile and how many tile to take from it.\n" +
                        "/activatecannons - Activate double cannons. Requires two sets of parameters. The first one with the position (row and column) of the cannons and the second one with the position of the battery tile and how many tile to take from it.\n" +
                        "/activateshield - Activate one shield. Requires four parameters, the positions of the shield and the battery." +
                        "/removecargo - Remove a cargo from your ship. Requires the position of the cargo and the value of the cargo to be removed.\n" +
                        "/addcargo - Add a cargo to your ship. Requires the position of the cargo and the value of the cargo to be added.\n" +
                        "/switchcargo - Switch a cargo with another one. Requires two set of parameters. The first one with the position and the value of the first cargo and the second one with the position of the second cargo.\n" +
                        "/ejectpeople - Eject a number of people from your ship. Requires the position of the cabin from the people will be ejected and the number of people to be ejected.\n" +
                        "/giveup - Give up the game. No parameters needed.\n" +
                        "/viewinvetory - View your inventory. No parameters needed.\n" +
                        "/choosesubship - Choose a subship in case of a destruction of part of your ship. Requires the position of a random tile in the subship that you want to keep.\n" +
                        "/chooseplanet - Choose a planet when the planet card is revealed. Requires the index of the planet you want to choose.\n" +
                        "/nochoice - Declares that you won't do any choice. No parameters needed.\n" +
                        "/done - Declare that you are done with your action. No parameters needed.\n" +
                        "\nEND GAME PHASE\n" +
                        "/claimreward - Claim the end of the game reward. No parameters needed.\n"
        );
 */
    }

    private void printVoidTile(int i,int j){
        String voidTile = "       ";
        System.out.print(voidTile);
    }

    private void printHeaders(){
        System.out.println(
                "╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮\n" +
                "│     │ │  1  │ │  2  │ │  3  │ │  4  │ │  5  │ │  6  │ │  7  │ │  8  │ │  9  │ │ 1 0 │ │ 1 1 │ │ 1 2 │ │ 1 3 │ │ 1 4 │ │ 1 5 │ │ 1 6 │\n" +
                "╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯"
        );
    }

    private void printShipHeaders(){
        System.out.println(
                "╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮\n" +
                "│     │ │  4  │ │  5  │ │  6  │ │  7  │ │  8  │ │  9  │ │ 1 0 │ │     │\n" +
                "╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯"
        );
    }

    private void printShipFooters(){
        System.out.println(
                "╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮ ╭─────╮\n" +
                "│     │ │     │ │     │ │     │ │     │ │     │ │     │ │     │ │     │\n" +
                "╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯ ╰─────╯"
        );
    }

    private List<String> checkConnectors(List<ConnectorType> connectors){
        List<String> connectorList = new ArrayList<String>();
        ConnectorType north = connectors.get(0);
        ConnectorType west = connectors.get(1);
        ConnectorType south = connectors.get(2);
        ConnectorType east = connectors.get(3);

        if (north == ConnectorType.NONE){
            connectorList.add("╭─────╮ ");
        }
        else if (north == ConnectorType.SINGLE){
            connectorList.add("╭─ | ─╮ ");
        }
        else if (north == ConnectorType.DOUBLE){
            connectorList.add("╭─| |─╮ ");
        }
        else if (north == ConnectorType.UNIVERSAL){
            connectorList.add("╭─|||─╮ ");
        }
        else if (north == ConnectorType.CANNON_SINGLE){
            connectorList.add("╭─ ↑ ─╮ ");
        }
        else if (north == ConnectorType.CANNON_DOUBLE){
            connectorList.add("╭─↑ ↑─╮ ");
        }
        else if (north == ConnectorType.ENGINE_SINGLE){
            connectorList.add("╭─ V ─╮ ");
        }
        else if (north == ConnectorType.ENGINE_DOUBLE){
            connectorList.add("╭─V V─╮ ");
        }

        else if (west == ConnectorType.NONE){
            connectorList.add("│");
        }
        else if (west == ConnectorType.SINGLE){
            connectorList.add("-");
        }
        else if (west == ConnectorType.DOUBLE){
            connectorList.add("=");
        }
        else if (west == ConnectorType.UNIVERSAL){
            connectorList.add("≡");
        }
        else if (west == ConnectorType.CANNON_SINGLE){
            connectorList.add("←");
        }
        else if (west == ConnectorType.CANNON_DOUBLE){
            connectorList.add("⇇");
        }
        else if (west == ConnectorType.ENGINE_SINGLE){
            connectorList.add(">");
        }
        else if (west == ConnectorType.ENGINE_DOUBLE){
            connectorList.add("≥");
        }

        else if (south == ConnectorType.NONE){
            connectorList.add("╰─────╯ ");
        }
        else if (south == ConnectorType.SINGLE){
            connectorList.add("╰─ | ─╯ ");
        }
        else if (south == ConnectorType.DOUBLE){
            connectorList.add("╰─| |─╯ ");
        }
        else if (south == ConnectorType.UNIVERSAL) {
            connectorList.add("╰─|||─╯ ");
        }
        else if (south == ConnectorType.CANNON_SINGLE){
            connectorList.add("╰─ ↓ ─╯ ");
        }
        else if (south == ConnectorType.CANNON_DOUBLE){
            connectorList.add("╰─↓ ↓─╯ ");
        }
        else if (south == ConnectorType.ENGINE_SINGLE){
            connectorList.add("╰─ V ─╯ ");
        }
        else if (south == ConnectorType.ENGINE_DOUBLE){
            connectorList.add("╰─V V─╯ ");
        }

        else if (east == ConnectorType.NONE){
            connectorList.add("│ ");
        }
        else if (east == ConnectorType.SINGLE){
            connectorList.add("- ");
        }
        else if (east == ConnectorType.DOUBLE){
            connectorList.add("= ");
        }
        else if (east == ConnectorType.UNIVERSAL){
            connectorList.add("≡ ");
        }
        else if (east == ConnectorType.CANNON_SINGLE){
            connectorList.add("→ ");
        }
        else if (east == ConnectorType.CANNON_DOUBLE){
            connectorList.add("⇉ ");
        }
        else if (east == ConnectorType.ENGINE_SINGLE){
            connectorList.add("< ");
        }
        else if (east == ConnectorType.ENGINE_DOUBLE){
            connectorList.add("≤ ");
        }
        return connectorList;
    }

    public void printTile(Tile tile){
        List<List<String>> allRow = buildTile(tile);
        List<String> upperRow = allRow.get(0);
        List<String> middleRow = allRow.get(1);
        List<String> lowerRow = allRow.get(2);
        for (int j =0; j<upperRow.size(); j++){
            System.out.print(upperRow.get(j));
            System.out.print(middleRow.get(j));
            System.out.print(lowerRow.get(j));
        }
    }

    public void printCurrentCard(Card card){
        System.out.println("Current card: \n");
        checkCard(card);
    }

    public void printCurrentCard(Game game){
        System.out.println("Current card: \n");
        GameState gameState = game.getGameState();
        if (gameState instanceof TravellingState){
            TravellingState travellingState = (TravellingState) gameState;
            Card currentCard = travellingState.getCurrentCard();
            checkCard(currentCard);
        }
        else{
            System.out.println("No current card");
        }

    }

    private void checkCard(Card card){
        if (card != null){
            if (card instanceof CombatZoneCard) { // TODO decide if we want to print the affected player's nickname or not
                System.out.println("Combat Zone Card \n" +
                        "Days to lose: " + ((CombatZoneCard) card).getDaysLost() + " \n");
                if (card instanceof CombatZoneCardL) {
                    System.out.println("Crew lost: " + ((CombatZoneCard) card).getCrewLost() + " \n");
                } else if (card instanceof CombatZoneCardNotL) {
                    System.out.println("Cargo lost: " + ((CombatZoneCard) card).getCargoLost() + " \n");
                }
                List <Cannonball> balls = ((CombatZoneCard) card).getCannonballList();
                printBalls(balls);
            }
            else if (card instanceof EpidemicCard){
                System.out.println("Epidemic Card \n");
            }
            else if (card instanceof MeteorsCard){
                System.out.println("Meteors Card: \n");
                List <Meteor> balls = ((MeteorsCard) card).getMeteorsList();
                System.out.println("Meteors: Tot number = " + balls.size() + "\n");
                int i = 1;
                for (Meteor ball : balls) {
                    System.out.println("   " + i + ": ");
                    if (ball.bigMeteor()) {
                        System.out.println("Size = Big, ");
                    } else {
                        System.out.println("Size = Small, ");
                    }
                    System.out.println("Direction = " + ball.direction() + "\n");
                    i++;
                }
            }
            else if (card instanceof OpenSpaceCard){
                System.out.println("Open Space Card \n");
            }
            else if(card instanceof PiratesCard){
                System.out.println("Pirates Card: \n" +
                        "Firepower: " + ((PiratesCard) card).getFirepower() + " \n" +
                        "Credits: " + ((PiratesCard) card).getCredits() + " \n" +
                        "Days to lose: " + ((PiratesCard) card).getDaysToLose() + " \n");
                List <Cannonball> balls = ((PiratesCard) card).getCannonballList();
                printBalls(balls);
            }
            else if (card instanceof PlanetsCard){
                List <Planet> planets = ((PlanetsCard) card).getPlanetsList();
                int i = 1;
                List <String> planetBlocks = new ArrayList<>();
                for (Planet planet : planets){
                    planetBlocks.add("Planet " + i + " \n");
                    List <Integer> blocks = planet.getBlocks();
                    List <Integer> blocks1 = new ArrayList<>();
                    List <Integer> blocks2 = new ArrayList<>();
                    List <Integer> blocks3 = new ArrayList<>();
                    List <Integer> blocks4 = new ArrayList<>();
                    for (int j = 0; j < blocks.size(); j++) {
                        if (blocks.get(j) == 1) {
                            blocks1.add(j);
                        } else if (blocks.get(j) == 2) {
                            blocks2.add(j);
                        } else if (blocks.get(j) == 3) {
                            blocks3.add(j);
                        } else if (blocks.get(j) == 4) {
                            blocks4.add(j);
                        }
                    }
                    int size = blocks1.size() + blocks2.size() + blocks3.size() + blocks4.size();
                    planetBlocks.add("Blocks: Tot value = " + size + "\n" +
                            "   Blue = " + blocks1.size() + ",\n" +
                            "   Green = " + blocks2.size() + ",\n" +
                            "   Yellow = " + blocks3.size() + ",\n" +
                            "   Red = " + blocks4.size() + "\n");
                }
                System.out.println("Planets Card: \n" + planetBlocks);
            }
            else if (card instanceof ShipCard){
                System.out.println("Ship Card: \n" +
                        "Credits: " + ((ShipCard) card).getCredits() + " \n" +
                        "Crew lost: " + ((ShipCard) card).getCrewNumberLost() + " \n" +
                        "Days to lose: " + ((ShipCard) card).getDaysToLose() + "\n");
            }
            else if (card instanceof SlaversCard){
                System.out.println("Slavers Card: \n"+
                        "Firepower: " + ((SlaversCard) card).getFirepower() + "\n"+
                        "Credits: " + ((SlaversCard) card).getNumberOfCredits() + "\n"+
                        "Crew lost: " + ((SlaversCard) card).getNumberOfCrewLost() + "\n"+
                        "Days to lose: " + ((SlaversCard) card).getNumberOfDaysToLose() + "\n");
            }
            else if (card instanceof SmugglersCard){
                List<Integer> blocks = ((SmugglersCard)card).getBlocksList();
                System.out.println("Smugglers Card: \n" +
                        "Firepower: " + ((SmugglersCard) card).getFirepower() + "\n");
                printBlocks(blocks);
                System.out.println("Days to lose: " + ((SmugglersCard) card).getDaysToLose() + "\n");
            }
            else if (card instanceof StardustCard){
                System.out.println("Stardust Card \n");
            }
            else if (card instanceof StationCard){
                List<Integer> blocks = ((StationCard)card).getBlockList();
                System.out.println("Station Card: \n"+
                        "Crew needed: " + ((StationCard) card).getCrewNumberNeeded() + "\n");
                printBlocks(blocks);
                System.out.println("Days to lose: " + ((StationCard) card).getDaysToLose() + " \n");
            }
        }
    }

    private void printBlocks(List<Integer> blocks) {
        List <Integer> blocks1 = new ArrayList<>();
        List <Integer> blocks2 = new ArrayList<>();
        List <Integer> blocks3 = new ArrayList<>();
        List <Integer> blocks4 = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) == 1) {
                blocks1.add(i);
            } else if (blocks.get(i) == 2) {
                blocks2.add(i);
            } else if (blocks.get(i) == 3) {
                blocks3.add(i);
            } else if (blocks.get(i) == 4) {
                blocks4.add(i);
            }
        }
        int size = blocks1.size() + blocks2.size() + blocks3.size() + blocks4.size();
        System.out.print("Blocks: Tot value = " + size + "\n" +
                "   Blue = " + blocks1.size() + ",\n" +
                "   Green = " + blocks2.size() + ",\n" +
                "   Yellow = " + blocks3.size() + ",\n" +
                "   Red = " + blocks4.size() + "\n");
    }

    private void printBalls(List<Cannonball> balls) {
        System.out.println("Cannonballs: Tot number = " + balls.size() + "\n");
        int i = 1;
        for (Cannonball ball : balls) {
            System.out.println("   " + i + ": ");
            if (ball.bigCannonball()) {
                System.out.println("Size = Big, ");
            } else {
                System.out.println("Size = Small, ");
            }
            System.out.println("Direction = " + ball.direction() + "\n");
            i++;
        }
    }
}
