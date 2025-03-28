package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlanetsCard extends Card {
    private final List <Planet> planets;
    private final int daysToLose;
    private boolean[] landed;
    private boolean goNext;

    public PlanetsCard(boolean levelTwo, boolean used, PlanetsCardVisitor visitor, List <Planet> planets, int daysToLose) {
        super(levelTwo, used, visitor);
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

    public void acceptCardVisitor(TravellingState state, PlanetsCardVisitor visitor) {
        visitor.handlePlanetsCard(state, this);
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

    public void process(List <Boolean> landed) {
        int planetsNumber = planets.size();
        boolean availablePlanets = true;

        List <Player> players = Game.getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
           goNext = false;

            executor.execute(new PlanetsCard.PlanetsTask(player, planets, daysToLose, this));

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
        private final Player player;
        private final Ship ship;
        private final List <Planet> planets;
        private final int daysToLose;
        private final PlanetsCard card;

        public PlanetsTask(Player player, List <Planet> planets, int daysToLose, PlanetsCard card) {
            this.player = player;
            this.ship = player.getShip();
            this.planets = planets;
            this.daysToLose = daysToLose;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Planets started for ship " + ship.getColor());

            if (player.playerEngages) {
                Planet chosenPlanet = player.getChosenPlanet();

                int i = 0;
                for (Planet planet : planets) {
                    if (chosenPlanet == planet) {
                        card.setLanded(true, i);
                        card.setGoNext(true);
                    }
                    i++;
                }

                chosenPlanet.setShipLanded(ship);

                ship.addBlocks(chosenPlanet.getBlocks());
                ship.setTravelDays(ship.getTravelDays() - daysToLose);
            } else {
                card.setGoNext(true);
            }

            System.out.println("Thread Planets ended for ship " + ship.getColor());
        }
    }
}
