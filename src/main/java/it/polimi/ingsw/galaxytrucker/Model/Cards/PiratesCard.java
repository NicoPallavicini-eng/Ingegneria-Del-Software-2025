package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.PiratesCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PiratesCard extends Card {
    private final int firepower;
    private final int credits;
    private final int daysToLose;
    private final List <Cannonball> cannonballList;
    private boolean defeated = false;

    public PiratesCard(boolean levelTwo, boolean used, int firepower, int credits, int daysToLose, List <Cannonball> cannonballList) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.credits = credits;
        this.daysToLose = daysToLose;
        this.cannonballList = cannonballList;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public List <Cannonball> getCannonballList() {
        return cannonballList;
    }

    public void acceptCardVisitor(PiratesCardVisitor visitor) {
        visitor.handlePiratesCard(this);
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
            executor.execute(new PiratesCard.PiratesTask(ship, firepower, credits, daysToLose, cannonballList, this));

            if (defeated) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class PiratesTask implements Runnable {
        private final Ship ship;
        private final int firepower;
        private final int credits;
        private final int daysToLose;
        private final List <Cannonball> cannonballList;
        private final PiratesCard card;

        public PiratesTask(Ship ship, int firepower, int credits, int daysToLose, List <Cannonball> cannonballList, PiratesCard card) {
            this.ship = ship;
            this.firepower = firepower;
            this.credits = credits;
            this.daysToLose = daysToLose;
            this.cannonballList = cannonballList;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Pirates started for ship " + ship.color);

            if (ship.getFirepower() < firepower) {
                // getShot(); TODO
            } else if (ship.getFirepower() > firepower) {
                card.setDefeated(true);

                if (player.playerEngages) {
                    ship.addCredits(credits);
                    ship.setTravelDays(- daysToLose); // negative because deducting
                }
            }

            System.out.println("Thread Pirates ended for ship " + ship.color);
        }
    }
}
