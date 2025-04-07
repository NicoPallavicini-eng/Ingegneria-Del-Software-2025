package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.StationCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class StationCard extends Card {
    private final int crewNumberNeeded;
    private final List <Integer> blocks; // Integer
    private final int daysToLose;

    public StationCard(boolean levelTwo, boolean used, StationCardVisitor visitor, int crewNumberNeeded, List <Integer> blocks, int daysToLose) {
        super(levelTwo, used, visitor);
        this.crewNumberNeeded = crewNumberNeeded;
        this.blocks = blocks;
        this.daysToLose = daysToLose;
    }

    public List <Integer> getBlockList() {
        return blocks;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getCrewNumberNeeded() {
        return crewNumberNeeded;
    }

    public void acceptCardVisitorSequential(SequentialTravellingState state, StationCardVisitor visitor, List <Player> players) {
        for (Player player : players) {
            visitor.handleStationCard(state,this, player);
            if (state.getAccomplished()) {
                break;
            }
        }
    }

    public void acceptNextVisitor(GameState state, StationCardVisitor visitor, Game game) {
        visitor.setNextStateStationCard(state, game, this);
    }

    public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if ((ship.getNumberOfCrewMembers() >= crewNumberNeeded) && player.getEngages()) {
            state.setAccomplished(true);

            ship.addBlocks(blocks);
            ship.setTravelDays(ship.getTravelDays() - daysToLose);

        }
    }
}
