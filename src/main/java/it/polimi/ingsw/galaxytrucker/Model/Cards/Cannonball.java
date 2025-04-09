package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldTile;

import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.COLUMN;
import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.ROW;
import static it.polimi.ingsw.galaxytrucker.Model.Direction.*;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation.*;

public record Cannonball(boolean bigCannonball, Direction direction, RowOrColumn rowOrColumn) {

    public static int rollTwoDice() {
        Random rand = new Random();

        // Random number between 1-6
        int die1 = rand.nextInt(6) + 1;
        int die2 = rand.nextInt(6) + 1;

        // Sum of two dice
        return die1 + die2;
    }

    public void getHit(Ship ship) {
        int diceRoll = rollTwoDice();

        if (this.rowOrColumn == ROW) {
            if (diceRoll >= 5 && diceRoll <= 9 && !ship.getRowListTiles(diceRoll).isEmpty()) {
                checkForShieldOrRemoveRow(ship, diceRoll);
            }
        } else if (this.rowOrColumn == COLUMN) {
            if (diceRoll >= 4 && diceRoll <= 10 && !ship.getColumnListTiles(diceRoll).isEmpty()) {
                checkForShieldOrRemoveColumn(ship, diceRoll);
            }
        }
    }

    private void checkForShieldOrRemoveRow(Ship ship, int diceRoll) {
        if (this.bigCannonball) {
            ProjectileUtils.removeHitTileRow(this.direction, ship, diceRoll);
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
                ProjectileUtils.removeHitTileRow(this.direction, ship, diceRoll);
            }
        }
    }

    private void checkForShieldOrRemoveColumn(Ship ship, int diceRoll) {
        if (this.bigCannonball) {
            ProjectileUtils.removeHitTileColumn(this.direction, ship, diceRoll);
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
                ProjectileUtils.removeHitTileColumn(this.direction, ship, diceRoll);
            }
        }
    }
}
