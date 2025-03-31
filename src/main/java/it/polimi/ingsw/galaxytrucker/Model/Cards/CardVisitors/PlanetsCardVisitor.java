package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.MultipleTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class PlanetsCardVisitor extends CardVisitor {
    public void handlePlanetsCard(MultipleTravellingState state, PlanetsCard planetsCard, Player player) {
        planetsCard.process(state.getLanded());
    }

    public void setNextStatePlanetsCard(GameState state, Game game, PlanetsCard card) {
        GameState nextState = new MultipleTravellingState(game, card);
        state.setNextState(nextState);
    }
}
