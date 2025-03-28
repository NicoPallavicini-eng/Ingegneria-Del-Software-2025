package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StationCard extends Card {
    private final int crewNumberNeeded;
    private final List <Integer> blocks; // Integer
    private final int daysToLose;
    private boolean landed = false;
    private boolean goNext;

    public StationCard(boolean levelTwo, boolean used, StationCardVisitor visitor, int crewNumberNeeded, List <Integer> blocks, int daysToLose) {
        super(levelTwo, used, visitor);
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

    public void acceptCardVisitor(TravellingState state, StationCardVisitor visitor, Player player) {
        visitor.handleStationCard(state, this, player);
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

    public void process(boolean accomplished) {
        List <Player> players = Game.getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            goNext = false;

            executor.execute(new StationCard.StationTask(player, crewNumberNeeded, blocks, daysToLose, this));

            while (!goNext);

            if (landed) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class StationTask implements Runnable {
        private final Player player;
        private final Ship ship;
        private final int crewNumberNeeded;
        private final List <Integer> blocks;
        private final int daysToLose;
        private final StationCard card;

        public StationTask(Player player, int crewNumberNeeded, List <Integer> blocks, int daysToLose, StationCard card) {
            this.player = player;
            this.ship = player.getShip();
            this.crewNumberNeeded = crewNumberNeeded;
            this.blocks = blocks;
            this.daysToLose = daysToLose;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Station started for ship " + ship.getColor());

            if ((ship.getNumberOfCrewMembers() >= crewNumberNeeded) && player.playerEngages) {
                // sets landed to true
                card.setLanded(true);
                card.setGoNext(true);

                ship.addBlocks(blocks);
                ship.setTravelDays(ship.getTravelDays() - daysToLose);

            } else {
                card.setGoNext(true);
            }

            System.out.println("Thread Station ended for ship " + ship.getColor());
        }
    }
}
