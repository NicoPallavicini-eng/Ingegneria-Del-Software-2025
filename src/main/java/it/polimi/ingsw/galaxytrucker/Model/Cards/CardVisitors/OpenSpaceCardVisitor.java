package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.OpenSpaceCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.ArrayList;
import java.util.List;

public class OpenSpaceCardVisitor extends CardVisitor {
    public void handleOpenSpaceCard(OpenSpaceCard openSpaceCard, List <Player> players) {
        List <Integer> engineChosenList = new ArrayList<>();

        for (Player player : players) {
            openSpaceCard.process1(player, engineChosenList);
        }

        List <Player> reversed = players.reversed();

        for (Player player : reversed) {
            openSpaceCard.process2(player, engineChosenList);
        }
    }

    public void setNextStateOpenSpaceCard(GameState state, Game game, OpenSpaceCard card) {
        GameState nextState = new SequentialTravellingState(game, card);
        state.setNextState(nextState);
    }
}
