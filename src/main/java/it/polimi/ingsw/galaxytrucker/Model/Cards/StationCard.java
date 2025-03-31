package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.StationCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

public class StationCard extends Card {
    private final int crewNumberNeeded;
    private final List <Integer> blocks; // Integer
    private final int daysToLose;
    private boolean landed = false;
    private boolean goNext;

    public StationCard(boolean levelTwo, boolean used, StationCardVisitor visitor, int crewNumberNeeded, List <Integer> blocks, int daysToLose) {
        super(levelTwo, used, visitor);
        this.crewNumberNeeded = crewNumberNeeded;
        this.blocks = blocks;
        this.daysToLose = daysToLose;
    }

    public List<Integer> getBlockList() {
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

    public void acceptNextVisitor(GameState state, StationCardVisitor visitor, Game game, Card card) {
        visitor.setNextStateStationCard(state, game, this);
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
    }

    public boolean getLanded() {
        return landed;
    }

    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    public boolean getGoNext() {
        return goNext;
    }

    public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if ((ship.getNumberOfCrewMembers() >= crewNumberNeeded) && player.playerEngages) {
            state.setAccomplished(true);

            ship.addBlocks(blocks);
            ship.setTravelDays(ship.getTravelDays() - daysToLose);

        }
    }
}
