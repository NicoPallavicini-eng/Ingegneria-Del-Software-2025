package it.polimi.ingsw.galaxytrucker.View.VirtualModel.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CombatZoneCardVisitor;

import java.util.List;

public class CombatZoneCard extends Card {
    private final int daysLostLessCrew;
    private final int crewLostLessEngine;
    private final List <Cannonball> cannonballList;

    public CombatZoneCard(boolean levelTwo, boolean used, int daysLostLessCrew, int crewLostLessEngine, List <Cannonball> cannonballList) {
        super(levelTwo, used);
        this.daysLostLessCrew = daysLostLessCrew;
        this.crewLostLessEngine = crewLostLessEngine;
        this.cannonballList = cannonballList;
    }

    public int getDaysLostLessCrew() {
        return daysLostLessCrew;
    }

    public int getCrewLostLessEngine() {
        return crewLostLessEngine;
    }

    public List <Cannonball> getCannonballList() {
        return cannonballList;
    }

/*    public void lessCrewProcess(Ship ship) {
        ship.setTravelDays(ship.getTravelDays() - daysLostLessCrew);
    }

    public void lessEngineProcess(Ship ship) {
        ship.setCrewMembers(ship.getNumberOfCrewMembers() - crewLostLessEngine);
    }

    public void lessFirepowerProcess(Ship ship) {
        for (Cannonball cannonball : cannonballList) {
            cannonball.getHit(ship);
        }
    }

 */
}
