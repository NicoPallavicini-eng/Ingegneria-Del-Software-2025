package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.ShipCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShipCard extends Card {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;

    public ShipCard(boolean levelTwo, boolean used, int crewNumberLost, int credits, int daysToLose) {
        super(levelTwo, used);
        this.crewNumberLost = crewNumberLost;
        this.credits = credits;
        this.daysToLose = daysToLose;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getCrewNumberLost() {
        return crewNumberLost;
    }

    public void acceptCardVisitor(ShipCardVisitor visitor) {
        visitor.handleShipCard(this);
    }

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new ShipCard.ShipTask(ship));
        }

        // Shut down when all tasks are done
        executor.shutdown();

        ///////////////////// TODO move logic down

        for (Player player : players) {
            if (player.playerEngages) {
                Ship ship = player.getShip();
                ship.addCredits(credits);
                ship.removeCrewMembers(crewNumberLost);
                ship.setTravelDays(- daysToLose); // negative because deducting
                break;
            }
        }
    }

    static class ShipTask implements Runnable {
        private final Ship ship;

        public ShipTask(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread Ship started for ship " + ship.color);

            // TODO move logic here

            System.out.println("Thread Ship ended for ship " + ship.color);
        }
    }
}
