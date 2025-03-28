package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class SlaversCardVisitor extends CardVisitor {

    public void visitSlaversCard() {}

    public void handleSlaversCard(TravellingState state, SlaversCard slaversCard, Player player) {
        slaversCard.process(state.getAccomplished());
    }
}
