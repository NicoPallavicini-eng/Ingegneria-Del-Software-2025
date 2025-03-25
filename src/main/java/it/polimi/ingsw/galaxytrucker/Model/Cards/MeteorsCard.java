package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.MeteorsCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Direction;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.COLUMN;
import static it.polimi.ingsw.galaxytrucker.Model.Cards.RowOrColumn.ROW;
import static it.polimi.ingsw.galaxytrucker.Model.Direction.*;
import static it.polimi.ingsw.galaxytrucker.Model.Tiles.ShieldOrientation.*;

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

        for (Player player : players) {
            Ship ship = player.getShip();
            executor.execute(new MeteorsTask(ship, diceRoll, meteors));
        }

        // Shut down when all tasks are done
        executor.shutdown();
    }

    static class MeteorsTask implements Runnable {
        private final Ship ship;
        private final int[] diceRoll;
        private final List <Meteor> meteors;

        public MeteorsTask(Ship ship, int[] diceRoll, List <Meteor> meteors) {
            this.ship = ship;
            this.diceRoll = diceRoll;
            this.meteors = meteors;
        }

        public void run() {
            System.out.println("Thread Meteors started for ship " + ship.getColor());

            int i = 0;

            for (Meteor meteor : meteors) {
                if ((meteor.getRowOrColumn() == ROW && (diceRoll[i] >= 5 && diceRoll[i] <= 9) && ship.getRowListTiles(diceRoll[i]))
                        || (meteor.getRowOrColumn() == COLUMN && (diceRoll[i] >= 4 && diceRoll[i] <= 10) && ship.getColumnListTiles(diceRoll[i]))) {
                    if (meteor.isBigMeteor()) {
                        // getHit(); TODO
                    } else {
                        List <Tile> shields = ship.getListOfShield();
                        boolean hasShield = true;
                        for (Tile shield : shields) {
                            if (shield.getShieldOrientation() == NORTHWEST && meteor.getDirection() != NORTH && meteor.getDirection() != WEST
                                    || shield.getShieldOrientation() == SOUTHWEST && meteor.getDirection() != SOUTH && meteor.getDirection() != WEST
                                    || shield.getShieldOrientation() == SOUTHEAST && meteor.getDirection() != SOUTH && meteor.getDirection() != EAST
                                    || shield.getShieldOrientation() == NORTHEAST && meteor.getDirection() != NORTH && meteor.getDirection() != EAST) {
                                hasShield = false;
                            }
                        }
                        if (meteor.getRowOrColumn() == COLUMN && /* first tile =! cannon && */ !hasShield
                                || meteor.getRowOrColumn() == ROW && /* first tile or adjacent ones =! cannon && */ !hasShield) {
                            // getHit(); TODO
                        }
                    }
                }
                i++;
            }

            System.out.println("Thread Meteors ended for ship " + ship.getColor());
        }
    }
}
