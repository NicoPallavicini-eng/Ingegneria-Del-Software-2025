package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class OpenSpaceCardVisitor extends CardVisitor {

    public void visitOpenSpaceCard() {}

    public void handleOpenSpaceCard(TravellingState state, OpenSpaceCard openSpaceCard, Player player) {
        openSpaceCard.process(state.getAccomplished());
    }
}
