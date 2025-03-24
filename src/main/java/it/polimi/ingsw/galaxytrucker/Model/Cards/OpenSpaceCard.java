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
            executor.execute(new OpenSpaceCard.OpenSpaceTask1(ship));
        }

        // Travel days get updated in reverse order
        List <Player> reversed = players.reversed();

        for (Player player : reversed) {
            Ship ship = player.getShip();
            executor.execute(new OpenSpaceCard.OpenSpaceTask2(ship));
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class OpenSpaceTask1 implements Runnable {
        private final Ship ship;

        public OpenSpaceTask1(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread OpenSpace1 started for ship " + ship.color);

            ship.setEnginePower(getPlayerInput()); // TODO implement
            if (ship.getEnginePower() == 0) {
                // If a player has zero engine power he is lost in space and out of further travelling
                ship.setTravelDays(null);
            }

            System.out.println("Thread OpenSpace1 ended for ship " + ship.color);
        }
    }

    static class OpenSpaceTask2 implements Runnable {
        private final Ship ship;

        public OpenSpaceTask2(Ship ship) {
            this.ship = ship;
        }

        public void run() {
            System.out.println("Thread OpenSpace2 started for ship " + ship.color);

            ship.setTravelDays(ship.getTravelDays() + ship.getEnginePower());

            System.out.println("Thread OpenSpace2 ended for ship " + ship.color);
        }
    }
}
