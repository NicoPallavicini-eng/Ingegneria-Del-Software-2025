package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

public class VirtualShipCard extends VirtualCard {
    private final int crewNumberLost;
    private final int credits;
    private final int daysToLose;

    public VirtualShipCard(boolean levelTwo, boolean used, int crewNumberLost, int credits, int daysToLose) {
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
}
