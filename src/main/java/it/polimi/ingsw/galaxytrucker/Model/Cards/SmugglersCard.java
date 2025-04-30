package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.SmugglersCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CargoTile;

import java.util.ArrayList;
import java.util.List;

public class SmugglersCard extends Card {
    private final int firepower;
    private final List <Integer> blocks;
    private final int lostBlocksNumber;
    private final int daysToLose;

    public SmugglersCard(boolean levelTwo, boolean used, int firepower, List <Integer> blocks, int lostBlocksNumber, int daysToLose) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.blocks = blocks;
        this.lostBlocksNumber = lostBlocksNumber;
        this.daysToLose = daysToLose;
    }

    public int getFirepower() {
        return firepower;
    }

    public List <Integer> getBlocksList() {
        return blocks;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getLostBlocksNumber() {
        return lostBlocksNumber;
    }

/*    public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if (ship.getFirepower() < firepower) {
            ArrayList <CargoTile> cargoTiles = ship.getListOfCargo();
            List <Integer> cargo = new ArrayList<>();

            for (CargoTile tile : cargoTiles) {
                cargo.addAll(tile.getTileContent());
            }

            // considering that list is ordered:
            for (int i = 0; i < lostBlocksNumber; i++) {
                cargo.removeFirst();
            }
        } else if (ship.getFirepower() > firepower) {
            state.setAccomplished(true);

            if (player.getEngages()) {
                ship.addBlocks(blocks);
                ship.setTravelDays(ship.getTravelDays() - daysToLose);
            }
        }
    }

 */
}
