package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PlanetsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlanetsCard extends Card {
    private final List <Planet> planets;
    private final int daysToLose;

    public PlanetsCard(boolean levelTwo, boolean used, List <Planet> planets, int daysToLose) {
        super(levelTwo, used);
        this.planets = planets;
        this.daysToLose = daysToLose;
    }

    public List <Planet> getPlanetsList() {
        return planets;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public void acceptCardVisitor(PlanetsCardVisitor visitor) {
        visitor.handlePlanetsCard(this);
    }

    @Override
    public void process() {
        int planetsNumber = planets.size();
        boolean availablePlanets = true;

        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new PlanetsCard.PlanetsTask(ship));
        }

        // Shut down when all tasks are done
        executor.shutdown();

        //////////// TODO move logic down

        for (Player player : players) {
            if (player.playerEngages) {
                // TODO implement player choosing planet
                Planet chosenPlanet = getChosenPlanet();
                chosenPlanet.setShipLanded(player.getShip());

                ship.addBlocks(chosenPlanet.getBlocksList());
                ship.setTravelDays(- daysToLose); // negative because deducting

                // Check for available planets
                availablePlanets = false;
                for (Planet planet : planets) {
                    if (planet.getShipLanded() == NULL) {
                        availablePlanets = true;
                    }
                }
            }

            // TODO should be in if?
            if (!availablePlanets) {
                break;
            }
        }
    }

    static class PlanetsTask implements Runnable {
        private final Ship ship;

        public PlanetsTask(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread Planets started for ship " + ship.color);

            // TODO move logic here

            System.out.println("Thread Planets ended for ship " + ship.color);
        }
    }
}
