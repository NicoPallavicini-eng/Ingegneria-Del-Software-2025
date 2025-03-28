package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;

public class TravellingState implements GameState {
    private final Game game;
    private final Card currentCard;

    public Game getGame() {
        return game;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public TravellingState(Game game ) {
        this.game = game;
        currentCard = getGame().getDeck().drawCard();
    }

    @Override
    public GameState next() {
        return new FinalState(game);
    }

    public void process() {
        currentCard.process();
    }

}
