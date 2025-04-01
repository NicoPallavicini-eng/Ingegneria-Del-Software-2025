package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StardustCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.ParallelTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

public class StardustCardVisitor extends CardVisitor {
    public void handleStardustCard(StardustCard stardustCard, Ship ship) {
        stardustCard.process(ship);
    }

    public void setNextStateStardustCard(GameState state, Game game, StardustCard card) {
        GameState nextState = new ParallelTravellingState(game, card);
        state.setNextState(nextState);
    }
}
