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

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(new CombatZoneCard.lessCrewTask(lessCrewShip, daysLostLessCrew));
        executor.execute(new CombatZoneCard.lessEngineTask(lessEngineShip, crewLostLessEngine));
        executor.execute(new CombatZoneCard.lessFirepowerTask(lessFirepowerShip, cannonballList));

        // Shut down when all tasks are done
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

            ship.getHit(cannonballList); // TODO

            System.out.println("Thread lessCrew ended for ship " + ship.getColor());
        }
    }
}
