package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

public class OpenSpaceCardVisitor extends CardVisitor {

    public void visitOpenSpaceCard() {}

    public void handleOpenSpaceCard(TravellingState state, OpenSpaceCard openSpaceCard) {
        openSpaceCard.process(state.getAccomplished());
    }
}
