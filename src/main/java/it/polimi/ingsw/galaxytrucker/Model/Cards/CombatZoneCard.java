package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

import java.util.Optional;

public class CombatZoneCard extends Card {
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

    public List <Cannonball> getCannonballList() {
        return cannonballList;
    }

    @Override
    public void process() {
        super.process();

        // TODO fix with ship attributes and methods

        // Finding the ship with the least crew using Streams
        Optional <Ship> lessCrewShip = ships.stream()
                .min((s1, s2, s3, s4) -> Integer.compare(s1.getCrewSize(), s2.getCrewSize(), s3.getCrewSize(), s4.getCrewSize()));

        // Finding the ship with the least engine using Streams
        Optional <Ship> lessEngineShip = ships.stream()
                .min((s1, s2, s3, s4) -> Integer.compare(s1.getEnginePower(), s2.getEnginePower(), s3.getEnginePower(), s4.getEnginePower()));

        // Finding the ship with the least firepower using Streams
        Optional <Ship> lessFirepowerShip = ships.stream()
                .min((s1, s2, s3, s4) -> Integer.compare(s1.getFirepower(), s2.getFirepower(), s3.getFirepower(), s4.getFirepower()));

        lessCrewShip.loseDays(daysLostLessCrew);

        lessEngineShip.loseCrew(crewLostLessEngine);

        lessFirepowerShip.getHit(cannonballList);
    }
}
