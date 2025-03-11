package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;

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
}
