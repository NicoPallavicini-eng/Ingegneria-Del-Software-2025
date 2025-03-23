package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.SlaversCardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

import java.util.List;

public class SlaversCard extends Card {
    private final int firepower;
    private final int credits;
    private final int crewLost;
    private final int daysToLose;

    public SlaversCard(boolean levelTwo, boolean used, int firepower, int credits, int crewLost, int daysToLose) {
        super(levelTwo, used);
        this.category = CardCategory.SLAVERS;
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

    public void acceptCardVisitor(SlaversCardVisitor visitor) {
        visitor.handleSlaversCard(this);
    }

    @Override
    public void process() {
        super.process();
        boolean defeated = false;

        List <Player> players = getListOfPlayers();

        for (Player player : players) {
            Ship ship = player.getShip();
            if (ship.getFirepower() < firepower) {
                ship.removeCrewMembers(crewLost);
            } else if (ship.getFirepower() > firepower) {
                defeated = true;
                if (player.playerEngages) {
                    ship.addCredits(credits);
                    ship.setTravelDays(- daysToLose); // negative because deducting
                }
            }
            if (defeated) {
                break;
            }
        }
    }
}
