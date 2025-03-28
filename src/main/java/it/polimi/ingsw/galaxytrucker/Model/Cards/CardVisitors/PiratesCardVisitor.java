package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

public class PiratesCardVisitor extends CardVisitor {

    public void visitPiratesCard() {}

    public void handlePiratesCard(TravellingState state, PiratesCard piratesCard) {
        piratesCard.process(state.getAccomplished());
    }
}
