package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.*;

import java.util.ArrayList;
import java.util.List;

public class TravellingState implements GameState {
    private final Game game;
    private final Card currentCard;
    private boolean enginePowerChosen = false;
    private boolean accomplished = false;
    private List <Boolean> landed = new ArrayList <Boolean>();

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public TravellingState(Game game) {
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

    public List <Boolean> getLanded() {
        return landed;
    }

    public void setLanded(List <Boolean> landed) {
        this.landed = landed;
    }

    @Override
    public GameState next() {
        return new FinalState(game);
    }

    public void process() {
        currentCard.acceptCardVisitor(this, currentCard.getCardVisitor());
    }

}
