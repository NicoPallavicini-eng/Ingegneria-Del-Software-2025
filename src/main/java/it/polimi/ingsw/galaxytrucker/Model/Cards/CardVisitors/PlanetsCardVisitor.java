package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.Planet;
import it.polimi.ingsw.galaxytrucker.Model.Cards.PlanetsCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.SequentialTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Player;

import java.util.List;

public class PlanetsCardVisitor extends CardVisitor {
    public void handlePlanetsCard(PlanetsCard planetsCard, List <Player> players) {
        for (Player player : players) {
            planetsCard.process(player);

            boolean someLeft = false;
            for (Planet planet : planetsCard.getPlanetsList()) {
                if (planet.getShipLanded() != null) {
                    someLeft = true;
                    break;
                }
            }

            if (!someLeft) {
                break;
            }
        }
    }

    public void setNextStatePlanetsCard(GameState state, Game game, PlanetsCard card) {
        GameState nextState = new SequentialTravellingState(game, card);
        state.setNextState(nextState);
    }
}
