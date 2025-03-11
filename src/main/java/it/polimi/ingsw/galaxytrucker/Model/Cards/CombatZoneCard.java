package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.util.List;

public class CombatZoneCard extends Card {
    // TODO add Ship link
    private final int daysLostLessCrew;
    private Ship lessCrewShip;
    private final int crewLostLessEngine;
    private Ship lessEngineShip;
    private Ship lessFirepowerShip;
    private final List <Cannonball> cannonballList;

    public CombatZoneCard(boolean levelTwo, boolean used, int daysLostLessCrew, int crewLostLessEngine, List <Cannonball> cannonballList) {
        super(levelTwo, used);
        this.category = CardCategory.COMBAT_ZONE;
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

    public List<Cannonball> getCannonballList() {
        return cannonballList;
    }
}
