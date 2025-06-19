package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.TravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;
import it.polimi.ingsw.galaxytrucker.Network.Client.RMIClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.SocketClient;
import it.polimi.ingsw.galaxytrucker.Network.Client.VirtualClient;
import it.polimi.ingsw.galaxytrucker.View.Trials.AnsiColor;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class TUI implements UI {
    private Game game;
    private String nickname;

    public void start(Stage primaryStage) {
        // not used
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void printTitle() {
        System.out.println();
        System.out.println(AnsiColor.BLUE_CARDBOARD.fg() +
                "  ▄████  ▄▄▄       ██▓    ▄▄▄      ▒██   ██▒▓██   ██▓ " + AnsiColor.PURPLE_CARDBOARD.fg() + "  ▄▄▄█████▓ ██▀███   █    ██  ▄████▄   ██ ▄█▀▓█████  ██▀███  \n" + AnsiColor.BLUE_CARDBOARD.fg() +
                " ██▒ ▀█▒▒████▄    ▓██▒   ▒████▄    ▒▒ █ █ ▒░ ▒██  ██▒ " + AnsiColor.PURPLE_CARDBOARD.fg() + "  ▓  ██▒ ▓▒▓██ ▒ ██▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒ ▓█   ▀ ▓██ ▒ ██▒\n" + AnsiColor.BLUE_CARDBOARD.fg() +
                "▒██░▄▄▄░▒██  ▀█▄  ▒██░   ▒██  ▀█▄  ░░  █   ░  ▒██ ██░ " + AnsiColor.PURPLE_CARDBOARD.fg() + "  ▒ ▓██░ ▒░▓██ ░▄█ ▒▓██  ▒██░▒▓█    ▄ ▓███▄░ ▒███   ▓██ ░▄█ ▒\n" + AnsiColor.BLUE_CARDBOARD.fg() +
                "░▓█  ██▓░██▄▄▄▄██ ▒██░   ░██▄▄▄▄██  ░ █ █ ▒   ░ ▐██▓░ " + AnsiColor.PURPLE_CARDBOARD.fg() + "  ░ ▓██▓ ░ ▒██▀▀█▄  ▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄ ▒▓█  ▄ ▒██▀▀█▄  \n" + AnsiColor.BLUE_CARDBOARD.fg() +
                "░▒▓███▀▒ ▓█   ▓██▒░██████▒▓█   ▓██▒▒██▒ ▒██▒  ░ ██▒▓░ " + AnsiColor.PURPLE_CARDBOARD.fg() + "    ▒██▒ ░ ░██▓ ▒██▒▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄░▒████▒░██▓ ▒██▒\n" + AnsiColor.BLUE_CARDBOARD.fg() +
                " ░▒   ▒  ▒▒   ▓▒█░░ ▒░▓  ░▒▒   ▓▒█░▒▒ ░ ░▓ ░   ██▒▒▒  " + AnsiColor.PURPLE_CARDBOARD.fg() + "    ▒ ░░   ░ ▒▓ ░▒▓░░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒░░ ▒░ ░░ ▒▓ ░▒▓░\n" + AnsiColor.BLUE_CARDBOARD.fg() +
                "  ░   ░   ▒   ▒▒ ░░ ░ ▒  ░ ▒   ▒▒ ░░░   ░▒ ░ ▓██ ░▒░  " + AnsiColor.PURPLE_CARDBOARD.fg() + "      ░      ░▒ ░ ▒░░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░ ░ ░  ░  ░▒ ░ ▒░\n" + AnsiColor.BLUE_CARDBOARD.fg() +
                "░ ░   ░   ░   ▒     ░ ░    ░   ▒    ░    ░   ▒ ▒ ░░   " + AnsiColor.PURPLE_CARDBOARD.fg() + "    ░        ░░   ░  ░░░ ░ ░ ░        ░ ░░ ░    ░     ░░   ░ \n" + AnsiColor.BLUE_CARDBOARD.fg() +
                "      ░       ░  ░    ░  ░     ░  ░ ░    ░   ░ ░      " + AnsiColor.PURPLE_CARDBOARD.fg() + "              ░        ░     ░ ░      ░  ░      ░  ░   ░     \n" + AnsiColor.BLUE_CARDBOARD.fg() +
                "                                             ░ ░      " + AnsiColor.PURPLE_CARDBOARD.fg() + "                             ░                               " + AnsiColor.RESET);
        System.out.println();
    }

    public void viewLeaderboard(Game game){
        System.out.println("LeaderBoard: ");
        int i = 1;
        for (Player player : game.getListOfPlayers()) {
            if(player.getShip().getTravelDays() == null){
                System.out.println(player.getNickname() + ": has not finished yet");
                i++;
            }else {
                System.out.println(i + ": " + player.getNickname() + ", " + player.getShip().getColor() +
                        ": " + player.getShip().getTravelDays() + " Travel Days");
                i++;
            }
        }
    }

    public void printMessage(String message){
        System.out.print(message);
    }

    public void viewTilePile(Game game){
        this.game = game;
        System.out.println("Tile pile: ");
        List<Tile> pile = game.getTilePile().getTilePile();
        ArrayList <ArrayList <Tile>> pileMatrix = new ArrayList<>();
        for (int k = 0; k < pile.size(); k += 16) {
            ArrayList <Tile> row = new ArrayList<>();
            if (k + 16 >= pile.size()) {
                for (int j = k; j < pile.size(); j++) {
                    row.add(pile.get(j));
                }
            } else {
                for (int j = k; j < k + 16; j++) {
                    row.add(pile.get(j));
                }
            }
            pileMatrix.add(row);
        }
        printPileHeaders();
        int i = 0;
        for (ArrayList<Tile> row : pileMatrix) {
            StringBuilder upperRow;
            StringBuilder middleRow;
            StringBuilder lowerRow;
            upperRow = new StringBuilder("║       ");
            middleRow = new StringBuilder("║ [" + i + "]   ");
            lowerRow = new StringBuilder("║       ");
            for (Tile tile : row) {
                List<List<String>> allRow = buildTile(tile, null);
                if (tile != null) {
                    if (tile.getFacingUp()) {
                        upperRow.append(allRow.get(0).get(0));
                        middleRow.append(allRow.get(1).get(0));
                        lowerRow.append(allRow.get(2).get(0));
                    } else {
                        upperRow.append("╭─────╮ ");
                        middleRow.append("│  x  │ ");
                        lowerRow.append("╰─────╯ ");
                    }
                } else {
                    upperRow.append("        ");
                    middleRow.append("        ");
                    lowerRow.append("        ");
                }
            }
            if (i < 9) {
                upperRow.append("      ║ ");
                middleRow.append("  [" + i + "] ║ ");
                lowerRow.append("      ║ ");
            } else {
                upperRow.append("                                                                      ║ ");
                middleRow.append("                                                                  [" + i + "] ║ ");
                lowerRow.append("                                                                      ║ ");
            }
            i++;
            System.out.println(String.join("", upperRow));
            System.out.println(String.join("", middleRow));
            System.out.println(String.join("", lowerRow));
        }
        printPileFooters();
        System.out.println();
    }

    public void printShips(Game game){
        this.game = game;
        for (Player player : game.getListOfPlayers()) {
            System.out.println(player.getNickname() + "'s ship: ");
            printMyShip(game, player.getNickname());
        }
    }

    public void printVoid() {
        System.out.println();
    }

    public void printMyShip(Game game, String nickname) {
        this.game = game;
        Ship ship = null;
        for (Player p : game.getListOfPlayers()) {
            if (p.getNickname().equals(nickname)) {
                ship = p.getShip();
            }
        }
        // printGuide();
        printActualShip(ship);
        System.out.println();
        printReservedAndHand(ship);
        printShipDetails(ship);
    }

    public void printGuide() {
        System.out.println("                                                 " + AnsiColor.CABIN_COLOR.fg() +
                "⊱ ────── {.⋅ " + AnsiColor.RED.fg() + "༺✧༻" + AnsiColor.CABIN_COLOR.fg() + " ⋅.} ────── ⊰");
        System.out.println("                                                 " + AnsiColor.CABIN_COLOR.fg() +
                "│   " + AnsiColor.BLUE.fg() + "༺✧༻" + AnsiColor.CABIN_COLOR.fg() + "    GUIDE     " + AnsiColor.YELLOW.fg() + "༺✧༻" + AnsiColor.CABIN_COLOR.fg() + "  │");
        System.out.println("                                                 " + AnsiColor.CABIN_COLOR.fg() +
                "⊱ ────── {.⋅ " + AnsiColor.GREEN.fg() + "༺✧༻" + AnsiColor.CABIN_COLOR.fg() + " ⋅.} ────── ⊰");

        System.out.println();
        System.out.println("                                                 " + AnsiColor.CABIN_COLOR.fg() +
                " .⋅ ༺✧༻    TILES     ༺✧༻ ⋅." + AnsiColor.RESET);

        StringBuilder upperRow1 = new StringBuilder("╭─────╮ ");
        StringBuilder middleRow1 = new StringBuilder("│" + AnsiColor.ORANGE.fg() + " O ✶ " + AnsiColor.RESET + "│ ");
        StringBuilder lowerRow1 = new StringBuilder("╰─────╯ ");
        upperRow1.append("                   ");
        middleRow1.append(AnsiColor.ORANGE.fg() + "ORANGE BIOADAPTOR  " + AnsiColor.RESET);
        lowerRow1.append("                   ");

        upperRow1.append("╭─────╮ ");
        middleRow1.append("│" + AnsiColor.PURPLE.fg() + " P ✶ " + AnsiColor.RESET + "│ ");
        lowerRow1.append("╰─────╯ ");
        upperRow1.append("                   ");
        middleRow1.append(AnsiColor.PURPLE.fg() + "PURPLE BIOADAPTOR  " + AnsiColor.RESET);
        lowerRow1.append("                   ");

        upperRow1.append("╭─────╮ ");
        middleRow1.append("│" + AnsiColor.BATTERY_COLOR.fg() + " 2 § " + AnsiColor.RESET + "│ ");
        lowerRow1.append("╰─────╯ ");
        upperRow1.append("                   ");
        middleRow1.append(AnsiColor.BATTERY_COLOR.fg() + "BATTERY            " + AnsiColor.RESET);
        lowerRow1.append("                   ");

        upperRow1.append("╭─────╮ ");
        middleRow1.append("│" + AnsiColor.BLUE.fg() + "  ◉  " + AnsiColor.RESET + "│ ");
        lowerRow1.append("╰─────╯ ");
        upperRow1.append("                   ");
        middleRow1.append(AnsiColor.BLUE.fg() + "MAIN CABIN         " + AnsiColor.RESET);
        lowerRow1.append("                   ");

        upperRow1.append("╭─────╮ ");
        middleRow1.append("│" + AnsiColor.CABIN_COLOR.fg() + "  ○  " + AnsiColor.RESET + "│ ");
        lowerRow1.append("╰─────╯ ");
        upperRow1.append("                   ");
        middleRow1.append(AnsiColor.CABIN_COLOR.fg() + "CABIN              " + AnsiColor.RESET);
        lowerRow1.append("                   ");

        StringBuilder upperRow2 = new StringBuilder("╭─────╮ ");
        StringBuilder middleRow2 = new StringBuilder("│" + AnsiColor.REGULAR_CARGO_COLOR.fg() + " 2 □ " + AnsiColor.RESET + "│ ");
        StringBuilder lowerRow2 = new StringBuilder("╰─────╯ ");
        upperRow2.append("                   ");
        middleRow2.append(AnsiColor.REGULAR_CARGO_COLOR.fg() + "REGULAR CARGO      " + AnsiColor.RESET);
        lowerRow2.append("                   ");

        upperRow2.append("╭─────╮ ");
        middleRow2.append("│" + AnsiColor.RED_CARGO_COLOR.fg() + " 1 ■ " + AnsiColor.RESET + "│ ");
        lowerRow2.append("╰─────╯ ");
        upperRow2.append("                   ");
        middleRow2.append(AnsiColor.RED_CARGO_COLOR.fg() + "RED CARGO          " + AnsiColor.RESET);
        lowerRow2.append("                   ");

        upperRow2.append("╭─────╮ ");
        middleRow2.append("│" + AnsiColor.ENGINE_COLOR.fg() + " 1 ¤ " + AnsiColor.RESET + "│ ");
        lowerRow2.append("╰─────╯ ");
        upperRow2.append("                   ");
        middleRow2.append(AnsiColor.ENGINE_COLOR.fg() + "SINGLE ENGINE      " + AnsiColor.RESET);
        lowerRow2.append("                   ");

        upperRow2.append("╭─────╮ ");
        middleRow2.append("│" + AnsiColor.GREEN.bg() + " " + AnsiColor.RESET +
                AnsiColor.DOUBLE_ENGINE_COLOR.fg() + "2 ¤" + AnsiColor.RESET +
                AnsiColor.GREEN.bg() + " " + AnsiColor.RESET + "│ ");
        lowerRow2.append("╰─────╯ ");
        upperRow2.append("                   ");
        middleRow2.append(AnsiColor.DOUBLE_ENGINE_COLOR.fg() + "DOUBLE ENGINE      " + AnsiColor.RESET);
        lowerRow2.append("(BATTERY POWERED)  ");

        upperRow2.append("╭─────╮ ");
        middleRow2.append("│" + AnsiColor.GREEN.bg() + " " + AnsiColor.RESET +
                AnsiColor.SHIELD_COLOR.fg() + "NW#" + AnsiColor.RESET +
                AnsiColor.GREEN.bg() + " " + AnsiColor.RESET + "│ ");
        lowerRow2.append("╰─────╯ ");
        upperRow2.append("                   ");
        middleRow2.append(AnsiColor.SHIELD_COLOR.fg() + "SHIELD             " + AnsiColor.RESET);
        lowerRow2.append("(BATTERY POWERED)  ");

        StringBuilder upperRow3 = new StringBuilder("╭─────╮ ");
        StringBuilder middleRow3 = new StringBuilder("│" + AnsiColor.CANNON_COLOR.fg() + " 1 + " + AnsiColor.RESET + "│ ");
        StringBuilder lowerRow3 = new StringBuilder("╰─────╯ ");
        upperRow3.append("                   ");
        middleRow3.append(AnsiColor.CANNON_COLOR.fg() + "SINGLE CANNON      " + AnsiColor.RESET);
        lowerRow3.append("                   ");

        upperRow3.append("╭─────╮ ");
        middleRow3.append("│" + AnsiColor.GREEN.bg() + " " + AnsiColor.RESET +
                AnsiColor.DOUBLE_CANNON_COLOR.fg() + "2 +" + AnsiColor.RESET +
                AnsiColor.GREEN.bg() + " " + AnsiColor.RESET + "│ ");
        lowerRow3.append("╰─────╯ ");
        upperRow3.append("                   ");
        middleRow3.append(AnsiColor.DOUBLE_CANNON_COLOR.fg() + "DOUBLE CANNON      " + AnsiColor.RESET);
        lowerRow3.append("(BATTERY POWERED)  ");

        upperRow3.append("╭─────╮ ");
        middleRow3.append("│" + AnsiColor.STRUCTURAL_COLOR.fg() + "  $  " + AnsiColor.RESET + "│ ");
        lowerRow3.append("╰─────╯ ");
        upperRow3.append("                   ");
        middleRow3.append(AnsiColor.STRUCTURAL_COLOR.fg() + "STRUCTURAL         " + AnsiColor.RESET);
        lowerRow3.append("                   ");

        upperRow3.append("╭─────╮ ");
        middleRow3.append("│ [ ] │ ");
        lowerRow3.append("╰─────╯ ");
        upperRow3.append("                   ");
        middleRow3.append("EMPTY TILE SLOT    ");
        lowerRow3.append("                   ");

        upperRow3.append("╭─────╮ ");
        middleRow3.append("│  x  │ ");
        lowerRow3.append("╰─────╯ ");
        upperRow3.append("                   ");
        middleRow3.append("UPSIDE DOWN TILE   ");
        lowerRow3.append("                   ");

        System.out.println(String.join("", upperRow1));
        System.out.println(String.join("", middleRow1));
        System.out.println(String.join("", lowerRow1));

        System.out.println(String.join("", upperRow2));
        System.out.println(String.join("", middleRow2));
        System.out.println(String.join("", lowerRow2));

        System.out.println(String.join("", upperRow3));
        System.out.println(String.join("", middleRow3));
        System.out.println(String.join("", lowerRow3));

        System.out.println();
        System.out.println("                                                  " + AnsiColor.CABIN_COLOR.fg() +
                " .⋅ ༺✧༻  CONNECTORS  ༺✧༻ ⋅." + AnsiColor.RESET);
        System.out.println();

        StringBuilder upperRow4 = new StringBuilder("|,  " + "-,  " + "SINGLE CONNECTOR;  ");
        upperRow4.append("||,  " + "=,  " + "DOUBLE CONNECTOR;  ");
        upperRow4.append("|||,  " + "≡,  " + "TRIPLE CONNECTOR");
        StringBuilder middleRow4 = new StringBuilder(AnsiColor.CANNON_COLOR.fg() + "↑,  " + "↓,  " + "→,  " + "←,  " + "SINGLE CANNON;  " + AnsiColor.RESET
                + AnsiColor.DOUBLE_CANNON_COLOR.fg() + "↑ ↑,  " + "↓ ↓,  " + "▸,  " + "◂,  " + "DOUBLE CANNON" + AnsiColor.RESET);
        StringBuilder lowerRow4 = new StringBuilder(AnsiColor.ENGINE_COLOR.fg() + "V,  " + "Λ,  " + "<,  " + ">,  " + "SINGLE ENGINE;  " + AnsiColor.RESET
                + AnsiColor.DOUBLE_ENGINE_COLOR.fg() + "V V,  " + "Λ Λ,  " + "«,  " + "»,  " + "DOUBLE ENGINE" + AnsiColor.RESET);

        System.out.println(String.join("", upperRow4));
        System.out.println(String.join("", middleRow4));
        System.out.println(String.join("", lowerRow4));
        System.out.println();
    }

    public void printActualShip(Ship ship) {
        Color color = ship.getColor();
        if (color == Color.RED) {
            System.out.println(AnsiColor.RED.fg() + "Red Ship:" + AnsiColor.RESET);
        } else if (color == Color.BLUE) {
            System.out.println(AnsiColor.BLUE.fg() + "Blue Ship:" + AnsiColor.RESET);
        } else if (color == Color.GREEN) {
            System.out.println(AnsiColor.GREEN.fg() + "Green Ship:" + AnsiColor.RESET);
        } else if (color == Color.YELLOW) {
            System.out.println(AnsiColor.YELLOW.fg() + "Yellow Ship:" + AnsiColor.RESET);
        }
        ArrayList<ArrayList<Tile>> shipList = ship.getFloorplanArrayList();
        printShipHeaders(color);
        int i = 5;
        for (ArrayList<Tile> row : shipList) {
            StringBuilder upperRow;
            StringBuilder middleRow;
            StringBuilder lowerRow;
            upperRow = new StringBuilder();
            middleRow = new StringBuilder();
            lowerRow = new StringBuilder();
            if (color == Color.RED) {
                upperRow.append(AnsiColor.RED.fg() + "║       " + AnsiColor.RESET);
                middleRow.append(AnsiColor.RED.fg() + "║ [" + i + "]   " + AnsiColor.RESET);
                lowerRow.append(AnsiColor.RED.fg() + "║       " + AnsiColor.RESET);
            } else if (color == Color.BLUE) {
                upperRow.append(AnsiColor.BLUE.fg() + "║       " + AnsiColor.RESET);
                middleRow.append(AnsiColor.BLUE.fg() + "║ [" + i + "]   " + AnsiColor.RESET);
                lowerRow.append(AnsiColor.BLUE.fg() + "║       " + AnsiColor.RESET);
            } else if (color == Color.GREEN) {
                upperRow.append(AnsiColor.GREEN.fg() + "║       " + AnsiColor.RESET);
                middleRow.append(AnsiColor.GREEN.fg() + "║ [" + i + "]   " + AnsiColor.RESET);
                lowerRow.append(AnsiColor.GREEN.fg() + "║       " + AnsiColor.RESET);
            } else if (color == Color.YELLOW) {
                upperRow.append(AnsiColor.YELLOW.fg() + "║       " + AnsiColor.RESET);
                middleRow.append(AnsiColor.YELLOW.fg() + "║ [" + i + "]   " + AnsiColor.RESET);
                lowerRow.append(AnsiColor.YELLOW.fg() + "║       " + AnsiColor.RESET);
            }
            int j = 4; // column
            for (Tile tile : row) {
                List<List<String>> allRow = buildTile(tile, ship);
                if (tile != null) {
                    upperRow.append(allRow.get(0).get(0));
                    middleRow.append(allRow.get(1).get(0));
                    lowerRow.append(allRow.get(2).get(0));
                } else {
                    if (((i == 5) && (j == 4 || j == 5 || j == 7 || j == 9 || j == 10))
                            || ((i == 6) && (j == 4 || j == 10))
                            || ((i == 9) && (j == 7))) { // non-selectable tiles
                        upperRow.append("        ");
                        middleRow.append("        ");
                        lowerRow.append("        ");
                    } else {
                        upperRow.append("╭─────╮ ");
                        middleRow.append("│ [ ] │ ");
                        lowerRow.append("╰─────╯ ");
                    }
                }
                j++;
            }
            if (color == Color.RED) {
                upperRow.append(AnsiColor.RED.fg() + "      ║ " + AnsiColor.RESET);
                middleRow.append(AnsiColor.RED.fg() + "  [" + i + "] ║ " + AnsiColor.RESET);
                lowerRow.append(AnsiColor.RED.fg() + "      ║ " + AnsiColor.RESET);
            } else if (color == Color.BLUE) {
                upperRow.append(AnsiColor.BLUE.fg() + "      ║ " + AnsiColor.RESET);
                middleRow.append(AnsiColor.BLUE.fg() + "  [" + i + "] ║ " + AnsiColor.RESET);
                lowerRow.append(AnsiColor.BLUE.fg() + "      ║ " + AnsiColor.RESET);
            } else if (color == Color.GREEN) {
                upperRow.append(AnsiColor.GREEN.fg() + "      ║ " + AnsiColor.RESET);
                middleRow.append(AnsiColor.GREEN.fg() + "  [" + i + "] ║ " + AnsiColor.RESET);
                lowerRow.append(AnsiColor.GREEN.fg() + "      ║ " + AnsiColor.RESET);
            } else if (color == Color.YELLOW) {
                upperRow.append(AnsiColor.YELLOW.fg() + "      ║ " + AnsiColor.RESET);
                middleRow.append(AnsiColor.YELLOW.fg() + "  [" + i + "] ║ " + AnsiColor.RESET);
                lowerRow.append(AnsiColor.YELLOW.fg() + "      ║ " + AnsiColor.RESET);
            }
            i++;
            System.out.println(String.join("", upperRow));
            System.out.println(String.join("", middleRow));
            System.out.println(String.join("", lowerRow));
        }
        printShipFooters(color);
    }

    private List<List<String>> appendReserved(Ship ship) {
        List<List<String>> allRow = new ArrayList<>();
        List <Tile> reserved = ship.getReservedTiles();
        List<String> upperRow = new ArrayList<>();
        List<String> middleRow = new ArrayList<>();
        List<String> lowerRow = new ArrayList<>();
        int i;
        for (i = 0; i < reserved.size(); i++) {
            Tile tile = reserved.get(i);
            allRow = buildTile(tile, null);
            upperRow.add(allRow.get(0).get(0));
            middleRow.add(allRow.get(1).get(0));
            lowerRow.add(allRow.get(2).get(0));
        }
        for (; i < 2; i++) {
            upperRow.add("╭─────╮ ");
            middleRow.add("│ [ ] │ ");
            lowerRow.add("╰─────╯ ");
        }
        allRow.clear();
        allRow.add(upperRow);
        allRow.add(middleRow);
        allRow.add(lowerRow);
        return allRow;
    }

    private List<List<String>> appendHand(Ship ship) {
        Tile hand = ship.getTileInHand();
        List<String> upperRow = new ArrayList<>();
        List<String> middleRow = new ArrayList<>();
        List<String> lowerRow = new ArrayList<>();
        if (hand != null) {
            List<List<String>> allRow = buildTile(hand, null);
            upperRow.add(allRow.get(0).get(0));
            middleRow.add(allRow.get(1).get(0));
            lowerRow.add(allRow.get(2).get(0));
            allRow.clear();
            allRow.add(upperRow);
            allRow.add(middleRow);
            allRow.add(lowerRow);
            return allRow;
        } else {
            return null;
        }
    }

    private void printShipDetails(Ship ship) {
        System.out.println(AnsiColor.CREW_COLOR.fg() + "Crew: " + ship.getNumberOfInhabitants() + AnsiColor.RESET);
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
        System.out.println(AnsiColor.CABIN_COLOR.fg() + "   Humans: " + humans + AnsiColor.RESET);
        if (aliens == 2) {
            System.out.println(AnsiColor.ORANGE.fg() + "   A" + AnsiColor.PURPLE.fg()
                    + "l" + AnsiColor.ORANGE.fg() + "i" + AnsiColor.PURPLE.fg()
                    + "e" + AnsiColor.ORANGE.fg() + "n" + AnsiColor.PURPLE.fg()
                    + "s" + AnsiColor.ORANGE.fg() + ":" + AnsiColor.PURPLE.fg()
                    + " 2 " + AnsiColor.ORANGE.fg() + "-" + AnsiColor.PURPLE.fg() + " both" + AnsiColor.PURPLE.fg() + " kinds" + AnsiColor.RESET);
        } else if (aliens == 1) {
            if (color == AlienColor.ORANGE) {
                System.out.println(AnsiColor.ORANGE.fg() + "   Aliens: 1 - orange" + AnsiColor.RESET);
            } else if (color == AlienColor.PURPLE) {
                System.out.println(AnsiColor.PURPLE.fg() + "   Aliens: 1 - purple" + AnsiColor.RESET);
            }
        } else if (aliens == 0) {
            System.out.println(AnsiColor.ORANGE.fg() + "   A" + AnsiColor.PURPLE.fg()
                    + "l" + AnsiColor.ORANGE.fg() + "i" + AnsiColor.PURPLE.fg()
                    + "e" + AnsiColor.ORANGE.fg() + "n" + AnsiColor.PURPLE.fg()
                    + "s" + AnsiColor.ORANGE.fg() + ":" + AnsiColor.PURPLE.fg()
                    + " 0" + AnsiColor.RESET);
        }
        System.out.println(AnsiColor.BATTERY_COLOR.fg() + "Batteries: " + ship.getBatteries() + AnsiColor.RESET);
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
        System.out.println(AnsiColor.CARGO_COLOR.fg() + "Cargo: " + allCargo + AnsiColor.RESET);
        System.out.println(AnsiColor.REGULAR_CARGO_COLOR.fg() + "   Normal: " + normCargo + AnsiColor.RESET);
        System.out.println(AnsiColor.RED_CARGO_COLOR.fg() + "   red: " + redCargo + AnsiColor.RESET);
        int firepower = 0;
        List <CannonTile> cannonList = ship.getListOfFirepower();
        for (CannonTile cannon : cannonList) {
            if (cannon.getDoublePower()) {
                firepower += 2;
            } else {
                firepower += 1;
            }
        }
        System.out.println(AnsiColor.CANNON_COLOR.fg() + "Firepower: " + firepower + AnsiColor.RESET);
        int enginePower = 0;
        List <EngineTile> engineList = ship.getListOfEngine();
        for (EngineTile engine : engineList) {
            if (engine.getDoublePower()) {
                enginePower += 2;
            } else {
                enginePower += 1;
            }
        }
        System.out.println(AnsiColor.ENGINE_COLOR.fg() + "Engine Power: " + enginePower + AnsiColor.RESET);
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
        String shieldLine = "Shielded sides: ";
        if (north) {
            shieldLine += "N ";
        }
        if (east) {
            shieldLine += "E ";
        }
        if (south) {
            shieldLine += "S ";
        }
        if (west) {
            shieldLine += "W ";
        }
        if (!north && !east && !south) {
            // west is automatically false because north & south are
            shieldLine += "none";
        }
        System.out.println(AnsiColor.SHIELD_COLOR.fg() + shieldLine + AnsiColor.RESET);
    }

    private void printReservedAndHand(Ship ship) {
        StringBuilder upperRow = new StringBuilder();
        StringBuilder middleRow = new StringBuilder();
        StringBuilder lowerRow = new StringBuilder();
        List<List<String>> allRow = appendReserved(ship);
        upperRow.append(allRow.get(0).get(0));
        upperRow.append(allRow.get(0).get(1));
        middleRow.append(allRow.get(1).get(0));
        middleRow.append(allRow.get(1).get(1));
        lowerRow.append(allRow.get(2).get(0));
        lowerRow.append(allRow.get(2).get(1));
        allRow = appendHand(ship);
        if (allRow != null) {
            upperRow.append("    ");
            middleRow.append("    ");
            lowerRow.append("    ");
            upperRow.append(allRow.get(0).get(0));
            middleRow.append(allRow.get(1).get(0));
            lowerRow.append(allRow.get(2).get(0));
            System.out.println("Reserved:           Hand: ");
        } else {
            System.out.println("Reserved:");
        }
        System.out.println(String.join("", upperRow));
        System.out.println(String.join("", middleRow));
        System.out.println(String.join("", lowerRow));
        System.out.println();
    }

    private List<List<String>> buildTile(Tile tile, Ship ship){
        List<String> upperRow = new ArrayList<>();
        List<String> middleRow = new ArrayList<>();
        List<String> lowerRow = new ArrayList<>();
        List <List <String>> allRow = new ArrayList<>();
        if (tile != null){
            if(tile.getFacingUp()){
                if(tile instanceof BioadaptorTile){
                    BioadaptorTile bioadaptorTile = (BioadaptorTile) tile;
                    String type = "     ";
                    if(bioadaptorTile.getAlienColor() == AlienColor.ORANGE){
                        type = " O ✶ ";
                    }
                    else if(bioadaptorTile.getAlienColor() == AlienColor.PURPLE){
                        type = " P ✶ ";
                    }
                    List<ConnectorType> connectors = bioadaptorTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    if (bioadaptorTile.getAlienColor() == AlienColor.ORANGE) {
                        middleRow.add(strConnectors.get(1) + AnsiColor.ORANGE.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    } else if (bioadaptorTile.getAlienColor() == AlienColor.PURPLE) {
                        middleRow.add(strConnectors.get(1) + AnsiColor.PURPLE.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    }
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
                    middleRow.add(strConnectors.get(1) + AnsiColor.BATTERY_COLOR.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));

                }
                else if(tile instanceof CabinTile){
                    CabinTile cabinTile = (CabinTile) tile;
                    String type;
                    List<ConnectorType> connectors = cabinTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    if (cabinTile.isMainCapsule()) { // in ship this is used
                        type = "  ◉  ";
                        if (ship.getColor() == Color.RED) {
                            middleRow.add(strConnectors.get(1) + AnsiColor.RED.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                        } else if (ship.getColor() == Color.BLUE) {
                            middleRow.add(strConnectors.get(1) + AnsiColor.BLUE.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                        } else if (ship.getColor() == Color.GREEN) {
                            middleRow.add(strConnectors.get(1) + AnsiColor.GREEN.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                        } else if (ship.getColor() == Color.YELLOW) {
                            middleRow.add(strConnectors.get(1) + AnsiColor.YELLOW.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                        }
                    } else {
                        type = "  ○  ";
                        middleRow.add(strConnectors.get(1) + AnsiColor.CABIN_COLOR.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    }
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof CargoTile){
                    CargoTile cargoTile = (CargoTile) tile;
                    String type = "     ";
                    List<ConnectorType> connectors = cargoTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    if (cargoTile.fitsRed()){
                        if (cargoTile.getSlotsNumber() == 1) {
                            type = " 1 ■ ";
                        } else if (cargoTile.getSlotsNumber() == 2) {
                            type = " 2 ■ ";
                        }
                        middleRow.add(strConnectors.get(1) + AnsiColor.RED_CARGO_COLOR.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    } else {
                        if (cargoTile.getSlotsNumber() == 2) {
                            type = " 2 □ ";
                        } else if (cargoTile.getSlotsNumber() == 3) {
                            type = " 3 □ ";
                        }
                        middleRow.add(strConnectors.get(1) + AnsiColor.REGULAR_CARGO_COLOR.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    }
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof EngineTile){
                    EngineTile engineTile = (EngineTile) tile;
                    String type;
                    List<ConnectorType> connectors = engineTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    if (engineTile.getDoublePower()){
                        type = AnsiColor.GREEN.bg() + " " + AnsiColor.RESET +
                                AnsiColor.DOUBLE_ENGINE_COLOR.fg() + "2 ¤" + AnsiColor.RESET +
                                AnsiColor.GREEN.bg() + " " + AnsiColor.RESET;
                        middleRow.add(strConnectors.get(1) + type + AnsiColor.RESET + strConnectors.get(3));
                    } else {
                        type = " 1 ¤ ";
                        middleRow.add(strConnectors.get(1) + AnsiColor.ENGINE_COLOR.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    }
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof ShieldTile){
                    ShieldTile shieldTile = (ShieldTile) tile;
                    String type = "   ";
                    if (shieldTile.getOrientation() == ShieldOrientation.NORTHWEST){
                        type = "NW#";
                    }
                    else if (shieldTile.getOrientation() == ShieldOrientation.NORTHEAST){
                        type = "NE#";
                    }
                    else if (shieldTile.getOrientation() == ShieldOrientation.SOUTHWEST){
                        type = "SW#";
                    }
                    else if (shieldTile.getOrientation() == ShieldOrientation.SOUTHEAST){
                        type = "SE#";
                    }
                    List<ConnectorType> connectors = shieldTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    String type2 = AnsiColor.GREEN.bg() + " " + AnsiColor.RESET +
                            AnsiColor.SHIELD_COLOR.fg() + type + AnsiColor.RESET +
                            AnsiColor.GREEN.bg() + " " + AnsiColor.RESET;
                    middleRow.add(strConnectors.get(1) + type2 + AnsiColor.RESET + strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));
                }
                else if(tile instanceof CannonTile){
                    CannonTile cannonTile = (CannonTile) tile;
                    String type;
                    List<ConnectorType> connectors = cannonTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    if(cannonTile.getDoublePower()){
                        type = AnsiColor.GREEN.bg() + " " + AnsiColor.RESET +
                                AnsiColor.DOUBLE_CANNON_COLOR.fg() + "2 +" + AnsiColor.RESET +
                                AnsiColor.GREEN.bg() + " " + AnsiColor.RESET;
                        middleRow.add(strConnectors.get(1) + type + AnsiColor.RESET + strConnectors.get(3));
                    } else {
                        type = " 1 + ";
                        middleRow.add(strConnectors.get(1) + AnsiColor.CANNON_COLOR.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    }
                    lowerRow.add(strConnectors.get(2));
                } else { // structural
                    Tile structuralTile = tile;
                    String type = "  $  ";
                    List<ConnectorType> connectors = structuralTile.getConnectors();
                    List<String> strConnectors = checkConnectors(connectors);
                    upperRow.add(strConnectors.get(0));
                    middleRow.add(strConnectors.get(1) + AnsiColor.STRUCTURAL_COLOR.fg() + type + AnsiColor.RESET + strConnectors.get(3));
                    lowerRow.add(strConnectors.get(2));
                }
            } else {
                upperRow.add("╭─────╮ ");
                middleRow.add("│  x  │ ");
                lowerRow.add("╰─────╯ ");
            }
        }
        else{
            upperRow.add("        ");
            middleRow.add("        ");
            lowerRow.add("        ");
        }
        allRow.add(upperRow);
        allRow.add(middleRow);
        allRow.add(lowerRow);
        return allRow;
    }

    public void printHelpMessage(){
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
    }

    @Override
    public void updateGame(Game game) {
        this.game = game;
    }

    @Override
    public void launchGUI(Game game, VirtualClient rmiClient, SocketClient socketClient) { /* do nothing */ }

    private void printVoidTile(int i,int j){
        String voidTile = "       ";
        System.out.print(voidTile);
    }

    private void printPileHeaders(){
        System.out.println(
                "╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                "║         [0]     [1]     [2]     [3]     [4]     [5]     [6]     [7]     [8]     [9]    [1 0]   [1 1]   [1 2]   [1 3]   [1 4]   [1 5]        ║\n" +
                "║                                                                                                                                             ║"
        );
    }

    private void printPileFooters(){
        System.out.println(
                "║                                                                                                                                             ║\n" +
                "║         [0]     [1]     [2]     [3]     [4]     [5]     [6]     [7]     [8]     [9]    [1 0]   [1 1]   [1 2]   [1 3]   [1 4]   [1 5]        ║\n" +
                "╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝"
        );
    }

    private void printShipHeaders(Color color){
        if (color == Color.RED) {
            System.out.println(AnsiColor.RED.fg() +
                    "╔═════════════════════════════════════════════════════════════════════╗\n" +
                    "║         [4]     [5]     [6]     [7]     [8]     [9]    [1 0]        ║\n" +
                    "║                                                                     ║" + AnsiColor.RESET);
        } else if (color == Color.BLUE) {
            System.out.println(AnsiColor.BLUE.fg() +
                    "╔═════════════════════════════════════════════════════════════════════╗\n" +
                    "║         [4]     [5]     [6]     [7]     [8]     [9]    [1 0]        ║\n" +
                    "║                                                                     ║" + AnsiColor.RESET);
        } else if (color == Color.GREEN) {
            System.out.println(AnsiColor.GREEN.fg() +
                    "╔═════════════════════════════════════════════════════════════════════╗\n" +
                    "║         [4]     [5]     [6]     [7]     [8]     [9]    [1 0]        ║\n" +
                    "║                                                                     ║" + AnsiColor.RESET);
        } else if (color == Color.YELLOW) {
            System.out.println(AnsiColor.YELLOW.fg() +
                    "╔═════════════════════════════════════════════════════════════════════╗\n" +
                    "║         [4]     [5]     [6]     [7]     [8]     [9]    [1 0]        ║\n" +
                    "║                                                                     ║" + AnsiColor.RESET);
        }
    }

    private void printShipFooters(Color color){
        if (color == Color.RED) {
            System.out.println(AnsiColor.RED.fg() +
                    "║                                                                     ║\n" +
                    "║         [4]     [5]     [6]     [7]     [8]     [9]    [1 0]        ║\n" +
                    "╚═════════════════════════════════════════════════════════════════════╝" + AnsiColor.RESET);
        } else if (color == Color.BLUE) {
            System.out.println(AnsiColor.BLUE.fg() +
                    "║                                                                     ║\n" +
                    "║         [4]     [5]     [6]     [7]     [8]     [9]    [1 0]        ║\n" +
                    "╚═════════════════════════════════════════════════════════════════════╝" + AnsiColor.RESET);
        } else if (color == Color.GREEN) {
            System.out.println(AnsiColor.GREEN.fg() +
                    "║                                                                     ║\n" +
                    "║         [4]     [5]     [6]     [7]     [8]     [9]    [1 0]        ║\n" +
                    "╚═════════════════════════════════════════════════════════════════════╝" + AnsiColor.RESET);
        } else if (color == Color.YELLOW) {
            System.out.println(AnsiColor.YELLOW.fg() +
                    "║                                                                     ║\n" +
                    "║         [4]     [5]     [6]     [7]     [8]     [9]    [1 0]        ║\n" +
                    "╚═════════════════════════════════════════════════════════════════════╝" + AnsiColor.RESET);
        }
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
            connectorList.add("╭─" + AnsiColor.CANNON_COLOR.fg() + " ↑ " + AnsiColor.RESET + "─╮ ");
        }
        else if (north == ConnectorType.CANNON_DOUBLE){
            connectorList.add("╭─" + AnsiColor.DOUBLE_CANNON_COLOR.fg() + "↑ ↑" + AnsiColor.RESET + "─╮ ");
        }
        else if (north == ConnectorType.ENGINE_SINGLE){
            connectorList.add("╭─" + AnsiColor.ENGINE_COLOR.fg() + " V " + AnsiColor.RESET + "─╮ ");
        }
        else if (north == ConnectorType.ENGINE_DOUBLE){
            connectorList.add("╭─" + AnsiColor.DOUBLE_ENGINE_COLOR.fg() + "V V" + AnsiColor.RESET + "─╮ ");
        }

        if (west == ConnectorType.NONE){
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
            connectorList.add(AnsiColor.CANNON_COLOR.fg() + "←" + AnsiColor.RESET);
        }
        else if (west == ConnectorType.CANNON_DOUBLE){
            connectorList.add(AnsiColor.DOUBLE_CANNON_COLOR.fg() + "◂" + AnsiColor.RESET);
        }
        else if (west == ConnectorType.ENGINE_SINGLE){
            connectorList.add(AnsiColor.ENGINE_COLOR.fg() + ">" + AnsiColor.RESET);
        }
        else if (west == ConnectorType.ENGINE_DOUBLE){
            connectorList.add(AnsiColor.DOUBLE_ENGINE_COLOR.fg() + "»" + AnsiColor.RESET);
        }

        if (south == ConnectorType.NONE){
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
            connectorList.add("╰─" + AnsiColor.CANNON_COLOR.fg() + " ↓ " + AnsiColor.RESET + "─╯ ");
        }
        else if (south == ConnectorType.CANNON_DOUBLE){
            connectorList.add("╰─" + AnsiColor.DOUBLE_CANNON_COLOR.fg() + "↓ ↓" + AnsiColor.RESET + "─╯ ");
        }
        else if (south == ConnectorType.ENGINE_SINGLE){
            connectorList.add("╰─" + AnsiColor.ENGINE_COLOR.fg() + " Λ " + AnsiColor.RESET + "─╯ ");
        }
        else if (south == ConnectorType.ENGINE_DOUBLE){
            connectorList.add("╰─" + AnsiColor.DOUBLE_ENGINE_COLOR.fg() + "Λ Λ" + AnsiColor.RESET + "─╯ ");
        }

        if (east == ConnectorType.NONE){
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
            connectorList.add(AnsiColor.CANNON_COLOR.fg() + "→ " + AnsiColor.RESET);
        }
        else if (east == ConnectorType.CANNON_DOUBLE){
            connectorList.add(AnsiColor.DOUBLE_CANNON_COLOR.fg() + "▸ " + AnsiColor.RESET);
        }
        else if (east == ConnectorType.ENGINE_SINGLE){
            connectorList.add(AnsiColor.ENGINE_COLOR.fg() + "< " + AnsiColor.RESET);
        }
        else if (east == ConnectorType.ENGINE_DOUBLE){
            connectorList.add(AnsiColor.DOUBLE_ENGINE_COLOR.fg() + "« " + AnsiColor.RESET);
        }
        return connectorList;
    }

    public void printTile(Tile tile){
        StringBuilder upperRow = new StringBuilder();
        StringBuilder middleRow = new StringBuilder();
        StringBuilder lowerRow = new StringBuilder();
        List<List<String>> allRow = buildTile(tile, null);
        if (tile != null) {
            upperRow.append(allRow.get(0).get(0));
            middleRow.append(allRow.get(1).get(0));
            lowerRow.append(allRow.get(2).get(0));
        } else {
            upperRow.append("╭─────╮ ");
            middleRow.append("│     │ ");
            lowerRow.append("╰─────╯ ");
        }
        System.out.println(String.join("", upperRow));
        System.out.println(String.join("", middleRow));
        System.out.println(String.join("", lowerRow));
    }

    public void viewCard(Game game){
        //need instanceof for current card.... or attribute currend card in every gamestate and whrere not needed set to null.
        if (game.getGameState() instanceof TravellingState){
            TravellingState travellingState = (TravellingState) game.getGameState();
            Card card = travellingState.getCurrentCard();
            System.out.println("Current card: ");
            checkCard(card);
        }
        else{
            System.out.println("No current card available");
        }

    }

    private void checkCard(Card card){
        if (card != null){
            if (card instanceof CombatZoneCard) { // TODO decide if we want to print the affected player's nickname or not
                System.out.println(AnsiColor.RED.fg() + "Combat Zone Card \n" + AnsiColor.RESET +
                        "Days to lose: " + ((CombatZoneCard) card).getDaysLost());
                if (card instanceof CombatZoneCardL) {
                    System.out.println("Crew lost: " + ((CombatZoneCard) card).getCrewLost());
                } else if (card instanceof CombatZoneCardNotL) {
                    System.out.println("Cargo lost: " + ((CombatZoneCard) card).getCargoLost());
                }
                List <Cannonball> balls = ((CombatZoneCard) card).getCannonballList();
                printBalls(balls);
            }
            else if (card instanceof EpidemicCard){
                System.out.println(AnsiColor.YELLOW.fg() + "Epidemic Card \n" + AnsiColor.RESET);
            }
            else if (card instanceof MeteorsCard){
                System.out.println(AnsiColor.ORANGE.fg() + "Meteors Card: \n" + AnsiColor.RESET);
                List <Meteor> balls = ((MeteorsCard) card).getMeteorsList();
                System.out.println("Meteors: Tot number = " + balls.size());
                int i = 1;
                for (Meteor ball : balls) {
                    System.out.println("   " + i + ": ");
                    if (ball.bigMeteor()) {
                        System.out.println("Size = Big, ");
                    } else {
                        System.out.println("Size = Small, ");
                    }
                    System.out.println("Direction = " + ball.direction());
                    i++;
                }
            }
            else if (card instanceof OpenSpaceCard){
                System.out.println(AnsiColor.BLUE.fg() + "Open Space Card" + AnsiColor.RESET);
            }
            else if(card instanceof PiratesCard){
                System.out.println(AnsiColor.PURPLE.fg() + "Pirates Card: \n" + AnsiColor.RESET +
                        "Firepower: " + ((PiratesCard) card).getFirepower() + " \n" +
                        "Credits: " + ((PiratesCard) card).getCredits() + " \n" +
                        "Days to lose: " + ((PiratesCard) card).getDaysToLose());
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
                    i++;
                    int size = blocks1.size() + blocks2.size() + blocks3.size() + blocks4.size();
                    planetBlocks.add("Blocks: Tot value = " + size + "\n" +
                            "   Blue = " + blocks1.size() + ",\n" +
                            "   Green = " + blocks2.size() + ",\n" +
                            "   Yellow = " + blocks3.size() + ",\n" +
                            "   Red = " + blocks4.size() + "\n");
                }
                System.out.println(AnsiColor.GREEN.fg() + "Planets Card: \n" +  AnsiColor.RESET + planetBlocks);
            }
            else if (card instanceof ShipCard){
                System.out.println(AnsiColor.CARGO_COLOR.fg() + "Ship Card: \n" + AnsiColor.RESET +
                        "Credits: " + ((ShipCard) card).getCredits() + " \n" +
                        "Crew lost: " + ((ShipCard) card).getCrewNumberLost() + " \n" +
                        "Days to lose: " + ((ShipCard) card).getDaysToLose());
            }
            else if (card instanceof SlaversCard){
                System.out.println(AnsiColor.PURPLE.fg() + "Slavers Card: \n"+ AnsiColor.RESET +
                        "Firepower: " + ((SlaversCard) card).getFirepower() + "\n"+
                        "Credits: " + ((SlaversCard) card).getNumberOfCredits() + "\n"+
                        "Crew lost: " + ((SlaversCard) card).getNumberOfCrewLost() + "\n"+
                        "Days to lose: " + ((SlaversCard) card).getNumberOfDaysToLose());
            }
            else if (card instanceof SmugglersCard){
                List<Integer> blocks = ((SmugglersCard)card).getBlocksList();
                System.out.println(AnsiColor.PURPLE.fg() + "Smugglers Card: \n" + AnsiColor.RESET +
                        "Firepower: " + ((SmugglersCard) card).getFirepower());
                printBlocks(blocks);
                System.out.println("Days to lose: " + ((SmugglersCard) card).getDaysToLose());
            }
            else if (card instanceof StardustCard){
                System.out.println(AnsiColor.YELLOW.fg() + "Stardust Card" + AnsiColor.RESET);
            }
            else if (card instanceof StationCard){
                List<Integer> blocks = ((StationCard)card).getBlockList();
                System.out.println(AnsiColor.CREW_COLOR.fg() + "Station Card: \n"+ AnsiColor.RESET +
                        "Crew needed: " + ((StationCard) card).getCrewNumberNeeded());
                printBlocks(blocks);
                System.out.println("Days to lose: " + ((StationCard) card).getDaysToLose());
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
