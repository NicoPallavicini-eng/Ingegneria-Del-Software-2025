package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        // Get list of ships
        List <Ship> ships = null;
        for (Player player : players) {
            ships.add(player.getShip());
        }

        // Find ship with the least crew
        List <Ship> lessCrewShips = null;
        lessCrewShips.add(ships.getFirst());
        for (Ship ship : ships) {
            if (ship.getNumberOfCrewMembers() == lessCrewShips.getFirst().getNumberOfCrewMembers()) {
                lessCrewShips.add(ship);
            } else if (ship.getNumberOfCrewMembers() < lessCrewShips.getFirst().getNumberOfCrewMembers()) {
                lessCrewShips.removeAll(lessCrewShips);
                lessCrewShips.add(ship);
            }
        }

        // Find ship with the least engine power
        List <Ship> lessEngineShips = null;
        lessEngineShips.add(ships.getFirst());
        for (Ship ship : ships) {
            if (ship.getEnginePower() == lessEngineShips.getFirst().getEnginePower()) {
                lessEngineShips.add(ship);
            } else if (ship.getEnginePower() < lessEngineShips.getFirst().getEnginePower()) {
                lessEngineShips.removeAll(lessEngineShips);
                lessEngineShips.add(ship);
            }
        }

        // Find ship with the least firepower
        List <Ship> lessFirepowerShips = null;
        lessFirepowerShips.add(ships.getFirst());
        for (Ship ship : ships) {
            if (ship.getFirepower() == lessFirepowerShips.getFirst().getFirepower()) {
                lessFirepowerShips.add(ship);
            } else if (ship.getFirepower() < lessFirepowerShips.getFirst().getFirepower()) {
                lessFirepowerShips.removeAll(lessFirepowerShips);
                lessFirepowerShips.add(ship);
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(lessCrewShips.size() + lessEngineShips.size() + lessFirepowerShips.size());

        for (Ship ship : lessCrewShips) {
            executor.execute(new CombatZoneCard.lessCrewTask(ship, daysLostLessCrew));
        }

        for (Ship ship : lessCrewShips) {
            executor.execute(new CombatZoneCard.lessEngineTask(ship, crewLostLessEngine));
        }

        for (Ship ship : lessCrewShips) {
            executor.execute(new CombatZoneCard.lessFirepowerTask(ship, cannonballList));
        }

        // Shut down when tasks done
        executor.shutdown();
    }

    static class lessCrewTask implements Runnable {
        private final Ship ship;
        private final int daysLostLessCrew;

        public lessCrewTask(Ship ship, int daysLostLessCrew) {
            this.ship = ship;
            this.daysLostLessCrew = daysLostLessCrew;
        }

        public void run() {
            System.out.println("Thread lessCrew started for ship " + ship.getColor());

            ship.setTravelDays(- daysLostLessCrew); // negative because deducting

            System.out.println("Thread lessCrew ended for ship " + ship.getColor());
        }
    }

    static class lessEngineTask implements Runnable {
        private final Ship ship;
        private final int crewLostLessEngine;

        public lessEngineTask(Ship ship, int crewLostLessEngine) {
            this.ship = ship;
            this.crewLostLessEngine = crewLostLessEngine;
        }

        public void run() {
            System.out.println("Thread lessCrew started for ship " + ship.getColor());

            ship.removeCrewMembers(crewLostLessEngine);

            System.out.println("Thread lessCrew ended for ship " + ship.getColor());
        }
    }

    static class lessFirepowerTask implements Runnable {
        private final Ship ship;
        private final List <Cannonball> cannonballList;

        public lessFirepowerTask(Ship ship, List <Cannonball> cannonballList) {
            this.ship = ship;
            this.cannonballList = cannonballList;
        }

        public void run() {
            System.out.println("Thread lessCrew started for ship " + ship.getColor());

            for (Cannonball cannonball : cannonballList) {
                cannonball.getHit(ship);
            }

            System.out.println("Thread lessCrew ended for ship " + ship.getColor());
        }
    }
}
