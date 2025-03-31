package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.EpidemicCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.ParallelTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class EpidemicCardVisitor extends CardVisitor {
    public void handleEpidemicCard(EpidemicCard epidemicCard, Player player) {
        epidemicCard.process();
    }

    public void setNextStateEpidemicCard(GameState state, Game game, EpidemicCard card) {
        GameState nextState = new ParallelTravellingState(game, card);
        state.setNextState(nextState);
    }
}
