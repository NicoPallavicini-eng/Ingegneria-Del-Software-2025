package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class PlanetsCardVisitor extends CardVisitor {

    public void visitPlanetsCard() {}

    public void handlePlanetsCard(TravellingState state, PlanetsCard planetsCard, Player player) {
        planetsCard.process(state.getLanded());
    }
}
