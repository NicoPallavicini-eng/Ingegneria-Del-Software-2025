package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class SmugglersCardVisitor extends CardVisitor {

    public void visitSmugglersCard() {}

    public void handleSmugglersCard(ParallelTravellingState state, SmugglersCard smugglersCard, Player player) {
        smugglersCard.process(state.getAccomplished());
    }
}
