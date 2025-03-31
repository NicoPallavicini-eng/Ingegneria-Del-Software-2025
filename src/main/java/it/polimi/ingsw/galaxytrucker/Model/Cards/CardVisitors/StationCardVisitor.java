package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.StationCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class StationCardVisitor extends CardVisitor {
    public void handleStationCard(SequentialTravellingState state, StationCard stationCard, Player player) {
        stationCard.process(player, state);
    }

    public void setNextStateStationCard(GameState state, Game game, StationCard card) {
        GameState nextState = new SequentialTravellingState(game, card);
        state.setNextState(nextState);
    }
}
