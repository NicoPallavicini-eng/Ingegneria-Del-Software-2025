package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.SmugglersCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.CargoTile;

import java.util.ArrayList;
import java.util.List;

public class SmugglersCard extends Card {
    private final int firepower;
    private final List <Integer> blocks;
    private final int lostBlocksNumber;
    private final int daysToLose;
    private boolean defeated = false;
    private boolean goNext;

    public SmugglersCard(boolean levelTwo, boolean used, SmugglersCardVisitor visitor, int firepower, List <Integer> blocks, int lostBlocksNumber, int daysToLose) {
        super(levelTwo, used, visitor);
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

    public void acceptCardVisitorSequential(SequentialTravellingState state, SmugglersCardVisitor visitor, List <Player> players) {
        for (Player player : players) {
            visitor.handleSmugglersCard(state, this, player);
            if (state.getAccomplished()) {
                break;
            }
        }
    }

    public void acceptNextVisitor(GameState state, SmugglersCardVisitor visitor, Game game, Card card) {
        visitor.setNextStateSmugglersCard(state, game, this);
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public boolean getDefeated() {
        return defeated;
    }

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public boolean getGoNext() {
        return goNext;
    }

    public void process(Player player, SequentialTravellingState state) {
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

            if (player.playerEngages) {
                ship.addBlocks(blocks);
                ship.setTravelDays(ship.getTravelDays() - daysToLose);
            }
        }
    }
}
