package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.SmugglersCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmugglersCard extends Card {
    private final int firepower;
    private final List <Integer> blocks;
    private final int lostBlocksNumber;
    private final int daysToLose;
    private boolean defeated = false;
    private boolean goNext;

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

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public boolean getGoNext() {
        return goNext;
    }

    @Override
    public void process() {
        List <Player> players = Game.getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            goNext = false;

            executor.execute(new SmugglersCard.SmugglersTask(player, firepower, blocks, lostBlocksNumber, daysToLose, this));

            while (!goNext);

            if (defeated) {
                break;
            }
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class SmugglersTask implements Runnable {
        private final Player player;
        private final Ship ship;
        private final int firepower;
        private final List <Integer> blocks;
        private final int lostBlocksNumber;
        private final int daysToLose;
        private final SmugglersCard card;

        public SmugglersTask(Player player, int firepower, List <Integer> blocks, int lostBlocksNumber, int daysToLose, SmugglersCard card) {
            this.player = player;
            this.ship = player.getShip();
            this.firepower = firepower;
            this.blocks = blocks;
            this.lostBlocksNumber = lostBlocksNumber;
            this.daysToLose = daysToLose;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread Smugglers started for ship " + ship.getColor());

            if (ship.getFirepower() < firepower) {
                card.setGoNext(true);

                ArrayList <CargoTile> cargoTiles = ship.getListOfCargo();
                List <Integer> cargo = new ArrayList<>();

                for (CargoTile tile : cargoTiles) {
                    cargo.addAll(tile.getTileContent());
                }

                // considering that list is ordered:
                for (int i = 0; i < lostBlocksNumber; i++) {
                    cargo.removeFirst();
                }
            } else if (ship.getFirepower() > firepower) {
                card.setDefeated(true);
                card.setGoNext(true);

                if (player.playerEngages) {
                    ship.addBlocks(blocks);
                    ship.setTravelDays(ship.getTravelDays() - daysToLose);
                }
            }

            System.out.println("Thread Smugglers ended for ship " + ship.getColor());
        }
    }
}
