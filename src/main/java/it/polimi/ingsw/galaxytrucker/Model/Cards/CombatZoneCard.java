package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CombatZoneCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class CombatZoneCard extends Card {
    private final int daysLostLessCrew;
    private final int crewLostLessEngine;
    private final List <Cannonball> cannonballList;

    public CombatZoneCard(boolean levelTwo, boolean used, CombatZoneCardVisitor visitor, int daysLostLessCrew, int crewLostLessEngine, List <Cannonball> cannonballList) {
        super(levelTwo, used, visitor);
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

    public void acceptCardVisitorAlternative(CombatZoneCardVisitor visitor, List <Ship> lessCrewShips, List <Ship> lessEngineShips, List <Ship> lessFirepowerShips) {

        for (Ship ship : lessCrewShips) {
            visitor.handleCombatZoneCardLessCrew(this, ship);
        }

        for (Ship ship : lessEngineShips) {
            visitor.handleCombatZoneCardLessEngine(this, ship);
        }

        for (Ship ship : lessFirepowerShips) {
            visitor.handleCombatZoneCardLessFirepower(this, ship);
        }
    }

    public void acceptNextVisitor(GameState state, CombatZoneCardVisitor visitor, Game game, Card card) {
        visitor.setNextStateCombatZoneCard(state, game, this);
    }

    public void lessCrewProcess(Ship ship) {
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
}
