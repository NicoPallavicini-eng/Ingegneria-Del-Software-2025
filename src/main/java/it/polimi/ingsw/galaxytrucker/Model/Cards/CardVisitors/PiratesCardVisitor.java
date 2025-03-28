package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class PiratesCardVisitor extends CardVisitor {

    public void visitPiratesCard() {}

    public void handlePiratesCard(TravellingState state, PiratesCard piratesCard, Player player) {
        piratesCard.process(state.getAccomplished());
    }
}
