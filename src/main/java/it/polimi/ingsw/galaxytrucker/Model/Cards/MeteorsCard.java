package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.MeteorsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteorsCard extends Card {
    private final List <Meteor> meteors;

    public MeteorsCard(boolean levelTwo, boolean used, List <Meteor> meteors) {
        super(levelTwo, used);
        this.meteors = meteors;
    }

    public List <Meteor> getMeteorsList() {
        return meteors;
    }

    public static int rollTwoDice() {
        Random rand = new Random();

        // Random number between 1-6
        int die1 = rand.nextInt(6) + 1;
        int die2 = rand.nextInt(6) + 1;

        // Sum of two dice
        return die1 + die2;
    }

    public void acceptCardVisitor(MeteorsCardVisitor visitor) {
        visitor.handleMeteorsCard(this);
    }

    @Override
    public void process() {
        int meteorsNumber = this.meteors.size();
        int[] diceRoll = new int[meteorsNumber];

        for (int i = 0; i < meteorsNumber; i++) {
            diceRoll[i] = rollTwoDice();
        }

        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        int threadNumber = 0;
        for (Player player : players) {
            executor.execute(new MeteorsTask(threadNumber, diceRoll));
            threadNumber++;
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class MeteorsTask implements Runnable {
        private final int threadNumber;
        private final int[] diceRoll;

        public MeteorsTask(int threadNumber, int[] diceRoll) {
            this.threadNumber = threadNumber;
            this.diceRoll = diceRoll;
        }

        public void run() {
            List <Player> players = getListOfPlayers();
            Player player = players.get(threadNumber);
            Ship ship = player.getShip();

            System.out.println("Thread Meteors stared for ship " + ship.color + " using thread " + threadNumber);

            // TODO logic

            // if column/row is part of ship {
                // if meteor.isBig() && there is a tile {
                    // getHit()
                // } else if !meteor.isBig() && there ia a tile {
                    // if (hits column && !cannon in line && !shield) |
                    // | (hits row && !cannon in row or adjacent rows && !shield) {
                        // getHit()
                    // }
                // }
            // }

            System.out.println("Thread Meteors ended for ship " + ship.color + " using thread " + threadNumber);
        }
    }
}
