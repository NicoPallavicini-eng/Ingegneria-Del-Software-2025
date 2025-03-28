package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

public class SmugglersCardVisitor extends CardVisitor {

    public void visitSmugglersCard() {}

    public void handleSmugglersCard(TravellingState state, SmugglersCard smugglersCard) {
        smugglersCard.process(state.getAccomplished());
    }
}
