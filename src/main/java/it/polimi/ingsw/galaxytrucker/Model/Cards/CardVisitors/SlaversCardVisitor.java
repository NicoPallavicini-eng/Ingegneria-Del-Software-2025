package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class SlaversCardVisitor extends CardVisitor {

    public void visitSlaversCard() {}

    public void handleSlaversCard(ParallelTravellingState state, SlaversCard slaversCard, Player player) {
        slaversCard.process(state.getAccomplished());
    }
}
