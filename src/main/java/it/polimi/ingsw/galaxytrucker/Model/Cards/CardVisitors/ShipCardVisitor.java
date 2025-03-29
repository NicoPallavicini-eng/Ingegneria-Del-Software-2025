package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class ShipCardVisitor extends CardVisitor {

    public void visitShipCard() {}

    public void handleShipCard(ParallelTravellingState state, ShipCard shipCard, Player player) {
        shipCard.process(state.getAccomplished());
    }
}
