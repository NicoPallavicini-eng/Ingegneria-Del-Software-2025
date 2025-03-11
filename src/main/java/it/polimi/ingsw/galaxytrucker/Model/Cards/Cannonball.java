package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;

public class Cannonball {
    private final boolean bigCannonball;
    private final Direction direction;
    private final RowOrColumn rowOrColumn;

    public Cannonball(boolean bigCannonball, Direction direction, RowOrColumn rowOrColumn) {
        this.bigCannonball = bigCannonball;
        this.direction = direction;
        this.rowOrColumn = rowOrColumn;
    }

    public boolean isBigCannonball() {
        return bigCannonball;
    }

    public Direction getDirection() {
        return direction;
    }

    public RowOrColumn getRowOrColumn() {
        return rowOrColumn;
    }
}
