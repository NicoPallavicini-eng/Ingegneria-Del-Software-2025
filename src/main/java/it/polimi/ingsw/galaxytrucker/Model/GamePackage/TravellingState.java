package it.polimi.ingsw.galaxytrucker.Model.GamePackage;

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

    public TravellingState(Game game, Card currentCard) {
        this.game = game;
        this.currentCard = currentCard;
    }

    @Override
    public void next() {
         Card nextCard = getGame().getDeck().drawCard();
        if (nextCard == null) {
            getGame().setGameState( new FinalState(game));
        } else {
            getGame().setGameState( new TravellingState(game, nextCard));
        }
    }

    public void process() {
        currentCard.acceptCardVisitorSequential(this, currentCard.getCardVisitor(), game.getListOfPlayers());
    }
}