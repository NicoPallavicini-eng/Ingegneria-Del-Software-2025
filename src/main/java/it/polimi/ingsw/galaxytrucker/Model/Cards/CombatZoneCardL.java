package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CombatZoneCardL extends CombatZoneCard implements Serializable {
    public CombatZoneCardL(boolean levelTwo, boolean used) {
        super(levelTwo, used, 3, 2, 0, createCannonballList());
    }

    private static List<Cannonball> createCannonballList() {
        List <Cannonball> cannonballList = new ArrayList<>();
        cannonballList.add(new Cannonball(false, Direction.SOUTH, RowOrColumn.COLUMN));
        cannonballList.add(new Cannonball(true, Direction.SOUTH, RowOrColumn.COLUMN));
        return cannonballList;
    }


}
