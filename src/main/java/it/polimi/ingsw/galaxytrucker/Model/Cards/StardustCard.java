package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.StardustCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StardustCard extends Card {
    public StardustCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
    }

    public void acceptCardVisitor(StardustCardVisitor visitor) {
        visitor.handleStardustCard(this);
    }

    @Override
    public void process() {
        List<Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new StardustCard.StardustTask(ship));
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class StardustTask implements Runnable {
        private final Ship ship;

        public StardustTask(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread Stardust started for ship " + ship.color);

            ship.setTravelDays(- ship.getExposedConnectors());

            System.out.println("Thread Stardust ended for ship " + ship.color);
        }
    }
}
