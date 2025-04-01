package it.polimi.ingsw.galaxytrucker.Model.Cards;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors.CardVisitor;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.List;

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

    public void acceptCardVisitorParallel(ParallelTravellingState state, CardVisitor visitor, List <Ship> ships) {}

    public void acceptCardVisitorSequential(SequentialTravellingState state, CardVisitor visitor, List <Player> players) {}

    public void acceptCardVisitorAlternative(AlternativeTravellingState state, CardVisitor visitor, List <Ship> lessCrewShips, List <Ship> lessEngineShips, List <Ship> lessFirepowerShips) {}

    public void acceptNextVisitor(GameState state, CardVisitor visitor, Game game, Card nextCard) {}

    public CardVisitor getCardVisitor() {
        return visitor;
    }
}
