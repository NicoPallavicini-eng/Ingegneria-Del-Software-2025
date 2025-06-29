package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ConnectorType;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldTile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.TilesVisitor.CannonTileVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.COLUMN;
import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.ROW;
import static it.polimi.ingsw.galaxytrucker.Model.Direction.*;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation.*;

/**
 * This record is used to create Meteor
 * @param bigMeteor
 * @param direction
 * @param rowOrColumn
 * @param diceRoll
 */
public record Meteor(boolean bigMeteor, Direction direction, RowOrColumn rowOrColumn, int diceRoll) implements Serializable {

    /**
     * @param bigMeteor
     * @param direction
     * @param rowOrColumn
     */
    public Meteor(boolean bigMeteor, Direction direction, RowOrColumn rowOrColumn) {
        this(bigMeteor, direction, rowOrColumn, rollTwoDice());
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
                checkForCannonOrRemoveRow(ship, diceRoll);
            }
        } else if (this.rowOrColumn == COLUMN) {
            if (diceRoll >= 4 && diceRoll <= 10 && !ship.getColumnListTiles(diceRoll-4).isEmpty()) {
                checkForCannonOrRemoveColumn(ship, diceRoll);
            }
        }
    }
    //TODO i think diceroll is ok now
    /**
     * This function checks Active Cannons on specific Row(diceroll).If Meteor is not in North,it also checks adjacent Rows,and if there is not,removes first element of Row.
     * @param ship
     * @param diceRoll
     */
    public void checkForCannonOrRemoveRow(Ship ship, int diceRoll) {
        diceRoll -= 5;
        if (this.bigMeteor) {
            List<Tile> list= ship.getRowListTiles(diceRoll);
            if(!direction.equals(NORTH)){
                if(diceRoll<(ship.getRowMax()-1)){
                    list.addAll(ship.getRowListTiles(diceRoll+1));
                }
                if(diceRoll>0){
                    list.addAll(ship.getRowListTiles(diceRoll-1));
                }
                 //add also the previous and following row
            }
            CannonTileVisitor ctv = new CannonTileVisitor();
            for (Tile t : list) {
                t.accept(ctv);
            }
            if (ctv.getList().stream()
                    .filter(c -> c.getActiveState() && c.getDirection() == direction).
                    toList().isEmpty()) {
                ship.removeFirstTile(diceRoll, direction);
            }
        } else {
            ArrayList<ShieldTile> shields = ship.getListOfShield();
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
            ConnectorType c;
            Tile hitTile;
            if(direction == WEST) {
                hitTile = ship.getRowListTiles(diceRoll).getFirst();
                c = hitTile.getConnectors().get(1);

            }
            else{//east
                hitTile = ship.getRowListTiles(diceRoll).getLast();
                c = hitTile.getConnectors().get(3);
            }


            if (c != ConnectorType.NONE
                    && c != ConnectorType.CANNON_SINGLE
                    && c != ConnectorType.CANNON_DOUBLE
                    && c != ConnectorType.ENGINE_SINGLE
                    && c != ConnectorType.ENGINE_DOUBLE
                    && !hasShield) {
                ship.removeFirstTile(diceRoll, direction);
            }
        }
    }
    /**
     * This function checks Active Cannons on specific Column(diceroll),and if there is not,removes first element of Column
     * @param ship
     * @param diceRoll
     */

    public void checkForCannonOrRemoveColumn(Ship ship, int diceRoll) {
        diceRoll -= 4;
        if (this.bigMeteor) {
            List<Tile> list = ship.getColumnListTiles(diceRoll);
            if (!direction.equals(NORTH)) {
                if(diceRoll<(ship.getColMax()-1)){
                    list.addAll(ship.getColumnListTiles(diceRoll + 1));
                }
                if(diceRoll>0){
                    list.addAll(ship.getColumnListTiles(diceRoll - 1));
                }
            }
            CannonTileVisitor ctv = new CannonTileVisitor();
            for (Tile t : list) {
                t.accept(ctv);
            }
            if (ctv.getList().stream()
                    .filter(c -> c.getActiveState() && c.getDirection() == direction).
                    toList().isEmpty()) {
                ship.removeFirstTile(diceRoll, direction);
            }
        } else {
            ArrayList<ShieldTile> shields = ship.getListOfShield();
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

            ConnectorType c;
            Tile hitTile;
            if(direction == NORTH) {
                hitTile = ship.getColumnListTiles(diceRoll).getFirst();
                c = hitTile.getConnectors().get(0);

            }
            else{//south
                hitTile = ship.getColumnListTiles(diceRoll).getLast();
                c = hitTile.getConnectors().get(2);
            }

            if (c != ConnectorType.NONE
                    && c != ConnectorType.CANNON_SINGLE
                    && c != ConnectorType.CANNON_DOUBLE
                    && c != ConnectorType.ENGINE_SINGLE
                    && c != ConnectorType.ENGINE_DOUBLE
                    && !hasShield) {
                ship.removeFirstTile(diceRoll, direction);
            }
        }
    }
}