package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldTile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.COLUMN;
import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.ROW;
import static it.polimi.ingsw.galaxytrucker.Model.Direction.*;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation.*;


/**
 * This record is used to create Cannonballs
 * @param bigCannonball
 * @param direction
 * @param rowOrColumn
 * @param diceRoll
 */
public record Cannonball(boolean bigCannonball, Direction direction, RowOrColumn rowOrColumn, int diceRoll) implements Serializable {

    /**
     * @param bigCannonball
     * @param direction
     * @param rowOrColumn
     */
    public Cannonball(boolean bigCannonball, Direction direction, RowOrColumn rowOrColumn){
        this(bigCannonball,direction,rowOrColumn,rollTwoDice());
    }

    /**
     * This function generate two roll dices and return their sum
     * @return int
     */
    public static int rollTwoDice() {
        Random rand = new Random();

        // Random number between 1-6
        int die1 = rand.nextInt(6) + 1;
        int die2 = rand.nextInt(6) + 1;

        // Sum of two dice
        return die1 + die2;
    }

    /**
     * This function decides whether Cannonball hit Ship or not
     * @param ship
     */
    public void getHit(Ship ship) {
        if (this.rowOrColumn == ROW) {
            if (diceRoll >= 5 && diceRoll <= 9 && !ship.getRowListTiles(diceRoll-5).isEmpty()) {
                checkForShieldOrRemoveRow(ship, diceRoll);
            }
        } else if (this.rowOrColumn == COLUMN) {
            if (diceRoll >= 4 && diceRoll <= 10 && !ship.getColumnListTiles(diceRoll-4).isEmpty()) {
                checkForShieldOrRemoveColumn(ship, diceRoll);
            }
        }
    }

    /**
     * This function checks Active Shileds on specific Row(diceroll),and if there is not,removes first element of Row
     * @param ship
     * @param diceRoll
     */
    private void checkForShieldOrRemoveRow(Ship ship, int diceRoll) {
        diceRoll -= 5;
        if (this.bigCannonball) {
            ship.removeFirstTile(diceRoll, direction);
        } else {
            ArrayList <ShieldTile> shields = ship.getListOfShield();
            boolean hasShield = false;
            for (ShieldTile shield : shields) {
                if ((shield.getOrientation() == NORTHWEST || shield.getOrientation() == SOUTHWEST) && this.direction == WEST
                        || (shield.getOrientation() == SOUTHEAST || shield.getOrientation() == NORTHEAST) && this.direction == EAST) {
                    if (shield.getActiveState()) {
                        hasShield = true;
                        break;
                    }
                }
            }
            if (!hasShield) {
                ship.removeFirstTile(diceRoll, direction);
            }
        }
    }
    /**
     * This function checks Active Shileds on specific Column(diceroll),and if there is not,removes first element of Column
     * @param ship
     * @param diceRoll
     */
    private void checkForShieldOrRemoveColumn(Ship ship, int diceRoll) {
        diceRoll -= 4;
        if (this.bigCannonball) {
            ship.removeFirstTile(diceRoll, direction);
        } else {
            ArrayList <ShieldTile> shields = ship.getListOfShield();
            boolean hasShield = false;
            for (ShieldTile shield : shields) {
                if ((shield.getOrientation() == NORTHWEST || shield.getOrientation() == NORTHEAST) && this.direction == NORTH
                        || (shield.getOrientation() == SOUTHEAST || shield.getOrientation() == SOUTHWEST) && this.direction == SOUTH) {
                    if (shield.getActiveState()) {
                        hasShield = true;
                        break;
                    }
                }
            }
            if (!hasShield) {
                ship.removeFirstTile(diceRoll, direction);
            }
        }
    }
}
