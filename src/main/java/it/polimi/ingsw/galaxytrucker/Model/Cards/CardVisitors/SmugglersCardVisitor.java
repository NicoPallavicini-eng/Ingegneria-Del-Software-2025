package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.ParallelTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class SmugglersCardVisitor extends CardVisitor {
    public void handleSmugglersCard(ParallelTravellingState state, SmugglersCard smugglersCard, Player player) {
        smugglersCard.process(state.getAccomplished());
    }

    public void setNextStateSmugglersCard(GameState state, Game game, SmugglersCard card) {
        GameState nextState = new SequentialTravellingState(game, card);
        state.setNextState(nextState);
    }
}
