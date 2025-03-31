package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StardustCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.ParallelTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;
import it.polimi.ingsw.galaxytrucker.Model.Ship;

public class StardustCardVisitor extends CardVisitor {
    public void handleStardustCard(StardustCard stardustCard, Player player) {
        Ship ship = player.getShip();
        ship.setTravelDays(ship.getTravelDays() - ship.getExposedConnectors());
    }

    public void setNextStateStardustCard(GameState state, Game game, StardustCard card) {
        GameState nextState = new ParallelTravellingState(game, card);
        state.setNextState(nextState);
    }
}
