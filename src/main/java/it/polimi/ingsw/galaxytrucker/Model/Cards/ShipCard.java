package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.ShipCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

public class ShipCard extends Card {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;

    public ShipCard(boolean levelTwo, boolean used, int crewNumberLost, int credits, int daysToLose) {
        super(levelTwo, used);
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

    public void acceptCardVisitor(ShipCardVisitor visitor) {
        visitor.handleShipCard(this);
    }

    @Override
    public void process() {
        List <Player> players = getListOfPlayers();

        for (Player player : players) {
            if (player.playerEngages) {
                Ship ship = player.getShip();
                ship.addCredits(credits);
                ship.removeCrewMembers(crewNumberLost);
                ship.setTravelDays(- daysToLose); // negative because deducting
                break;
            }
        }
    }
}
