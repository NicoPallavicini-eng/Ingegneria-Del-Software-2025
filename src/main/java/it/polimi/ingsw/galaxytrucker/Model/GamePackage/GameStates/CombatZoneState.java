package it.polimi.ingsw.galaxytrucker.Model.GamePackage.GameStates;

import it.polimi.ingsw.galaxytrucker.Model.Cards.CombatZoneCard;
import it.polimi.ingsw.galaxytrucker.Model.GamePackage.Game;

import java.io.Serializable;

//todo understand
public class CombatZoneState extends TravellingState implements Serializable {

    public CombatZoneState(Game game, CombatZoneCard card) {
        super(game, card);
    }
}
