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
    }

    public void acceptCardVisitor(EpidemicCardVisitor visitor) {
        visitor.handleEpidemicCard(this);
    }

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new EpidemicTask(ship));
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
        private final Ship ship;

        public EpidemicTask(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread Epidemic started for ship " + ship.color);

            // TODO logic

            System.out.println("Thread Epidemic ended for ship " + ship.color);
        }
    }
}
