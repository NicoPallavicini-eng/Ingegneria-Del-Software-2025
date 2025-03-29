package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public abstract class Card {
    private final boolean levelTwo;
    private final CardVisitor visitor;
    private boolean used;

    public Card(boolean levelTwo, boolean used, CardVisitor visitor) {
        this.levelTwo = levelTwo;
        this.used = false;
        this.visitor = visitor;
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

    public void process () {}

    public void acceptCardVisitor(ParallelTravellingState state, CardVisitor visitor, Player player) {}

    public CardVisitor getCardVisitor() {
        return visitor;
    }
}
