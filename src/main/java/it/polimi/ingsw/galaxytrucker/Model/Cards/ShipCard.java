package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.ShipCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class ShipCard extends Card {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;

    public ShipCard(boolean levelTwo, boolean used, ShipCardVisitor visitor, int crewNumberLost, int credits, int daysToLose) {
        super(levelTwo, used, visitor);
        this.crewNumberLost = crewNumberLost;
        this.credits = credits;
        this.daysToLose = daysToLose;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public int getCrewNumberLost() {
        return crewNumberLost;
    }

    public void acceptCardVisitorSequential(SequentialTravellingState state, ShipCardVisitor visitor, List <Player> players) {
        for (Player player : players) {
            visitor.handleShipCard(state,this, player);
            if (state.getAccomplished()) {
                break;
            }
        }
    }

    public void acceptNextVisitor(GameState state, ShipCardVisitor visitor, Game game) {
        visitor.setNextStateShipCard(state, game, this);
    }

    public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if ((ship.getNumberOfCrewMembers() >= crewNumberLost) && player.getEngages()) {
            state.setAccomplished(true);

            ship.setCredits(ship.getCredits() + credits);
            ship.setCrewMembers(ship.getNumberOfCrewMembers() - crewNumberLost);
            ship.setTravelDays(ship.getTravelDays() - daysToLose);
        }
    }
}
