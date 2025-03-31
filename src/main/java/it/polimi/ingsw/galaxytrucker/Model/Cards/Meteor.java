package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldTile;

import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.COLUMN;
import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.ROW;
import static it.polimi.ingsw.galaxytrucker.Model.Direction.*;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation.*;

public class Meteor {
    private final boolean bigMeteor;
    private final Direction direction;
    private final RowOrColumn rowOrColumn;

    public Meteor(boolean bigMeteor, Direction direction, RowOrColumn rowOrColumn) {
        this.bigMeteor = bigMeteor;
        this.direction = direction;
        this.rowOrColumn = rowOrColumn;
    }

    public boolean isBigMeteor() {
        return bigMeteor;
    }

    public Direction getDirection() {
        return direction;
    }

    public RowOrColumn getRowOrColumn() {
        return rowOrColumn;
    }

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
                if (this.bigMeteor) {
                    if (/* first tile or adjacent ones =! cannon */) {
                        // ship.getHit(); TODO
                    }
                } else {
                    ArrayList <ShieldTile> shields = ship.getListOfShield();
                    boolean hasShield = true;
                    for (ShieldTile shield : shields) { // TODO fix
                        if ((shield.getOrientation() == NORTHWEST || shield.getOrientation() == SOUTHWEST) && this.direction != WEST
                                || (shield.getOrientation() == SOUTHEAST || shield.getOrientation() == NORTHEAST) && this.direction != EAST) {
                            hasShield = false;
                        }
                    }
                    if (/* open connector || */ !hasShield) {
                        // ship.getHit(); TODO
                    }
                }
            }
        } else if (this.rowOrColumn == COLUMN) {
            if (diceRoll >= 4 && diceRoll <= 10 && !ship.getColumnListTiles(diceRoll).isEmpty()) {
                if (this.bigMeteor) {
                    if (/* first tile =! cannon */) {
                        // ship.getHit(); TODO
                    }
                } else {
                    ArrayList <ShieldTile> shields = ship.getListOfShield();
                    boolean hasShield = true;
                    for (ShieldTile shield : shields) {
                        if ((shield.getOrientation() == NORTHWEST || shield.getOrientation() == NORTHEAST) && this.direction != NORTH
                                || (shield.getOrientation() == SOUTHEAST || shield.getOrientation() == SOUTHWEST) && this.direction != SOUTH) {
                            hasShield = false;
                        }
                    }
                    if (/* open connector || */ !hasShield) {
                        // ship.getHit(); TODO
                    }
                }
            }
        }
    }
}
