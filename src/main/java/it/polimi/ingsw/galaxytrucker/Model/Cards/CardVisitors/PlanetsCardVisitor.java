package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

public class PlanetsCardVisitor extends CardVisitor {

    public void visitPlanetsCard() {}

    public void handlePlanetsCard(TravellingState state, PlanetsCard planetsCard) {
        planetsCard.process(state.getLanded());
    }
}
