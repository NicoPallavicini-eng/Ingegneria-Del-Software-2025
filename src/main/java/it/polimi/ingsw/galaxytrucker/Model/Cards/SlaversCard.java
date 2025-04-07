package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.SlaversCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

public class SlaversCard extends Card {
    private final int firepower;
    private final int credits;
    private final int crewLost;
    private final int daysToLose;

    public SlaversCard(boolean levelTwo, boolean used, SlaversCardVisitor visitor, int firepower, int credits, int crewLost, int daysToLose) {
        super(levelTwo, used, visitor);
        this.firepower = firepower;
        this.credits = credits;
        this.crewLost = crewLost;
        this.daysToLose = daysToLose;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getNumberOfCredits() {
        return credits;
    }

    public int getNumberOfCrewLost() {
        return crewLost;
    }

    public int getNumberOfDaysToLose() {
        return daysToLose;
    }

    public void acceptCardVisitorSequential(SequentialTravellingState state, SlaversCardVisitor visitor, List <Player> players) {
        for (Player player : players) {
            visitor.handleSlaversCard(state, this, player);
            if (state.getAccomplished()) {
                break;
            }
        }
    }

    public void acceptNextVisitor(GameState state, SlaversCardVisitor visitor, Game game) {
        visitor.setNextStateSlaversCard(state, game, this);
    }

    public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if (ship.getFirepower() < firepower) {
            ship.setCrewMembers(ship.getNumberOfCrewMembers() - crewLost);
        } else if (ship.getFirepower() > firepower) {
            state.setAccomplished(true);

            if (player.getEngages()) {
                ship.setCredits(ship.getCredits() + credits);
                ship.setTravelDays(ship.getTravelDays() - daysToLose);
            }
        }
    }
}
