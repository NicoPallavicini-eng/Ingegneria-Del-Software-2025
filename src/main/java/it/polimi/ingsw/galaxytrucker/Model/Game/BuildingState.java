package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;

public class BuildingState implements GameState {
    private Card currentCard;
    private Game game;

    public BuildingState( Game game ) {
        this.game = game;
    }

    public GameState next() {
        return new TravellingState();
    }
}
