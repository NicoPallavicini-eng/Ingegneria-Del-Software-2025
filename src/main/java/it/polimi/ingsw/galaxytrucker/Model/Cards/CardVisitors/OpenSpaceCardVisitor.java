package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.InteractiveTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Game.ParallelTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class OpenSpaceCardVisitor extends CardVisitor {
    public void handleOpenSpaceCard(ParallelTravellingState state, OpenSpaceCard openSpaceCard, Player player) {
        openSpaceCard.process(state.getAccomplished());
    }

    public void setNextStateOpenSpaceCard(GameState state, Game game, OpenSpaceCard card) {
        GameState nextState = new InteractiveTravellingState(game, card);
        state.setNextState(nextState);
    }
}
