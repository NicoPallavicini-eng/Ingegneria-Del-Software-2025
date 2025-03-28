package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteorsCard extends Card {
    private final List <Meteor> meteors;

    public MeteorsCard(boolean levelTwo, boolean used, List <Meteor> meteors, MeteorsCardVisitor visitor) {
        super(levelTwo, used, visitor);
        this.meteors = meteors;
    }

    public List <Meteor> getMeteorsList() {
        return meteors;
    }

    public void acceptCardVisitor(MeteorsCardVisitor visitor) {
        visitor.handleMeteorsCard(this);
    }

    @Override
    public void process() {
        List <Player> players = Game.getListOfPlayers();

        ExecutorService executor = Executors.newFixedThreadPool(players.size());

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new MeteorsTask(ship, meteors));
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class MeteorsTask implements Runnable {
        private final Ship ship;
        private final List <Meteor> meteors;

        public MeteorsTask(Ship ship, List <Meteor> meteors) {
            this.ship = ship;
            this.meteors = meteors;
        }

        public void run() {
            System.out.println("Thread Meteors started for ship " + ship.getColor());

            for (Meteor meteor : meteors) {
                meteor.getHit(ship);
            }

            System.out.println("Thread Meteors ended for ship " + ship.getColor());
        }
    }
}
