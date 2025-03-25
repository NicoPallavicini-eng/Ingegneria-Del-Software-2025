package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;

public class BuildingState implements GameState {
    private Card currentCard;

    public GameState next() {
        return new TravellingState();
    }
}
