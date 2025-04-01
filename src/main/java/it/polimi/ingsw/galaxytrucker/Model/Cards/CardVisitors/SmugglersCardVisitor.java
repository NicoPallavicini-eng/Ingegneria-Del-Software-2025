package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.SmugglersCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

public class SmugglersCardVisitor extends CardVisitor {
    public void handleSmugglersCard(SequentialTravellingState state, SmugglersCard smugglersCard, Player player) {
        smugglersCard.process(player, state);
    }

    public void setNextStateSmugglersCard(GameState state, Game game, SmugglersCard card) {
        GameState nextState = new SequentialTravellingState(game, card);
        state.setNextState(nextState);
    }
}
