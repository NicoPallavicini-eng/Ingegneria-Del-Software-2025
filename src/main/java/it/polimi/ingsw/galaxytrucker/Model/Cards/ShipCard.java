package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShipCard extends Card {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;
    private boolean landed = false;
    private boolean goNext;

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

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public boolean getGoNext() {
        return goNext;
    }

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            goNext = false;

            executor.execute(new ShipCard.ShipTask(player, crewNumberLost, credits, daysToLose, this));

            while (!goNext);

            if (landed) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class ShipTask implements Runnable {
        private final Ship ship;
        private final Player player;
        private final int crewNumberLost;
        private final int credits;
        private final int daysToLose;
        private final ShipCard card;

        public ShipTask(Player player, int crewNumberLost, int credits, int daysToLose, ShipCard card) {
            this.player = player;
            this.ship = player.getShip();
            this.crewNumberLost = crewNumberLost;
            this.credits = credits;
            this.daysToLose = daysToLose;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Ship started for ship " + ship.getColor());

            if ((ship.getNumberOfCrewMembers() >= crewNumberLost) && player.playerEngages) {
                // sets landed to true
                card.setLanded(true);
                card.setGoNext(true);

                ship.setCredits(ship.getCredits() + credits);
                ship.setCrewMembers(ship.getNumberOfCrewMembers() - crewNumberLost);
                ship.setTravelDays(ship.getTravelDays() - daysToLose);
            } else {
                card.setGoNext(true);
            }

            System.out.println("Thread Ship ended for ship " + ship.getColor());
        }
    }
}
