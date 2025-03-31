package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.Game.ParallelTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Player;

public class CombatZoneCardVisitor extends CardVisitor {
    public void handleCombatZoneCard(CombatZoneCard combatZoneCard, Player player) {
        combatZoneCard.process();
    }

    public void setNextStateCombatZoneCard(GameState state, Game game, CombatZoneCard card) {
        GameState nextState = new ParallelTravellingState(game, card);
        state.setNextState(nextState);
    }
}
