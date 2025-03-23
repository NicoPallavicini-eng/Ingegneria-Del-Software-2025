package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CombatZoneCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public void acceptCardVisitor(CombatZoneCardVisitor visitor) {
        visitor.handleCombatZoneCard(this);
    }

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        // Get list of ships using Streams
        List <Ship> ships = players.stream()
                .map(Player::getShip).collect(Collectors.toList());

        // Finding the ship with the least crew using Streams
        Ship lessCrewShip = ships.stream()
                .min(Comparator.comparingInt(Ship::getNumberOfCrewMembers));

        // Finding the ship with the least engine using Streams
        Ship lessEngineShip = ships.stream()
                .min(Comparator.comparingInt(Ship::getEnginePower));

        // Finding the ship with the least firepower using Streams
        Ship lessFirepowerShip = ships.stream()
                .min(Comparator.comparingInt(Ship::getFirepower));

        lessCrewShip.setTravelDays(- daysLostLessCrew); // negative because deducting

        lessEngineShip.removeCrewMembers(crewLostLessEngine);

        // TODO understand how to
        lessFirepowerShip.getHit(cannonballList);
    }
}
