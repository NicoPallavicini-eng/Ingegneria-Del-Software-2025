package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.SlaversCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SlaversCard extends Card {
    private final int firepower;
    private final int credits;
    private final int crewLost;
    private final int daysToLose;
    private boolean defeated = false;

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

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new SlaversCard.SlaversTask(ship, firepower, credits, daysToLose, crewLost, this));

            if (defeated) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class SlaversTask implements Runnable {
        private final Ship ship;
        private final int firepower;
        private final int credits;
        private final int daysToLose;
        private final int crewLost;
        private final SlaversCard card;

        public SlaversTask(Ship ship, int firepower, int credits, int daysToLose, int crewLost, SlaversCard card) {
            this.ship = ship;
            this.firepower = firepower;
            this.credits = credits;
            this.daysToLose = daysToLose;
            this.crewLost = crewLost;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Slavers started for ship " + ship.color);

            if (ship.getFirepower() < firepower) {
                ship.removeCrewMembers(crewLost);
            } else if (ship.getFirepower() > firepower) {
                card.setDefeated(true);

                if (player.playerEngages) {
                    ship.addCredits(credits);
                    ship.setTravelDays(- daysToLose); // negative because deducting
                }
            }

            System.out.println("Thread Slavers ended for ship " + ship.color);
        }
    }
}
