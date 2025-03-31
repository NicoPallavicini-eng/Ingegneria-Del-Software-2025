package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.Player;

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

    public void acceptCardVisitorParallel(ParallelTravellingState state, CardVisitor visitor, Player player) {}

    public void acceptCardVisitorInteractive(InteractiveTravellingState state, CardVisitor visitor, Player player) {}

    public void acceptCardVisitorMultiple(MultipleTravellingState state, CardVisitor visitor, Player player) {}

    public void acceptCardVisitorSequential(SequentialTravellingState state, CardVisitor visitor, Player player) {}

    public void acceptNextVisitor(GameState state, CardVisitor visitor, Game game, Card nextCard) {}

    public CardVisitor getCardVisitor() {
        return visitor;
    }
}
