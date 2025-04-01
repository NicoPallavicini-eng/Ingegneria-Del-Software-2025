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
                        removeHitTile(ship, diceRoll);
                    }
                } else {
                    ArrayList <ShieldTile> shields = ship.getListOfShield();
                    boolean hasShield = false;
                    for (ShieldTile shield : shields) {
                        if ((shield.getOrientation() == NORTHWEST || shield.getOrientation() == SOUTHWEST) && this.direction == WEST
                                || (shield.getOrientation() == SOUTHEAST || shield.getOrientation() == NORTHEAST) && this.direction == EAST) {
                            hasShield = true;
                            break;
                        }
                    }
                    if (/* open connector || */ !hasShield) {
                        removeHitTile(ship, diceRoll);
                    }
                }
            }
        } else if (this.rowOrColumn == COLUMN) {
            if (diceRoll >= 4 && diceRoll <= 10 && !ship.getColumnListTiles(diceRoll).isEmpty()) {
                if (this.bigMeteor) {
                    if (/* first tile =! cannon */) {
                        removeHitTile(ship, diceRoll);
                    }
                } else {
                    ArrayList <ShieldTile> shields = ship.getListOfShield();
                    boolean hasShield = false;
                    for (ShieldTile shield : shields) {
                        if ((shield.getOrientation() == NORTHWEST || shield.getOrientation() == NORTHEAST) && this.direction == NORTH
                                || (shield.getOrientation() == SOUTHEAST || shield.getOrientation() == SOUTHWEST) && this.direction == SOUTH) {
                            hasShield = true;
                            break;
                        }
                    }
                    if (/* open connector || */ !hasShield) {
                        removeHitTile(ship, diceRoll);
                    }
                }
            }
        }
    }

    private void removeHitTile(Ship ship, int diceRoll) {
        int rowSize = ship.getRowListTiles(diceRoll).size();
        if (this.direction == WEST) {
            if (rowSize == 5) {
                ship.removeTileOnFloorPlan(diceRoll, 9);
            } else if (rowSize == 4) {
                if (ship.getColumnListTiles(9).isEmpty()) {
                    ship.removeTileOnFloorPlan(diceRoll, 8);
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 9);
                }
            } else if (rowSize == 3) {
                if (ship.getColumnListTiles(9).isEmpty()) {
                    if (ship.getRowListTiles(8).isEmpty()) {
                        ship.removeTileOnFloorPlan(diceRoll, 7);
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 8);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 9);
                }
            } else if (rowSize == 2) {
                if (ship.getColumnListTiles(9).isEmpty()) {
                    if (ship.getRowListTiles(8).isEmpty()) {
                        if (ship.getColumnListTiles(7).isEmpty()) {
                            ship.removeTileOnFloorPlan(diceRoll, 6);
                        } else {
                            ship.removeTileOnFloorPlan(diceRoll, 7);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 8);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 9);
                }
            } else if (rowSize == 1) {
                if (ship.getColumnListTiles(9).isEmpty()) {
                    if (ship.getRowListTiles(8).isEmpty()) {
                        if (ship.getColumnListTiles(7).isEmpty()) {
                            if (ship.getRowListTiles(6).isEmpty()) {
                                ship.removeTileOnFloorPlan(diceRoll, 5);
                            } else {
                                ship.removeTileOnFloorPlan(diceRoll, 6);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(diceRoll, 7);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 8);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 9);
                }
            }
        } else if (this.direction == EAST) {
            if (rowSize == 5) {
                ship.removeTileOnFloorPlan(diceRoll, 5);
            } else if (rowSize == 4) {
                if (ship.getColumnListTiles(5).isEmpty()) {
                    ship.removeTileOnFloorPlan(diceRoll, 4);
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 5);
                }
            } else if (rowSize == 3) {
                if (ship.getColumnListTiles(5).isEmpty()) {
                    if (ship.getRowListTiles(6).isEmpty()) {
                        ship.removeTileOnFloorPlan(diceRoll, 7);
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 6);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 5);
                }
            } else if (rowSize == 2) {
                if (ship.getColumnListTiles(5).isEmpty()) {
                    if (ship.getRowListTiles(6).isEmpty()) {
                        if (ship.getColumnListTiles(7).isEmpty()) {
                            ship.removeTileOnFloorPlan(diceRoll, 8);
                        } else {
                            ship.removeTileOnFloorPlan(diceRoll, 7);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 6);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 5);
                }
            } else if (rowSize == 1) {
                if (ship.getColumnListTiles(5).isEmpty()) {
                    if (ship.getRowListTiles(6).isEmpty()) {
                        if (ship.getColumnListTiles(7).isEmpty()) {
                            if (ship.getRowListTiles(8).isEmpty()) {
                                ship.removeTileOnFloorPlan(diceRoll, 9);
                            } else {
                                ship.removeTileOnFloorPlan(diceRoll, 8);
                            }
                        } else {
                            ship.removeTileOnFloorPlan(diceRoll, 7);
                        }
                    } else {
                        ship.removeTileOnFloorPlan(diceRoll, 6);
                    }
                } else {
                    ship.removeTileOnFloorPlan(diceRoll, 5);
                }
            }
        }
    }
}
