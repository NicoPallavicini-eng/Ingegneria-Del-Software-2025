package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StardustCard extends Card {
    public StardustCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
        this.category = CardCategory.STARDUST;
    }

    @Override
    public void process() {
        super.process();

        List<Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        int threadNumber = 0;
        for (Player player : players) {
            executor.execute(new StardustCard.StardustTask(threadNumber));
            threadNumber++;
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class StardustTask implements Runnable {
        private final int threadNumber;

        public StardustTask(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        public void run() {
            List <Player> players = getListOfPlayers();
            Player player = players.get(threadNumber);
            Ship ship = player.getShip();

            System.out.println("Thread Stardust stared for ship " + ship.color + " using thread " + threadNumber);

            ship.setTravelDays(- ship.getExposedConnectors());

            System.out.println("Thread Stardust ended for ship " + ship.color + " using thread " + threadNumber);
        }
    }
}
