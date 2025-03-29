package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

// Handles Planets Card
public class MultipleTravellingState implements GameState {
    private final Game game;
    private final Card currentCard;
    private boolean enginePowerChosen = false;
    private boolean accomplished = false;
    private int landed = 0;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public MultipleTravellingState(Game game) {
        this.game = game;
        currentCard = getGame().getDeck().drawCard();
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
        return new FinalState(game);
    }

    public void process() {
        for (Player player : game.getListOfPlayers()) {
            // TODO add specific visitor
            currentCard.acceptCardVisitor(this, currentCard.getCardVisitor(), player);
        }
    }

}