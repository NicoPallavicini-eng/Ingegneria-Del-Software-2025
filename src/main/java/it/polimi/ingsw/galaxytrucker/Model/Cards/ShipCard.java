package it.polimi.ingsw.galaxytrucker.Model.Cards;

public class ShipCard extends Card {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;

    public ShipCard(boolean levelTwo, boolean used, int crewNumberLost, int credits, int daysToLose) {
        super(levelTwo, used);
        this.category = CardCategory.SHIP;
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

    @Override
    public void process() {
        super.process();

        // forall players until ShipLanded {
            // if player wants to land {
                // loseCrew(crewNumberLost); getCredits(credits); loseDays(daysToLose); ShipLanded = true;
            // }
        // }
    }
}
