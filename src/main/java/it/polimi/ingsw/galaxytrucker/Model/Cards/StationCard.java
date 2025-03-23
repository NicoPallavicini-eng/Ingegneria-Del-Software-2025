package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.StationCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StationCard extends Card {
    private final int crewNumberNeeded;
    private final List <Integer> blocks; // Integer
    private final int daysToLose;

    public StationCard(boolean levelTwo, boolean used, int crewNumberNeeded, List <Integer> blocks, int daysToLose) {
        super(levelTwo, used);
        this.crewNumberNeeded = crewNumberNeeded;
        this.blocks = blocks;
        this.daysToLose = daysToLose;
    }

    public List<Integer> getBlockList() {
        return blocks;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getCrewNumberNeeded() {
        return crewNumberNeeded;
    }

    public void acceptCardVisitor(StationCardVisitor visitor) {
        visitor.handleStationCard(this);
    }

    @Override
    public void process() {
        boolean landed = false;
        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            if (landed) { // TODO find a way to update landed
                break;
            }
            Ship ship = player.getShip();
            executor.execute(new StationCard.StationTask(ship, crewNumberNeeded, landed));
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class StationTask implements Runnable {
        private final Ship ship;
        private final int crewNumberNeeded;
        private boolean landed = false;

        public StationTask(Ship ship, int crewNumberNeeded, boolean landed) {
            this.ship = ship;
            this.crewNumberNeeded = crewNumberNeeded;
        }

        public void run() {
            System.out.println("Thread Station started for ship " + ship.color);

            if ((ship.getNumberOfCrewMembers() >= crewNumberNeeded) && playerEngages) {
                landed = true; // TODO transmit
                ship.addBlocks(blocks);
                ship.setTravelDays(- daysToLose); // negative because deducting
            }

            System.out.println("Thread Station ended for ship " + ship.color);
        }
    }
}
