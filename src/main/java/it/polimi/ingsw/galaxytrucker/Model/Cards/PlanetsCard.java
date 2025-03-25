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
    private boolean[] landed;
    private boolean goNext;

    public PlanetsCard(boolean levelTwo, boolean used, List <Planet> planets, int daysToLose) {
        super(levelTwo, used);
        int dim = planets.size();
        this.planets = planets;
        this.daysToLose = daysToLose;
        landed = new boolean[dim];

        for (int i = 0; i < dim; i++) {
            landed[i] = false;
        }
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

    public void setLanded(boolean landed, int i) {
        this.landed[i] = landed;
    }

    public boolean getLanded(int i) {
        return landed[i];
    }

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public boolean getGoNext() {
        return goNext;
    }

    @Override
    public void process() {
        int planetsNumber = planets.size();
        boolean availablePlanets = true;

        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
           goNext = false;

            Ship ship = player.getShip();
            executor.execute(new PlanetsCard.PlanetsTask(ship, planets, daysToLose, this));

            while (!goNext);

            // check for remaining planets
            int i = 0;
            boolean someLeft = false;
            for (Planet planet : planets) {
                if (!landed[i]) {
                    someLeft = true;
                }
                i++;
            }

            if (!someLeft) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class PlanetsTask implements Runnable {
        private final Ship ship;
        private final List <Planet> planets;
        private final int daysToLose;
        private final PlanetsCard card;

        public PlanetsTask(Ship ship, List <Planet> planets, int daysToLose, PlanetsCard card) {
            this.ship = ship;
            this.planets = planets;
            this.daysToLose = daysToLose;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Planets started for ship " + ship.getColor());

            if (playerEngages) {
                // TODO choice logic
                Planet chosenPlanet = getChosenPlanet();

                int i = 0;
                for (Planet planet : planets) {
                    if (chosenPlanet == planet) {
                        card.setLanded(true, i);
                        card.setGoNext(true);
                    }
                    i++;
                }

                chosenPlanet.setShipLanded(player.getShip());

                ship.addBlocks(chosenPlanet.getBlocksList());
                ship.setTravelDays(- daysToLose); // negative because deducting
            } else {
                card.setGoNext(true);
            }

            System.out.println("Thread Planets ended for ship " + ship.getColor());
        }
    }
}
