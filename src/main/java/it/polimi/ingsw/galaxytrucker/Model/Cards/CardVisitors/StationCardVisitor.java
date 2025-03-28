package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.TravellingState;

public class StationCardVisitor extends CardVisitor {

    public void visitStationCard() {}

    public void handleStationCard(TravellingState state, StationCard stationCard) {
        stationCard.process(state.getAccomplished());
    }
}
