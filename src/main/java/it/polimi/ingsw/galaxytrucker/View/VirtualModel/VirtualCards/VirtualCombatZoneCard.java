package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import java.util.List;

public class VirtualCombatZoneCard extends VirtualCard {
    private final int daysLostLessCrew;
    private final int crewLostLessEngine;
    private final List <VirtualCannonball> cannonballList;

    public VirtualCombatZoneCard(boolean levelTwo, boolean used, int daysLostLessCrew, int crewLostLessEngine, List <VirtualCannonball> cannonballList) {
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

    public List <VirtualCannonball> getCannonballList() {
        return cannonballList;
    }
}
