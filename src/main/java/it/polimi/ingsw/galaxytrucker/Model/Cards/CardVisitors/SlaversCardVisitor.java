package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SlaversCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class SlaversCardVisitor extends CardVisitor {
    public void handleSlaversCard(SequentialTravellingState state, SlaversCard slaversCard, Player player) {
        slaversCard.process(player, state);
    }

    public void setNextStateSlaversCard(GameState state, Game game, SlaversCard card) {
        GameState nextState = new SequentialTravellingState(game, card);
        state.setNextState(nextState);
    }
}
