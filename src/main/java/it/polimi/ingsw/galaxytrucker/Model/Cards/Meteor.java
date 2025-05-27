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

public record Meteor(boolean bigMeteor, Direction direction, RowOrColumn rowOrColumn, int diceRoll) implements Serializable {

    public Meteor(boolean bigMeteor, Direction direction, RowOrColumn rowOrColumn) {
        this(bigMeteor, direction, rowOrColumn, rollTwoDice());
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

    public void checkForCannonOrRemoveRow(Ship ship, int diceRoll) {
        if (this.bigMeteor) {
            List<Tile> list= ship.getRowListTiles(diceRoll);
            if(!direction.equals(NORTH)){
                list.addAll(ship.getRowListTiles(diceRoll+1));
                list.addAll(ship.getRowListTiles(diceRoll-1));
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
            Tile firstTile = ship.getRowListTiles(diceRoll).getFirst();
            ConnectorType c;
            if (this.direction == WEST) {
                c = firstTile.getConnectors().get(1);
            } else {//east
                c = firstTile.getConnectors().get(3);
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

    public void checkForCannonOrRemoveColumn(Ship ship, int diceRoll) {
        if (this.bigMeteor) {
            List<Tile> list = ship.getColumnListTiles(diceRoll);
            if (!direction.equals(NORTH)) {
                list.addAll(ship.getColumnListTiles(diceRoll + 1));
                list.addAll(ship.getColumnListTiles(diceRoll - 1));
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
            Tile firstTile = ship.getRowListTiles(diceRoll).getFirst();
            ConnectorType c;
            if (this.direction == NORTH) {
                c = firstTile.getConnectors().get(0);
            } else {//south
                c = firstTile.getConnectors().get(2);
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