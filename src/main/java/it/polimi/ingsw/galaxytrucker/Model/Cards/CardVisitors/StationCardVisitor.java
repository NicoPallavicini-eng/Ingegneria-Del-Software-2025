package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class StationCardVisitor extends CardVisitor {

    public void visitStationCard() {}

    public void handleStationCard(ParallelTravellingState state, StationCard stationCard, Player player) {
        stationCard.process(state.getAccomplished());
    }
}
