package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.*;
import it.polimi.ingsw.galaxytrucker.Model.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.galaxytrucker.Model.Tiles.CabinInhabitants.*;

public class EpidemicCard extends Card {
    public EpidemicCard(boolean levelTwo, boolean used, EpidemicCardVisitor visitor) {
        super(levelTwo, used, visitor);
    }

    public void acceptCardVisitor(EpidemicCardVisitor visitor, Player player) {
        visitor.handleEpidemicCard(this, player);
    }

    @Override
    public void process() {
        List <Player> players = Game.getListOfPlayers();

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
            System.out.println("Thread Epidemic started for ship " + ship.getColor());

            List <CabinTile> cabins = ship.getListOfCabin();
            List <CabinTile> visited = new ArrayList<>();

            // Process each cabin
            for (CabinTile tile : cabins) {
                if (visited.contains(tile)) continue; // Skip already visited cabins

                visited.add(tile);
                List <Tile> adjacentTiles = ship.getAdiacentTiles(tile);
                List <CabinTile> adjacentCabins = new ArrayList<>();

                // Find adjacent cabins
                for (Tile adjacent : adjacentTiles) {
                    if (cabins.contains(adjacent)) {
                        adjacentCabins.add((CabinTile) adjacent);
                    }
                }

                // Process adjacent cabins
                for (CabinTile adjacentCabin : adjacentCabins) {
                    if (visited.contains(adjacentCabin)) continue; // Skip already visited cabins

                    visited.add(adjacentCabin);
                    updateInhabitants(adjacentCabin);
                    updateInhabitants(tile);
                }
            }

            System.out.println("Thread Epidemic ended for ship " + ship.getColor());
        }

        // Helper method to update inhabitants
        private void updateInhabitants(CabinTile cabin) {
            if (cabin.getInhabitants() == ONE) {
                cabin.updateInhabitants(NONE);
            } else if (cabin.getInhabitants() == TWO) {
                cabin.updateInhabitants(ONE);
            } else if (cabin.getInhabitants() == ALIEN) {
                cabin.updateInhabitants(NONE);
            }
        }
    }
}
