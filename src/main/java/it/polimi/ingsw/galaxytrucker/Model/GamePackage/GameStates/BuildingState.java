package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class BuildingState implements GameState {
    private final Game game;
    private GameState nextState;

    public BuildingState( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void next() {
        Card nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            getGame().setGameState(new FinalState(game));
        } else {
            getGame().setGameState(new TravellingState(game, nextCard));
        }
    }

    public void process() {
        // 4 processes in parallel
    }
}
