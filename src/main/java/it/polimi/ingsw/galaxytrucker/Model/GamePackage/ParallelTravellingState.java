package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.FinalState;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates.GameState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

import java.util.ArrayList;
import java.util.List;

// Handles CombatZone, Epidemic, Meteors, Stardust Cards
public class ParallelTravellingState implements GameState {
    private final Game game;
    private final Card currentCard;
    private Card nextCard;
    private GameState nextState;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public ParallelTravellingState(Game game, Card currentCard) {
        this.game = game;
        this.currentCard = currentCard;
    }

    public GameState getNextState() {
        return nextState;
    }

    public void setNextState(GameState nextState) {
        this.nextState = nextState;
    }

    @Override
    public GameState next() {
        nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            return new FinalState(game);
        } else {
            nextCard.acceptNextVisitor(this, nextCard.getCardVisitor(), game);
            return nextState;
        }
    }

    public void process() {
        List <Ship> ships = new ArrayList<>();
        for (Player player : game.getListOfPlayers()) {
            ships.add(player.getShip());
        }

        currentCard.acceptCardVisitorParallel(this, currentCard.getCardVisitor(), ships);
    }

}
