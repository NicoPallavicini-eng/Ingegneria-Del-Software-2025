package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OpenSpaceCard extends Card {
    private boolean goNext;

    public OpenSpaceCard(boolean levelTwo, boolean used, OpenSpaceCardVisitor visitor) {
        super(levelTwo, used, visitor);
    }

    public void acceptCardVisitor(ParallelTravellingState state, OpenSpaceCardVisitor visitor, Player player) {
        visitor.handleOpenSpaceCard(state, this, player);
    }

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public boolean getGoNext() {
        return goNext;
    }

    public void process(boolean enginePowerChosen) {
        List <Player> players = Game.getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new OpenSpaceCard.OpenSpaceTask1(player));
        }

        // Travel days get updated in reverse order
        List <Player> reversed = players.reversed();

        for (Player player : reversed) {
            goNext = false;

            Ship ship = player.getShip();
            executor.execute(new OpenSpaceCard.OpenSpaceTask2(ship, this));

            while (!goNext);
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class OpenSpaceTask1 implements Runnable {
        private final Player player;
        private final Ship ship;

        public OpenSpaceTask1(Player player) {
            this.player = player;
            this.ship = player.getShip();
        }

        public void run() {
            System.out.println("Thread OpenSpace1 started for ship " + ship.getColor());

            ship.setEnginePower(player.getPlayerInput()); // TODO implement
            if (ship.getEnginePower() == 0) {
                // If a player has zero engine power he is lost in space and out of further travelling
                ship.setTravelDays(null);
            }

            System.out.println("Thread OpenSpace1 ended for ship " + ship.getColor());
        }
    }

    static class OpenSpaceTask2 implements Runnable {
        private final Ship ship;
        private final OpenSpaceCard card;

        public OpenSpaceTask2(Ship ship, OpenSpaceCard card) {
            this.ship = ship;
            this.card = card;
        }

        public void run() {
            System.out.println("Thread OpenSpace2 started for ship " + ship.getColor());

            // TODO player set engine power (?)
            ship.setTravelDays(ship.getTravelDays() + ship.getEnginePower());
            card.setGoNext(true);

            System.out.println("Thread OpenSpace2 ended for ship " + ship.getColor());
        }
    }
}
