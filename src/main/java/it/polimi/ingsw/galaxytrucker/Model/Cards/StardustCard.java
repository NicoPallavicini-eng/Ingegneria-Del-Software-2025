package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StardustCard extends Card {
    public StardustCard(boolean levelTwo, boolean used, StardustCardVisitor visitor) {
        super(levelTwo, used, visitor);
    }

    public void acceptCardVisitor(StardustCardVisitor visitor, Player player) {
        visitor.handleStardustCard(this, player);
    }

    @Override
    public void process() {
        List <Player> players = Game.getListOfPlayers();

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
            System.out.println("Thread Stardust started for ship " + ship.getColor());

            ship.setTravelDays(ship.getTravelDays() - ship.getExposedConnectors());

            System.out.println("Thread Stardust ended for ship " + ship.getColor());
        }
    }
}
