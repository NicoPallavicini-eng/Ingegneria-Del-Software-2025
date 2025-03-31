package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.Player;

// Handles Planets Card
public class MultipleTravellingState implements GameState {
    private final Game game;
    private final Card currentCard;
    private Card nextCard;
    private boolean enginePowerChosen = false;
    private boolean accomplished = false;
    private int landed = 0;
    private GameState nextState;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public MultipleTravellingState(Game game, Card currentCard) {
        this.game = game;
        this.currentCard = currentCard;
    }

    public GameState getNextState() {
        return nextState;
    }

    public void setNextState(GameState nextState) {
        this.nextState = nextState;
    }

    public boolean getEnginePowerChosen() {
        return enginePowerChosen;
    }

    public void setEnginePowerChosen(boolean enginePowerChosen) {
        this.enginePowerChosen = enginePowerChosen;
    }

    public boolean getAccomplished() {
        return accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }

    public int getLanded() {
        return landed;
    }

    public void setLanded(int landed) {
        this.landed = landed;
    }

    @Override
    public GameState next() {
        nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            return new FinalState(game);
        } else {
            nextCard.acceptNextVisitor(this, nextCard.getCardVisitor(), game, nextCard);
            return nextState;
        }
    }

    public void process() {
        for (Player player : game.getListOfPlayers()) {
            currentCard.acceptCardVisitorMultiple(this, currentCard.getCardVisitor(), player);
        }
    }

}