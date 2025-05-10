package it.polimi.ingsw.galaxytrucker.View;

import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilePile;

public class TUI{
    private Game game;
    private String nickname;


    public void start(){

    }

    public void viewLeaderboard(Game game, String nickname){

    }
//PER OGNI TILE HO BISOGNO DI TRE STRINGHE!!! POSSIAMO STAMPARE SOLO UNA RIGA PER VOLTA, COME VEDI NELL'HELPMESSAGE (ERA L'UNICO CHE SO CHE FUNZIONA COME COMANDO, SE DEVI TESTARE TESTA SU QUEL COMANDO)
    public void viewTilePile(Game game){
        this.game = game;
        TilePile tilePile = game.getTilePile();
        System.out.println("Tile pile: ");
        int i = 1; // Needed to know on witch row we are
        int j = 0; // Needed to know on witch tile we are
        for (Tile tile : tilePile.getTilePile()) {
            printHeaders();
            if (tile != null){
                //printTile(Tile, i, j);
            }
            else{
                printVoidTile(i, j);
            }
            i++;
        }

    }



    public void printHelpMessage(){
        System.out.print("╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮\n" );
        System.out.print("-  1  -  -  2  -  -  3  -  -  4  -  -  5  -  -  6  -  -  7  -  -  8  -  -  9  -  - 1 0 -  - 1 1 -  - 1 2 -  - 1 3 -  - 1 4 -  - 1 5 -  - 1 6 -\n");
        System.out.print("╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯\n");

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
        if (j % 15 == 0){
            System.out.print(
                    "╭─|||─╮\n" +
                    "-  " + j + "   -\n" +
                    "╰─|||─╯"
            );
        }
        String voidTile = "       ";
        System.out.print(voidTile);
    }

    private void printHeaders(){
        System.out.println(
                "╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮  ╭─|||─╮\n" +
                "-  1  -  -  2  -  -  3  -  -  4  -  -  5  -  -  6  -  -  7  -  -  8  -  -  9  -  - 1 0 -  - 1 1 -  - 1 2 -  - 1 3 -  - 1 4 -  - 1 5 -  - 1 6 -\n" +
                "╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯  ╰─|||─╯\n"
        );
    }
}
