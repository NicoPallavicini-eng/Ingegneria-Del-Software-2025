package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Direction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CombatZoneCardNotL extends CombatZoneCard implements Serializable {
    public CombatZoneCardNotL(boolean levelTwo, boolean used) {
        super(levelTwo, used, 4, 0, 3, createCannonballList());
    }

    private static List<Cannonball> createCannonballList() {
        List <Cannonball> cannonballList = new ArrayList<>();
        cannonballList.add(new Cannonball(false, Direction.NORTH, RowOrColumn.COLUMN));
        cannonballList.add(new Cannonball(false, Direction.WEST, RowOrColumn.ROW));
        cannonballList.add(new Cannonball(false, Direction.EAST, RowOrColumn.ROW));
        cannonballList.add(new Cannonball(true, Direction.SOUTH, RowOrColumn.COLUMN));
        return cannonballList;
    }

//    @Override
//    public void lessCrewProcess(Ship ship) {
//        for (Cannonball cannonball : getCannonballList()) {
//            cannonball.getHit(ship);
//        }
//    }
//
//    @Override
//    public void lessEngineProcess(Ship ship) {
//        ship.setCargo(ship.getListOfCargo().size() - getCargoLost());
//        // TODO make this functional (player chooses)
//    }
//
//    @Override
//    public void lessFirepowerProcess(Ship ship) {
//        ship.setTravelDays(ship.getTravelDays() - getDaysLost());
//    }
}
