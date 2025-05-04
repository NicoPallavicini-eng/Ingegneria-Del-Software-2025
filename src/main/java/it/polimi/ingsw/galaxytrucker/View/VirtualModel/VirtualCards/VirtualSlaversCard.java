package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

public class VirtualSlaversCard extends VirtualCard {
    private final int firepower;
    private final int credits;
    private final int crewLost;
    private final int daysToLose;

    public VirtualSlaversCard(boolean levelTwo, boolean used, int firepower, int credits, int crewLost, int daysToLose) {
        super(levelTwo, used);
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
}
