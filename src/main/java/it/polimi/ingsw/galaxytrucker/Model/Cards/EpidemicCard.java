package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.EpidemicCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.Tile;

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

            List <Tile> cabins = ship.getListOfCabin();

            List <Tile> visited = null;

            for (Tile tile : cabins) {
                visited.add(tile);
                List <Tile> adjacentTiles = ship.getAdiacentTiles(tile);
                for (Tile adjacent : adjacentTiles) {
                    if (cabins.contains(adjacent) && !visited.contains(adjacent)) {
                        visited.add(adjacent);
                        if (adjacent.getInhabitants() > 0) {
                            adjacent.updateInhabitants(adjacent.getInhabitants() - 1);
                        }
                        if (tile.getInhabitants() > 0) {
                            tile.updateInhabitants(tile.getInhabitants() - 1);
                        }
                    }
                }
            }

            System.out.println("Thread Epidemic ended for ship " + ship.color);
        }
    }
}
