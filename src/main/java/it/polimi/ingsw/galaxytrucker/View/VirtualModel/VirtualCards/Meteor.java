package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import it.polimi.ingsw.galaxytrucker.View.VirtualModel.Direction;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualPlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.ConnectorType;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.EngineTile;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.ShieldTile;
import it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualTiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.COLUMN;
import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.ROW;
import static it.polimi.ingsw.galaxytrucker.Model.Direction.*;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation.*;

public record Meteor(boolean bigMeteor, Direction direction, RowOrColumn rowOrColumn) {

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
                checkForCannonOrRemoveRow(ship, diceRoll);
            }
        } else if (this.rowOrColumn == COLUMN) {
            if (diceRoll >= 4 && diceRoll <= 10 && !ship.getColumnListTiles(diceRoll).isEmpty()) {
                checkForCannonOrRemoveColumn(ship, diceRoll);
            }
        }
    }

    public void checkForCannonOrRemoveRow(Ship ship, int diceRoll) {
        if (this.bigMeteor) {
            List<EngineTile> engineTileList = ship.getListOfEngine();
            Tile firstTile = ship.getRowListTiles(diceRoll).getFirst();
            if (!engineTileList.contains(firstTile)) {
                ProjectileUtils.removeHitTileRow(this.direction, ship, diceRoll);
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
            if (this.direction == WEST) {
                if ((firstTile.getConnectors().get(1) != ConnectorType.NONE
                        || firstTile.getConnectors().get(1) != ConnectorType.CANNON_SINGLE
                        || firstTile.getConnectors().get(1) != ConnectorType.CANNON_DOUBLE
                        || firstTile.getConnectors().get(1) != ConnectorType.ENGINE_SINGLE
                        || firstTile.getConnectors().get(1) != ConnectorType.ENGINE_DOUBLE)
                        && !hasShield) {
                    ProjectileUtils.removeHitTileRow(this.direction, ship, diceRoll);
                }
            } else if (this.direction == EAST) {
                if ((firstTile.getConnectors().getLast() != ConnectorType.NONE
                        || firstTile.getConnectors().get(1) != ConnectorType.CANNON_SINGLE
                        || firstTile.getConnectors().get(1) != ConnectorType.CANNON_DOUBLE
                        || firstTile.getConnectors().get(1) != ConnectorType.ENGINE_SINGLE
                        || firstTile.getConnectors().get(1) != ConnectorType.ENGINE_DOUBLE)
                        && !hasShield) {
                    ProjectileUtils.removeHitTileRow(this.direction, ship, diceRoll);
                }
            }
        }
    }

    public void checkForCannonOrRemoveColumn(Ship ship, int diceRoll) {
        if (this.bigMeteor) {
            List<EngineTile> engineTileList = ship.getListOfEngine();
            Tile firstTile = ship.getColumnListTiles(diceRoll).getFirst();
            Tile firstLeft = ship.getColumnListTiles(diceRoll - 1).getFirst();
            Tile firstRight = ship.getColumnListTiles(diceRoll + 1).getFirst();
            if (!engineTileList.contains(firstTile) && !engineTileList.contains(firstLeft) && !engineTileList.contains(firstRight)) {
                ProjectileUtils.removeHitTileColumn(this.direction, ship, diceRoll);
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
            Tile firstTile = ship.getColumnListTiles(diceRoll).getFirst();
            if (this.direction == NORTH) {
                if ((firstTile.getConnectors().getFirst() != ConnectorType.NONE
                        || firstTile.getConnectors().get(1) != ConnectorType.CANNON_SINGLE
                        || firstTile.getConnectors().get(1) != ConnectorType.CANNON_DOUBLE
                        || firstTile.getConnectors().get(1) != ConnectorType.ENGINE_SINGLE
                        || firstTile.getConnectors().get(1) != ConnectorType.ENGINE_DOUBLE)
                        && !hasShield) {
                    ProjectileUtils.removeHitTileColumn(this.direction, ship, diceRoll);
                }
            } else if (this.direction == SOUTH) {
                if ((firstTile.getConnectors().get(2) != ConnectorType.NONE
                        || firstTile.getConnectors().get(1) != ConnectorType.CANNON_SINGLE
                        || firstTile.getConnectors().get(1) != ConnectorType.CANNON_DOUBLE
                        || firstTile.getConnectors().get(1) != ConnectorType.ENGINE_SINGLE
                        || firstTile.getConnectors().get(1) != ConnectorType.ENGINE_DOUBLE)
                        && !hasShield) {
                    ProjectileUtils.removeHitTileColumn(this.direction, ship, diceRoll);
                }
            }
        }
    }
}