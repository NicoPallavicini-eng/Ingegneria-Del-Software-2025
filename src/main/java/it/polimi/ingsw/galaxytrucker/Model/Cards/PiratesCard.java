package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.io.Serializable;
import java.util.List;

public class PiratesCard extends Card implements Serializable {
    private final int firepower;
    private final int credits;
    private final int daysToLose;
    private final List <Cannonball> cannonballList;

    public PiratesCard(boolean levelTwo, boolean used, int firepower, int credits, int daysToLose, List <Cannonball> cannonballList) {
        super(levelTwo, used);
        this.firepower = firepower;
        this.credits = credits;
        this.daysToLose = daysToLose;
        this.cannonballList = cannonballList;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysToLose() {
        return daysToLose;
    }

    public List <Cannonball> getCannonballList() {
        return cannonballList;
    }

/*    public void process(Player player, SequentialTravellingState state) {
        Ship ship = player.getShip();

        if (ship.getFirepower() < firepower) {
            for (Cannonball cannonball : cannonballList) {
                cannonball.getHit(ship);
            }
        } else if (ship.getFirepower() > firepower) {
            state.setAccomplished(true);

            if (player.getEngages()) {
                ship.setCredits(ship.getCredits() + credits);
                ship.setTravelDays(ship.getTravelDays() - daysToLose);
            }
        }
    }

 */
}
