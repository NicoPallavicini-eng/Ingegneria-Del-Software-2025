package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameEvents.GameEvent;

public class BuildingState extends GameState {
    private final Game game;

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
            getGame().setGameState(TravellingStateFactory.createGameState(game, nextCard));
        }
        game.getGameState().init();
    }

    public void handleEvent() {}
}
