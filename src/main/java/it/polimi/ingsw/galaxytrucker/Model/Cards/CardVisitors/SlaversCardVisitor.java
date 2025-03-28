package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

public class SlaversCardVisitor extends CardVisitor {

    public void visitSlaversCard() {}

    public void handleSlaversCard(TravellingState state, SlaversCard slaversCard) {
        slaversCard.process(state.getAccomplished());
    }
}
