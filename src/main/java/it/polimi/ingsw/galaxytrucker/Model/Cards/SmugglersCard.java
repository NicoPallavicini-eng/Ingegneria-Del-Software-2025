package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.SmugglersCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmugglersCard extends Card {
    private final int firepower;
    private final List <Integer> blocks;
    private final int lostBlocksNumber;
    private final int daysToLose;
    private boolean defeated = false;

    public SmugglersCard(boolean levelTwo, boolean used, int firepower, List <Integer> blocks, int lostBlocksNumber, int daysToLose) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.blocks = blocks;
        this.lostBlocksNumber = lostBlocksNumber;
        this.daysToLose = daysToLose;
    }

    public int getFirepower() {
        return firepower;
    }

    public List <Integer> getBlocksList() {
        return blocks;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getLostBlocksNumber() {
        return lostBlocksNumber;
    }

    public void acceptCardVisitor(SmugglersCardVisitor visitor) {
        visitor.handleSmugglersCard(this);
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
            executor.execute(new SmugglersCard.SmugglersTask(ship, firepower, blocks, lostBlocksNumber, daysToLose, this));

            if (defeated) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class SmugglersTask implements Runnable {
        private final Ship ship;
        private final int firepower;
        private final List <Integer> blocks;
        private final int lostBlocksNumber;
        private final int daysToLose;
        private final SmugglersCard card;

        public SmugglersTask(Ship ship, int firepower, List <Integer> blocks, int lostBlocksNumber, int daysToLose, SmugglersCard card) {
            this.ship = ship;
            this.firepower = firepower;
            this.blocks = blocks;
            this.lostBlocksNumber = lostBlocksNumber;
            this.daysToLose = daysToLose;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Smugglers started for ship " + ship.color);

            if (ship.getFirepower() < firepower) {
                List <Integer> cargo = ship.getListOfCargo();

                // considering that list is ordered:
                for (int i = 0; i < lostBlocksNumber; i++) {
                    cargo.removeFirst();
                }
            } else if (ship.getFirepower() > firepower) {
                card.setDefeated(true);

                if (player.playerEngages) {
                    ship.addBlocks(blocks);
                    ship.setTravelDays(- daysToLose); // negative because deducting
                }
            }

            System.out.println("Thread Smugglers ended for ship " + ship.color);
        }
    }
}
