package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.MeteorsCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.ParallelTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class MeteorsCardVisitor extends CardVisitor {
    public void handleMeteorsCard(MeteorsCard meteorsCard, Player player) {
        meteorsCard.process();
    }

    public void setNextStateMeteorsCard(GameState state, Game game, MeteorsCard card) {
        GameState nextState = new ParallelTravellingState(game, card);
        state.setNextState(nextState);
    }
}
