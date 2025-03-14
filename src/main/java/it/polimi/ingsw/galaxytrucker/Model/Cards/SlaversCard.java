package it.polimi.ingsw.galaxytrucker.Model.Cards;

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

    @Override
    public void process() {
        super.process();

        // foreach player until slavers_defeated {
            // if player.firepower < slavers.firepower {
                // loseCrew();
            // } else if player.firepower > slavers.firepower {
                // slavers_defeated
                // if players_wants_credits {
                    // getCredits && loseDays
                // }
            // }
        // }
    }
}
