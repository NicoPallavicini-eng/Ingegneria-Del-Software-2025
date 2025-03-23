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

    @Override
    public void process() {
        boolean defeated = false;

        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new PiratesCard.PiratesTask(ship));
        }

        // Shut down when all tasks are done
        executor.shutdown();

        /////////////////// TODO move down

        for (Player player : players) {
            Ship ship = player.getShip();
            if (ship.getFirepower() < firepower) {
                // get shot TODO capire
            } else if (ship.getFirepower() > firepower) {
                defeated = true;
                if (player.playerEngages) {
                    ship.addCredits(credits);
                    ship.setTravelDays(- daysToLose); // negative because deducting
                }
            }
            if (defeated) {
                break;
            }
        }
    }

    static class PiratesTask implements Runnable {
        private final Ship ship;

        public PiratesTask(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread Pirates started for ship " + ship.color);

            // TODO move logic here

            System.out.println("Thread Pirates ended for ship " + ship.color);
        }
    }
}
