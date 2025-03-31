package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.PiratesCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.ParallelTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class PiratesCardVisitor extends CardVisitor {
    public void handlePiratesCard(ParallelTravellingState state, PiratesCard piratesCard, Player player) {
        piratesCard.process(state.getAccomplished());
    }

    public void setNextStatePiratesCard(GameState state, Game game, PiratesCard card) {
        GameState nextState = new SequentialTravellingState(game, card);
        state.setNextState(nextState);
    }
}
