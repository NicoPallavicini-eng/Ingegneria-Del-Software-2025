package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.Game.AlternativeTravellingState;
import it.polimi.ingsw.galaxytrucker.Model.Game.Game;
import it.polimi.ingsw.galaxytrucker.Model.Game.GameState;
import it.polimi.ingsw.galaxytrucker.Model.PlayerShip.Ship;

public class CombatZoneCardVisitor extends CardVisitor {
    public void handleCombatZoneCardLessCrew(CombatZoneCard combatZoneCard, Ship ship) {
        combatZoneCard.lessCrewProcess(ship);
    }

    public void handleCombatZoneCardLessEngine(CombatZoneCard combatZoneCard, Ship ship) {
        combatZoneCard.lessEngineProcess(ship);
    }

    public void handleCombatZoneCardLessFirepower(CombatZoneCard combatZoneCard, Ship ship) {
        combatZoneCard.lessFirepowerProcess(ship);
    }

    public void setNextStateCombatZoneCard(GameState state, Game game, CombatZoneCard card) {
        GameState nextState = new AlternativeTravellingState(game, card);
        state.setNextState(nextState);
    }
}
