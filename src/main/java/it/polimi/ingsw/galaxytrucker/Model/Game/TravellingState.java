package it.polimi.ingsw.galaxytrucker.Model.Game;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Card;

public class TravellingState implements GameState {
    private Game game;
    private Card currentCard;


    public TravellingState( Game game ) {
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
