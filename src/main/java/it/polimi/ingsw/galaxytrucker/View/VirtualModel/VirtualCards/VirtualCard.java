package it.polimi.ingsw.galaxytrucker.View.VirtualModel.VirtualCards;

public abstract class VirtualCard {
    private final boolean levelTwo;
    private boolean used;

    public VirtualCard(boolean levelTwo, boolean used) {
        this.levelTwo = levelTwo;
        this.used = false;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isLevelTwo() {
        return levelTwo;
    }
}
