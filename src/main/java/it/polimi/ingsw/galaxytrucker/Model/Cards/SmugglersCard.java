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

    @Override
    public void process() {
        boolean defeated = false;

        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new SmugglersCard.SmugglersTask(ship));
        }

        // Shut down when all tasks are done
        executor.shutdown();

        ////////////////////// TODO move logic down

        for (Player player : players) {
            Ship ship = player.getShip();
            if (ship.getFirepower() < firepower) {
                ship.removeBlocks(lostBlocksNumber); // TODO change logic with getListOfCargo()
            } else if (ship.getFirepower() > firepower) {
                defeated = true;
                if (player.playerEngages) {
                    ship.addBlocks(blocks);
                    ship.setTravelDays(- daysToLose); // negative because deducting
                }
            }
            if (defeated) {
                break;
            }
        }
    }

    static class SmugglersTask implements Runnable {
        private final Ship ship;

        public SmugglersTask(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread Smugglers started for ship " + ship.color);

            // TODO move logic here

            System.out.println("Thread Smugglers ended for ship " + ship.color);
        }
    }
}
