package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CombatZoneCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new CombatZoneCard.CombatZoneTask(ship));
        }

        // Shut down when all tasks are done
        executor.shutdown();

        //////////////////////////////////////

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

        // TODO
        lessFirepowerShip.getHit(cannonballList);

        ////////////////////////////////////// TODO remove/change logic
    }

    static class CombatZoneTask implements Runnable {
        private final Ship ship;

        public CombatZoneTask(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread CombatZone started for ship " + ship.color);

            // TODO logic

            System.out.println("Thread CombatZone ended for ship " + ship.color);
        }
    }
}
