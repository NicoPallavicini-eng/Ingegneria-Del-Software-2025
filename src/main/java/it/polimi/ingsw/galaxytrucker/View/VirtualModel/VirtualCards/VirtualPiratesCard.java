package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

import java.util.List;

public class VirtualPiratesCard extends VirtualCard {
    private final int firepower;
    private final int credits;
    private final int daysToLose;
    private final List <VirtualCannonball> cannonballList;

    public VirtualPiratesCard(boolean levelTwo, boolean used, int firepower, int credits, int daysToLose, List <VirtualCannonball> cannonballList) {
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

    public List <VirtualCannonball> getCannonballList() {
        return cannonballList;
    }
}
