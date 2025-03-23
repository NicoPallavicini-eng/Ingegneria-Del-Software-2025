package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.OpenSpaceCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OpenSpaceCard extends Card {
    public OpenSpaceCard(boolean levelTwo, boolean used) {
        super(levelTwo, used);
    }

    public void acceptCardVisitor(OpenSpaceCardVisitor visitor) {
        visitor.handleOpenSpaceCard(this);
    }

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new OpenSpaceCard.OpenSpaceTask(ship));
        }

        // Shut down when all tasks are done
        executor.shutdown();

        ////////////////// TODO move in thread

        for (Player player : players) {
            Ship ship = player.getShip();
            // TODO player input (sets number of engines on)
            if (ship.getEnginePower() == 0) {
                ship.setTravelDays(NULL);
            }
        }

        List <Player> reversed = players.reversed();

        for (Player player : reversed) {
            Ship ship = player.getShip();
            ship.setTravelDays(ship.getEnginePower());
        }
    }

    static class OpenSpaceTask implements Runnable {
        private final Ship ship;

        public OpenSpaceTask(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread OpenSpace started for ship " + ship.color);

            // TODO move logic here

            System.out.println("Thread OpenSpace ended for ship " + ship.color);
        }
    }
}
