package it.polimi.ingsw.galaxytrucker.Model.Cards.CardVisitors;
import it.polimi.ingsw.galaxytrucker.Model.Cards.*;
import it.polimi.ingsw.galaxytrucker.Model.Game.*;
import it.polimi.ingsw.galaxytrucker.Model.*;

public class CombatZoneCardVisitor extends CardVisitor {

    public void visitCombatZoneCard() {}

    public void handleCombatZoneCard(CombatZoneCard combatZoneCard, Player player) {
        combatZoneCard.process();
    }
}
