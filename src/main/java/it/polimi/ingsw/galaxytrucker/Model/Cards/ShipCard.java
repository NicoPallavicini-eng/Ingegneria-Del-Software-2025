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
    private boolean landed = false;

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

    public void setLanded(boolean landed) {
        this.landed = landed;
    }

    public boolean getLanded() {
        return landed;
    }

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new ShipCard.ShipTask(ship, crewNumberLost, credits, daysToLose, this));

            if (landed) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class ShipTask implements Runnable {
        private final Ship ship;
        private final int crewNumberLost;
        private final int credits;
        private final int daysToLose;
        private final ShipCard card;

        public ShipTask(Ship ship, int crewNumberLost, int credits, int daysToLose, ShipCard card) {
            this.ship = ship;
            this.crewNumberLost = crewNumberLost;
            this.credits = credits;
            this.daysToLose = daysToLose;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Ship started for ship " + ship.color);

            if ((ship.getNumberOfCrewMembers() >= crewNumberLost) && playerEngages) {
                // sets landed to true
                card.setLanded(true);

                ship.addCredits(credits);
                ship.removeCrewMembers(crewNumberLost);
                ship.setTravelDays(- daysToLose); // negative because deducting
            }

            System.out.println("Thread Ship ended for ship " + ship.color);
        }
    }
}
