package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SlaversCard extends Card {
    private final int firepower;
    private final int credits;
    private final int crewLost;
    private final int daysToLose;
    private boolean defeated = false;
    private boolean goNext;

    public SlaversCard(boolean levelTwo, boolean used, int firepower, int credits, int crewLost, int daysToLose) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.credits = credits;
        this.crewLost = crewLost;
        this.daysToLose = daysToLose;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getNumberOfCredits() {
        return credits;
    }

    public int getNumberOfCrewLost() {
        return crewLost;
    }

    public int getNumberOfDaysToLose() {
        return daysToLose;
    }

    public void acceptCardVisitor(SlaversCardVisitor visitor) {
        visitor.handleSlaversCard(this);
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public boolean getDefeated() {
        return defeated;
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

            executor.execute(new SlaversCard.SlaversTask(player, firepower, credits, daysToLose, crewLost, this));

            while (!goNext);

            if (defeated) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class SlaversTask implements Runnable {
        private final Player player;
        private final Ship ship;
        private final int firepower;
        private final int credits;
        private final int daysToLose;
        private final int crewLost;
        private final SlaversCard card;

        public SlaversTask(Player player, int firepower, int credits, int daysToLose, int crewLost, SlaversCard card) {
            this.player = player;
            this.ship = player.getShip();
            this.firepower = firepower;
            this.credits = credits;
            this.daysToLose = daysToLose;
            this.crewLost = crewLost;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Slavers started for ship " + ship.getColor());

            if (ship.getFirepower() < firepower) {
                card.setGoNext(true);
                ship.removeCrewMembers(crewLost);
            } else if (ship.getFirepower() > firepower) {
                card.setDefeated(true);
                card.setGoNext(true);

                if (player.playerEngages) {
                    ship.setCredits(ship.getCredits() + credits);
                    ship.setTravelDays(ship.getTravelDays() - daysToLose);
                }
            }

            System.out.println("Thread Slavers ended for ship " + ship.getColor());
        }
    }
}
