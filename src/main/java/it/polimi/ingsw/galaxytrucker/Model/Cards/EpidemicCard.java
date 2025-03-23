package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.EpidemicCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EpidemicCard extends Card {
    public EpidemicCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
        this.category = CardCategory.EPIDEMIC;
    }

    public void acceptCardVisitor(EpidemicCardVisitor visitor) {
        visitor.handleEpidemicCard(this);
    }

    @Override
    public void process() {
        super.process();

        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        int threadNumber = 0;
        for (Player player : players) {
            executor.execute(new EpidemicTask(threadNumber));
            threadNumber++;
        }

        // foreach ship {
            // foreach cabin check adjacent tiles {
                // if there is at least another cabin in those {
                    // remove one occupant from each of the connected cabins
                    // only remove one from each cabin
                // }
            // }
        // }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class EpidemicTask implements Runnable {
        private final int threadNumber;

        public EpidemicTask(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        public void run() {
            List <Player> players = getListOfPlayers();
            Player player = players.get(threadNumber);
            Ship ship = player.getShip();

            System.out.println("Thread Epidemic stared for ship " + ship.color + " using thread " + threadNumber);

            // TODO logic

            System.out.println("Thread Epidemic ended for ship " + ship.color + " using thread " + threadNumber);
        }
    }
}
