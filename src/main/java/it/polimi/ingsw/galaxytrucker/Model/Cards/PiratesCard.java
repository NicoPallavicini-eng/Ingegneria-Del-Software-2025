package it.polimi.ingsw.galaxytrucker.Model.Cards;

import java.util.List;

public class PiratesCard extends Card {
    private final int firepower;
    private final int credits;
    private final int daysToLose;
    private final List <Cannonball> cannonballList;

    public PiratesCard(boolean levelTwo, boolean used, int firepower, int credits, int daysToLose, List <Cannonball> cannonballList) {
        super(levelTwo, used);
        this.category = CardCategory.PIRATES;
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

    @Override
    public void process() {
        super.process();

        // foreach player until pirates_defeated {
            // if player.firepower < pirates.firepower {
                // getShot();
            // } else if player.firepower > pirates.firepower {
                // pirates_defeated
                // if players_wants_credits {
                    // getCredits && loseDays
                // }
            // }
        // }
    }
}
