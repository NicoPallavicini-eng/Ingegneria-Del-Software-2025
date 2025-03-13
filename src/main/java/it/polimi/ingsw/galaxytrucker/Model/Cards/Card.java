package it.polimi.ingsw.galaxytrucker.Model.Cards;

public abstract class Card {
    private final boolean levelTwo;
    protected CardCategory category;
    private boolean used;

    public Card(boolean levelTwo, boolean used) {
        this.levelTwo = levelTwo;
        this.used = false;
    }

    public boolean isUsed() {
        return used;
    }

    public void updateUsed(boolean used) {
        this.used = used;
    }

    public boolean isLevelTwo() {
        return levelTwo;
    }

    public void process () {
        System.out.println(this.category + " CARD");
    }
}
