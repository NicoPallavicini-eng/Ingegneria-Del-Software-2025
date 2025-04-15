package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

public class CombatZoneState extends TravellingState{

    public CombatZoneState(Game game, CombatZoneCard card) {
        super(game, card);
    }
}
